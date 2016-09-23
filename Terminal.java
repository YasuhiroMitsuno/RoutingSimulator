import java.util.Random;
import java.io.IOException;

public class Terminal extends Device {
    private Port port;
    private String hostname;
    private byte[] MAC;

    public Terminal() {
        this.port = new Port();
        Random rnd = new Random();
        this.MAC = new byte[6];
        this.MAC[0] = 0x00;
        this.MAC[1] = 0x0c;
        this.MAC[2] = 0x29;
        this.MAC[3] = (byte)rnd.nextInt(256);
        this.MAC[4] = (byte)rnd.nextInt(256);
        this.MAC[5] = (byte)rnd.nextInt(256);
    }

    public Terminal(String hostname) {
        this.port = new Port();
        Random rnd = new Random();
        this.MAC = new byte[6];
        this.MAC[0] = 0x00;
        this.MAC[1] = 0x0c;
        this.MAC[2] = 0x29;
        this.MAC[3] = (byte)rnd.nextInt(256);
        this.MAC[4] = (byte)rnd.nextInt(256);
        this.MAC[5] = (byte)rnd.nextInt(256);
        this.hostname = hostname;
    }

    public byte[] getMAC() {
        return this.MAC;
    }

    public Port getPort() {
        return port;
    }

    public Port getPort(int index) {
	return getPort();
    }

    public void run() {
        while(true) {
            try {
                if (port.available() > 0) {
                    System.out.println("TERMINAL " + hostname);
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
		    Ethernet.read(frame);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void test(Frame frame) throws IOException {
        frame.setSource(this.getMAC());
        byte[] data = frame.getBytes();
        port.write(data, 0, data.length);
    }
}
