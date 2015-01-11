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

/* Global TODOs
 * 
 * TODO: Add more documentation
 * TODO: Implement saving of actors
 * TODO: Add examples and tutorials how to use this
 */
package at.highstreeto.xnllayoutparser;

import at.highstreeto.xnllayoutparser.element.base.ElementParser;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Converts a XML file in a Collection of Actors and vice-versa. It uses the
 * XMLReader from libGDX
 *
 * @author highstreeto
 */
public class LayoutParser {

    private ElementParsers parsers;

    /**
     * Initializes a new LayoutParser with the default set of parsers (from
     * {@link ElementParsers#getDefault()}
     */
    public LayoutParser() {
        this(ElementParsers.getDefault());
    }

    /**
     * Initializes a new LayoutParser with the Parsers given
     *
     * @param parsers ElementParsers used for loading/saving data
     */
    public LayoutParser(ElementParsers parsers) {
        this.parsers = parsers;
    }

    /**
     * Returns the currently used Parsers
     *
     * @return Collection of ElementParsers
     */
    public ElementParsers getParsers() {
        return parsers;
    }

    /**
     * Loads the specified XML file and returns a collection of actors.
     *
     * @param layoutFile Contains the layout of the scene
     * @param skin Skin for the actors
     * @return Collection of actors
     * @throws LayoutParseException
     */
    public Collection<Actor> load(FileHandle layoutFile, Skin skin)
            throws LayoutParseException {
        XmlReader reader = new XmlReader();
        LayoutParserContext context = new LayoutParserContext();
        context.setParsers(parsers);
        context.setSkin(skin);

        try {
            Collection<Actor> actors = new ArrayList<Actor>();
            Element rootElement = reader.parse(layoutFile);
            if (rootElement.getName().equals("Layout")) {
                for (int i = 0; i < rootElement.getChildCount(); i++) {
                    Element child = rootElement.getChild(i);
                    ElementParser parser = parsers.getParserByElementName(child);

                    Actor actor = parser.load(child, context);
                    actors.add(actor);
                }
                return actors;
            } else {
                throw new LayoutParseException(
                        "XML is malformed - root element ist not Layout!");
            }
        } catch (IOException e) {
            throw new LayoutParseException("IO error while parsing layout file!",
                    e);
        } catch (SerializationException e) {
            throw new LayoutParseException(
                    "Serialization error while parsing layout file!", e);
        }
    }

    /**
     * Convenient method that adds the actors loaded from given XML file to the given Stage
     * @param layoutFile Contains the layout of the scene
     * @param stage Stage, to which the actors should be added
     * @param skin Skin for the actors 
     * @throws LayoutParseException
     */
    public void load(FileHandle layoutFile, Stage stage, Skin skin)
            throws LayoutParseException {
        for (Actor i : load(layoutFile, skin)) {
            stage.addActor(i);
        }
    }

    /**
     *
     * @param layoutFile
     * @param actors
     * @throws LayoutParseException
     */
    public void save(FileHandle layoutFile, Collection<Actor> actors)
            throws LayoutParseException {
        try {
            XmlWriter writer = new XmlWriter(layoutFile.writer(false));
            LayoutParserContext context = new LayoutParserContext();
            context.setParsers(parsers);

            writer.element("Layout");

            for (Actor i : actors) {
                parsers.getParserByClass(i.getClass()).save(writer, i, context);
            }

            writer.pop();
            writer.close();
        } catch (IOException e) {
            throw new LayoutParseException(e); // TODO More detailed Exception
        }
    }
}
