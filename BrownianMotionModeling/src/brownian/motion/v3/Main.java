package brownian.motion.v3;

public class Main {
    public static void main(String[] args) {
        Cells cells = new Cells(10, 10, 0.9);
        cells.startMotion();
    }
}
