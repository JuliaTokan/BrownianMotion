package brownian.motion.v4;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//working version with synchronized all array
public class Cells {
    private Integer[] cells;
    private int K;
    private double p;
    private int timeMillisec = 60000;

    public Cells(int N, int K, double p) {
        this.cells = new Integer[N];
        this.K = K;
        this.p = p;

        for(int i = 0; i < N; i++){
            cells[i] = new Integer(0);
        }
        cells[0] = K;

        printCells();
    }

    public void startMotion(){
        Atom[] atoms = new Atom[K];
        for(int i = 0; i<K; i++){
            atoms[i] = new Atom(cells, 0, p);
            atoms[i].start();
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
            atoms[i].stop();
            numOfMovies += atoms[i].getCounter();
        }

        int numOfAtoms = 0;
        for (Integer cell : cells)
            numOfAtoms += cell;

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
        for(Integer num: cells)
            System.out.print("["+num+"] ");
        System.out.println();
    }
}
