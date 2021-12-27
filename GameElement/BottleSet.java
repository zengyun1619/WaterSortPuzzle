package GameElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BottleSet{
    private static final int waterLevelMax = 4;

    private int bufferBottleCount;
    private int bottleCount;

    private ArrayList<Water[]> bottles = new ArrayList<>();

    public BottleSet(int bottleCount, int bufferBottleCount) {
        this.bottleCount = bottleCount;
        this.bufferBottleCount = bufferBottleCount;
    }

    public int getBottleCount() { return bottleCount; }

    public void initiate(Random rd) {
        for (int i = 0; i < bottleCount - bufferBottleCount; i++) {
            Water[] bottle = new Water[waterLevelMax];
            for (int j = 0; j < waterLevelMax; j++) {
                bottle[j] = new Water(WaterColorSet.waterColorSet[i]);
            }
            bottles.add(bottle);
        }

        for (int k = 0; k < 100; k ++) {
            int bottleFrom = rd.nextInt( bottleCount - bufferBottleCount);
            int bottleTo = rd.nextInt(bottleCount - bufferBottleCount);
            int waterLevelFrom = rd.nextInt(waterLevelMax);
            int waterLevelTo = rd.nextInt(waterLevelMax);
            Water temp = bottles.get(bottleFrom)[waterLevelFrom];
            bottles.get(bottleFrom)[waterLevelFrom] = bottles.get(bottleTo)[waterLevelTo];
            bottles.get(bottleTo)[waterLevelTo] = temp;
        }

        for (int i = 0; i < bufferBottleCount; i++) {
            Water[] bottle = new Water[waterLevelMax];
            bottles.add(bottle);
        }


    }

    public Water[] getBottle(int bottleIndex) {
        return bottles.get(bottleIndex);
    }

    public void addNewEmptyBottle() {
        Water[] bottle = new Water[waterLevelMax];
        addBottle(bottle);
    }

    public void addBottle(Water[] bottle) {
        bottles.add(bottle);
        bottleCount += 1;
    }

    public int getWaterLevelMax() { return waterLevelMax;}

    public boolean isFull(Water[] bottle) {
        return bottle[waterLevelMax - 1] != null;
    }

    public boolean isEmpty(Water[] bottle) {
        return bottle[0] == null;
    }

    public int getWaterLevel(Water[] bottle) {
        int waterLevel = 0;
        while(waterLevel < waterLevelMax) {
            if (bottle[waterLevel] == null) {
                return waterLevel;
            }
            waterLevel += 1;
        }
        return waterLevel;
    }

    public Color getTopWaterColor(Water[] bottle) {
        int waterLevel = getWaterLevel(bottle);
        if (waterLevel == 0) {
            return null;
        } else {
            return bottle[waterLevel - 1].getColor();
        }
    }

    public int getTopWaterSize(Water[] bottle) {
        int waterLevel = getWaterLevel(bottle);
        if (waterLevel == 0) {
            return 0;
        }
        int topWaterSize = 1;
        Color topWaterColor = bottle[waterLevel - 1].getColor();
        while(waterLevel > 1) {
            waterLevel -= 1;
            if (bottle[waterLevel - 1].getColor().equals(topWaterColor)) {
                topWaterSize += 1;
            } else {
                return topWaterSize;
            }
        }
        return topWaterSize;
    }

    public int getTopSpaceSize(Water[] bottle) {
        int waterLevel = getWaterLevel(bottle);
        return waterLevelMax - waterLevel;
    }

    public int moveTopWater(int bottleFromIndex, int bottleToIndex) {
        Water[] bottleFrom = bottles.get(bottleFromIndex);
        Water[] bottleTo = bottles.get(bottleToIndex);
        if (isEmpty(bottleFrom) || isFull(bottleTo)) {
            return 0;
        }
        int waterLevelFrom = getWaterLevel(bottleFrom);
        int waterLevelTo = getWaterLevel(bottleTo);
        if (!isEmpty(bottleTo) && !getTopWaterColor(bottleFrom).equals(getTopWaterColor(bottleTo))) {
            return 0;
        }
        int waterSize = getTopWaterSize(bottleFrom);
        int spaceSize = getTopSpaceSize(bottleTo);
        if (waterSize > spaceSize) {
            return 0;
        }
        for (int i = 0; i < waterSize; i++) {
            bottleTo[waterLevelTo + i] = bottleFrom[waterLevelFrom - 1 - i];
            bottleFrom[waterLevelFrom - 1 - i] = null;
        }
        return waterSize;
    }

    public void moveWater(int bottleFromIndex, int bottleToIndex, int movedWaterLevel) {
        Water[] bottleFrom = bottles.get(bottleFromIndex);
        Water[] bottleTo = bottles.get(bottleToIndex);
        int waterLevelFrom = getWaterLevel(bottleFrom);
        int waterLevelTo = getWaterLevel(bottleTo);
        for (int i = 0; i < movedWaterLevel; i++) {
            bottleTo[waterLevelTo + i] = bottleFrom[waterLevelFrom - 1 - i];
            bottleFrom[waterLevelFrom - 1 - i] = null;
        }
    }

    public boolean isSameColorAndFull(Water[] bottle) {
        if (bottle[0] == null) {
            return false;
        }
        Color waterColor = bottle[0].getColor();
        for (int i = 1; i < waterLevelMax; i++) {
            if (bottle[i] == null || !bottle[i].getColor().equals(waterColor)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkSuccess() {
        for (int i = 0; i < bottleCount; i++) {
            if (getWaterLevel(bottles.get(i)) == 0) {
                continue;
            } else if (getWaterLevel(bottles.get(i)) == 4 && getTopWaterSize(bottles.get(i)) == 4) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }


}
