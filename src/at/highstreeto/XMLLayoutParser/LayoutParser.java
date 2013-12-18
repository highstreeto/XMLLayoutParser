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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Converts a XML file in a Collection of {@link Actor Actors} and vice-versa.
 * It uses the XMLReader from libGDX
 * 
 * @author highstreeto
 */
public class LayoutParser {
	private ElementParsers parsers;

	/**
	 * Initalizes a new LayoutParser with the default set of parsers (from
	 * {@link ElementParser.getDefault()}
	 */
	public LayoutParser() {
		parsers = ElementParsers.getDefault();
	}

	/**
	 * 
	 * @param parsers
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
			Collection<Actor> actors = new ArrayList<>();
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
}
