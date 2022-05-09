package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;
public class GameStateManager //класс для обработки команд
{
    private Stack<State> states; //стэк перемещает команды пользователя "наверх"
    public GameStateManager() //конструктор стэка состояний
    {
        states = new Stack<State>();
    }
    public void push(State state) //добавляет состояния в стэк
    {
        states.push(state);
    }
    public void pop() //удаляет состояния/команды из стэка
    {
        states.pop();
    }
    public void set(State state)
    {
        states.pop();
        states.push(state);
    }
    public void update(float dt)
    {
        states.peek().update(dt); //обновляем верхушку
    }
    public void render(SpriteBatch sb)
    {
        states.peek().render(sb); //рендерим верхушку
    }
}
