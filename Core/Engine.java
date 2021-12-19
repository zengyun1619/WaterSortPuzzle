package Core;

import Animation.Animation;
import GameElement.*;
import User.MoveWater;
import User.UserAction;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Engine{

    private Random rd;
    private int seed;
    private int bottleCountInitial;

    private Animation animationImplementor = new Animation();


    private BottleSet bottleSet;
    private BottleSet bottleSetBackup;

    private int additionalBottleCount = 0;
    private int additionalBottleLimit = 1;

    private ArrayList<UserAction> userActionArrayList = new ArrayList<>();

    private Solver solver = new Solver();

    public Engine(){

    }


    public void startGame() {
        animationImplementor.startWindowDisplay();
        char c = getKey();

        //P: Play Game. S: Save Game. L: Load Game. Q: Quit

        if (c == 'P') {
            if (bottleSet == null) {
                this.bottleCountInitial = 8;
                this.seed = 200;
                newGame();
            } else {
                animationImplementor.gameDisplay(bottleSet);
                playGame();
            }
        }
        if (c == 'Q') {
            System.exit(0);
        }
    }


    public void newGame() {
        initializeGame(this.bottleCountInitial, this.seed);
        animationImplementor.gameDisplayInitialize();
        animationImplementor.gameDisplay(bottleSet);
        playGame();
    }

    private void initializeGame(int bottleCount, int seed){
        rd = new Random(seed);
        bottleSet = new BottleSet(bottleCount);
        bottleSet.initiate(rd);
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
                // R: Reset    U: Undo    A: Add Bottle    S: Solution    M: Menu     Q: Quit

                if (c == 'Q') {
                    System.exit(0);
                } else if (c == 'M') {
                    startGame();
                } else if (c == 'S') {
                    solver.solve(bottleSet);
                } else if (c == 'A') {
                    if (additionalBottleCount < additionalBottleLimit) {
                        additionalBottleCount += 1;
                        bottleSet.addNewEmptyBottle();
                        animationImplementor.gameDisplay(bottleSet);
                    }
                } else if (c == 'U') {
                    if (!userActionArrayList.isEmpty()) {
                        userActionArrayList.get(userActionArrayList.size() - 1).unDoAction(bottleSet);
                        userActionArrayList.remove(userActionArrayList.size() - 1);
                        animationImplementor.gameDisplay(bottleSet);
                    }
                } else if (c == 'R') {
                    initializeGame(this.bottleCountInitial, this.seed);
                    animationImplementor.gameDisplayInitialize();
                    animationImplementor.gameDisplay(bottleSet);
                }
            }
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                int bottleIndex = animationImplementor.getBottleIndexAtLocation(x, y, bottleSet);
                if (bottleIndex != -1) {
                    if (animationImplementor.getSelectedBottleIndex() != -1 && animationImplementor.getSelectedBottleIndex() != bottleIndex) {
                        int movedWaterSize = bottleSet.moveTopWater(animationImplementor.getSelectedBottleIndex(), bottleIndex);
                        if (movedWaterSize > 0) {
                            userActionArrayList.add(new MoveWater(animationImplementor.getSelectedBottleIndex(), bottleIndex, movedWaterSize));
                        }
                        animationImplementor.selectBottle(animationImplementor.getSelectedBottleIndex());
                    } else {
                        animationImplementor.selectBottle(bottleIndex);
                    }
                    animationImplementor.gameDisplay(bottleSet);
                }
                StdDraw.pause(200);
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
