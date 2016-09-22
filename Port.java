import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.IOException;

class Port {
    protected PipedInputStream input;
    protected PipedOutputStream output;
    private boolean connected;
    
    public Port() {
        input = new PipedInputStream();
        output = new PipedOutputStream();
    }
    
    public void connect(Port port) {
        try {
            this.input.connect(port.output);
            this.output.connect(port.input);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.connected = true;
        port.connected = true;
    }

    public int available() throws IOException {
        try {
            return this.input.available();
        } catch (IOException e) {
            throw new IOException("Port not connected");
        }
    }

    public int read() throws IOException {
        try {
            return this.input.read();
        } catch (IOException e) {
            throw new IOException("Port not connected");
        }
    }
    
    public void write(byte[] b, int off, int len)  throws IOException {
        try {
            this.output.write(b, off, len);
        } catch (IOException e) {
            throw new IOException("Port not connected");
        }
    }
    
    public void write(int b) throws IOException {
        try {
            this.output.write(b);
        } catch (IOException e) {
            throw new IOException("Port not connected");
        }
    }
    public void close() {
        try {
            this.output.close();
            //this.output = new PipedOutputStream();
            //            this.port.input = new PipedInputStream();            
            //System.out.println(this.port.input);//.input = new PipedInputStream();
            //this.output.connect(this.port.input);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void flush() throws IOException {
        this.output.flush();
    }
}
