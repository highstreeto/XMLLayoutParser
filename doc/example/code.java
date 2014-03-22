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