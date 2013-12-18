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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.XmlReader.Element;

import at.highstreeto.XMLLayoutParser.LayoutParseException;
import at.highstreeto.XMLLayoutParser.LayoutParserContext;
import at.highstreeto.XMLLayoutParser.element.base.ElementParser;
import at.highstreeto.XMLLayoutParser.element.base.ElementParserHelper;

public class TextButtonParser implements ElementParser {

	@Override
	public String getElementName() {
		return "TextButton";
	}

	@Override
	public Class<? extends Actor> getActorClass() {
		return TextButton.class;
	}

	@Override
	public Actor load(Element element, LayoutParserContext context)
			throws LayoutParseException {
		String text = element.getText();
		TextButton textButton = new TextButton(text == null ? "" : text,
				context.getSkin());

		context.addActor(textButton, element);
		ElementParserHelper.setActorName(element, textButton);
		
		return textButton;
	}

	@Override
	public void save(Actor actor, LayoutParserContext context)
			throws LayoutParseException {
		// TODO Auto-generated method stub

	}

}
