package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Frog
{
    private static final int GRAVITY = -15;
    private static final int MOVE = 100;
    private Vector3 position; //вектор позиций
    private Vector3 speed;
    private Rectangle bounds;

    public boolean crash;

    private Texture frog;

    public Frog(int x, int y) //конструктор лягушки
    {
        position = new Vector3(x, y, 0); //ставим позицию
        speed = new Vector3(0, 0 ,0); //ставим скорость
        frog = new Texture("peepo_angel.png"); //добавляем текстуру
        bounds = new Rectangle(x, y, frog.getWidth(), frog.getHeight()); //добавляем прямоугольник для "столкновений"
        crash = false;
    }

    public void update(float dt)
    {
        //два if нужны, чтобы текстура персонажа не проваливалась за установленные рамки
        speed.add(0, GRAVITY, 0); //добавляем графитацию по оси ОУ
        speed.scl(dt); //считаем дельта-тайм
        if (!crash)
        {
            position.add(MOVE * dt, speed.y,0);
        }
        if (position.y < 0)
        {
            position.y = 0;
        }
        speed.scl(1/dt); //снова считаем дельта-тайм
        bounds.setPosition(position.x, position.y);
    }
    public Rectangle getbounds()
    {
        return bounds;
    }

    public Vector3 getPosition()
    {
        return position;
    }

    public Texture getFrog()
    {
        return frog;
    }

    public void jump()
    {
        speed.y = 180;
    }

    public void dispose() {
        frog.dispose();
    }

}
