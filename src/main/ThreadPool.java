package main;

import java.util.ArrayList;
import java.util.HashMap;

public class ThreadPool {

    private Worker[] workers;

    private int workToDo;
    private int workDone;
    private boolean workDoneMutexIsTaken = false;

    private HashMap<Integer, SequentialVector> resultVectors;
    private boolean resultVectorsMutexIsTaken = false;
    private ArrayList<Double> resultDoubles;
    private boolean resultDoublesMutexIsTaken = false;

    private ConcurrentVector concurrentVector;

    public ThreadPool(int threads, Buffer buffer, ConcurrentVector concurrentVector) {
        this.concurrentVector = concurrentVector;

        resultVectors = new HashMap<Integer, SequentialVector>();
        resultDoubles = new ArrayList<Double>();

        workers = new Worker[threads];
        for (int i = 0; i < threads; i++) {
            workers[i] = new Worker(buffer, this);
            workers[i].start();
        }
    }

    public int dimension() {
        return workers.length;
    }

    public synchronized void increaseWorkDone() {
        while (workDoneMutexIsTaken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workDoneMutexIsTaken = true;
        workDone++;
        if (finishedExecuting()) {
            concurrentVector.WorkIsDone();
        }
        workDoneMutexIsTaken = false;
        notifyAll();
    }

    public synchronized void addResultVector(Integer i, SequentialVector resultVector) {
        while (resultVectorsMutexIsTaken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resultVectorsMutexIsTaken = true;
        resultVectors.put(i, resultVector);
        resultVectorsMutexIsTaken = false;
        notifyAll();
    }

    public synchronized void addResultDoubles(double d) {
        while (resultDoublesMutexIsTaken) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resultDoublesMutexIsTaken = true;
        resultDoubles.add(d);
        resultDoublesMutexIsTaken = false;
        notifyAll();
    }

    public ArrayList<Double> resultDoubles() {
        return resultDoubles;
    }

    public HashMap<Integer, SequentialVector> resultVectors() {
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

    public void emptyResults() {
        resultVectors.clear();
        resultDoubles.clear();
    }
}
