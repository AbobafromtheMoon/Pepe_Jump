import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator
{
    private static final int MAX_ITERATIONS_COUNT = 2000;

    public void getInitialRange(Rectangle2D.Double range) //находит наиболее "интересную" область на плоскости
    {
        range.x = -2;
        range.y = -2;
        range.height = 4;
        range.width = 4;
    }
    public int numIterations(double x, double y) //реализует итеративную функцию фрактала
    {
        int k = 0;
        double Im = 0, Re = 0, Z = 0;
        while (k < MAX_ITERATIONS_COUNT && Z < 4)
        {
            k++;
            double nextIm = -2 * Re * Im + y;
            double nextRe = Math.pow(Re, 2) - Math.pow(Im, 2) + x;
            Z = Math.pow(nextRe, 2) + Math.pow(nextIm, 2);
            Re = nextRe;
            Im = nextIm;
        }
        if (k == MAX_ITERATIONS_COUNT) return -1;
        return k;
    }
    public static String getString()
    {
        return "Tricorn";
    }
}
