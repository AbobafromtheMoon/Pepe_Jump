package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State //абстрактный класс для работы с графикой
{
    //protected - данные могут получить либо другие классы того же пакета, либо объекты классов "секретных" переменных
    protected OrthographicCamera camera; //камера, показывающая юзеру игровой мир и позицию персонада
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm) //конструктор класса
    {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt); //нужен для подсчетных методов рендера: прыжки персонажа, сколько пикселей считалось и тд
    public abstract void render(SpriteBatch sb); //нужен для рисовки на экране мира и тд
    public abstract void dispose();
}
