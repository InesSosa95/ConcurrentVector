package main;

public class Buffer {

    private Task[] buffer;
    private int nextAddition = 0;
    private int nextConsumption = 0;

    public Buffer(int size) {
        this.buffer = new Task[size];
    }

    public synchronized void push(Task t) {
        while (this.isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer[this.nextAddition] = t;
        this.nextAddition = this.next(this.nextAddition);
        this.notifyAll();
    }

    public synchronized Task pop() {
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Task result = this.buffer[this.nextConsumption];
        this.nextConsumption = this.next(this.nextConsumption);
        this.notifyAll();
        return result;
    }

    private boolean isEmpty() {
        return this.nextAddition == this.nextConsumption;
    }

    private boolean isFull() {
        return this.next(this.nextAddition) == this.nextConsumption;
    }

    private int next(int i) {
        return (i + 1) % this.buffer.length;
    }

    public int dimension() {
        return buffer.length;
    }
}
