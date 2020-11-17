package brownian.motion.v4;

public class Atom extends Thread {
    private Integer[] cells;
    private int index;
    private double p;
    private int counter = 0;

    public Atom(Integer[] cells, int index, double p) {
        this.cells = cells;
        this.index = index;
        this.p = p;
    }

    @Override
    public void run() {
        while (true) {
            double m = Math.random();
            int nextIndex = m > p ? (index + 1) : (index - 1);
            nextIndex = (nextIndex < 0 || nextIndex >= cells.length) ? index : nextIndex;
            synchronized (cells[index]) {
                synchronized (cells[nextIndex]) {
                    cells[index]--;
                    cells[nextIndex]++;
                    index = nextIndex;
                    counter++;
                }
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}
