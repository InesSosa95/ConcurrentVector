public class ThreadPool {

    private Worker[] workers;

    private Buffer buffer;

//    private result

    public ThreadPool(int threads, Buffer buffer) {
        workers = new Worker[threads];
        this.buffer = buffer;
        for (int i = 0; i < threads; i++) {
            workers[i] = new Worker(buffer);
        }
    }

    public Worker[] workers() {
        return workers;
    }

    public int dimension() {
        return workers.length;
    }

    public void work() {

        for (Worker w : workers) {
            w.start();
        }

    }
}
