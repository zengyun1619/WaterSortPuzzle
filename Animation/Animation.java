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

    private static final double xIndent = 10.0;
    private static final double yIndent = 40.0;
    private static final double yIndentAdditionalForSelectedBottle = 3.0;
    
    private static final double bottleHalfWidth = 3.5;
    private static final double bottleGap = 15.0;
    
    private double yIndentForSelectedBottle;
    private int selectedBottleIndex = -1;

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

        StdDraw.rectangle(xScale / 2.0, yScale / 8.0 * 7.0 , xScale / 12.0, yScale / 16.0);
        StdDraw.text(xScale / 2.0, yScale / 8.0 * 7.0, "P: Play Game");

        StdDraw.rectangle(xScale / 2.0, yScale / 8.0 * 5.0, xScale / 12.0, yScale / 16.0);
        StdDraw.text(xScale / 2.0, yScale / 8.0 * 5.0, "S: Switch User");

        StdDraw.rectangle(xScale / 2.0, yScale / 8.0 * 3.0, xScale / 12.0, yScale / 16.0);
        StdDraw.text(xScale / 2.0, yScale / 8.0 * 3.0, "J: Jump to Level");

        StdDraw.rectangle(xScale / 2.0, yScale / 8.0 * 1.0, xScale / 12.0, yScale / 16.0);
        StdDraw.text(xScale / 2.0, yScale / 8.0 * 1.0, "Q: Quit");

        StdDraw.show();
    }

    public void gameDisplayInitialize() {
        StdDraw.setCanvasSize(canvasWidth, canvasHeight);
        StdDraw.clear(Color.BLACK);

        StdDraw.setXscale(0, xScale);
        StdDraw.setYscale(0, yScale);

        StdDraw.enableDoubleBuffering();
        StdDraw.setPenRadius(0.005);

        StdDraw.clear(Color.BLACK);
    }

    public void gameDisplay(BottleSet bottleSet) {

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(xScale / 2.0, yScale / 2.0, xScale / 2.0, yScale / 2.0);

        for (int i = 0; i < bottleSet.getBottleCount(); i++) {
            Water[] bottle = bottleSet.getBottle(i);
            yIndentForSelectedBottle = yIndent;
            if (i == selectedBottleIndex) {
                yIndentForSelectedBottle += yIndentAdditionalForSelectedBottle;
            }
            if (bottleSet.getWaterLevel(bottle) > 0) {
                StdDraw.setPenColor(bottle[0].getColor());
                StdDraw.filledCircle(i * bottleGap + xIndent, 0 * bottleHalfWidth * 2.0 + yIndentForSelectedBottle - bottleHalfWidth / 2, bottleHalfWidth);
            }
            for (int j = 0; j < bottleSet.getWaterLevel(bottle); j ++) {
                StdDraw.setPenColor(bottle[j].getColor());
                StdDraw.filledSquare(i * bottleGap + xIndent, j * bottleHalfWidth * 2.0 + yIndentForSelectedBottle, bottleHalfWidth);
            }
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setPenRadius(0.005);
            StdDraw.arc(i * bottleGap + xIndent, yIndentForSelectedBottle - bottleHalfWidth / 2.0, bottleHalfWidth, 180, 360);
            StdDraw.line(i * bottleGap + xIndent + bottleHalfWidth, yIndentForSelectedBottle - bottleHalfWidth / 2.0
                    , i * bottleGap + xIndent + bottleHalfWidth, yIndentForSelectedBottle - bottleHalfWidth + bottleHalfWidth * 2.0 * bottleSet.getWaterLevelMax());
            StdDraw.line(i * bottleGap + xIndent - bottleHalfWidth, yIndentForSelectedBottle - bottleHalfWidth / 2.0
                    , i * bottleGap + xIndent - bottleHalfWidth, yIndentForSelectedBottle - bottleHalfWidth + bottleHalfWidth * 2.0 * bottleSet.getWaterLevelMax());
        }
        
        userControlDisplay();

        StdDraw.show();
    }

    public void userControlDisplay() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(xScale / 2.0, yScale - 10., yScale / 2.0, 10.0);
        StdDraw.setPenColor(Color.WHITE);

        Font font = new Font("ITALIC", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.text(xScale / 2.0, yScale - 5.0, "R: Reset    U: Undo    A: Add Bottle    S: Solution    M: Menu     Q: Quit");
    }

    public int getBottleIndexAtLocation(double x, double y, BottleSet bottleSet) {
        if (y <= yIndent - bottleHalfWidth + bottleHalfWidth * 2.0 * bottleSet.getWaterLevelMax() && y >= yIndent - bottleHalfWidth) {
            if ( (x - xIndent) / bottleGap - Math.floor((x - xIndent) / bottleGap) <= bottleHalfWidth / bottleGap && Math.floor((x - xIndent) / bottleGap) >= 0 ) {
                return (int) Math.floor((x - xIndent) / bottleGap);
            } else if ( (x - xIndent) / bottleGap - Math.ceil((x - xIndent) / bottleGap) >= - bottleHalfWidth / bottleGap && Math.ceil((x - xIndent) / bottleGap) <= bottleSet.getBottleCount() - 1)  {
                return (int) Math.ceil((x - xIndent) / bottleGap);
            }
        }
        return -1;
    }
    
    public void selectBottle(int selectedBottleIndex) {
        if (this.selectedBottleIndex == selectedBottleIndex) {
            this.selectedBottleIndex = -1;
        } else {
            this.selectedBottleIndex = selectedBottleIndex;
        }
    }
    public int getSelectedBottleIndex() {
        return this.selectedBottleIndex;
    }
    
    public void showHeadsUpDisplay(double x, double y) {
        Font font = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(20, 5, 10, 3);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text((double) 20, (double) 4, String.valueOf(x));
        StdDraw.text((double) 20, (double) 6, String.valueOf(y));
        StdDraw.show();
    }

    public void gameActionDisplay(UserAction action) {

    }

    public void gameWinDisplay() {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(xScale / 2.0, yScale / 2.0, "YOU WIN!");
        Font fontSmall = new Font("Monaco", Font.BOLD, 15 );
        StdDraw.setFont(fontSmall);
        StdDraw.text(xScale / 2.0, yScale / 4.0, "Press any key for the next level.");
        StdDraw.show();
    }

    public void gameLoseDisplay() {

    }

    public void pause() {
        StdDraw.pause(500);
    }

}
