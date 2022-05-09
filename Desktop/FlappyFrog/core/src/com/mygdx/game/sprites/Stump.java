package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Stump
{
    private Texture stump, stumpTop;
    private Vector2 posTop, posBottom;
    private Random random;

    public static final int STUMP_WIDTH = 52;
    private static final int FLUCTUATION = 130; //расстояние между пеньками по ОХ
    private static final int STUMP_GAP = 100; //расстояние между пеньками по ОУ
    private static final int LOWEST_OPENING = 120; //самый маленький проход
    private Rectangle boundsT, boundsB; //невидимые прямоугольники в пеньках, которые будут границами

    public Stump(float x)
    {
        stump = new Texture("bottomtube.png"); //создаем текстуры пеньков
        stumpTop = new Texture("toptube.png");
        random = new Random();

        posTop = new Vector2(x, random.nextInt(FLUCTUATION) + STUMP_GAP + LOWEST_OPENING); //создаем позиции пеньков
        posBottom = new Vector2(x, posTop.y - STUMP_GAP - stump.getHeight());

        boundsT = new Rectangle(posTop.x, posTop.y, stumpTop.getWidth(), stumpTop.getHeight()); //создаем прямоугольники
        boundsB = new Rectangle(posBottom.x, posBottom.y, stump.getWidth(), stump.getHeight());
    }

    public Texture getBotStump()
    {
        return stump;
    }
    public Texture getStumpTop()
    {
        return stumpTop;
    }
    public Vector2 getPosTop()
    {
        return posTop;
    }
    public Vector2 getPosBottom()
    {
        return posBottom;
    }

    public void reposition(float x) //генерируем пеньки на карте
    {
        posTop.set(x, random.nextInt(FLUCTUATION) + STUMP_GAP + LOWEST_OPENING); //устанавливаем позицию пеньков через рандом
        posBottom.set(x, posTop.y - STUMP_GAP - stump.getHeight());
        boundsT.setPosition(posTop.x, posTop.y); //устанавливаем позицию прямоугольников
        boundsB.setPosition(posBottom.x, posBottom.y);
    }
    public boolean crash(Rectangle player) //столкновение с прямоугольниками
    {
        return player.overlaps(boundsT) || player.overlaps(boundsB); //overlaps - метод для столкновений двух фигур
    }

    public void dispose()
    {
        stump.dispose();
    }
}
