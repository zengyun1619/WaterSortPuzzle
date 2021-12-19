package User;

import GameElement.BottleSet;

public interface UserAction {
    public void doAction(BottleSet bottleset);
    public void unDoAction(BottleSet bottleSet);
}
