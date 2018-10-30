package test;

import main.ConcurrentVector;
import main.SequentialVector;
import org.junit.Test;

public class ConcurrentVectorTest {

    @Test
    public void testSet() {
        ConcurrentVector c = new ConcurrentVector(4, 2);
        int expected = 5;
        c.set(expected);

        for (int i = 0; i < c.dimension(); i++) {
            assert expected == c.get(i);
        }
    }

    @Test
    public void testAssign() {
        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 10);
        c.set(1, 10);
        c.set(2, 10);
        c.set(3, 10);

        SequentialVector s = new SequentialVector(4);
        s.set(0, 5);
        s.set(1, 10);
        s.set(2, 2);
        s.set(3, 60);

        c.assign(s);

        for (int i = 0; i < c.dimension(); i++) {
            assert c.get(i) == s.get(i);
        }
    }

    @Test
    public void testAssignWithMask() {
        double[] elements = new double[4];
        elements[0] = 10;
        elements[1] = 20;
        elements[2] = 30;
        elements[3] = 40;

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, elements[0]);
        c.set(1, elements[1]);
        c.set(2, elements[2]);
        c.set(3, elements[3]);

        SequentialVector s = new SequentialVector(4);
        s.set(0, 3);
        s.set(1, 6);
        s.set(2, 9);
        s.set(3, 9);

        SequentialVector m = new SequentialVector(4);

        m.set(0, -1);
        m.set(1, 1);
        m.set(2, -1);
        m.set(3, 1);

        c.assign(m, s);

        for (int i = 0; i < c.dimension(); i++) {
            if (m.get(i) > -1) {
                assert c.get(i) == s.get(i);
            } else {
                assert c.get(i) == elements[i];
            }
        }
    }

    @Test
    public void testAdd() {
        double[] elements = new double[4];
        elements[0] = 10;
        elements[1] = 20;
        elements[2] = 30;
        elements[3] = 40;

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, elements[0]);
        c.set(1, elements[1]);
        c.set(2, elements[2]);
        c.set(3, elements[3]);

        SequentialVector s = new SequentialVector(4);
        s.set(0, 3);
        s.set(1, 6);
        s.set(2, 9);
        s.set(3, 9);

        c.add(s);

        for (int i = 0; i < c.dimension(); i++) {
            assert (elements[i] + s.get(i)) == c.get(i);
        }
    }

    @Test
    public void testMul() {
        double[] elements = new double[4];
        elements[0] = 10;
        elements[1] = 20;
        elements[2] = 30;
        elements[3] = 40;

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, elements[0]);
        c.set(1, elements[1]);
        c.set(2, elements[2]);
        c.set(3, elements[3]);

        SequentialVector s = new SequentialVector(4);
        s.set(0, 2);
        s.set(1, 1);
        s.set(2, 20);
        s.set(3, 44);

        c.mul(s);

        for (int i = 0; i < c.dimension(); i++) {
            assert (elements[i] * s.get(i)) == c.get(i);
        }
    }

    @Test
    public void testAbs() {
        double[] elements = new double[4];
        elements[0] = -10;
        elements[1] = 20;
        elements[2] = -30;
        elements[3] = 40;

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, elements[0]);
        c.set(1, elements[1]);
        c.set(2, elements[2]);
        c.set(3, elements[3]);

        c.abs();

        for (int i = 0; i < c.dimension(); i++) {
            assert Math.abs(elements[i]) == c.get(i);
        }
    }

    @Test
    public void testSum() {
        double[] elements = new double[4];
        elements[0] = 10;
        elements[1] = 20;
        elements[2] = 30;
        elements[3] = 40;

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, elements[0]);
        c.set(1, elements[1]);
        c.set(2, elements[2]);
        c.set(3, elements[3]);

        double sum = c.sum();

        double expected = 0;

        for (double element : elements) expected += element;

        assert (expected == sum);
    }

    @Test
    public void testMean() {
        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 3.14);
        c.set(1, 6.10);
        c.set(2, 10);
        c.set(3, 77);

        double mean = c.mean();

        assert mean == 24.06;
    }

    @Test
    public void testProd() {
        SequentialVector s = new SequentialVector(4);
        s.set(0, 1);
        s.set(1, 2);
        s.set(2, 3);
        s.set(3, 4);

        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 1);
        c.set(1, 2);
        c.set(2, 3);
        c.set(3, 4);

        double prod = c.prod(s);

        assert (30 == prod);
    }

    @Test
    public void testNorm() {
        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 3.14);
        c.set(1, 6.10);
        c.set(2, 10);
        c.set(3, 77);

        double norm = c.norm();

        assert 77.94914752580685 == norm;
    }

    @Test
    public void testMax() {
        ConcurrentVector c = new ConcurrentVector(4, 2);
        c.set(0, 25);
        c.set(1, 55);
        c.set(2, 80);
        c.set(3, 10);

        double max = c.max();

        assert 80 == max;
    }
}
