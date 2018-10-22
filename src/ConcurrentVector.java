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
            SequentialVector v;
            int start = i * elementsPerTask;
            int end;

            if (modulus() > 0 && i == threadPool.dimension() - 1) {
                int vectorSize = sequentialVector.dimension() - ((threadPool.dimension() - 1) * elementsPerTask);
                v = new SequentialVector(vectorSize);
                end = start + vectorSize - 1;
            } else {
                v = new SequentialVector(elementsPerTask);
                end = start + elementsPerTask - 1;
            }


            int pos = 0;

            for (int j = start; j <= end; j++) {
                try {
                    double val = sequentialVector.get(j);
                    v.set(pos, val);
                    pos++;
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }

            System.out.println(v.dimension());
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

    private int modulus() {
        return sequentialVector.dimension() % threadPool.dimension();
    }

}
