/*
  RFC3422(https://www.rfc-editor.org/rfc/rfc791.txt)に基づいたMACフレームクラス．

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+
      |  HDLC Flag    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |      Address and Control      |      0xFE     |      0x31     |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |        (reserved)             |     Source MAPOS Address      |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |F|0|Z|0| Pads  |   MAC Type    |    Destination MAC Address    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                       Destination MAC Address                 |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                       Source MAC Address                      |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |     Source MAC Address        |          Length/Type          |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                    LLC data ...
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                   LAN FCS (optional)                          |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |               potential line protocol pad                     |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                   Frame FCS (16/32bits)                       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

           Figure 3. 802.3 Frame format (IEEE 802 Un-tagged Frame)
 */

class Frame {
    private byte[] bytes;       /* Binary Data */
    private byte HDLF_Flag;
    private byte[] destination; /* Destination Address */
    private byte[] source;      /* Source Address */
    private int length;

    public Frame() {
        this.bytes = new byte[14];
    }
    
    public Frame(byte[] bytes) {
        this.bytes          = bytes;
        this.destination    = new byte[6];
        this.destination[0] = bytes[0];
        this.destination[1] = bytes[1];        
        this.destination[2] = bytes[2];
        this.destination[3] = bytes[3];        
        this.destination[4] = bytes[4];
        this.destination[5] = bytes[5];
        this.source         = new byte[6];
        this.source[0]      = bytes[6];
        this.source[1]      = bytes[7];        
        this.source[2]      = bytes[8];
        this.source[3]      = bytes[9];        
        this.source[4]      = bytes[10];
        this.source[5]      = bytes[11];;
        this.length         = (bytes[12] & 0xFF) << 8 | bytes[13] & 0xFF;
    }

    public void setDestination(byte[] destination) {
        this.destination = destination;
        this.bytes[0] = this.destination[0];
        this.bytes[1] = this.destination[1];
        this.bytes[2] = this.destination[2];
        this.bytes[3] = this.destination[3];
        this.bytes[4] = this.destination[4];
        this.bytes[5] = this.destination[5];
    }

    public void setDestination(String addr) {
        setDestination(addr2Bytes(addr));
    }

    public byte[] getDestination() {
        return this.destination;
    }

    public void setSource(byte[] source) {
        this.source = source;
        this.bytes[6] = this.source[0];
        this.bytes[7] = this.source[1];
        this.bytes[8] = this.source[2];
        this.bytes[9] = this.source[3];
        this.bytes[10] = this.source[4];
        this.bytes[11] = this.source[5];
    }

    public void setSource(String addr) {
        setSource(addr2Bytes(addr));
    }

    public byte[] getSource() {
        return this.source;
    }

    public void setData(byte[] data) {
        setLength(14 + data.length);
        byte[] newBytes = new byte[this.length];
        /* Copy Header */
        System.arraycopy(this.bytes, 0, newBytes, 0, 14);
        System.arraycopy(data, 0, newBytes, 14, data.length);
        this.bytes = newBytes;
    }

    public byte[] getData() {
        int len = this.length - 14;
        byte[] data = new byte[len];
        System.arraycopy(this.bytes, 14, data, 0, len);
        return data;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public String description() {
        String str = "";
        str += "MAC Frame";
        //        str += "\n\tVersion: " + this.version;
        //        str += "\n\tHeader Length: " + this.IHL * 4 + " bytes";
        //        str += "\n\tType of Service: " + String.format("0x%02x", this.TOS);
        //        str += "\n\tTotal Length: " + this.length;
        //        str += "\n\tIdentificatin: " + String.format("0x%1$04x (%1$d)", this.id);
        //        str += "\n\tFlags: " + String.format("0x%02x", this.flags);
        //        str += "\n\tFlagment Offset: " + this.offset;
        //        str += "\n\tTime to Live: " + this.TTL;
        //        str += "\n\tProtocol: " + "XXX" + " (" + this.protocol + ")";
        //        str += "\n\tHeader Checksum: " + String.format("0x%04x", this.checksum);
        str += "\n\tDestination Address: " + bytes2Addr(this.destination);        
        str += "\n\tSource Address: " + bytes2Addr(this.source);
        return str;
    }
    
    @Deprecated
    public void setLength(int length) {
        this.length = length & 0xFFFF;
        this.bytes[12] = (byte)(this.length >> 8 & 0xFF);
        this.bytes[13] = (byte)(this.length & 0xFF);
    }

    private byte[] addr2Bytes(String addr) {
        String[] addrs = addr.split("\\:", 0);
        byte[] bytes = new byte[6];
        bytes[0] = (byte)Integer.parseInt(addrs[0], 16);
        bytes[1] = (byte)Integer.parseInt(addrs[1], 16);
        bytes[2] = (byte)Integer.parseInt(addrs[2], 16);
        bytes[3] = (byte)Integer.parseInt(addrs[3], 16);
        bytes[4] = (byte)Integer.parseInt(addrs[4], 16);
        bytes[5] = (byte)Integer.parseInt(addrs[5], 16);        
        return bytes;
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
