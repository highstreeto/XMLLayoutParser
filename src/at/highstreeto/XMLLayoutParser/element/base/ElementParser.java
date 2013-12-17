package at.highstreeto.XMLLayoutParser.element.base;

import at.highstreeto.XMLLayoutParser.LayoutParseException;
import at.highstreeto.XMLLayoutParser.LayoutParser;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * The abstract class for element-wise parsers. The implementations of this
 * class have to provide a way to load / save an actor to a XML file.
 * 
 * @author highstreeto
 * 
 */
public abstract class ElementParser {

	private Skin skin;
	private LayoutParser parser;

	/**
	 * 
	 * @param skin
	 */
	public ElementParser(Skin skin) {
		this.skin = skin;
	}

	/**
	 * Returns the name of the name of the XML element
	 * 
	 * @return
	 */
	public abstract String getElementName();

	/**
	 * 
	 * @return
	 */
	public abstract Class<? extends Actor> getActorClass();

	/**
	 * Returns the skin, that will be used to while loading actors
	 * 
	 * @return Skin
	 */
	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	/**
	 * Returns the corresponding LayoutParser. This instance will be used to
	 * parse the children of a element.
	 * 
	 * @return
	 */
	public LayoutParser getParser() {
		return parser;
	}

	/**
	 * Sets the corresponding LayoutParser. It is only called by the
	 * LayoutParser, when a new ElementParser is added
	 * 
	 * @param parser
	 */
	void setParser(LayoutParser parser) {
		this.parser = parser;
	}

	/**
	 * 
	 * @param element
	 * @return
	 * @throws LayoutParseException
	 */
	public abstract Actor load(Element element) throws LayoutParseException;

	/**
	 * 
	 * @param actor
	 * @throws LayoutParseException
	 */
	public abstract void save(Actor actor) throws LayoutParseException;
}
