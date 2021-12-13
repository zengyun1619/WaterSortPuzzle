package GameElement;

import java.util.ArrayList;

public class StandardBottleSet implements BottleSet{
    private int bottleSetSize = 10;
    private int successCount = 0;

    private ArrayList<Bottle> bottles = new ArrayList<>();

    public StandardBottleSet() {

    }

    @Override
    public void addBottle(Bottle bottle) {
        bottles.add(bottle);
    }

    public void addSuccessBottle() {
        successCount += 1;
    }

}
