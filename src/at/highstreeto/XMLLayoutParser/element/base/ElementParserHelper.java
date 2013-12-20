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

package at.highstreeto.XMLLayoutParser.element.base;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.XmlReader.Element;

public final class ElementParserHelper {

	public static void setActorName(Element element, Actor actor) {
		if (element.getAttributes() != null
				&& element.getAttributes().containsKey("name")) {
			actor.setName(element.getAttribute("name"));
		}
	}

	public static String getStyle(Element element) {
		if (element.getAttributes() != null
				&& element.getAttributes().containsKey("styleName")) {
			return element.getAttribute("styleName");
		} else {
			return "default";
		}
	}

	public static void writeDefaultAttributes(XmlWriter writer, Actor actor)
			throws IOException {
		if (actor.getName() != null) {
			writer.attribute("name", actor.getName());
		}
	}
}
