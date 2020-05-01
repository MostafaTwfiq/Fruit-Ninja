package GameTypes;

public final class NumberRange {

    private int firstNumber;
    private int lastNumber;

    public NumberRange(int firstNumber, int lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getLastNumber() {
        return lastNumber;
    }

}
