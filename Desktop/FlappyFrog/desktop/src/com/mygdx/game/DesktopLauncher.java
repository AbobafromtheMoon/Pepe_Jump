package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.FlappyFrog;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration(); //создаем конфиг, по которому будет формироваться гуишка
		config.setWindowedMode(FlappyFrog.WIDTH, FlappyFrog.HEIGHT); //устанавливаем размер рамки
		config.setForegroundFPS(60);
		config.setTitle("Flappy Frog"); //ставим заголовок
		new Lwjgl3Application(new FlappyFrog(), config); //создаем гуишку
	}
}
