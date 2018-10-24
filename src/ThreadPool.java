import java.util.ArrayList;

public class ThreadPool {

    private Worker[] workers;

    private Buffer buffer;

    private int workToDo;
    private int workDone;

    private ArrayList<SequentialVector> resultVectors;
    private ArrayList<Double> resultDoubles;

    private ConcurrentVector concurrentVector;

    public ThreadPool(int threads, Buffer buffer, ConcurrentVector concurrentVector) {
        this.concurrentVector = concurrentVector;

        resultVectors = new ArrayList<SequentialVector>();
        resultDoubles = new ArrayList<Double>();

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
        workDone++;
        if (finishedExecuting()) {
            concurrentVector._notify();
        }
    }

    public void addResultVector(SequentialVector resultVector) {
        resultVectors.add(resultVector);
    }

    public void addResultDoubles(double d) {
        resultDoubles.add(d);
    }

    public ArrayList<Double> resultDoubles() {
        return resultDoubles;
    }

    public ArrayList<SequentialVector> resultVectors() {
        return resultVectors;
    }

    public boolean isExecuting() {
        return workToDo > 0 && workToDo != workDone;
    }

    private boolean finishedExecuting() {
        return workToDo > 0 && workToDo == workDone;
    }

    public void setWorkToDo(int workToDo) {
        this.workToDo = workToDo;
    }

    public void resetExecution() {
        workDone = 0;
        workToDo = 0;
    }
}
