package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.MenuState;

public class FlappyFrog extends ApplicationAdapter
{
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private GameStateManager gsm;
	SpriteBatch batch;

	private Music music;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("android_assets_music.mp3")); //добавляем музыку
		music.setLooping(true); //повторяем мелодию
		music.setVolume(0.1f); //ставим громкость
		music.play();
		gsm.push(new MenuState(gsm));
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render ()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime()); //сначала обновляем (дельтатайм -- время между обновлением и рендером)
		gsm.render(batch); //потом рендерим
	}

	@Override
	public void dispose()
	{
		super.dispose();
		music.dispose();
	}
}
