package GameElement;

import java.util.Stack;


public class StandardBottle implements Bottle{
    private static final int BottleSize = 4;

    private Stack<Water> waters = new Stack<>();

    public StandardBottle() {

    }

    @Override
    public void addWater(Water water) {
        waters.push(water);
    }

    public Water removeWater() {
        return waters.pop();
    }

}
