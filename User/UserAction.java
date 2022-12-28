package User;

import GameElement.BottleSet;

public interface UserAction {
    public void doAction(BottleSet bottleset);
    public void unDoAction(BottleSet bottleSet);
    public int getSourceIndex();
    public int getDestinationIndex();
    public int getMovedWaterLevel();
}
