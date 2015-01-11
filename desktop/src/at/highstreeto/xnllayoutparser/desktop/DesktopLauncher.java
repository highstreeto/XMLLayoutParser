package at.highstreeto.xnllayoutparser.desktop;

import at.highstreeto.xnllayoutparser.ExampleGame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        ApplicationListener game = new ExampleGame();
        
        new LwjglApplication(game, conf);
    }
}
