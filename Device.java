public abstract class Device {
    protected final ActivationInputQueue<Frame> inputQueue;
    protected final ActivationOutputQueue<Frame> outputQueue;
    protected String name;
    protected int MTU;
    
    public static void send(Device device, Frame frame) {
        device.inputQueue.put(frame);
    }
    
    public Device() {
        inputQueue = new ActivationInputQueue<Frame>();
        outputQueue = new ActivationOutputQueue<Frame>();
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
        //        System.out.println("GET HUB");
        if (frame.getLength() > MTU + 18) {
            System.out.println("MAKE FRAGMENT");
            for (Frame f : Ethernet.makeFragment(frame, MTU)) {
                outputQueue.put(f);
            }
            return;
        } else {
            outputQueue.put(frame);
        }
    }

    protected abstract void output(Frame frame);
    
    protected abstract void fetch(Frame frame);
    
    /* Input Buffer Class */
    class ActivationInputQueue<T> extends ActivationQueue<T> {
        protected Device delegate;
        public void setDelegate(Device device) {
            this.delegate = device;
        }

        public void show() {
            String str = "";
            for(int i=0;i<MAX_SIZE;i++) {
                if (i<count) {
                    str += "■";
                } else {
                    str += "□";
                }
            }
            System.out.println(str);// + " " + delegate.getName());
        }

        
        public synchronized void put(T t) {
            show();
            if (count >= queue.length) {
                System.out.println("Frame Lost");
                return;
            }
            queue[tail] = t;
            tail = (tail + 1) % queue.length;
            count++;
            notifyAll();
        }

        public synchronized T take() {
            //            show();
            return (T)super.take();
        }
    
        protected void fetch(T t) {
            delegate.fetch((Frame)t);
        }
    }
    class ActivationOutputQueue<T> extends ActivationQueue<T> {
        protected Device delegate;
        public void setDelegate(Device device) {
            this.delegate = device;
        }
        
        public void fetch(T t) {
            delegate.output((Frame)t);
        }
    }
}
