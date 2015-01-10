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
package at.highstreeto.xnllayoutparser.element.base;

import at.highstreeto.xnllayoutparser.LayoutParseException;
import at.highstreeto.xnllayoutparser.LayoutParserContext;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;

/**
 * The abstract class for element-wise parsers. The implementations of this
 * class have to provide a way to load / save an actor to a XML file.
 *
 * @author highstreeto
 *
 */
public interface ElementParser {

    /**
     * Returns the name of the XML element that can be loaded
     *
     * @return Name of the XML Element that can be loaded
     */
    String getElementName();

    /**
     * Returns the class that this parser can save
     *
     * @return Class, that this parser can save
     */
    Class<? extends Actor> getActorClass();

    /**
     * Reads an Actor from the XML-Element element
     *
     * @param element
     * @param context
     * @return
     * @throws LayoutParseException
     */
    Actor load(Element element, LayoutParserContext context)
            throws LayoutParseException;

    /**
     * Saves an Actor with the XmlWriter writer
     *
     * @param writer
     * @param actor
     * @param context
     * @throws LayoutParseException
     */
    void save(XmlWriter writer, Actor actor, LayoutParserContext context)
            throws LayoutParseException;
}
