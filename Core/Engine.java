package Core;

import Animation.Animation;
import GameElement.*;
import User.UserAction;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Engine{

    private Random rd;

    private Animation animationImplementor = new Animation();


    private BottleSet bottleSet;

    private ArrayList<UserAction> userActionArrayList = new ArrayList<>();


    public Engine(){

    }


    public void startGame() {
        animationImplementor.startWindowDisplay();
        char c = getKey();
        if (c == 'N') {
            if (bottleSet == null) {
                newGame();
            } else {
                playGame();
            }
        }
        if (c == 'Q') {
            System.exit(0);
        }
    }


    public void newGame() {
        int bottleCount = 8;
        int seed = 200;
        initializeGame(bottleCount, seed);
        playGame();
    }

    private void initializeGame(int bottleCount, int seed){
        rd = new Random(seed);
        bottleSet = new BottleSet(bottleCount, rd);
        animationImplementor.gameDisplay(bottleSet);
    }

    private char getKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                return Character.toUpperCase(StdDraw.nextKeyTyped());
            }
        }
    }

    private void playGame(){
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                // user control using keyboard
            }
            if (StdDraw.isMousePressed()) {
                double[] coordinate = new double[2];
                coordinate[0] = StdDraw.mouseX();
                coordinate[1] = StdDraw.mouseY();
                // user control using mouse
            }
        }
    }




    public void resetGame() {

    }


    public void saveGame(String userID){

    }


    public void loadGame(String userID){

    }




}
