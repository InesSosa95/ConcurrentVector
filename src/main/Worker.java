package main;

public class Worker extends Thread {

    private Buffer buffer;

    private ThreadPool pool;

    public Worker(Buffer b, ThreadPool pool) {
        buffer = b;
        this.pool = pool;
    }

    public void run() {
        while (true) {
            Task task = buffer.pop();
            switch (task.instruction()) {
                case Set:
                    task.sequentialVector().set(task.parameter());
                    pool.addResultVector(task.sequentialVector());
                    break;
                case Add:
                    task.sequentialVector().add(task.parameterVector());
                    pool.addResultVector(task.sequentialVector());
                    break;
                case Abs:
                    task.sequentialVector().abs();
                    pool.addResultVector(task.sequentialVector());
                    break;
                case Mul:
                    task.sequentialVector().mul(task.parameterVector());
                    pool.addResultVector(task.sequentialVector());
                    break;
                case Assign:
                    task.sequentialVector().assign(task.parameterVector());
                    pool.addResultVector(task.sequentialVector());
                    break;
                case AssignWithMask:
                    task.sequentialVector().assign(task.maskVector(), task.parameterVector());
                    pool.addResultVector(task.sequentialVector());
                    break;
            }
            pool.increaseWorkDone();
        }
    }

}
