package at.highstreeto.xnllayoutparser;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class ExampleGame extends ApplicationAdapter {

    private Stage stage;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = setupSkin();

        FileHandle layoutFile = Gdx.files.internal("example.xml");
        LayoutParser parser = new LayoutParser();
        try {
            parser.load(layoutFile, stage, skin);

            stage.getRoot().findActor("btStart").addListener(
                    new ChangeListener() {

                        @Override
                        public void changed(ChangeListener.ChangeEvent ce,
                                Actor actor) {
                            TextField tfServer = (TextField) stage.getRoot().
                            findActor(
                                    "tfServer");
                            TextField tfPlayerName
                            = (TextField) stage.getRoot().findActor(
                                    "tfPlayerName");
                            Label laStatus = (Label) stage.getRoot().findActor(
                                    "laStatus");
                            
                            laStatus.setText("");

                            System.out.println("Server: " + tfServer.getText());
                            System.out.println("Spieler: " + tfPlayerName.
                                    getText());

                            if (tfServer.getText().isEmpty()) {
                                laStatus.setText(
                                        "Es muss ein Server angeben werden!");
                                return;
                            }
                            if (tfPlayerName.getText().isEmpty()) {
                                laStatus.setText(
                                        "Es muss ein Spieler Name angeben werden!");
                                return;
                            }
                        }
                    });
        } catch (LayoutParseException ex) {
            Logger.getLogger(ExampleGame.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setups a simple skin without any ressource
     *
     * @return A simple Skin
     */
    private Skin setupSkin() {
        Skin skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        skin.add("default", new BitmapFont());

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.BLACK;
        skin.add("default", labelStyle);

        LabelStyle redLabelStyle = new LabelStyle();
        redLabelStyle.font = skin.getFont("default");
        redLabelStyle.fontColor = Color.RED;
        skin.add("red", redLabelStyle);

        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.background = skin.newDrawable("white", Color.LIGHT_GRAY);
        skin.add("default", textFieldStyle);
        return skin;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
