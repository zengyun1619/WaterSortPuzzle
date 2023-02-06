package Core;

import GameElement.BottleSet;
import User.MoveWater;
import User.UserAction;

import java.util.ArrayList;

public class Solver {

    boolean solved;
    ArrayList<UserAction> solutionSteps;
    int bottleCount;

    public Solver() {
        solved = false;
        solutionSteps = new ArrayList<>();
    }

    public ArrayList<UserAction> solve(BottleSet bottleSet) {
        solutionSteps.clear();
        bottleCount = bottleSet.getBottleCount();

        for (int i = 0; i < bottleCount; i++) {
            solveHelper(i, bottleSet);
            if (solved) {
                return solutionSteps;
            };
        }
        return null;
    }

    private void solveHelper(int start, BottleSet bottleSet) {
        if (bottleSet.checkSuccess()) {
            solved = true;
            return;
        }

        int[] findResult = findNextAvailableMove(start, bottleSet);

        if (findResult == null) {
            return;
        }

        int waterSize = findResult[0];
        int end = findResult[1];
        solutionSteps.add(new MoveWater(start, end, waterSize));

        for (int j = start; j < bottleCount + start; j++) {
            if (j < bottleCount) {
                solveHelper(j, bottleSet);
            } else {
                solveHelper(j - bottleCount, bottleSet);
            }
        }

        if (!solved){
            solutionSteps.remove(solutionSteps.size() - 1);
            new MoveWater(start, end, waterSize).unDoAction(bottleSet);
        }

        return;
    }

    private int[] findNextAvailableMove(int bottleIndex, BottleSet bottleSet) {
        for (int i = 0; i < bottleSet.getBottleCount(); i++) {
            if (i == bottleIndex) {
                continue;
            } else if (bottleSet.isSameColor(bottleSet.getBottle(bottleIndex)) && bottleSet.isEmpty(bottleSet.getBottle(i))) {
                continue;
            } else {
                int moveWaterSize = bottleSet.moveTopWater(bottleIndex, i);
                if (moveWaterSize == 0) {
                    continue;
                } else {
                    return new int[]{moveWaterSize, i};
                }
            }
        }
        return null;
    }



}
