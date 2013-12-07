package org.xwiki.rendering.citations;

import org.xwiki.properties.annotation.PropertyMandatory;
import org.xwiki.properties.annotation.PropertyDescription;

public class CitationMacroParameters
{
    private String key;
    
    private String pages;
    
    private String footnote;

    public String getFootnote() {
		return footnote;
	}

    @PropertyDescription("Enables/Disable Footnotes")
	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	public String getPages() {
		return pages;
	}

    @PropertyDescription("Optional Pages")
	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getKey()
    {
        return this.key;
    }
    
    @PropertyMandatory
    @PropertyDescription("Citation Key")
    public void setKey(String key)
    {
        this.key = key;
    }
}
