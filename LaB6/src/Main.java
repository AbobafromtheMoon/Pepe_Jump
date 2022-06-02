public class Main {

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800); //экземпляр класса с размером из. 800
        displayExplorer.createAndShowGUI(); //вызываем метод создания и показа фрактала
        displayExplorer.drawFractal(); //рисуем фрактал
    }
}