package brownian.motion.v3;

public class Atom extends Thread {
    private Cell[] cells;
    private int index;
    private double p;
    private int counter = 0;

    public Atom(Cell[] cells, int index, double p) {
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

            Cell cellCurr = cells[index];
            if(!cellCurr.locker.isLocked()){
                cellCurr.locker.lock();
                Cell cellNext = cells[nextIndex];

                if(!cellNext.locker.isLocked()){
                    cellNext.locker.lock();

                    int num = cellCurr.getNum();
                    cellCurr.setNum(--num);

                    int nextNum = cellNext.getNum();
                    cellNext.setNum(++nextNum);

                    index = nextIndex;

                    counter++;

                    cellNext.locker.unlock();
                }
                cellCurr.locker.unlock();
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}
