package GameElement;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;

public class Water {
    private Color color;

    public Water(Color color){
        this.color = color;
    }

    public boolean isSameColor(Water anotherWater) {
        return this.getColor().equals(anotherWater.getColor());
    }

    public Color getColor() {
        return this.color;
    }

}
