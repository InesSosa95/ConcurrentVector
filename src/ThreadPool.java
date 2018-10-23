import java.util.ArrayList;

public class ThreadPool {

    private Worker[] workers;

    private Buffer buffer;



    private ArrayList<SequentialVector> resultVectors;

    private ConcurrentVector concurrentVector;

    public ThreadPool(int threads, Buffer buffer, ConcurrentVector concurrentVector) {
        this.concurrentVector = concurrentVector;
        resultVectors = new ArrayList<SequentialVector>();

        workers = new Worker[threads];
        this.buffer = buffer;
        for (int i = 0; i < threads; i++) {
            workers[i] = new Worker(buffer, this);
            workers[i].start();
        }
    }

    public int dimension() {
        return workers.length;
    }

    public void increaseWorkDone() {
        concurrentVector.increaseWorkDone();
    }

    public void addResultVector(SequentialVector resultVector) {
        resultVectors.add(resultVector);
    }

    public ArrayList<SequentialVector> resultVectors() {
        return resultVectors;
    }
}
