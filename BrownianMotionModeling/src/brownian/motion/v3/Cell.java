package brownian.motion.v3;

import java.util.concurrent.locks.ReentrantLock;

public class Cell {
    int n;
    ReentrantLock locker = new ReentrantLock();

    public Cell(int n) {
        this.n = n;
    }

    public int getNum() {
        return n;
    }

    public void setNum(int n) {
        this.n = n;
    }
}
