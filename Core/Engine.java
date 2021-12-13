package Core;

import Animation.Animation;
import GameElement.BottleSet;
import GameElement.StandardBottleSet;
import User.UserAction;

import java.util.ArrayList;

public class Engine{
    private Animation animationImplementor = new Animation();


    private BottleSet bottleSet = new StandardBottleSet();

    private ArrayList<UserAction> userActionArrayList = new ArrayList<>();


    public Engine(){

    }


    public void startGame() {
        animationImplementor.startWindowDisplay();
    }


    public void newGame() {
        initializeGame();
        playGame();
    }

    private void initializeGame(){

    }

    private void playGame(){

    }




    public void resetGame() {

    }


    public void saveGame(String userID){

    }


    public void loadGame(String userID){

    }

}
