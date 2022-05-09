package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FlappyFrog;
import com.mygdx.game.sprites.Frog;
import com.mygdx.game.sprites.Stump;


public class PlayState extends State
{
    private Frog frog;
    private Texture background;
    private Texture ground;
    private Texture restartbutton;

    private Stump stump;

    private static final int STUMP_SPACING = 125;
    private static final int STUMP_COUNT = 50;
    private static final int GROUND_Y = -50;

    private Vector2 groundPos1, groundPos2;

    private boolean gameover; //конец игры

    private Array<Stump> stumpes; //массив пеньков

    public PlayState(GameStateManager gsm)
    {
        super(gsm);
        gameover = false;

        frog = new Frog(50, 250); //генерируем лягушку в определенных координатах
        background = new Texture("clouds_1.png"); //создаем текстуры фона, земли и кнопки рестарта
        ground = new Texture("ground.png");
        restartbutton = new Texture("gameover.png");

        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y);

        camera.setToOrtho(false, FlappyFrog.WIDTH / 2, FlappyFrog.HEIGHT / 2);
        stumpes = new Array<Stump>();

        for (int i = 1; i < STUMP_COUNT; i++)
        {
            stumpes.add(new Stump(i * (STUMP_SPACING + Stump.STUMP_WIDTH))); //добавляем пеньки
        }
    }
    @Override
    protected void handleInput()
    {
        if (Gdx.input.isTouched())
        {
            if (gameover)
                gsm.set(new PlayState(gsm));
            else
                frog.jump();
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
        frog.update(dt);
        updateground();
        camera.position.x = frog.getPosition().x + 80; //получаем позицию лягушки
        for (Stump stump : stumpes) {
            if (camera.position.x - (camera.viewportWidth / 2) > stump.getPosTop().x + stump.getStumpTop().getWidth()) //если пеньки за экраном
            {
                stump.reposition(stump.getPosTop().x + ((Stump.STUMP_WIDTH + STUMP_SPACING) * STUMP_COUNT));
            }
            if (stump.crash(frog.getbounds())) //если произошло столкновение, то gsm обновляет гуишку на новую и ставит конец игры
            {
                frog.crash = true;
                gameover = true;
            }
        }
        if (frog.getPosition().y <= ground.getHeight() + GROUND_Y) //убиваем лягушку при столкновении с землей
        {
            gameover = true;
            frog.crash = true;
        }
        camera.update(); //обновляем вид камеры
    }
    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(camera.combined); //позиция камеры
        sb.begin(); //начинаем процесс
        sb.draw(background, camera.position.x - (camera.viewportWidth / 2), 0);
        sb.draw(frog.getFrog(), frog.getPosition().x, frog.getPosition().y); //рисуем иконку персонажа
        for (Stump stump : stumpes) //рисуем пеньки
        {
            sb.draw(stump.getStumpTop(), stump.getPosTop().x, stump.getPosTop().y);
            sb.draw(stump.getBotStump(), stump.getPosBottom().x, stump.getPosBottom().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y); //рисуем землю
        sb.draw(ground, groundPos2.x, groundPos2.y);
        if (gameover)
        {
            sb.draw(restartbutton, camera.position.x - restartbutton.getWidth() / 2, camera.position.y);
        }
        sb.end(); //заканчиваем процесс
    }

    @Override
    public void dispose()
    {
        background.dispose(); //располагаем фон, лягушку, землю и пеньки
        frog.dispose();
        ground.dispose();
        for (Stump stump: stumpes)
            stump.dispose();
        System.out.println("Play State Disposed");
    }

    private void updateground()
    {
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);

        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}
