package Data;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates {
    @JacksonXmlProperty
    private final float x;
    @JacksonXmlProperty
    private final int y; //Максимальное значение поля: 202

    public Coordinates(float x, int y) {
        if (y > 202) throw new IllegalArgumentException("значение ординаты слишком велико");
        this.x = x;
        this.y = y;
    }

    private Coordinates () {
        this.x = 1.5F;
        this.y = 1;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null || this.getClass() != otherObject.getClass()) return false;
        Coordinates other = (Coordinates) otherObject;
        return Math.abs(Float.compare(x, other.x)) < 0.0001 && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
