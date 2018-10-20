public class Buffer {

    private int size;
    private int[] buffer;
    private int nextAddition = 0;
    private int nextConsumption = 0;

    public Buffer(int size) {
        this.size = size;
        this.buffer = new int[size];
    }

    public synchronized void push(int i) {
        while (this.isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer[this.nextAddition] = i;
        this.nextAddition = this.next(this.nextAddition);
        this.notify();
    }

    public synchronized int pop() {
        while (this.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int result = this.buffer[this.nextConsumption];
        this.nextConsumption = this.next(this.nextConsumption);
        this.notify();
        return result;
    }

    private boolean isEmpty() {
        return this.nextAddition == this.nextConsumption;
    }

    private boolean isFull() {
        return this.next(this.nextAddition) == this.nextConsumption;
    }

    private int next(int i) {
        return (i + 1) % this.size;
    }
}
