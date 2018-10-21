public class Main {
    public static void main(String[] args) {
        ConcurrentVector c = new ConcurrentVector(4, 2);

        c.set(0, 1);
        c.set(0, 2);
        c.set(0, 3);
        c.set(0, 4);

        c.max();
    }
}
