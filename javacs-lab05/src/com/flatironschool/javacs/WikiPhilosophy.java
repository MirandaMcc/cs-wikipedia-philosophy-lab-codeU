package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	final static List<String> visited = new ArrayList<String>();
	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		Elements paragraphs = wf.fetchWikipedia(url);
		visited.add(url);
		Element firstPara = paragraphs.get(0);

		Iterable<Node> iter = new WikiNodeIterable(firstPara);
		
		//depth first search

		for (Node n: iter) {
			if (n instanceof Element) {
				Element node = (Element) n;
				if(node.tagName().equals("p"))
				{
					if(!WikiPhilosophy.parentParensOrEm(node) && WikiPhilosophy.isValidLink(node.ownText()))
						visited.add(node.ownText());
					if(node.ownText().contains("philosophy"))
						break;
				}
			}
			
			if(visited.size() > 15)
				break;

        }

        // the following throws an exception so the test fails
        // until you update the code
        System.out.println(visited);
	}
	
	public static boolean isValidLink(String link){

		return !visited.contains(link);
	}

	public static boolean parentParensOrEm(Node node){
		Element p = (Element) node.parent();
		String tag = p.tagName();
		return tag.equals("em"); //|| node.parent().equals(parens);
	}
}
