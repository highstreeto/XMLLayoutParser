package at.highstreeto.XMLLayoutParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.esotericsoftware.tablelayout.Cell;

public class LayoutParser {
	public interface ElementParser {
		Actor parseElement(Element element, Map<Element, Actor> actors,
				Skin skin, LayoutParser layoutParser)
				throws LayoutParseException;
	}

	private FileHandle layoutFile;
	private Skin skin;
	private Map<Element, Actor> actors;
	private Map<String, Actor> namedActors;
	private Map<String, ElementParser> parsers;

	public LayoutParser(FileHandle layoutFile, Skin skin) {
		this.layoutFile = layoutFile;
		this.skin = skin;

		actors = new HashMap<>();
		namedActors = new HashMap<>();
		parsers = new HashMap<>();
		
		parsers.put("Label", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				String text = element.getText();
				Label label = new Label(text == null ? "" : text, skin);

				actors.put(element, label);
				return label;
			}
		});
		parsers.put("TextField", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				String text = element.getText();
				TextField textField = new TextField(text == null ? "" : text,
						skin);

				actors.put(element, textField);
				return textField;
			}
		});
		parsers.put("TextButton", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				String text = element.getText();
				TextButton textButton = new TextButton(
						text == null ? "" : text, skin);

				actors.put(element, textButton);
				return textButton;
			}
		});
		parsers.put("Table", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				Table table = new Table();

				if (element.getAttributes().containsKey("fillParent")) {
					table.setFillParent(element
							.getBooleanAttribute("fillParent"));
				}
				if (element.getAttributes().containsKey("debug")) {
					String[] parts = element.getAttribute("debug").split(" ");
					for (String i : parts) {
						switch (i) {
							case "cell":
								table.debugCell();
								break;
							case "widget":
								table.debugWidget();
								break;
							case "table":
								table.debugTable();
								break;
							default:
								break;
						}
					}
				}

				actors.put(element, table);
				for (int i = 0; i < element.getChildCount(); i++) {
					Element child = element.getChild(i);
					layoutParser.parseElement(child);
				}
				return table;
			}
		});
		parsers.put("Row", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				Table table = (Table) actors.get(element.getParent());
				for (int i = 0; i < element.getChildCount(); i++) {
					Element child = element.getChild(i);
					layoutParser.parseElement(child);
				}
				table.row();
				return null;
			}
		});
		parsers.put("Cell", new ElementParser() {

			@Override
			public Actor parseElement(Element element,
					Map<Element, Actor> actors, Skin skin,
					LayoutParser layoutParser) throws LayoutParseException {
				Table table = (Table) actors.get(element.getParent()
						.getParent());
				Actor actor = layoutParser.parseElement(element.getChild(0));
				Cell<?> cell = table.add(actor);

				if (element.getAttributes().containsKey("fill")) {
					switch (element.getAttribute("fill")) {
						case "x":
							cell.fillX();
							break;
						case "y":
							cell.fillY();
							break;
						case "xy":
							cell.fill();
							break;
						default:
							throw new LayoutParseException();
					}
				}
				// Padding - Format: Top Left Bottom Right
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
					int colspan = Integer.parseInt(element
							.getAttribute("colspan"));
					cell.colspan(colspan);
				}
				return null;
			}
		});
	}
	
	public void putParser(String elementName, ElementParser elementParser) {
		parsers.put(elementName, elementParser);
	}
	
	public Collection<Actor> load() throws LayoutParseException {
		XmlReader reader = new XmlReader();
		try {
			Collection<Actor> stageActors = new ArrayList<>();
			Element rootElement = reader.parse(layoutFile);
			if (rootElement.getName().equals("Layout")) {
				for (int i = 0; i < rootElement.getChildCount(); i++) {
					Element child = rootElement.getChild(i);
					Actor actor = parseElement(child);
					stageActors.add(actor);
				}

				for (Entry<Element, Actor> i : actors.entrySet()) {
					Element element = i.getKey();
					if (element.getAttributes() != null
							&& element.getAttributes().containsKey("name")) {
						namedActors.put(element.getAttribute("name"),
								i.getValue());
					}
				}
				return stageActors;
			} else {
				throw new LayoutParseException();
			}
		} catch (IOException e) {
			throw new LayoutParseException(e);
		}
	}

	public void load(Stage stage) throws LayoutParseException {
		for (Actor i : load()) {
			stage.addActor(i);
		}
	}

	/**
	 * 
	 * @param element
	 * @throws LayoutParseException
	 */
	public Actor parseElement(Element element) throws LayoutParseException {
		if (parsers.containsKey(element.getName())) {
			return parsers.get(element.getName()).parseElement(element, actors,
					skin, this);
		} else {
			throw new LayoutParseException();
		}
	}

	public Actor getNamedActor(String name) {
		return namedActors.get(name);
	}
}
