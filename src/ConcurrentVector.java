import java.util.ArrayList;

public class ConcurrentVector {

    private ThreadPool threadPool;

    private SequentialVector sequentialVector;

    private Buffer buffer;

    public ConcurrentVector(int dimension, int threads) {
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
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public void assign(SequentialVector v) {
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public void assign(SequentialVector mask, SequentialVector v) {
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public void add(SequentialVector v) {
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public void mul(SequentialVector v) {
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public void abs() {
        waitUntilWorkIsDone();

        threadPool.setWorkToDo(threadPool.dimension());

        int elementsPerTask = elementsPerTask();

        for (int i = 0; i < threadPool.dimension(); i++) {
            int start = sliceStartAt(i, elementsPerTask);
            int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
            int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

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

        waitUntilWorkIsDone();

        mergeWithThreadPoolVectors();

        resetWorkCounters();
    }

    synchronized public double sum() {
        if (sequentialVector.dimension() <= threadPool.dimension()) {
            waitUntilWorkIsDone();
            threadPool.setWorkToDo(1);
            SequentialVector v = new SequentialVector(sequentialVector.dimension());
            for (int i = 0; i < sequentialVector.dimension(); i++) {
                v.set(i, sequentialVector.get(i));
            }
            Task task = new Task(Instruction.Sum, v);
            buffer.push(task);

        } else {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(threadPool.dimension());

            int elementsPerTask = elementsPerTask();

            for (int i = 0; i < threadPool.dimension(); i++) {
                int start = sliceStartAt(i, elementsPerTask);
                int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
                int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

                SequentialVector v = new SequentialVector(vectorSize);
                int pos = 0;

                for (int j = start; j <= end; j++) {
                    double val = sequentialVector.get(j);
                    v.set(pos, val);
                    pos++;
                }

                Task task = new Task(Instruction.Sum, v);
                buffer.push(task);
            }
        }

        waitUntilWorkIsDone();

        resetWorkCounters();

        ConcurrentVector doublesVector = concurrentVectorFromArray(threadPool.resultDoubles(), threadPool.dimension());

        if (doublesVector.dimension() > 1)
            return doublesVector.sum();

        return doublesVector.get(0);
    }

    synchronized public double mean() {
        if (sequentialVector.dimension() <= threadPool.dimension()) {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(1);
            SequentialVector v = new SequentialVector(sequentialVector.dimension());
            for (int i = 0; i < sequentialVector.dimension(); i++) {
                v.set(i, sequentialVector.get(i));
            }
            Task task = new Task(Instruction.Mean, v);
            buffer.push(task);
        } else {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(threadPool.dimension());

            int elementsPerTask = elementsPerTask();

            for (int i = 0; i < threadPool.dimension(); i++) {
                int start = sliceStartAt(i, elementsPerTask);
                int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
                int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

                SequentialVector v = new SequentialVector(vectorSize);
                int pos = 0;

                for (int j = start; j <= end; j++) {
                    double val = sequentialVector.get(j);
                    v.set(pos, val);
                    pos++;
                }

                Task task = new Task(Instruction.Mean, v);
                buffer.push(task);
            }
        }

        waitUntilWorkIsDone();

        resetWorkCounters();

        ConcurrentVector doublesVector = concurrentVectorFromArray(threadPool.resultDoubles(), threadPool.dimension());

        if (doublesVector.dimension() > 1)
            return doublesVector.mean();

        return doublesVector.get(0);
    }

    synchronized public double prod(SequentialVector v) {

        if (sequentialVector.dimension() <= threadPool.dimension()) {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(1);

            Task task = new Task(Instruction.Prod, sequentialVector, v);
            buffer.push(task);
        } else {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(threadPool.dimension());

            int elementsPerTask = elementsPerTask();

            for (int i = 0; i < threadPool.dimension(); i++) {
                int start = sliceStartAt(i, elementsPerTask);
                int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
                int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

                SequentialVector selfSlice = new SequentialVector(vectorSize);
                SequentialVector otherSlice = new SequentialVector(vectorSize);
                int pos = 0;

                for (int j = start; j <= end; j++) {
                    selfSlice.set(pos, sequentialVector.get(j));
                    otherSlice.set(pos, v.get(j));
                    pos++;
                }

                Task task = new Task(Instruction.Prod, selfSlice, otherSlice);
                buffer.push(task);
            }
        }

        waitUntilWorkIsDone();

        resetWorkCounters();

        ConcurrentVector doublesVector = concurrentVectorFromArray(threadPool.resultDoubles(), threadPool.dimension());

        return doublesVector.sum();
    }

    synchronized public double norm() {
        if (sequentialVector.dimension() <= threadPool.dimension()) {
            waitUntilWorkIsDone();
            threadPool.setWorkToDo(1);
            SequentialVector v = new SequentialVector(sequentialVector.dimension());
            for (int i = 0; i < sequentialVector.dimension(); i++) {
                v.set(i, sequentialVector.get(i));
            }
            Task task = new Task(Instruction.Norm, v);
            buffer.push(task);

        } else {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(threadPool.dimension());

            int elementsPerTask = elementsPerTask();

            for (int i = 0; i < threadPool.dimension(); i++) {
                int start = sliceStartAt(i, elementsPerTask);
                int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
                int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

                SequentialVector v = new SequentialVector(vectorSize);
                int pos = 0;

                for (int j = start; j <= end; j++) {
                    double val = sequentialVector.get(j);
                    v.set(pos, val);
                    pos++;
                }

                Task task = new Task(Instruction.Norm, v);
                buffer.push(task);
            }

        }

        waitUntilWorkIsDone();

        resetWorkCounters();

        ConcurrentVector doublesVector = concurrentVectorFromArray(threadPool.resultDoubles(), threadPool.dimension());

        if (doublesVector.dimension() > 1)
            return doublesVector.norm();

        return doublesVector.get(0);
    }

    synchronized public double max() {
        if (sequentialVector.dimension() <= threadPool.dimension()) {
            waitUntilWorkIsDone();
            threadPool.setWorkToDo(1);
            SequentialVector v = new SequentialVector(sequentialVector.dimension());
            for (int i = 0; i < sequentialVector.dimension(); i++) {
                v.set(i, sequentialVector.get(i));
            }
            Task task = new Task(Instruction.Max, v);
            buffer.push(task);
        } else {
            waitUntilWorkIsDone();

            threadPool.setWorkToDo(threadPool.dimension());

            int elementsPerTask = elementsPerTask();

            for (int i = 0; i < threadPool.dimension(); i++) {
                int start = sliceStartAt(i, elementsPerTask);
                int vectorSize = sliceSize(i, threadPool.dimension(), elementsPerTask);
                int end = sliceEndsAt(i, start, vectorSize, elementsPerTask);

                SequentialVector v = new SequentialVector(vectorSize);
                int pos = 0;

                for (int j = start; j <= end; j++) {
                    double val = sequentialVector.get(j);
                    v.set(pos, val);
                    pos++;
                }

                Task task = new Task(Instruction.Max, v);
                buffer.push(task);
            }
        }

        waitUntilWorkIsDone();

        resetWorkCounters();

        ConcurrentVector doublesVector = concurrentVectorFromArray(threadPool.resultDoubles(), threadPool.dimension());

        if (doublesVector.dimension() > 1)
            return doublesVector.max();

        return doublesVector.get(0);
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

    public synchronized void WorkIsDone() {
        notifyAll();
    }

    private ConcurrentVector concurrentVectorFromArray(ArrayList<Double> array, int threads) {
        ConcurrentVector concurrentVector = new ConcurrentVector(array.size(), threads);

        for (int i = 0; i < array.size(); i++) {
            concurrentVector.set(i, array.get(i));
        }

        return concurrentVector;
    }

    private void waitUntilWorkIsDone() {
        while (threadPool.isExecuting()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void mergeWithThreadPoolVectors() {
        int cont = 0;

        for (SequentialVector resultVector : threadPool.resultVectors()) {
            for (int i = 0; i < resultVector.dimension(); i++) {
                this.set(cont, resultVector.get(i));
                cont++;
            }
        }
    }

    private void resetWorkCounters() {
        threadPool.resetExecution();
    }

    private int sliceSize(int i, int threads, int elementsPerTask) {
        if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
            int elementsUpToNow = (threadPool.dimension() - 1) * elementsPerTask;
            return sequentialVector.dimension() - elementsUpToNow;
        } else {
            return elementsPerTask;
        }
    }

    private int sliceEndsAt(int i, int start, int vectorSize, int elementsPerTask) {
        if (hasModulus() && isLastIteration(i, threadPool.dimension())) {
            return start + vectorSize - 1;
        } else {
            return start + elementsPerTask - 1;
        }
    }

    private int sliceStartAt(int i, int elementsPerTask) {
        return i * elementsPerTask;
    }
}
