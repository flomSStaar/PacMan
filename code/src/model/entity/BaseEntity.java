package model.entity;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public abstract class BaseEntity {
    private FloatProperty x = new SimpleFloatProperty();
    public float getX() { return x.get(); }
    public void setX(float x) { this.x.set(x); }
    public FloatProperty xProperty() { return x; }

    private FloatProperty y = new SimpleFloatProperty();
    public float getY() { return y.get(); }
    public void setY(float y) { this.y.set(y); }
    public FloatProperty yProperty() { return y; }

    private FloatProperty width=new SimpleFloatProperty();
    public float getWidth() { return width.get(); }
    public void setWidth(float width) { this.width.set(width); }
    public FloatProperty widthProperty() { return width; }

    private FloatProperty height=new SimpleFloatProperty();
    public float getHeight() { return height.get(); }
    public void setHeight(float height) { this.height.set(height); }
    public FloatProperty heightProperty() { return height; }
}
