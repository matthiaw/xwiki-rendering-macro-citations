package org.xwiki.rendering.citations;

import java.lang.reflect.Type;

import org.xwiki.component.wiki.WikiComponentScope;
import org.xwiki.model.reference.DocumentReference;

import com.xpn.xwiki.doc.XWikiDocument;

public class DefaultCitation implements Citation {

	private String adress;
	private String attachment;
	private String author;
	private DocumentReference authorReference;

	private String booktitle;

	private DocumentReference documentReference;

	private String journal;

	private String month;

	private String note;

	private String number;

	private String pages;

	private String key;
	
	private String prettyId;

	private String publisher;

	private String roleHint;

	private String school;

	private String title;

	private String type;
	
	private String isbn;
	
	private String series;
	
	private String edition;

	private String url;

	private String volume;

	private XWikiDocument xwikiDocument;

	private String year;

	@Override
	public String getAddress() {
		return adress;
	}

	@Override
	public String getAttachment() {
		return attachment;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	public DocumentReference getAuthorReference() {
		return authorReference;
	}

	@Override
	public String getBooktitle() {
		return booktitle;
	}

	public XWikiDocument getDocument() {
		return xwikiDocument;
	}

	public DocumentReference getDocumentReference() {
		return documentReference;
	}

	@Override
	public String getJournal() {
		return journal;
	}

	@Override
	public String getMonth() {
		return month;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getPages() {
		return pages;
	}

	@Override
	public String getPrettyId() {
		return prettyId;
	}

	@Override
	public String getPublisher() {
		return publisher;
	}

	@Override
	public String getRoleHint() {
		return roleHint;
	}

	@Override
	public Type getRoleType() {
		return Citation.class;
	}

	@Override
	public String getSchool() {
		return school;
	}

	@Override
	public WikiComponentScope getScope() {
		return WikiComponentScope.WIKI;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getVolume() {
		return volume;
	}

	@Override
	public String getYear() {
		return year;
	}

	public void setAddress(String adress) {
		this.adress = adress;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setAuthorReference(DocumentReference authorReference) {
		this.authorReference = authorReference;
	}

	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}

	public void setDocument(XWikiDocument xwikiDocument) {
		this.xwikiDocument = xwikiDocument;
	}

	public void setDocumentReference(DocumentReference documentReference) {
		this.documentReference = documentReference;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public void setEdition(String edition) {
		this.edition = edition;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public void setSeries(String series) {
		this.series = series;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public void setPrettyId(String prettyId) {
		this.prettyId = prettyId;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setRoleHint(String roleHint) {
		this.roleHint = roleHint;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getSeries() {
		return series;
	}

	@Override
	public String getIsbn() {
		return isbn;
	}

	@Override
	public String getEdition() {
		return edition;
	}

}
