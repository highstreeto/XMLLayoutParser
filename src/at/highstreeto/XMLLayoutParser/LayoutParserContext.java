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

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.XmlReader.Element;

public class LayoutParserContext {
	private Map<Actor, Element> elementsByActor;
	private Map<Element, Actor> actorsByElement;
	private ElementParsers parsers;
	private Skin skin;

	public LayoutParserContext() {
		elementsByActor = new HashMap<Actor, Element>();
		actorsByElement = new HashMap<Element, Actor>();
	}

	public ElementParsers getParsers() {
		return parsers;
	}

	public void setParsers(ElementParsers parsers) {
		this.parsers = parsers;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public void addActor(Actor actor, Element element) {
		elementsByActor.put(actor, element);
		actorsByElement.put(element, actor);
	}

	public Actor getActorByElement(Element element) {
		return actorsByElement.get(element);
	}

	public Element getElementByActor(Actor actor) {
		return elementsByActor.get(actor);
	}
}
