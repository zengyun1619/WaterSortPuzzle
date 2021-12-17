package GameElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class BottleSet{
    private static final int waterLevelMax = 4;

    private int bufferBottleCount = 2;
    private int bottleCount;
    private int successCount = 0;

    private ArrayList<Water[]> bottles = new ArrayList<>();

    public BottleSet(int bottleCount, Random rd) {
        this.bottleCount = bottleCount;
        initiate(rd);
    }

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

        // suffle the colors

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

    public int getWaterLevelMax() { return waterLevelMax;}

    public int getTopWaterSize(Water[] bottle) {
        int waterLevel = getWaterLevel(bottle);
        if (waterLevel == 0) {
            return 0;
        }
        int topWaterSize = 1;
        Color topWaterColor = bottle[waterLevel].getColor();
        while(waterLevel > 0) {
            waterLevel -= 1;
            if (bottle[waterLevel].getColor().equals(topWaterColor)) {
                topWaterSize += 1;
            }
        }
        return topWaterSize;
    }

    public int getBottleCount() { return bottleCount; }

    public int getSuccessCount() { return successCount; }

    public void addSuccessCount() {
        successCount += 1;
    }

    public void addWater(Water[] bottle, Water water) {
        int waterLevel = getWaterLevel(bottle);
        if (waterLevel < waterLevelMax) {
            bottle[waterLevel] = water;
        }
    }

    public void removeWater(Water[] bottle, Water water) {
        int waterLevel = getWaterLevel(bottle);
        if (waterLevel > 0) {
            bottle[waterLevel - 1] = null;
        }
    }

    public int moveTopWater(Water[] bottleFrom, Water[] bottleTo) {
        int waterLevelFrom = getWaterLevel(bottleFrom);
        int waterLevelTo = getWaterLevel(bottleTo);
        int waterSize = getTopWaterSize(bottleFrom);
        if (waterLevelTo <= waterLevelMax - waterSize && waterLevelFrom > 0) {
            for (int i = 0; i < waterSize; i++) {
                bottleTo[waterLevelTo + i] = bottleFrom[waterLevelFrom - 1 - i];
                bottleFrom[waterLevelFrom - 1 - i] = null;
            }
            return waterSize;
        } else {
            return 0;
        }
    }

    public int moveTopWater(int bottleFromIndex, int bottleToIndex) {
        return moveTopWater(bottles.get(bottleFromIndex), bottles.get(bottleToIndex));
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

}
