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
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * 
 * @author highstreeto
 */
public class LayoutParser {
	public interface ElementParser {
		Actor parseElement(Element element, Map<Element, Actor> actors,
				Skin skin, LayoutParser layoutParser)
				throws LayoutParseException;
	}

	private FileHandle layoutFile;
	private ElementParsers parsers;

	public LayoutParser(FileHandle layoutFile, Skin skin) {
		this.layoutFile = layoutFile;

		parsers = ElementParsers.getDefault();
	}

	public ElementParsers getParsers() {
		return parsers;
	}

	public Collection<Actor> load(Skin skin) throws LayoutParseException {
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

	public void load(Stage stage, Skin skin) throws LayoutParseException {
		for (Actor i : load(skin)) {
			stage.addActor(i);
		}
	}
}
