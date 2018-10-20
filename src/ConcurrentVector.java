public class ConcurrentVector {

    private int dimension;
    private int threads;
    private SequentialVector sequentialVector;

    public ConcurrentVector(int dimension, int threads) {
        this.dimension = dimension;
        this.threads = threads;
        this.sequentialVector = new SequentialVector(dimension);
    }

    public int dimension() {
        return this.sequentialVector.dimension();
    }

    synchronized public double get(int i) {
        return this.sequentialVector.get(i);
    }

    synchronized public void set(int i, double d) {
        this.sequentialVector.set(i, d);
    }

    public void assign(SequentialVector vector) {
        this.sequentialVector.assign(vector);
    }

    public void assign(SequentialVector mask, SequentialVector vector) {
        this.sequentialVector.assign(mask, vector);
    }

    public void add(SequentialVector vector) {
        this.sequentialVector.add(vector);
    }

    public void mul(SequentialVector vector) {
        this.sequentialVector.mul(vector);
    }

    public void abs() {
        this.sequentialVector.abs();
    }

    public double sum() {
        return this.sequentialVector.sum();
    }

    public double mean() {
        return this.sequentialVector.mean();
    }

    public double prod(SequentialVector v) {
        return this.sequentialVector.prod(v);
    }

    public double norm() {
        return this.sequentialVector.norm();
    }

    public double max() {
        return this.sequentialVector.max();
    }


}
