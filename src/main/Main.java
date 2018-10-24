package main;

public class Main {
    public static void main(String[] args) {
        // Set
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//
//        c.set(1);

        // Add
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        main.SequentialVector s = new main.SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.add(s);

//        Abs
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//
//        c.set(0, -10);
//        c.set(1, 20);
//        c.set(2, -30);
//        c.set(3, 40);
//
//        c.abs();

        // Add
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        main.SequentialVector s = new main.SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.mul(s);

        // Assign
//
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        main.SequentialVector s = new main.SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        c.assign(s);

        // Assign
//
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 20);
//        c.set(2, 30);
//        c.set(3, 40);
//        main.SequentialVector s = new main.SequentialVector(4);
//        s.set(0, 3);
//        s.set(1, 6);
//        s.set(2, 9);
//        s.set(3, 9);
//        main.SequentialVector m = new main.SequentialVector(4);
//        m.set(0, -1);
//        m.set(1, 1);
//        m.set(2, -1);
//        m.set(3, 1);
//        c.assign(m, s);

        // Sum
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 15);
//        c.set(1, 15);
//        c.set(2, 15);
//        c.set(3, 15);
//        double sum = c.sum();

        // Sum
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 8);
//        c.set(1, 8);
//        c.set(2, 6);
//        c.set(3, 6);
//        double mean = c.mean();

        // Max
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 1);
//        c.set(1, 5);
//        c.set(2, 3);
//        c.set(3, 9);
//        double max = c.max();

        // Norm
//        main.SequentialVector s = new main.SequentialVector(4);
//        s.set(0, 10);
//        s.set(1, 13);
//        s.set(2, 25);
//        s.set(3, 55);
//        double sNorm = s.norm();
//
//        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
//        c.set(0, 10);
//        c.set(1, 13);
//        c.set(2, 25);
//        c.set(3, 55);
//        double cNorm = c.norm();

        // Prod
        SequentialVector s = new SequentialVector(4);
        s.set(0, 1);
        s.set(1, 2);
        s.set(2, 3);
        s.set(3, 4);

        SequentialVector ss = new SequentialVector(4);
        ss.set(0, 1);
        ss.set(1, 2);
        ss.set(2, 3);
        ss.set(3, 4);

        double prodA = s.prod(ss);

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 1);
        c.set(1, 2);
        c.set(2, 3);
        c.set(3, 4);
        double prodB = c.prod(s);
    }
}
