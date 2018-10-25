package test;

import main.ConcurrentVector;
import main.SequentialVector;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ConcurrentVectorModelTests {

    private ConcurrentVector vector;
    private ConcurrentVector vector1;
    private SequentialVector seqVector;

    @Before
    public void setUp() {
        vector = new ConcurrentVector(50, 10);
        vector1 = new ConcurrentVector(4, 2);
        seqVector = new SequentialVector(4);

        seqVector.set(0, 3);
        seqVector.set(1, 3);
        seqVector.set(2, 3);
        seqVector.set(3, 3);

        vector1.set(0, 10);
        vector1.set(1, 10);
        vector1.set(2, 10);
        vector1.set(3, 10);

    }

    @Test
    public void testValues() {
        int expected = 50;
        int actual = vector.dimension();
        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleSet() {
        vector.set(0, 1.2);
        double expected = 1.2;
        double actual = vector.get(0);

        assert (expected == actual);
    }

    @Test
    public void testSet() {
        vector.set(3.14);
        //Recorre todas las posiciones del vector y acerta que todos los valores sean 3,14
        IntStream.range(0, vector.dimension()).forEach(i -> {
            assert (vector.get(i) == 3.14);
        });
    }

    @Test
    public void testSum() {
        vector.set(1D);

        double expected = 50D;
        double actual = vector.sum();

        assert (expected == actual);
    }

    @Test
    public void testAdd() {

        //Si se hace con set(double), se rompe, vaya une a saber porque
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 10);
        concurrentVector.set(2, 10);
        concurrentVector.set(3, 10);

        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 3);
        sequentialVector.set(1, 3);
        sequentialVector.set(2, 3);
        sequentialVector.set(3, 3);

        concurrentVector.add(sequentialVector);


        IntStream.range(0, concurrentVector.dimension()).forEach(i -> {
            assert (concurrentVector.get(i) == 13);
        });
    }

    @Test
    public void testAssing() {
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 10);
        concurrentVector.set(2, 10);
        concurrentVector.set(3, 10);

        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 3);
        sequentialVector.set(1, 3);
        sequentialVector.set(2, 3);
        sequentialVector.set(3, 3);

        concurrentVector.assign(sequentialVector);

        IntStream.range(0, concurrentVector.dimension()).forEach(i -> {
            assert (concurrentVector.get(i) == 3);
        });
    }

    @Test
    public void assingWithMask() {
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 20);
        concurrentVector.set(2, 30);
        concurrentVector.set(3, 40);

        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 3);
        sequentialVector.set(1, 6);
        sequentialVector.set(2, 9);
        sequentialVector.set(3, 9);

        SequentialVector maskVector = new SequentialVector(4);

        maskVector.set(0, -1);
        maskVector.set(1, 1);
        maskVector.set(2, -1);
        maskVector.set(3, 1);

        concurrentVector.assign(maskVector, sequentialVector);

        assert concurrentVector.get(0) == 10D;
        assert concurrentVector.get(1) == 6D;
        assert concurrentVector.get(2) == 30D;
        assert concurrentVector.get(3) == 9D;

    }

    @Test
    public void testMax() {
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 40);
        concurrentVector.set(2, 30);
        concurrentVector.set(3, 20);

        assert concurrentVector.max() == 40D;

    }

    @Test
    public void testMul() {
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 10);
        concurrentVector.set(2, 10);
        concurrentVector.set(3, 10);

        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 2);
        sequentialVector.set(1, 2);
        sequentialVector.set(2, 2);
        sequentialVector.set(3, 2);

        concurrentVector.mul(sequentialVector);

        IntStream.range(0, concurrentVector.dimension()).forEach(i -> {
            assert (concurrentVector.get(i) == 20);
        });
    }

    @Test
    public void testMul2() {
        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 10);
        concurrentVector.set(1, 2.2);
        concurrentVector.set(2, 3.14);
        concurrentVector.set(3, 4.2);

        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 2);
        sequentialVector.set(1, 2);
        sequentialVector.set(2, 2);
        sequentialVector.set(3, 1);

        concurrentVector.mul(sequentialVector);

        assert concurrentVector.get(0) == 20;
        assert concurrentVector.get(1) == 4.4;
        assert concurrentVector.get(2) == 6.28;
        assert concurrentVector.get(3) == 4.2;
    }

    @Test
    //Mi test favorito
    public void testAbs() {
        ConcurrentVector vector = new ConcurrentVector(4, 2);
        vector.set(0, -1);
        vector.set(1, 1);
        vector.set(2, 1);
        vector.set(3, -1);

        vector.abs();

        IntStream.range(0, vector.dimension()).forEach(i -> {
            assert vector.get(i) == 1;
        });
    }

    @Test
    public void testMeanWithBasicValues() {
        assert vector1.mean() == 10;
    }

    @Test
    public void testMean() {
        vector.set(0, 3.14);
        vector.set(1, 6.10);
        vector.set(2, 10);
        vector.set(3, 77);

        assert vector.mean() == 1.9248;
    }

    @Test
    public void testProd() {
        SequentialVector sequentialVector = new SequentialVector(4);
        sequentialVector.set(0, 1);
        sequentialVector.set(1, 2);
        sequentialVector.set(2, 3.5);
        sequentialVector.set(3, 4);

        ConcurrentVector concurrentVector = new ConcurrentVector(4, 2);
        concurrentVector.set(0, 1);
        concurrentVector.set(1, 2);
        concurrentVector.set(2, 3);
        concurrentVector.set(3, 4);

        assert (concurrentVector.prod(sequentialVector) == 31.5);
    }

    @Test
    public void testNorm() {
        vector.set(0, 3.14);
        vector.set(1, 6.10);
        vector.set(2, 10);
        vector.set(3, 77);

        assert vector.norm() == 77.94914752580685;
    }

}
