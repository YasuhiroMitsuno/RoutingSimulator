import java.io.IOException;

public class Hub extends Device {
    protected Port[] ports;
    private int size;

    private Device[] devices;
    
    public Hub() {
        ports = new Port[8];
        for (int i=0;i<8;i++) {
            ports[i] = new Port();
        }
        devices = new Device[8];
    }
    
    public void connect(Port _port, int _index) {
        Port port = this.ports[_index];
        port.connect(_port);
    }

    public void connect(Device device, int index) {
        if (devices[index] != null) {
            System.out.println("ERR-0001");
        }
        devices[index] = device;
    }

    public Port getPort(int index) {
        return this.ports[index];
    }

    /*    public void sendFrame(Frame frame, Device except_device) {
        for (Device device : devices) {
            if (device == null || except_device == device) continue;
            device.putFrame(frame);
        }
    }
    */
    public void fetch(Frame frame) {
        outputQueue.put(frame);        
        //  Ethernet.read(frame);
    }

    public void output(Frame frame) {
        System.out.println("IPForward");
        for (Device device : devices) {
            if (device == null) continue;
            Device.send(device, frame);
        }
    }
    
    /*
    protected Frame fetch(int i) {
        Port port = this.ports[i];
        try {
            if (port.available() > 0) {
                System.out.println("From Port " + i);
                byte[] data = new byte[5000];
                int len = 0;
                int count = 0;
                while (port.available() > 0) {
                    int b = port.read();
                    data[len++] = (byte)b;
                }
                byte[] bytes = new byte[len];
                System.arraycopy(data, 0, bytes, 0, len);
		Frame frame = new Frame(bytes);
		//		Ethernet.read(frame);
                //	/               System.out.println(f.description());
                return frame;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    */
    protected void test(Frame frame, int index) {
        byte[] data = frame.getBytes();
        for(int i=0;i<8;i++) {
            if (i == index) continue;
            Port port = this.ports[i];
            try {
                if (port.isConnected()) {
                    try {
                        Thread.sleep(data.length);
                    } catch (Exception e) {
                    }
                    port.write(data, 0, data.length);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
