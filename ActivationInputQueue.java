class ActivationInputQueue extends ActivationQueue {

    public void run() {
        while(true) {
            fetch(takeFrame());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void putFrame(Frame frame) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        show();
        if (count >= frameQueue.length) {
            System.out.println("Frame Lost");
            return;
        }
        frameQueue[tail] = frame;
        tail = (tail + 1) % frameQueue.length;
        count++;
        notifyAll();
    }

    public synchronized Frame takeFrame() {
        show();
        return super.takeFrame();
    }
    
    public void fetch(Frame frame) {
        delegate.fetch(frame);
    }
}
