package Sword;

public final class PointsVector {

    private MousePoint points[];
    private int count;
    private int length;

    public PointsVector(int length) {
        this.length = length;
        count = 0;
        points = new MousePoint[length];
    }

    public PointsVector() {
        length = 10;
        count = 0;
        points = new MousePoint[length];
    }

    public void add(MousePoint point) {
        shiftArrayRight();

        points[0] = point;

        if (!isFull())
            count++;
    }

    public MousePoint get(int index) {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException();

        return points[index];
    }

    public int find(MousePoint point) {
        for (int i = 0; i < count; i++) {

            if (point.equals(points[i]))
                return i;

        }

        return -1;
    }

    public void remove(int index) {
        if (isEmpty())
            throw new NullPointerException();

        shiftArrayLeft(index);
        count--;
    }

    public void clear() {
        points = new MousePoint[length];
        count = 0;
    }

    private void shiftArrayLeft(int index) {

        for (int i = index;i < count - 1; i++)
            points[i] = points[i + 1];

    }

    private void shiftArrayRight() {

        for (int i = (!isFull()) ? count : count - 1; i > 0; i--)
            points[i] = points[i - 1];

    }

    public boolean isFull() {
        return count == length;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int getLength() {
        return count;
    }
}
