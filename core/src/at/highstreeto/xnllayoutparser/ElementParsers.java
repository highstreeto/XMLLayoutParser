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
package at.highstreeto.xnllayoutparser;

import at.highstreeto.xnllayoutparser.element.CellParser;
import at.highstreeto.xnllayoutparser.element.ImageButtonParser;
import at.highstreeto.xnllayoutparser.element.LabelParser;
import at.highstreeto.xnllayoutparser.element.RowParser;
import at.highstreeto.xnllayoutparser.element.TableParser;
import at.highstreeto.xnllayoutparser.element.TextButtonParser;
import at.highstreeto.xnllayoutparser.element.TextFieldParser;
import at.highstreeto.xnllayoutparser.element.base.ElementParser;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader.Element;
import java.util.HashMap;
import java.util.Map;

public class ElementParsers {

    private final Map<String, ElementParser> parsersByElementName;
    private final Map<Class<? extends Actor>, ElementParser> parsersByClass;

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
        parsers.addParser(new ImageButtonParser());

        return parsers;
    }

    public void addParser(ElementParser parser) {
        if (parser == null) {
            throw new IllegalArgumentException("parser can not be null!");
        }
        parsersByElementName.put(parser.getElementName(), parser);
        parsersByClass.put(parser.getActorClass(), parser);
    }

    public void removeParser(ElementParser parser) {
        if (parser == null) {
            throw new IllegalArgumentException("parser can not be null!");
        } 
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
