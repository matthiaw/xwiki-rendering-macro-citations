/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.citations;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import java.util.List;
import java.util.Arrays;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.component.manager.ComponentManager;
import org.xwiki.rendering.block.Block;
import org.xwiki.rendering.block.RawBlock;
import org.xwiki.rendering.block.WordBlock;
import org.xwiki.rendering.block.ParagraphBlock;
import org.xwiki.rendering.macro.AbstractMacro;
import org.xwiki.rendering.macro.MacroContentParser;
import org.xwiki.rendering.macro.MacroExecutionException;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.transformation.MacroTransformationContext;

/**
 * Place a citation object into a document as reference or footnote
 * 
 * @version 1.0
 * @since 5.1
 */
@Component
@Named("citation")
public class CitationMacro extends AbstractMacro<CitationMacroParameters> {
	/**
	 * The description of the macro.
	 */
	private static final String DESCRIPTION = "Citation Macro";

	/**
	 * Create and initialize the descriptor of the macro.
	 */
	public CitationMacro() {
		super("Citation", DESCRIPTION, CitationMacroParameters.class);
	}

	/**
	 * Provide Component Manager
	 */
	@Inject
	@Named("context")
	private Provider<ComponentManager> componentManagerProvider;

	/**
	 * Content Parser
	 */
	@Inject
	private MacroContentParser contentParser;

	/**
	 * Find the Citation Object which holds the cited key
	 * 
	 * @param key
	 *            key of the citation object
	 * @return Object of DefaultCitation over Interface Citation
	 */
	private Citation findCitation(String key) {
		try {
			for (Object citObj : componentManagerProvider.get().getInstanceList(Citation.class)) {
				Citation citation = (Citation) citObj;
				if (citation.getKey().equals(key)) {
					return citation;
				}
			}
		} catch (ComponentLookupException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper Method which evaluates value
	 * 
	 * @param value
	 *            String Value which is evaluated
	 * @return true if value is not Null or empty
	 */
	private boolean isValid(String value) {
		if (value == null) {
			return false;
		} else {
			if (value.trim().equals("")) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public List<Block> execute(CitationMacroParameters parameters, String content, MacroTransformationContext context) throws MacroExecutionException {
		
		/** Build-String for relevant content*/
		StringBuilder citationContent = new StringBuilder();

		/** boolean value which holds the information if the citation refers to a specific page*/
		boolean hasPages = false;
		
		/** Holds refered Pages if it is given*/
		String citePages = "";

		/** citation of key */
		Citation citation = findCitation(parameters.getKey());

		if (citation != null) {
			/** Content is "styled" as article. Hard coded*/
			// TODO Would be better as CSS.
			if (citation.getType().equals("Article")) {
				String value = citation.getAuthor();
				if (isValid(value)) {
					citationContent.append(value);
				} else {
					citationContent.append(", {{error}}Author missing{{/error}}");
				}

				value = citation.getTitle();
				if (isValid(value)) {
					citationContent.append(", " + value);
				} else {
					citationContent.append(", {{error}}Title missing{{/error}}");
				}

				value = citation.getJournal();
				if (isValid(value)) {
					citationContent.append(", //" + value + "//");
				} else {
					citationContent.append(", {{error}}Title missing{{/error}}");
				}

				String volume = citation.getVolume();
				String number = citation.getNumber();
				String pages = citation.getPages();
				if (isValid(pages)) {
					if (isValid(volume)) {
						if (isValid(number)) {
							citationContent.append(", " + volume + "(" + number + "):" + pages + "");
						} else {
							citationContent.append(", " + volume + "({{error}}Number missing{{/error}}):" + pages + "");
						}
					}
				} else {
					citationContent.append(", {{error}}Pages missing{{/error}}");
				}

				String month = citation.getMonth();
				String year = citation.getYear();
				if (isValid(year)) {
					if (isValid(month)) {
						citationContent.append(", " + month + " " + year + "");
					} else {
						citationContent.append(", " + year + "");
					}
				} else {
					citationContent.append(", {{error}}Year missing{{/error}}");
				}

				value = citation.getIsbn();
				if (isValid(value)) {
					citationContent.append(", ISBN: " + value + "");
				}

				value = citation.getUrl();
				if (isValid(value)) {
					citationContent.append(", [[" + value + "]]");
				}

				value = citation.getNote();
				if (isValid(value)) {
					citationContent.append(". " + value + ".");
				}

			}

			if (citation.getType().equals("Book")) {
				String value = citation.getAuthor();
				if (isValid(value)) {
					citationContent.append(value);
				} else {
					citationContent.append(", {{error}}Author missing{{/error}}");
				}

				value = citation.getTitle();
				if (isValid(value)) {
					citationContent.append(", //" + value + "//");
				} else {
					citationContent.append(", {{error}}Title missing{{/error}}");
				}

				String volume = citation.getVolume();
				String series = citation.getSeries();
				if (isValid(volume)) {
					if (isValid(series)) {
						citationContent.append(", Volume " + volume + " of " + series);
					} else {
						citationContent.append(", Volume " + volume);
					}
				} else {
					if (isValid(series)) {
						citationContent.append(", " + series);
					}
				}

				value = citation.getAddress();
				if (isValid(value)) {
					citationContent.append(", " + value + "");
				}

				value = citation.getEdition();
				if (isValid(value)) {
					citationContent.append(", " + value + ". Edition");
				} else {
					citationContent.append(", {{error}}Edition missing{{/error}}");
				}

				String month = citation.getMonth();
				String year = citation.getYear();
				if (isValid(year)) {
					if (isValid(month)) {
						citationContent.append(", " + month + " " + year + "");
					} else {
						citationContent.append(", " + year + "");
					}
				} else {
					citationContent.append(", {{error}}Year missing{{/error}}");
				}

				value = citation.getIsbn();
				if (isValid(value)) {
					citationContent.append(", ISBN: " + value + "");
				}

				value = citation.getUrl();
				if (isValid(value)) {
					citationContent.append(", [[" + value + "]]");
				}

				value = citation.getNote();
				if (isValid(value)) {
					citationContent.append(". " + value + ".");
				}

				String pages = parameters.getPages();
				if (isValid(pages)) {
					citePages = pages;
					hasPages = true;
				}

			}

			if (citation.getType().equals("Conference") || citation.getType().equals("Inbook") || citation.getType().equals("Incollection") || citation.getType().equals("Inproceedings")
					|| citation.getType().equals("Manual") || citation.getType().equals("Masterthesis") || citation.getType().equals("Misc") || citation.getType().equals("Phdthesis")
					|| citation.getType().equals("Proceedings") || citation.getType().equals("Unpublished") || citation.getType().equals("Techreport")) {
				String value = citation.getAuthor();
				if (isValid(value)) {
					citationContent.append(value);
				} else {
					citationContent.append(", {{error}}Author missing{{/error}}");
				}

				value = citation.getTitle();
				if (isValid(value)) {
					citationContent.append(", //" + value + "//");
				}

				value = citation.getBooktitle();
				if (isValid(value)) {
					citationContent.append(", " + value + "");
				}

				value = citation.getJournal();
				if (isValid(value)) {
					citationContent.append(", " + value + "");
				}

				value = citation.getVolume();
				if (isValid(value)) {
					citationContent.append(", Volume " + value + "");
				}

				value = citation.getNumber();
				if (isValid(value)) {
					citationContent.append(", No. " + value + "");
				}

				value = citation.getPages();
				if (isValid(value)) {
					citationContent.append(", Pages " + value + "");
				}

				String month = citation.getMonth();
				String year = citation.getYear();
				if (isValid(year)) {
					if (isValid(month)) {
						citationContent.append(", " + month + " " + year + "");
					} else {
						citationContent.append(", " + year + "");
					}
				} else {
					citationContent.append(", {{error}}Year missing{{/error}}");
				}

				value = citation.getIsbn();
				if (isValid(value)) {
					citationContent.append(", ISBN: " + value + "");
				}

				value = citation.getUrl();
				if (isValid(value)) {
					citationContent.append(", [[" + value + "]]");
				}

				value = citation.getAttachment();
				if (isValid(value)) {
					citationContent.append(", [[attach:" + value + "]]");
				}

				value = citation.getNote();
				if (isValid(value)) {
					citationContent.append(". " + value + ".");
				}

				String pages = parameters.getPages();
				if (isValid(pages)) {
					citePages = pages;
					hasPages = true;
				}

			}
		} else {
			citationContent.append("{{error}}Citation '" + parameters.getKey() + "' not found{{/error}}");
		}

		String pages = parameters.getPages();
		if (isValid(pages)) {
			citePages = pages;
			hasPages = true;
		}

		/** Enables the usage as reference or footnote */
		if (parameters.getFootnote() != null) {
			if (parameters.getFootnote().equals("false")) {
				citationContent.insert(0, "{{reference}}");
				citationContent.append("{{/reference}}");
			} else {
				citationContent.insert(0, "{{footnote}}");
				citationContent.append("{{/footnote}}");
				hasPages = false;
			}
		} else {
			citationContent.insert(0, "{{reference}}");
			citationContent.append("{{/reference}}");
		}

		/** Parse the citation and place it*/
		List<Block> result = this.contentParser.parse(citationContent.toString(), context, true, context.isInline()).getChildren();

		/** Add pages at the end of the parsed content */
		if (hasPages) {
			RawBlock rb = new RawBlock("<sup><span class=\"footnoteRef\">, p." + citePages + "</span></sup>", Syntax.HTML_4_01);
			result.add(rb);
		}

		return result;
	}

	@Override
	public boolean supportsInlineMode() {
		return true;
	}
}
