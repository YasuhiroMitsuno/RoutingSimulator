
public class Main {

    public static void main(String args[]) {
        byte[] bytes = { (byte)0x45, (byte)0x00, (byte)0x00, (byte)0x28,
                         (byte)0x5f, (byte)0xbf, (byte)0x40, (byte)0x00,
                         (byte)0x35, (byte)0x06, (byte)0x2c, (byte)0x6f,
                         (byte)0x11, (byte)0xfc, (byte)0xdb, (byte)0xf6,
                         (byte)0xc0, (byte)0xa8, (byte)0x0b, (byte)0x07};
        Packet packet = new Packet(bytes);
        System.out.println(packet.description());

        packet = new Packet();
        packet.setVersion(4);
        System.out.println(String.format("0x%04x", packet.verifyChecksum()));
        packet.setId(0x5fbf);
        packet.setFlags(0x02);
        packet.setOffset(0);
        packet.setTTL(53);
        packet.setProtocol(6);
        packet.setSource("17.252.219.246");
        packet.setDestination("192.168.11.7");
        packet.setData(new byte[20]);
        System.out.println(packet.description());
    }
}
