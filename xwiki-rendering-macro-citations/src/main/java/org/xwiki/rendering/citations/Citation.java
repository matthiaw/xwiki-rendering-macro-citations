package org.xwiki.rendering.citations;

import org.xwiki.component.wiki.WikiComponent;

public interface Citation extends WikiComponent {

	public static final String CLASS = "Citation";

	public String getPrettyId();

	public String getAuthor();

	public String getTitle();
	
	public String getJournal();
	
	public String getYear();
	
	public String getType();
	
	public String getPublisher();
	
	public String getSchool();
	
	public String getBooktitle();
	
	public String getNote();
	
	public String getUrl();
	
	public String getAttachment();
	
	public String getVolume();
	
	public String getNumber();
	
	public String getSeries();
	
	public String getIsbn();
	
	public String getEdition();
	
	public String getPages();
	
	public String getMonth();
	
	public String getAddress();
	
	public String getKey();
}
