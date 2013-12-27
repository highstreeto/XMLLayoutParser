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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;
import com.esotericsoftware.tablelayout.Cell;

public class CellParser implements ElementParser {

	@Override
	public String getElementName() {
		return "Cell";
	}

	@Override
	public Class<? extends Actor> getActorClass() {
		return null;
	}

	@Override
	public Actor load(Element element, LayoutParserContext context)
			throws LayoutParseException {
		Table table = (Table) context.getActorByElement(element.getParent()
				.getParent());

		Element child = element.getChild(0);
		Actor actor = context.getParsers().getParserByElementName(child)
				.load(child, context);
		Cell<?> cell = table.add(actor);

		if (element.getAttributes().containsKey("fill")) {
			if ("x".equals(element.getAttribute("fill"))) {
				cell.fillX();
			} else if ("y".equals(element.getAttribute("fill"))) {
				cell.fillY();
			} else if ("xy".equals(element.getAttribute("fill"))) {
				cell.fill();
			} else {
				throw new LayoutParseException();
			}
		} // Padding - Format: Top Left Bottom Right
		if (element.getAttributes().containsKey("pad")) {
			String[] pads = element.getAttribute("pad").split(" ");
			if (pads.length == 4) {
				float top = Float.parseFloat(pads[0]);
				float right = Float.parseFloat(pads[1]);
				float bottom = Float.parseFloat(pads[2]);
				float left = Float.parseFloat(pads[3]);

				cell.pad(top, left, bottom, right);
			} else {
				throw new LayoutParseException();
			}
		}
		if (element.getAttributes().containsKey("colspan")) {
			int colspan = Integer.parseInt(element.getAttribute("colspan"));
			cell.colspan(colspan);
		}
		return null;
	}

	@Override
	public void save(XmlWriter writer, Actor actor, LayoutParserContext context)
			throws LayoutParseException {
		// TODO Auto-generated method stub

	}

}
