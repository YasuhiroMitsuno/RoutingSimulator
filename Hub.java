import java.io.IOException;

public class Hub extends Device {
    protected Port[] ports;
    private int size;

    public Hub() {
        ports = new Port[8];
        for (int i=0;i<8;i++) {
            ports[i] = new Port();
        }
    }
    
    public void connect(Port _port, int _index) {
        Port port = this.ports[_index];
        port.connect(_port);
    }

    public Port getPort(int index) {
        return this.ports[index];
    }

    public void run() {
        while(true) {
            for(int i=0;i<8;i++) {
                Frame frame = fetch(i);
                if (frame == null) continue;
                Packet p = new Packet(frame.getData());
                System.out.println("CS: " + p.verifyChecksum());
                //System.out.println(p.description());
		for (Frame f : Ethernet.makeFragment(frame)) {
		    test(f, i);
		}
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

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
