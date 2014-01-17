package at.highstreeto.XMLLayoutParser.element;

import at.highstreeto.XMLLayoutParser.LayoutParseException;
import at.highstreeto.XMLLayoutParser.LayoutParserContext;
import at.highstreeto.XMLLayoutParser.element.base.ElementParser;
import at.highstreeto.XMLLayoutParser.element.base.ElementParserHelper;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.XmlWriter;

public class ImageButtonParser implements ElementParser {

	@Override
	public String getElementName() {
		return "ImageButton";
	}

	@Override
	public Class<? extends Actor> getActorClass() {
		return ImageButton.class;
	}

	@Override
	public Actor load(Element element, LayoutParserContext context)
			throws LayoutParseException {
		ImageButton imageButton = new ImageButton(context.getSkin(),
				ElementParserHelper.getStyle(element));

		context.addActor(imageButton, element);
		ElementParserHelper.setActorName(element, imageButton);

		return imageButton;
	}

	@Override
	public void save(XmlWriter writer, Actor actor, LayoutParserContext context)
			throws LayoutParseException {
	}

}
