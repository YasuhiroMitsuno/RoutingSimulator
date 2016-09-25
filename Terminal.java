import java.util.Random;
import java.io.IOException;

public class Terminal extends Device {
    private Port port;
    private String hostname;
    private byte[] MAC;
    private Device device;

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

    public void connect(Device device) {
        this.device = device;
    }
    
    public Port getPort(int index) {
	return getPort();
    }

    public void fetch(Frame frame) {
        System.out.println("GET" + hostname);
        //Ethernet.read(frame);
    }

    public void output(Frame frame) {
        if (device != null) {
            Device.send(device, frame);
        }
    }
    
    /*    public void test(Frame frame) throws IOException {
        frame.setSource(this.getMAC());
        byte[] data = frame.getBytes();
        port.write(data, 0, data.length);
    }
    */
}
