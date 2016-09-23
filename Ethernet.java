public class Ethernet {
    public static void read(Frame frame) {
	System.out.println(frame.description());
	Packet packet = new Packet(frame);
	IPv4.read(packet);
    }
    public static Frame[] makeFragment(Frame frame) {
	Packet packet = new Packet(frame);
	Packet[] packets = IPv4.makeFragment(packet);

	Frame[] frames = new Frame[packets.length];

	for (int i=0;i<packets.length;i++) {
	    
	    //    System.out.println(packets[i].description());
	    Frame f = Frame.getHeader(frame);
	    f.setData(packets[i].getBytes());
	    frames[i] = f;
	}
	return frames;
    }
}
