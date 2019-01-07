package game.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;

import java.util.LinkedList;

public class Turtle {
    private IntegerProperty x;
    private IntegerProperty y;
    private ObjectProperty<Orientation> orientation;
    private LinkedList<MoveType> turtleMemory = new LinkedList<>();


    public Turtle(int x, int y, Orientation orientation) {
        this.x= new SimpleIntegerProperty(x);
        this.y=new SimpleIntegerProperty(y);

        this.orientation = new SimpleObjectProperty<Orientation>(orientation);
    }

    public int getX() {
        return x.getValue();
    }

    public IntegerProperty getXProperty() {
        return x;
    }

    public void setXProperty(int x) {
        this.x = new SimpleIntegerProperty(x);
    }

    public void setX(int x) {
        this.x.setValue(x);
    }

    public IntegerProperty getYProperty() {
        return y;
    }

    public void setYProperty(int y) {
        this.y=new SimpleIntegerProperty(y);
    }

    public int getY() {
        return y.getValue();
    }

    public void setY(int y) {
        this.y.setValue(y);
    }

    public ObjectProperty<Orientation> getOrientationProperty() {
        return orientation;
    }

    public void setOrientationProperty(Orientation orientation) {
        this.orientation = new SimpleObjectProperty<Orientation>(orientation);
    }

    public Orientation getOrientation() {
        return orientation.getValue();
    }

    public void setOrientation(Orientation orientation) {
        this.orientation.setValue(orientation);
    }

    public LinkedList<MoveType> getMemory(){
        return turtleMemory;
    }

    public void wipeMemory(){
        this.turtleMemory.clear();
    }

    public void rotateTo(Orientation orientation){
        turtleMemory.add(MoveType.rotate(this.orientation.getValue(),orientation));
        setOrientation(orientation);
    }

    public void moveTo(int x, int y){
        this.setX(x);
        this.setY(y);
        turtleMemory.add(MoveType.forward(getOrientation()));
    }
}
