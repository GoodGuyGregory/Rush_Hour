
public class Car {

    private Orientation orientation;
    private int rowPosition;
    private int colPosition;

    public Car() {
    }

    public Car(Orientation orientation, int rowPos, int colPos) {
        this.orientation = orientation;
        this.rowPosition = rowPos;
        this.colPosition = colPos;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPos) {
        this.setRowPosition(rowPos);
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPos) {
        this.setColPosition(colPos);
    }

    public void horizontalMove() {

    }

    public void verticalMove() {

    }

}
