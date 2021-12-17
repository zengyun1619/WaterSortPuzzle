package Test;

import Core.Engine;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Test {
    public static void main(String args[]) {
        StdDraw.setCanvasSize(800, 400);
        StdDraw.clear(new Color(0, 0, 0));

        //StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 80);

        StdDraw.setPenColor(Color.red);
        StdDraw.filledSquare(10 + 0.5, 30 + 0.5, 0.5);

        StdDraw.show();
    }


}
