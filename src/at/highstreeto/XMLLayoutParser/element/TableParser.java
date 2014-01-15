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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;

import at.highstreeto.XMLLayoutParser.LayoutParseException;
import at.highstreeto.XMLLayoutParser.LayoutParserContext;
import at.highstreeto.XMLLayoutParser.element.base.ElementParser;
import at.highstreeto.XMLLayoutParser.element.base.ElementParserHelper;

public class TableParser implements ElementParser {

	@Override
	public String getElementName() {
		return "Table";
	}

	@Override
	public Class<? extends Actor> getActorClass() {
		return Table.class;
	}

	@Override
	public Actor load(Element element, LayoutParserContext context)
			throws LayoutParseException {
		Table table = new Table();

		if (element.getAttributes().containsKey("fillParent")) {
			table.setFillParent(element.getBooleanAttribute("fillParent"));
		}
		if (element.getAttributes().containsKey("debug")) {
			String[] parts = element.getAttribute("debug").split(" ");
			for (String i : parts) {
				if ("cell".equals(i)) {
					table.debugCell();
				} else if ("widget".equals(i)) {
					table.debugWidget();
				} else if ("table".equals(i)) {
					table.debugTable();
				}
			}
		}
		// Combination of c (Center), t (Top), b (Bottom), l (Left), r (Right)
		if (element.getAttributes().containsKey("align")) {
			String[] parts = element.getAttribute("align").split(" ");
			for (String i : parts) {
				if ("c".equals(i)) {
					table.center();
				} else if ("t".equals(i)) {
					table.top();
				} else if ("b".equals(i)) {
					table.bottom();
				} else if ("l".equals(i)) {
					table.left();
				} else if ("r".equals(i)) {
					table.right();
				}
			}
		}

		context.addActor(table, element);
		ElementParserHelper.setActorName(element, table);

		for (int i = 0; i < element.getChildCount(); i++) {
			Element child = element.getChild(i);
			context.getParsers().getParserByElementName(child)
					.load(child, context);
		}
		return table;
	}

	@Override
	public void save(XmlWriter writer, Actor actor, LayoutParserContext context)
			throws LayoutParseException {
		// TODO Auto-generated method stub

	}

}
