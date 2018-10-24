package main;

public class ConcurrentVector {

    private ThreadPool threadPool;

    private SequentialVector sequentialVector;

    private Buffer buffer;

    private int threadsToExecute;
    private int threadsExecuted;

    public  ConcurrentVector(int dimension, int threads) {
        threadsToExecute = 0;
        threadsExecuted = 0;
        buffer = new Buffer(1000);
        threadPool = new ThreadPool(threads, buffer, this);
        sequentialVector = new SequentialVector(dimension);
    }

    public int dimension() {
        return sequentialVector.dimension();
    }

    public double get(int i) {
        return sequentialVector.get(i);
    }

    public void set(int i, double d) {
        sequentialVector.set(i, d);
    }

    /**
     * Synchronized methods
     */

    synchronized public void set(double d) {

        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector v = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                double val = sequentialVector.get(j);
                v.set(pos, val);
                pos++;
            }

            Task task = new Task(Instruction.Set, v, d);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public void assign(SequentialVector v) {
        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector selfSlice = new SequentialVector(vectorSize);
            SequentialVector otherSlice = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                selfSlice.set(pos, sequentialVector.get(j));
                otherSlice.set(pos, v.get(j));
                pos++;
            }

            Task task = new Task(Instruction.Assign, selfSlice, otherSlice);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public void assign(SequentialVector mask, SequentialVector v) {
        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector selfSlice = new SequentialVector(vectorSize);
            SequentialVector otherSlice = new SequentialVector(vectorSize);
            SequentialVector maskSlice = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                selfSlice.set(pos, sequentialVector.get(j));
                otherSlice.set(pos, v.get(j));
                maskSlice.set(pos, mask.get(j));
                pos++;
            }

            Task task = new Task(Instruction.AssignWithMask, selfSlice, otherSlice, maskSlice);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public void add(SequentialVector v) {
        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector selfSlice = new SequentialVector(vectorSize);
            SequentialVector otherSlice = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                selfSlice.set(pos, sequentialVector.get(j));
                otherSlice.set(pos, v.get(j));
                pos++;
            }

            Task task = new Task(Instruction.Add, selfSlice, otherSlice);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public void mul(SequentialVector v) {
        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector selfSlice = new SequentialVector(vectorSize);
            SequentialVector otherSlice = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                selfSlice.set(pos, sequentialVector.get(j));
                otherSlice.set(pos, v.get(j));
                pos++;
            }

            Task task = new Task(Instruction.Mul, selfSlice, otherSlice);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public void abs() {
        int elementsPerTask = elementsPerTask();

        threadPool.setWorkToDo(threadPool.dimension());

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = i * elementsPerTask;
            int end;
            int vectorSize;

            if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
                int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
                vectorSize = sequentialVector.dimension() - elementsUpToNow;
                end = start + vectorSize - 1;
            } else {
                vectorSize = elementsPerTask;
                end = start + elementsPerTask - 1;
            }

            SequentialVector v = new SequentialVector(vectorSize);
            int pos = 0;

            for (int j = start; j <= end; j++) {
                double val = sequentialVector.get(j);
                v.set(pos, val);
                pos++;
            }

            Task task = new Task(Instruction.Abs, v);
            buffer.push(task);
        }

        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }

        threadPool.resetExecution();
    }

    synchronized public double sum() {
        // TODO: implement sum
        return sequentialVector.sum();
    }

    synchronized public double avg() {
        // TODO: implement mean
        return sequentialVector.mean();
    }

    synchronized public double prod(SequentialVector v) {
        // TODO: implement prod
        return sequentialVector.prod(v);
    }

    synchronized public double norm() {
        // TODO: implement norm
        return sequentialVector.norm();
    }

    synchronized public double max() {
        // TODO: implement max
        return 1;
    }

    /*
     * Auxiliar methods
     */
    private int elementsPerTask() {
        return sequentialVector.dimension() / threadPool.dimension();
    }

    private boolean hasModulus() {
        return (sequentialVector.dimension() % threadPool.dimension()) > 0;
    }

    private boolean isLastIteration(int i, int totalLength) {
        return (i + 1) == totalLength;
    }

    public synchronized void _notify() {
        notifyAll();
    }
}
