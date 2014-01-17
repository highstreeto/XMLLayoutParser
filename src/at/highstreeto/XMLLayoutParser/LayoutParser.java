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
 * TODO: Add examples and tutorials how to use this piece of software
 */

package at.highstreeto.XMLLayoutParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;

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
		parsers = ElementParsers.getDefault();
	}

	/**
	 * Initializes a new LayoutParser with the Parsers from parsers
	 * 
	 * @param parsers
	 *            ElementParsers used for loading/saving data
	 */
	public LayoutParser(ElementParsers parsers) {
		this();
		this.parsers = parsers;
	}

	/**
	 * 
	 * @return
	 */
	public ElementParsers getParsers() {
		return parsers;
	}

	/**
	 * 
	 * @param layoutFile
	 * @param skin
	 * @return
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
					Actor actor = parsers.getParserByElementName(child).load(
							child, context);
					actors.add(actor);
				}
				return actors;
			} else {
				throw new LayoutParseException(); // TODO More detailed
													// Exception
			}
		} catch (IOException e) {
			throw new LayoutParseException(e); // TODO More detailed Exception
		} catch (SerializationException e) {
			throw new LayoutParseException(e);
		}
	}

	/**
	 * 
	 * @param stage
	 * @param skin
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
