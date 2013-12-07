package org.xwiki.rendering.citations;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;
import org.xwiki.model.reference.DocumentReference;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.sheet.SheetBinder;

import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.internal.mandatory.AbstractMandatoryDocumentInitializer;
import com.xpn.xwiki.objects.classes.BaseClass;

@Component
@Named(Citation.CLASS+"Initializer")
@Singleton
public class CitationClassInitializer extends AbstractMandatoryDocumentInitializer {

	/**
	 * Default list separators of Graph.GraphClass fields.
	 */
	public static final String DEFAULT_FIELDS = "|";
	
	public static final String FIELD_TITLE = "title";
	public static final String FIELDPN_TITLE = "Title";
	
	public static final String FIELD_AUTHOR = "author";
	public static final String FIELDPN_AUTHOR = "Author";
	
	public static final String FIELD_PRETTYID = "prettyid";
	public static final String FIELDPN_PRETTYID = "Humane Readable Id";
	
	public static final String FIELD_ADDRESS = "address";
	public static final String FIELDPN_ADDRESS = "Address";
	
	public static final String FIELD_ATTACHMENT = "attachment";
	public static final String FIELDPN_ATTACHMENT = "Attachment as Space.Page@img.png";
	
	public static final String FIELD_BOOKTITLE = "booktitle";
	public static final String FIELDPN_BOOKTITLE = "Booktitle";
	
	public static final String FIELD_JOURNAL = "journal";
	public static final String FIELDPN_JOURNAL = "Journal";
	
	public static final String FIELD_MONTH = "month";
	public static final String FIELDPN_MONTH = "Month";
	
	public static final String FIELD_NOTE = "note";
	public static final String FIELDPN_NOTE = "Note";
	
	public static final String FIELD_NUMBER = "number";
	public static final String FIELDPN_NUMBER = "Number";
	
	public static final String FIELD_PAGES = "pages";
	public static final String FIELDPN_PAGES = "Pages";
	
	public static final String FIELD_PUBLISHER = "publisher";
	public static final String FIELDPN_PUBLISHER = "Publisher";
	
	public static final String FIELD_KEY = "key";
	public static final String FIELDPN_KEY = "Key";
	
	public static final String FIELD_SCHOOL = "school";
	public static final String FIELDPN_SCHOOL = "School";
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELDPN_TYPE = "Type";
	
	public static final String FIELD_URL = "url";
	public static final String FIELDPN_URL = "Url";
	
	public static final String FIELD_VOLUME = "volume";
	public static final String FIELDPN_VOLUME = "Volume";
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELDPN_YEAR = "Year";
	
	public static final String FIELD_EDITION = "edition";
	public static final String FIELDPN_EDITION = "Edition";
	
	public static final String FIELD_SERIES = "series";
	public static final String FIELDPN_SERIES = "Series";
	
	public static final String FIELD_ISBN = "isbn";
	public static final String FIELDPN_ISBN = "ISBN";
	
	/**
	 * Used to bind a class to a document sheet.
	 */
	@Inject
	@Named("class")
	private SheetBinder classSheetBinder;

	/**
	 * Used to access current XWikiContext.
	 */
	@Inject
	private Provider<XWikiContext> xcontextProvider;

	/**
	 * Overriding the abstract class' private reference.
	 */
	private DocumentReference reference;

	public CitationClassInitializer() {
		// Since we can`t get the main wiki here, this is just to be able to use the Abstract class.
		super(Constants.SPACE, Citation.CLASS+"Class");
	}

	@Override
	public boolean updateDocument(XWikiDocument document) {
		boolean needsUpdate = false;
		
		// Add missing class fields
		BaseClass baseClass = document.getXClass();
		
		needsUpdate |= baseClass.addStaticListField(FIELD_TYPE, FIELDPN_TYPE, "Article|Book|Booklet|Conference|Inbook|Incollection|Inproceedings|Manual|Masterthesis|Misc|Phdthesis|Proceedings|Techreport|Unpublished");
		needsUpdate |= baseClass.addTextField(FIELD_KEY, FIELDPN_KEY, 30);
		needsUpdate |= baseClass.addTextField(FIELD_AUTHOR, FIELDPN_AUTHOR, 30);
		needsUpdate |= baseClass.addTextField(FIELD_TITLE, FIELDPN_TITLE, 30);
		needsUpdate |= baseClass.addTextField(FIELD_JOURNAL, FIELDPN_JOURNAL, 30);
		needsUpdate |= baseClass.addTextField(FIELD_YEAR, FIELDPN_YEAR, 30);
		needsUpdate |= baseClass.addTextField(FIELD_ADDRESS, FIELDPN_ADDRESS, 30);
		needsUpdate |= baseClass.addTextField(FIELD_ATTACHMENT, FIELDPN_ATTACHMENT, 30);
		needsUpdate |= baseClass.addTextField(FIELD_BOOKTITLE, FIELDPN_BOOKTITLE, 30);
		needsUpdate |= baseClass.addTextField(FIELD_MONTH, FIELDPN_MONTH, 30);
		needsUpdate |= baseClass.addTextField(FIELD_NUMBER, FIELDPN_NUMBER, 30);
		needsUpdate |= baseClass.addTextField(FIELD_EDITION, FIELDPN_EDITION, 30);
		needsUpdate |= baseClass.addTextField(FIELD_SERIES, FIELDPN_SERIES, 30);
		needsUpdate |= baseClass.addTextField(FIELD_ISBN, FIELDPN_ISBN, 30);
		needsUpdate |= baseClass.addTextField(FIELD_PAGES, FIELDPN_PAGES, 30);
		needsUpdate |= baseClass.addTextField(FIELD_PUBLISHER, FIELDPN_PUBLISHER, 30);
		needsUpdate |= baseClass.addTextField(FIELD_SCHOOL, FIELDPN_SCHOOL, 30);
		needsUpdate |= baseClass.addTextField(FIELD_URL, FIELDPN_URL, 30);
		needsUpdate |= baseClass.addTextField(FIELD_VOLUME, FIELDPN_VOLUME, 30);
		needsUpdate |= baseClass.addTextField(FIELD_NOTE, FIELDPN_NOTE, 30);
		needsUpdate |= baseClass.addTextField(FIELD_PRETTYID, FIELDPN_PRETTYID, 30);
		
		// Add missing document fields
		needsUpdate |= setClassDocumentFields(document, Citation.CLASS+"Class");

		// Use Sheet to display documents having Class objects if no other class sheet is specified.
		if (this.classSheetBinder.getSheets(document).isEmpty()) {
			String wikiName = document.getDocumentReference().getWikiReference().getName();
			DocumentReference sheet = new DocumentReference(wikiName, Constants.SPACE, Citation.CLASS+"Class"+"Sheet");
			needsUpdate |= this.classSheetBinder.bind(document, sheet);
		}

		return needsUpdate;
	}

	/**
	 * Initialize and return the main wiki's class document reference.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public EntityReference getDocumentReference() {
		if (this.reference == null) {
			synchronized (this) {
				if (this.reference == null) {
					String mainWikiName = xcontextProvider.get().getMainXWiki();
					this.reference = new DocumentReference(mainWikiName, Constants.SPACE, Citation.CLASS+"Class");
				}
			}
		}

		return this.reference;
	}

}
