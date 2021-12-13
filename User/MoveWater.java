package User;

import GameElement.Bottle;
import GameElement.Water;

public class MoveWater implements UserAction{
    Bottle originBottle;
    Bottle destinationBottle;

    public MoveWater(Bottle originBottle, Bottle destinationBottle) {
        this.originBottle = originBottle;
        this.destinationBottle = destinationBottle;
        moveWaterAction(originBottle, destinationBottle);
    }

    public void moveWaterAction(Bottle bottleA, Bottle bottleB) {
        // add check valid move
        Water water = bottleA.removeWater();
        bottleB.addWater(water);
    }
}
