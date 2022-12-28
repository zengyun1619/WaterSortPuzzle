package Core;

import Animation.Animation;
import GameElement.*;
import User.MoveWater;
import User.User;
import User.UserAction;
import edu.princeton.cs.introcs.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Engine{

    private Random rd;
    private int seed;
    private int bottleCountInitial;

    private Animation animationImplementor = new Animation();

    private BottleSet bottleSet;

    private int bufferBottleCount = 2;
    private int additionalBottleCount = 0;
    private int additionalBottleLimit = 1;

    private ArrayList<UserAction> userActionArrayList = new ArrayList<>();

    private Solver solver = new Solver();

    private User user;

    public Engine(){

    }


    public void startGame() {

        // fix bottle count
        this.bottleCountInitial = 11;

        enterUser();
        newGame();
    }

    public void menuControl() {
        animationImplementor.startWindowDisplay();

        while (true) {
            char c = getKey();

            //P: Play Game. S: Switch User. J: Jump to Level. Q: Quit

            if (c == 'P') {
                animationImplementor.gameDisplay(bottleSet);
                playGame();
            } else if (c == 'S') {
                enterUser();
                newGame();
            } else if (c == 'J') {
                enterLevel();
                newGame();
            } else if (c == 'Q') {
                saveThenQuit();
            }
        }
    }


    public void enterUser() {
        JFrame frame = new JFrame("Water Sort Puzzle");
        String defaultUser;
        if (this.user == null) {
            defaultUser = "Rudolph";
        } else {
            defaultUser = this.user.getUserId();
        }
        String s = (String) JOptionPane.showInputDialog(
                frame,
                "Enter user id: ",
                "Water Sort Puzzle",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultUser);

        readUserInfo(s);
    }

    public void enterLevel() {
        try {
            JFrame frame = new JFrame("Water Sort Puzzle");
            String defaultLevel;
            if (this.user == null) {
                defaultLevel = "0";
            } else {
                defaultLevel = Integer.toString(this.user.getUserLevel());
            }
            String s = (String) JOptionPane.showInputDialog(
                    frame,
                    "Enter level (0~999): ",
                    "Water Sort Puzzle",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    defaultLevel);
            int level = Integer.parseInt(s);
            if (level > 999) {
                level = 999;
            } else if (level < 0) {
                level = 0;
            }
            user.setLevel(level);
            this.seed = level;
        } catch (Exception e) {

        }
    }


    private void readUserInfo(String userId) {
        String readString;
        try {
            File gameState = new File(new File(User.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + '/' + userId + ".txt");
            if (gameState.exists()) {
                Scanner myReader = new Scanner(gameState);
                readString = myReader.nextLine();
                myReader.close();
                this.user = new User(userId);
                user.setLevel(Integer.parseInt(readString));
                this.seed = user.getUserLevel();
            } else {
                this.user = new User(userId);
                this.seed = user.getUserLevel();
            }
        } catch (Exception e) {
            this.user = new User(userId);
            this.seed = user.getUserLevel();
        }
    }

    private void saveThenQuit() {
        String userId = user.getUserId();
        int userLevel = user.getUserLevel();
        try {
            File gameState = new File(new File(User.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + '/' + userId + ".txt");
            FileWriter fw = new FileWriter(gameState.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(userLevel));
            bw.close();
        } catch(IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }



    public void newGame() {
        resetGame();
        playGame();
    }

    private void initializeGame(int bottleCount, int bufferBottleCount, int seed){
        rd = new Random(seed);
        bottleSet = new BottleSet(bottleCount, bufferBottleCount);
        bottleSet.initiate(rd);
        this.userActionArrayList = new ArrayList<>();
        this.additionalBottleCount = 0;
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
                    saveThenQuit();
                } else if (c == 'M') {
                    menuControl();
                } else if (c == 'S') {
                    ArrayList<UserAction> solutionSteps = solver.solve(bottleSet);
                    resetGame();
                    for (UserAction solutionStep : solutionSteps) {
                        int sourceBottle = solutionStep.getSourceIndex();
                        animationImplementor.selectBottle(sourceBottle);
                        animationImplementor.gameDisplay(bottleSet);
                        animationImplementor.pause();
                        solutionStep.doAction(bottleSet);
                        animationImplementor.selectBottle(sourceBottle);
                        animationImplementor.gameDisplay(bottleSet);
                        animationImplementor.pause();
                    }

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
                    resetGame();
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
                if (bottleSet.checkSuccess()) {
                    animationImplementor.gameWinDisplay();
                    while (!StdDraw.hasNextKeyTyped()) {
                        // Wait for keypress
                    }
                    nextGame();
                }
            }
        }
    }

    public void resetGame() {
        initializeGame(this.bottleCountInitial, this.bufferBottleCount, this.seed);
        animationImplementor.gameDisplayInitialize();
        animationImplementor.gameDisplay(bottleSet);
    }


    public void nextGame() {
        this.seed += 1;
        newGame();
    }


    public void saveGame(String userID){

    }


    public void loadGame(String userID){

    }




}
