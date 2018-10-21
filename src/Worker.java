public class Worker extends Thread {

    Buffer buffer;

    public Worker(Buffer b) {
        buffer = b;
    }

    public void run() {
        Task task = buffer.pop();

    }

}
