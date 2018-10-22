public class Main {
    public static void main(String[] args) {
        ConcurrentVector c = new ConcurrentVector(5, 1);

        c.set(0, 10);
        c.set(1, 20);
        c.set(2, 30);
        c.set(3, 40);
        c.set(4, 50);

        c.max();
    }
}
