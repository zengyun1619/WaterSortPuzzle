package User;

import GameElement.BottleSet;
import GameElement.Water;

public class MoveWater implements UserAction{
    public int originBottleIndex;
    public int destinationBottleIndex;
    public int movedWaterLevel;

    public MoveWater(int originBottleIndex, int destinationBottleIndex, int movedWaterLevel) {
        this.originBottleIndex = originBottleIndex;
        this.destinationBottleIndex = destinationBottleIndex;
        this.movedWaterLevel = movedWaterLevel;
    }

    @Override
    public int getSourceIndex() {
        return originBottleIndex;
    }

    @Override
    public int getDestinationIndex() {
        return destinationBottleIndex;
    }

    @Override
    public int getMovedWaterLevel() {
        return movedWaterLevel;
    }

    @Override
    public void doAction(BottleSet bottleset) {
        bottleset.moveWater(originBottleIndex, destinationBottleIndex, movedWaterLevel);
    }

    @Override
    public void unDoAction(BottleSet bottleSet) {
        bottleSet.moveWater(destinationBottleIndex, originBottleIndex, movedWaterLevel);
    }

}
