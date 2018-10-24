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
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        SequentialVector s = new SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.add(s);

//        Abs
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//
//        c.set(0, -10);
//        c.set(1, 20);
//        c.set(2, -30);
//        c.set(3, 40);
//
//        c.abs();

        // Add
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        SequentialVector s = new SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.mul(s);

        // Assign
//
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        SequentialVector s = new SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.assign(s);

        // Assign
//
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        SequentialVector s = new SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        SequentialVector m = new SequentialVector(4);
//        m.set(0, -1);
//        m.set(1, 1);
//        m.set(2, -1);
//        m.set(3, 1);
//        c.assign(m, s);

        // Sum
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 15);
//        c.set(1, 15);
//        c.set(2, 15);
//        c.set(3, 15);
//        double sum = c.sum();

        // Sum
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 8);
//        c.set(1, 8);
//        c.set(2, 6);
//        c.set(3, 6);
//        double mean = c.mean();

        // Max
//        ConcurrentVector c = new ConcurrentVector(4, 2);
//        c.set(0, 1);
//        c.set(1, 5);
//        c.set(2, 3);
//        c.set(3, 9);
//        double max = c.max();

        SequentialVector s = new SequentialVector(4);
        s.set(0, 10);
        s.set(1, 13);
        s.set(2, 25);
        s.set(3, 55);
        double sNorm = s.norm();

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 10);
        c.set(1, 13);
        c.set(2, 25);
        c.set(3, 55);
        double cNorm = c.norm();
    }
}
