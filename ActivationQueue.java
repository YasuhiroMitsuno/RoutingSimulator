abstract class ActivationQueue  extends Thread {
    protected static final int MAX_FRAME_SIZE = 30;
    protected final Frame[] frameQueue;
    protected int tail;
    protected int head;
    protected int count;
    protected Device delegate;

    public ActivationQueue() {
        this.frameQueue = new Frame[MAX_FRAME_SIZE];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

    public void setDelegate(Device device) {
        this.delegate = device;
    }

    public void run() {
        while(true) {
            fetch(takeFrame());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public abstract void fetch(Frame frame);

    public synchronized void putFrame(Frame frame) {
        //show();
        //        System.out.println("PUT");
        while (count >= frameQueue.length) {
            try {
                wait();
	      } catch (InterruptedException e) {
            }
        }
        frameQueue[tail] = frame;
        tail = (tail + 1) % frameQueue.length;
        count++;
        notifyAll();
    }

    public synchronized void show() {
        String str = "";
        for(int i=0;i<MAX_FRAME_SIZE;i++) {
            if (i<count) {
                str += "■";
            } else {
                str += "□";
            }
        }
        System.out.println(str + " " + delegate.getName());
    }

    public synchronized Frame takeFrame() {
        //        show();
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Frame frame  = frameQueue[head];
        head = (head + 1) % frameQueue.length;
        count--;
        notifyAll();
        return frame;
    }
    public int size() {
        return count;
    }
    public Frame topFrame() {
        return frameQueue[head];
    }
}
