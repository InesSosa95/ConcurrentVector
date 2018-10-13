public class Buffer {

    private int size = 2;
    private int[] buffer;
    private int proximoAgregar = 0;
    private int proximoConsumir = 0;

    public Buffer(int size){
        this.size = size;
        this.buffer = new int[size];
    }

        public synchronized void push(int i) {
            while(this.isFull()) {
                try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            this.buffer[this.proximoAgregar] = i;
            this.proximoAgregar = this.next(this.proximoAgregar);
            this.notify();
        }

        public synchronized int pop() {
            while (this.isEmpty()) {
                try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            int result = this.buffer[this.proximoConsumir];
            this.proximoConsumir = this.next(this.proximoConsumir);
            this.notify();
            return result;
        }

        private boolean isEmpty() {
            return this.proximoAgregar == this.proximoConsumir;
        }

        private boolean isFull() {
            return this.next(this.proximoAgregar) == this.proximoConsumir;
        }

        private int next(int i) {
            return (i + 1) % this.size;
        }
}
