package org.xwiki.rendering.citations;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.component.wiki.WikiComponent;
import org.xwiki.component.wiki.WikiComponentBuilder;
import org.xwiki.component.wiki.WikiComponentException;
import org.xwiki.context.Execution;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.model.reference.EntityReferenceSerializer;
import org.xwiki.query.Query;
import org.xwiki.query.QueryManager;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.user.api.XWikiRightService;

@Component
@Singleton
@Named(Citation.CLASS)
public class CitationClassBuilder implements WikiComponentBuilder {

	@Inject
	@Named("context")
	private Provider<ComponentManager> componentManagerProvider;
	
	@Inject
	private Execution execution;

	@Inject
	private QueryManager queryManager;

	@Inject
	private EntityReferenceSerializer<String> serializer;

	@Override
	public List<DocumentReference> getDocumentReferences() {
		List<DocumentReference> references = new ArrayList<DocumentReference>();

		try {
			Query query = queryManager.createQuery("SELECT doc.space, doc.name FROM Document doc, doc.object(" + Constants.SPACE + "." + Citation.CLASS + "Class) AS obj", Query.XWQL);
			List<Object[]> results = query.execute();
			for (Object[] result : results) {
				references.add(new DocumentReference(getXWikiContext().getDatabase(), (String) result[0], (String) result[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return references;
	}

	@Override
	public List<WikiComponent> buildComponents(DocumentReference reference) throws WikiComponentException {
		List<WikiComponent> components = new ArrayList<WikiComponent>();
		DocumentReference documentReference = new DocumentReference(getXWikiContext().getDatabase(), Constants.SPACE, Citation.CLASS + "Class");

		try {
			XWikiDocument doc = getXWikiContext().getWiki().getDocument(reference, getXWikiContext());

			XWikiRightService rightService = getXWikiContext().getWiki().getRightService();

			if (!rightService.hasAccessLevel("admin", doc.getAuthor(), "XWiki.XWikiPreferences", getXWikiContext())) {
				throw new WikiComponentException(String.format("Failed to building " + Citation.CLASS + "Class components from document " + " [%s], author [%s] doesn't have admin rights in the wiki",
						reference.toString(), doc.getAuthor()));
			}

			List<BaseObject> objects = doc.getXObjects(documentReference);

			for (final BaseObject obj : objects) {
				String roleHint = serializer.serialize(obj.getReference());

				DefaultCitation cit = new DefaultCitation();

				cit.setTitle(obj.getStringValue("title"));
				cit.setPrettyId(doc.getDocumentReference().getLastSpaceReference().getName() + "." + doc.getDocumentReference().getName() + "." + obj.getStringValue("key") + " (" + obj.getGuid()
						+ ")");

				cit.setAuthorReference(doc.getAuthorReference());
				cit.setRoleHint(roleHint);
				cit.setDocumentReference(reference);
				cit.setDocument(doc);
				cit.setAuthor(obj.getStringValue("author"));
				cit.setAddress(obj.getStringValue("address"));
				cit.setAttachment(obj.getStringValue("attachment"));
				cit.setBooktitle(obj.getStringValue("booktitle"));
				cit.setJournal(obj.getStringValue("journal"));
				cit.setMonth(obj.getStringValue("month"));
				cit.setNote(obj.getStringValue("note"));
				cit.setNumber(obj.getStringValue("number"));
				cit.setPages(obj.getStringValue("pages"));
				cit.setPublisher(obj.getStringValue("publisher"));
				cit.setEdition(obj.getStringValue("edition"));
				cit.setYear(obj.getStringValue("year"));
				cit.setSchool(obj.getStringValue("school"));
				cit.setType(obj.getStringValue("type"));
				cit.setKey(obj.getStringValue("key"));
				cit.setUrl(obj.getStringValue("url"));
				cit.setVolume(obj.getStringValue("volume"));
				
				obj.set("prettyid", cit.getPrettyId(), getXWikiContext());

				components.add(cit);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WikiComponentException(String.format("Failed to build " + Citation.CLASS + "Class components from document [%s]", reference.toString()), e);
		}

		return components;
	}

	private XWikiContext getXWikiContext() {
		return (XWikiContext) this.execution.getContext().getProperty("xwikicontext");
	}

}
