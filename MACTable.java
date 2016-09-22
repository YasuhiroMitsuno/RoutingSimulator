public class MACTable {
    private int[] ports;
    private byte[][] addresses;

    public MACTable() {
        ports = new int[16];
        addresses = new byte[16][];
    }

    public void showTable() {
        System.out.println("TABLE");
        for(int i=0;i<16;i++) {
            System.out.println(ports[i] + " " bytes2Addr(addresses[i]));
        }
    }

    private String bytes2Addr(byte[] bytes) {
        String addr = String.format("%02x", (bytes[0] & 0xFF)) + ":" +
            String.format("%02x", (bytes[1] & 0xFF)) + ":" +
            String.format("%02x", (bytes[2] & 0xFF)) + ":" +
            String.format("%02x", (bytes[3] & 0xFF)) + ":" +
            String.format("%02x", (bytes[4] & 0xFF)) + ":" +
            String.format("%02x", (bytes[5] & 0xFF));
        return addr;
    }
    
}
