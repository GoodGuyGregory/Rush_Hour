package org.example;

public class Car {

    private String id;
    private String orientation;

    private boolean touched;
    private int[][] position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public int[][] getPosition() {
        return position;
    }

    public void setPosition(int[][] position) {
        this.position = position;
    }

    public boolean isBlocked(TrafficGrid grid) {
        // check if the position above is the end or blocked...
        if (this.position.length < grid.getParkingLotSize()) {
            return true;
        }

        // check if right is blocked...

        // check if below is blocked...
    }



}
