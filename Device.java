public abstract class Device extends Thread {
    protected final ActivationQueue queue;

    public Device() {
        queue = new ActivationQueue();
    }
    
    public void run() {
        while(true) {
            Frame frame = queue.takeFrame();
            fetch(frame);
            /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            */
        }
    }
    
    public void sendFrame(Frame frame) {
        queue.putFrame(frame);
    }

    public abstract void fetch(Frame frame);
    //    abstract public Port getPort();
    //    abstract public Port getPort(int index);
    //    abstract public byte[] getMAC();
    //    abstract public void test(Frame frame);
}
