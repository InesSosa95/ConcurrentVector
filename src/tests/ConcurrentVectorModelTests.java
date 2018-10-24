package tests;


import static org.junit.Assert.*;

import main.ConcurrentVector;
import main.SequentialVector;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

public class ConcurrentVectorModelTests {

    private ConcurrentVector vector;

    @Before
    public void setUp(){
        vector = new ConcurrentVector(50, 10);
    }

    @Test
    public void testValues(){
        int expected = 50;
        int actual = vector.dimension();
        assertEquals(expected,actual);
    }

    @Test
    public void testSimpleSet(){
        vector.set(0, 1.2);
        double expected = 1.2;
        double actual = vector.get(0);

        assert(expected == actual);
    }

    @Test
    public void testSet(){
        vector.set(3.14);
        //Recorre todas las posiciones del vector y acerta que todos los valores sean 3,14
        IntStream.range(0, vector.dimension()).forEach(i -> {
            assert (vector.get(i) == 3.14);
        });
    }

    @Test
    public void testSum(){
        vector.set(1D);

        double expected = 50D;
        double actual = vector.sum();

        assert (expected == actual);
    }

    @Test
    public void testAdd(){

        //Si se hace con set(double), se rompe, vaya une a saber porque
        main.ConcurrentVector c = new main.ConcurrentVector(4, 2);
        c.set(0, 10);
        c.set(1, 10);
        c.set(2, 10);
        c.set(3, 10);
        main.SequentialVector s = new main.SequentialVector(4);
        s.set(0, 3);
        s.set(1, 3);
        s.set(2, 3);
        s.set(3, 3);
        c.add(s);

        IntStream.range(0, c.dimension()).forEach(i -> {
            assert (c.get(i) == 13);
        });
    }
}
