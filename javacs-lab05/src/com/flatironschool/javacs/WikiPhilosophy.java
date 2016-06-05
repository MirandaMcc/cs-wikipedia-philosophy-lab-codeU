package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class WikiPhilosophy {
	
	final static WikiFetcher wf = new WikiFetcher();
	final static List<String> visited = new ArrayList<String>();
	final static String target = "https://en.wikipedia.org/wiki/Philosophy";
	final static String start = "https://en.wikipedia.org/wiki/Java_(programming_language)";
	
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
		
        // current url to be searched, updated frequently
		String url = start;
		
		//depth first search
		do{
			//get first good link
			visited.add(url);
			Document doc = Jsoup.connect(url).get();
        	Elements links = doc.select("a[href]");
			Element valid = null;
			for(Element e : links){
				if(!WikiPhilosophy.parentParensOrEm(e) && WikiPhilosophy.isValidLink(e.text()))
				{
					valid = e;
					break;
				}
			}
			
			if(valid == null){
				System.out.println("Dead End!");
				return;
			}
			
			//update url to search new page
			url = valid.attr("abs:href");;
		
			
		} while(visited.size() < 15 && !visited.contains(target)); //caps number of links followed and stops when Philosophy reached

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
