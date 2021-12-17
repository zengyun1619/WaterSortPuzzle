package Animation;

import GameElement.BottleSet;
import GameElement.Water;
import User.UserAction;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Animation{

    private static final int canvasWidth = 1200;
    private static final int canvasHeight = 600;
    private static final double xScale = 200.0;
    private static final double yScale = 100.0;

    private static final double xIndent = 40.0;
    private static final double yIndent = 40.0;
    
    private static final double bottleWidth = 3.0;
    private static final double bottleGap = 15.0;

    public Animation() {

    }

    public void startWindowDisplay() {
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.clear(Color.BLACK);

        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("ITALIC", Font.BOLD, 25);
        StdDraw.setFont(font);

        StdDraw.rectangle(xScale / 2, yScale / 8 * 6 , xScale / 12, yScale / 16);
        StdDraw.text(xScale / 2, yScale / 8 * 6, "N: New Game");

        StdDraw.rectangle(xScale / 2, yScale / 8 * 5, xScale / 12, yScale / 16);
        StdDraw.text(xScale / 2, yScale / 8 * 5, "S: Save Game");

        StdDraw.rectangle(xScale / 2, yScale / 8 * 4, xScale / 12, yScale / 16);
        StdDraw.text(xScale / 2, yScale / 8 * 4, "L: Load Game");

        StdDraw.rectangle(xScale / 2, yScale / 8 * 3, xScale / 12, yScale / 16);
        StdDraw.text(xScale / 2, yScale / 8 * 3, "Q: Quit");

        StdDraw.show();



    }

    public void gameDisplay(BottleSet bottleSet) {

        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.clear(Color.BLACK);

        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);

        for (int i = 0; i < bottleSet.getBottleCount(); i++) {
            Water[] bottle = bottleSet.getBottle(i);
            if (bottleSet.getWaterLevel(bottle) > 0) {
                StdDraw.setPenColor(bottle[0].getColor());
                StdDraw.filledCircle(i * bottleGap + xIndent, 0 * bottleWidth * 2 + yIndent - bottleWidth / 2, bottleWidth);
            }
            for (int j = 0; j < bottleSet.getWaterLevel(bottle); j ++) {
                StdDraw.setPenColor(bottle[j].getColor());
                StdDraw.filledSquare(i * bottleGap + xIndent, j * bottleWidth * 2 + yIndent, bottleWidth);
            }
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setPenRadius(0.005);
            StdDraw.arc(i * bottleGap + xIndent, yIndent - bottleWidth / 2, bottleWidth, 180, 360);
            StdDraw.line(i * bottleGap + xIndent + bottleWidth, yIndent - bottleWidth / 2
                    , i * bottleGap + xIndent + bottleWidth, yIndent - bottleWidth + bottleWidth * 2 * bottleSet.getWaterLevelMax());
            StdDraw.line(i * bottleGap + xIndent - bottleWidth, yIndent - bottleWidth / 2
                    , i * bottleGap + xIndent - bottleWidth, yIndent - bottleWidth + bottleWidth * 2 * bottleSet.getWaterLevelMax());
        }


        userControlDisplay();

        StdDraw.show();



    }

    public void userControlDisplay() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(xScale / 2, yScale - 10, yScale/2, 10);
        StdDraw.setPenColor(Color.WHITE);

        Font font = new Font("ITALIC", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(xScale / 2, yScale - 5.0, "R: reset    U: undo    A: add bottle    S: solution    M: menu     Q: quit");
    }

    public void showHeadsUpDisplay(double x, double y) {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(2, 2, 3, 3);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text((double) 2, (double) 0, String.valueOf(Math.round(x)));
        StdDraw.text((double) 2, (double) 2, String.valueOf(Math.round(y)));
        StdDraw.show();
    }

    public void gameActionDisplay(UserAction action) {

    }

    public void gameWinDisplay() {

    }

    public void gameLoseDisplay() {

    }
}
