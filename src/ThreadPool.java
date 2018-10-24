import java.util.ArrayList;

public class ThreadPool {

    private Worker[] workers;

    private Buffer buffer;

    private int workToDo;
    private int workDone;

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
        workDone++;
        if (finishedExecuting()) {
            concurrentVector._notify();
        }
    }

    public void addResultVector(SequentialVector resultVector) {
        resultVectors.add(resultVector);
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

    public int getWorkToDo() {
        return workToDo;
    }

    public void setWorkToDo(int workToDo) {
        this.workToDo = workToDo;
    }

    public int getWorkDone() {
        return workDone;
    }

    public void setWorkDone(int workDone) {
        this.workDone = workDone;
    }

    public void resetExecution() {
        workDone = 0;
        workToDo = 0;
    }
}
