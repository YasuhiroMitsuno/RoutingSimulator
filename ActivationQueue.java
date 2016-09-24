class ActivationQueue {
    private static final int MAX_FRAME_SIZE = 100;
    private final Frame[] frameQueue;
    private int tail;
    private int head;
    private int count;

    public ActivationQueue() {
        this.frameQueue = new Frame[MAX_FRAME_SIZE];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }
    public synchronized void putFrame(Frame frame) {
        while (count >= frameQueue.length) {
            System.out.println("Lost Frame");	    
            return;
            /*	      try {
	      wait();
	      } catch (InterruptedException e) {
	      }
            */
              //takeFrame();
        }
        frameQueue[tail] = frame;
        tail = (tail + 1) % frameQueue.length;
        count++;
        notifyAll();
    }
    public synchronized Frame takeFrame() {
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
