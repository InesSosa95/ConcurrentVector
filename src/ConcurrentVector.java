public class ConcurrentVector {

    private ThreadPool threadPool;

    private SequentialVector sequentialVector;

    private Buffer buffer;

    public ConcurrentVector(int dimension, int threads) {
        buffer = new Buffer(1000);
        threadPool = new ThreadPool(threads, buffer);
        sequentialVector = new SequentialVector(dimension);
    }

    public int dimension() {
        return this.sequentialVector.dimension();
    }

    public double get(int i) {
        return this.sequentialVector.get(i);
    }

    public void set(int i, double d) {
        sequentialVector.set(i, d);
    }

    /**
     * Inicio de métodos synchronized
     */

    synchronized public void set(double d) {
        // TODO: implement set
        sequentialVector.set(d);
    }

    synchronized public void assign(SequentialVector v) {
        // TODO: implement assign
        sequentialVector.assign(v);
    }

    synchronized public void assign(SequentialVector mask, SequentialVector v) {
        // TODO: implement assign
        sequentialVector.assign(mask, v);
    }

    synchronized public void add(SequentialVector v) {
        // TODO: implement add
        sequentialVector.add(v);
    }

    synchronized public void mul(SequentialVector v) {
        // TODO: implement mul
        sequentialVector.mul(v);
    }

    synchronized public void abs() {
        // TODO: implement abs
        sequentialVector.abs();
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

        int elementsPerTask = elementsPerTask();

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

            Task task = new Task(Instruction.Max, v);
            buffer.push(task);
        }

        return 1; // TODO: Debería retornar el resultado
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

}
