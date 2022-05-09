package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.FlappyFrog;

public class MenuState extends State
{
    private Texture background;
    private Texture playbutton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("heaven.jpeg"); //ставим текстуры фона и кнопки
        playbutton = new Texture("playbutton.png");
    }

    @Override
    public void handleInput() //ввод касаний для начала игры
    {
        if (Gdx.input.justTouched()) //если на панель нажали
        {
            gsm.set(new PlayState(gsm)); //переходим на игровое поле
            dispose(); //располагаем игровое поле
        }
    }

    @Override
    public void update(float dt) //обновляем касания
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappyFrog.WIDTH, FlappyFrog.HEIGHT); //отрисовываем задний фон
        sb.draw(playbutton, (FlappyFrog.WIDTH / 2) - (playbutton.getWidth() / 2), FlappyFrog.HEIGHT / 2); //отрисовываем кнопку и ее позицию
        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose(); //располагаем задний фон
        playbutton.dispose(); //располагаем кнопку
        System.out.println("Menu State Disposed");
    }
}
