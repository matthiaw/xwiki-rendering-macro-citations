package org.xwiki.rendering.citations;

import org.xwiki.properties.annotation.PropertyMandatory;
import org.xwiki.properties.annotation.PropertyDescription;

/**
 * Parameters of Citation-Macro
 *
 * @since 5.1
 */
public class CitationMacroParameters {
	/** Key of citation (Mandatory value) */
	private String key;

	/** Pages of citation (Optional value). Usefull for book-citations. */
	private String pages;

	/**
	 * Enables/Disables macro usage footnote macro (Optional value). Default is
	 * references-macro.
	 */
	private String footnote;

	/**
	 * Returns footnote value
	 * 
	 * @return Text of footnote-macro-enabling
	 */
	public String getFootnote() {
		return footnote;
	}

	/**
	 * Set allowance of reference or footnote macro
	 * 
	 * @param footnote
	 *            "true" or "false"
	 */
	@PropertyDescription("Enables/Disable Footnotes-Macro (true/false). Default Usage is Reference-Macro.")
	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}

	/**
	 * Get Pages
	 * 
	 * @return Text of pages
	 */
	public String getPages() {
		return pages;
	}

	/**
	 * Set Text of page numbers
	 * 
	 * @param pages
	 *            Number of pages, could be 123, 123ff, 123-124, ...
	 */
	@PropertyDescription("Optional Pages")
	public void setPages(String pages) {
		this.pages = pages;
	}

	/**
	 * Returns key of citation
	 * 
	 * @return key of citation
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Set key of citation (Mandatory)
	 * 
	 * @param key
	 */
	@PropertyMandatory
	@PropertyDescription("Citation Key")
	public void setKey(String key) {
		this.key = key;
	}
}
