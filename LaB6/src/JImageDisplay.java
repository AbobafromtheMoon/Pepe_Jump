import javax.swing.*;
import java.awt.image.*;
import java.awt.*;

public class JImageDisplay extends JComponent
{
    private BufferedImage DisplayImage;
    public BufferedImage getImage()
    {
        return DisplayImage;
    }
    public JImageDisplay(int width, int height)
    {
        DisplayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); //объект с шириной и высотой
        Dimension imageDimension = new Dimension(width, height); //создаем объект, который поможет отбразить изображение
        super.setPreferredSize(imageDimension);
    }
    @Override
    public void paintComponent(Graphics g) //выводит на экран данные изображния
    {
        super.paintComponent(g); //вызывает методы суперкласса для правильного отображения объектов
        g.drawImage(DisplayImage, 0, 0, DisplayImage.getWidth(), DisplayImage.getHeight(), null);
    }
    public void clearImage() //устанавливает все пиксели в черный цвет
    {
        int[] blankArray = new int[getWidth() * getHeight()];
        DisplayImage.setRGB(0, 0, getWidth(), getHeight(), blankArray, 0, 1);
    }
    public void drawPixel(int x, int y, int rgbColor) //устанавливает пиксели в определенные цвета
    {
        DisplayImage.setRGB(x, y, rgbColor);
    }
}

