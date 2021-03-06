import java.io.IOException;

public class Switch extends Hub {
    private byte[][] table;

    public Switch() {
        table = new byte[8][];
        for(int i=0;i<8;i++) {
            table[i] = new byte[6];
        }
    }
    
    public void showTable() {
        System.out.println("TABLE");
        for(int i=0;i<8;i++) {
            System.out.println(bytes2Addr(table[i]));
        }
    }
    
    protected void test(Frame frame, int index) {
        table[index] = frame.getSource();
        showTable();
        byte[] data = frame.getBytes();
        /* search destination */
        boolean notExist = true;
        for(int i=0;i<8;i++) {
            Port port = this.ports[i];
            if (port.isConnected() &&  match(table[i],frame.getDestination())) {
                notExist = false;
                try {
                    try {
                        Thread.sleep(data.length);
                    } catch (Exception e) {
                    }
                    port.write(data, 0, data.length);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }                
            }
        }
        if (notExist) {
            super.test(frame, index);
        }
    }

    private boolean match(byte[] a, byte[] b) {
        for(int i=0;i<6;i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
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
