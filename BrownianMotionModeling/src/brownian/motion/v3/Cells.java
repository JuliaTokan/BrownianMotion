package brownian.motion.v3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//working version with synchronized two cells
public class Cells {
    private Cell[] cells;
    private int K;
    private double p;
    private int timeMillisec = 60000;

    public Cells(int N, int K, double p) {
        this.cells = new Cell[N];
        this.K = K;
        this.p = p;

        cells[0] = new Cell(K);
        for (int i = 1; i < N; i++) {
            cells[i] = new Cell(0);
        }

        printCells();
    }

    public void startMotion() {
        Atom[] threads = new Atom[K];
        for (int i = 0; i < K; i++) {
            Atom atom = new Atom(cells, 0, p);
            threads[i] = atom;
            threads[i].start();
        }

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            printCells();
        };
        executor.scheduleWithFixedDelay(task, 0, 5, TimeUnit.SECONDS);

        programTimer(timeMillisec);

        executor.shutdown();

        int numOfMovies = 0;
        for (int i = 0; i < K; i++) {
            threads[i].stop();
            numOfMovies += threads[i].getCounter();
        }

        int numOfAtoms = 0;
        for (Cell cell : cells)
            numOfAtoms += cell.getNum();

        System.out.println("Number of atoms = " + numOfAtoms);
        System.out.println("Number of atom`s movies for " + (timeMillisec / 1000) + " sec = " + numOfMovies);
    }

    public void programTimer(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printCells(){
        for (Cell cell : cells)
            System.out.print("[" + cell.getNum() + "] ");
        System.out.println();
    }
}
