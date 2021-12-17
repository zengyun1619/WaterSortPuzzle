package User;

import GameElement.BottleSet;
import GameElement.Water;

public class MoveWater implements UserAction{
    int originBottleIndex;
    int destinationBottleIndex;
    int movedWaterLevel;

    public MoveWater(int originBottleIndex, int destinationBottleIndex) {
        this.originBottleIndex = originBottleIndex;
        this.destinationBottleIndex = destinationBottleIndex;
    }

    public void moveWaterAction(BottleSet bottles) {
        movedWaterLevel = bottles.moveTopWater(originBottleIndex, destinationBottleIndex);
    }
}
