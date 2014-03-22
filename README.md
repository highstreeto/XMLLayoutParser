XMLLayoutParser
===============

The XMLLayoutParser reads in a XML file and generates scene2d-Actors out of the elements

libGDX is framework for making games on wide range of plattforms:
[libGDX](http://libgdx.badlogicgames.com/ "Official libGDX Website")

Examples
===============
![Example with XML File](https://raw.githubusercontent.com/highstreeto/XMLLayoutParser/master/doc/example/xml_result.png)

Essential code of this example:
```java
public Game() {
	stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
					false, spriteBatch);
	Gdx.input.setInputProcessor(stage);

	skin = setupSkin();

	FileHandle layout = Gdx.files.internal("gui/login.xml");
	final LayoutParser parser = new LayoutParser();
	try {
		parser.load(layout, stage, skin);
		setupStage();
	} catch (LayoutParseException e) {
		e.printStackTrace();
		throw e;
	}
}
