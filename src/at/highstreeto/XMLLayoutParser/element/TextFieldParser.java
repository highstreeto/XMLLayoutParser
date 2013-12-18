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

package at.highstreeto.XMLLayoutParser.element;

import at.highstreeto.XMLLayoutParser.LayoutParseException;
import at.highstreeto.XMLLayoutParser.LayoutParserContext;
import at.highstreeto.XMLLayoutParser.element.base.ElementParser;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.XmlReader.Element;

public class TextFieldParser implements ElementParser {

	@Override
	public String getElementName() {
		return "TextField";
	}

	@Override
	public Class<? extends Actor> getActorClass() {
		return TextField.class;
	}

	@Override
	public Actor load(Element element, LayoutParserContext context) throws LayoutParseException {
		String text = element.getText();
		TextField textField = new TextField(text == null ? "" : text,
				context.getSkin());
		
		context.addActor(textField, element);
		return textField;
	}

	@Override
	public void save(Actor actor, LayoutParserContext context) throws LayoutParseException {

	}
}
