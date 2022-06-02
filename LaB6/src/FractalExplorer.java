import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.*;

public class FractalExplorer
{
    private int displaySize;
    private JImageDisplay image;
    private JComboBox<String> chooser;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;
    private JButton saveButton;
    private JButton resetButton;
    private int rows_remaining;

    private void enableUI(boolean val) //включает и выключает кнопки с выпадающим списком в пользовательском интерфейсе
    {
        chooser.setEnabled(val);
        saveButton.setEnabled(val);
        resetButton.setEnabled(val);
    }

    private class FractalWorker extends SwingWorker<Object, Object> //отвечает за вычисление значений цвета для одной строки фрактала
    {
        private int Y; //координата строки
        private int[] RGB_values; //цвета для каждого пикселя в строке
        public FractalWorker(int y)
        {
            Y = y;
        }
        public Object doInBackground() //выполнение длительной задачи в фоновом потоке
        {
            RGB_values = new int[displaySize];
            Double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, Y);
            for (int x = 0; x < displaySize; x++ )
            {
                Double xCoord = FractalGenerator.getCoord(range.y, range.y + range.width, displaySize, x);
                int iteration = fractal.numIterations(xCoord, yCoord);
                int rgbColor = 0;
                if (iteration >= 0)
                {
                    float hue = 0.7f + (float) iteration / 200f; //выбираем цвет пикселя на основе числа итераций
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                else
                {
                    image.drawPixel(x, Y, 0);
                }
                RGB_values[x] = rgbColor;
            }
            return null;
        }
        public void done() //после фоновой задачи перерисовывает измененную часть изображения
        {
            for (int x = 0; x < displaySize; x++)
            {
                image.drawPixel(x, Y, RGB_values[x]);
            }
            image.repaint(0, 0, Y, displaySize, 1);
            if (rows_remaining-- < 1)
                enableUI(true);
        }
    }

    private class ButtonHandler implements ActionListener //внутренний класс для работы с кнопками и названиями
    {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (e.getSource() == chooser)
            {
                String selectedItem = chooser.getSelectedItem().toString();
                if (selectedItem.equals(Mandelbrot.getString())) fractal = new Mandelbrot(); //создаем выбранные фракталы
                if (selectedItem.equals(Tricorn.getString())) fractal = new Tricorn();
                if (selectedItem.equals(Burning_ship.getString())) fractal = new Burning_ship();
                range = new Rectangle2D.Double();
                fractal.getInitialRange(range);
                drawFractal();
            } else if (cmd.equals("reset")) ////если кнопка сброса - сбросить дисплей и нарисовать фрактал
            {
                range = new Rectangle2D.Double();
                fractal.getInitialRange(range);
                drawFractal();
            } else if (cmd.equals("save")) //если кнопка сохранить - сохранить изображение фрактала
            {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = chooser.getSelectedFile();
                        String filePath = f.getPath();
                        if (!filePath.toLowerCase().endsWith(".png")) {
                            f = new File(filePath + ".png");
                        }
                        ImageIO.write(image.getImage(), "png", f);
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(null, "Error: Couldn't save image ( " + exc.getMessage() + " )");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Couldn't recognize action");
            }
        }
    }
    private class MouseHandler extends MouseAdapter //внутренний класс для обработки дисплея
    {
        public void mouseClicked(MouseEvent e)
        {
            if (rows_remaining > 0) return;
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public FractalExplorer(int size) //конструктор, принимающий и сохраняющий размер изображения, а также инициализирующий
    {                                // объекты диапазона и фрактал-генератора
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
    }

    public void createAndShowGUI()  //по аналогии с лр 4
    {
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        ButtonHandler handler = new ButtonHandler();
        JPanel fractalPanel = new JPanel();
        JLabel label = new JLabel("Fractal: ");
        fractalPanel.add(label);

        chooser = new JComboBox<String>();
        chooser.addItem(Mandelbrot.getString());
        chooser.addItem(Tricorn.getString());
        chooser.addItem(Burning_ship.getString());
        chooser.addActionListener(handler);
        fractalPanel.add(chooser);
        frame.getContentPane().add(fractalPanel, BorderLayout.NORTH);

        image = new JImageDisplay(displaySize, displaySize);
        frame.getContentPane().add(image, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        saveButton = new JButton("Save Image");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(handler);
        buttonsPanel.add(saveButton);
        resetButton = new JButton("Reset Display");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(handler);
        buttonsPanel.add(resetButton);

        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        frame.addMouseListener(new MouseHandler());
        frame.addMouseMotionListener(new MouseHandler());
        frame.addMouseWheelListener(new MouseHandler());

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public void drawFractal()
    {
        enableUI(false); //отключает все элементы интерфейса во время рисования фрактала
        for (int y = 0; y < displaySize; y++)
        {
            FractalWorker worker = new FractalWorker(y);
            worker.execute(); //запускает фоновый поток в фоновом режиме
        }
        image.repaint();
    }
}