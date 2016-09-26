abstract class ActivationQueue<T>  extends Thread {
    protected static final int MAX_SIZE = 30;
    protected final Object[] queue;
    protected int tail;
    protected int head;
    protected int count;

    public ActivationQueue() {
        this.queue = new Object[MAX_SIZE];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    public void run() {
        while(true) {
            fetch(take());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void put(T t) {
        while (count >= queue.length) {
            try {
                wait();
	      } catch (InterruptedException e) {
            }
        }
        queue[tail] = t;
        tail = (tail + 1) % queue.length;
        count++;
        notifyAll();
    }

    @SuppressWarnings("unchecked")
    public synchronized T take() {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        T t  = (T)queue[head];
        head = (head + 1) % queue.length;
        count--;
        notifyAll();
        return t;
    }
    
    @SuppressWarnings("unchecked")
    public T top() {
        return (T)queue[head];
    }

    public int size() {
        return count;
    }

    protected abstract void fetch(T t);
}
