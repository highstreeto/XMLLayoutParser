package at.highstreeto.XMLLayoutParser;

import java.util.HashMap;
import java.util.Map;

import at.highstreeto.XMLLayoutParser.element.base.ElementParser;

public class ElementParsers {
	
	private Map<String, ElementParser> parsersByElementName;
	
	public ElementParsers() {
		parsersByElementName = new HashMap<String, ElementParser>();
	}

}
