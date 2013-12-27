/*
Copyright 2013 Martin Hochstrasser

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package at.highstreeto.XMLLayoutParser;

import java.util.HashMap;
import java.util.Map;

import at.highstreeto.XMLLayoutParser.element.CellParser;
import at.highstreeto.XMLLayoutParser.element.LabelParser;
import at.highstreeto.XMLLayoutParser.element.RowParser;
import at.highstreeto.XMLLayoutParser.element.TableParser;
import at.highstreeto.XMLLayoutParser.element.TextButtonParser;
import at.highstreeto.XMLLayoutParser.element.TextFieldParser;
import at.highstreeto.XMLLayoutParser.element.base.ElementParser;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader.Element;

public class ElementParsers {

	private Map<String, ElementParser> parsersByElementName;
	private Map<Class<? extends Actor>, ElementParser> parsersByClass;

	public ElementParsers() {
		parsersByElementName = new HashMap<String, ElementParser>();
		parsersByClass = new HashMap<Class<? extends Actor>, ElementParser>();
	}

	public static ElementParsers getDefault() {
		ElementParsers parsers = new ElementParsers();
		parsers.addParser(new TextFieldParser());
		parsers.addParser(new LabelParser());
		parsers.addParser(new TextButtonParser());
		parsers.addParser(new TableParser());
		parsers.addParser(new RowParser());
		parsers.addParser(new CellParser());

		return parsers;
	}

	public void addParser(ElementParser parser) {
		parsersByElementName.put(parser.getElementName(), parser);
		parsersByClass.put(parser.getActorClass(), parser);
	}

	public void removeParser(ElementParser parser) {
		parsersByElementName.remove(parser.getElementName());
		parsersByClass.remove(parser.getActorClass());
	}

	public ElementParser getParserByElementName(String elementName) {
		return parsersByElementName.get(elementName);
	}

	public ElementParser getParserByElementName(Element element) {
		return getParserByElementName(element.getName());
	}

	public ElementParser getParserByClass(Class<? extends Actor> actorClass) {
		return parsersByClass.get(actorClass);
	}

	public ElementParser getParserByCLass(Actor actor) {
		return getParserByClass(actor.getClass());
	}
}
