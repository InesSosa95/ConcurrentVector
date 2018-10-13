public class ConcurrentVector {

    private int dimension;
    private int threads;
    private SeqVector sequentialVector;

    public ConcurrentVector(int dimension, int threads){
        this.dimension = dimension;
        this.threads = threads;
        this.sequentialVector = new SeqVector(dimension);
    }

    public int dimension(){
        return this.sequentialVector.dimension();
    }

    synchronized public double get(int i){
       return this.sequentialVector.get(i);
    }

    synchronized public void set(int i, double d){
        this.sequentialVector.set(i, d);
    }

    public void assign(SeqVector vector) {
        this.sequentialVector.assign(vector);
    }

    public void assign(SeqVector mask, SeqVector vector){
        this.sequentialVector.assign(mask, vector);
    }

    public void add(SeqVector vector){
        this.sequentialVector.add(vector);
    }

    public void mul(SeqVector vector){
        this.sequentialVector.mul(vector);
    }

    public void abs(){
        this.sequentialVector.abs();
    }

    public double sum(){
        return this.sequentialVector.sum();
    }

    public double mean(){
        return this.sequentialVector.mean();
    }

    public double prod(SeqVector v){
       return this.sequentialVector.prod(v);
    }

    public double norm(){
        return this.sequentialVector.norm();
    }

    public double max(){
        return this.sequentialVector.max();
    }


}
