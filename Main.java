import java.io.IOException;

public class Main {

    public static void main(String args[]) {
        byte[] bytes = { (byte)0x45, (byte)0x00, (byte)0x00, (byte)0x28,
                         (byte)0xe6, (byte)0x0f, (byte)0x40, (byte)0x00,
                         (byte)0x40, (byte)0x06, (byte)0x35, (byte)0x33,
                         (byte)0xc0, (byte)0xa8, (byte)0x0b, (byte)0x07,
                         (byte)0x11, (byte)0x9a, (byte)0x42, (byte)0x44,
                         (byte)0xc3, (byte)0x2d, (byte)0x01, (byte)0xbb,
                         (byte)0xf0, (byte)0xf0, (byte)0xa6, (byte)0xbe,
                         (byte)0x2a, (byte)0x0b, (byte)0xbf, (byte)0xdc,
                         (byte)0x50, (byte)0x10, (byte)0x20, (byte)0x00,
                         (byte)0x29, (byte)0xc7, (byte)0x00, (byte)0x00
        };
        Packet packet = new Packet(bytes);

	packet.setData(new byte[813]);
	//	packet.setData(bytes);

	//        TCPSegment seg = new TCPSegment(packet);
        try {
            //Switch sw = new Switch();
            Hub hub = new Hub();
            Terminal t1 = new Terminal("localhost");
            Terminal t2 = new Terminal("T2");
            //sw.start();
            //hub.start();
            hub.start();
            t1.start();
            t2.start();

            hub.connect(t1.getPort(), 0);
            hub.connect(t2.getPort(), 1);

            Frame frame = new Frame();
            frame.setDestination(t1.getMAC());
            frame.setSource(t2.getMAC());
            frame.setData(packet.getBytes());
            t2.test(frame);
	    
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
//            t1.test(frame);
//            t3.test(frame);
//            t2.test(frame);            
            //            t1.test(frame.getBytes());
            //            p2.write(data, 0, data.length);            
            //            p.write(data, 0, data.length);
//            sw.run();
/*
            while(p2.available() < 1) {
                System.out.println("a");                
            }

            while(p2.available() > 0) {
                System.out.println(p2.read());
            }
  */          

//            while(true) {
//                System.out.println(p2.read());
//            }

//            System.out.println(p3.read());
//            System.out.println(p.read());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
