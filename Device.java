public abstract class Device {
    protected final ActivationQueue outputQueue;
    protected final ActivationQueue inputQueue;
    protected String name;

    public static void send(Device device, Frame frame) {
        device.inputQueue.putFrame(frame);
    }
    
    public Device() {
        inputQueue = new ActivationInputQueue();
        outputQueue = new ActivationOutputQueue();
        inputQueue.setDelegate(this);
        outputQueue.setDelegate(this);
        outputQueue.start();
        inputQueue.start();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public void sendFrame(Frame frame) {
        outputQueue.putFrame(frame);
    }

    public abstract void output(Frame frame);
    
    public abstract void fetch(Frame frame);
    //    abstract public Port getPort();
    //    abstract public Port getPort(int index);
    //    abstract public byte[] getMAC();
    //    abstract public void test(Frame frame);
}
