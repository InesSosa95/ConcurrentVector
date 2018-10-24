public class Main {
    public static void main(String[] args) {
        // Set
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//
//        c.set(1);

        // Add
        ConcurrentVector c = new ConcurrentVector(4,2);
        c.set(0, 10);
        c.set(1, 20);
        c.set(2, 30);
        c.set(3, 40);
        SequentialVector s = new SequentialVector(4);
        s.set(0,3);
        s.set(1,6);
        s.set(2,9);
        s.set(3,9);
        c.add(s);
    }
}
