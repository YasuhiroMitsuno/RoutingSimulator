import java.io.PipedInputStream;
import java.io.IOException;

public class FrameOutputStream extends PipedOutputStream {
    protected PipedOutputStream[] innerStreams;
    private int size;
    
    public FrameOutputStream() {
        innerStreams = new PipedOutputStream[256];
        size = 0;
    }

    public getPipedOutputStream(FrameOutputStream frameOutputStream) {
        PipedOutputStream stream = new PipedOutputStream();
        innerStreams[size++] = stream;
        return stream;
    }

    public void flush() {
        for(int i=0;i<size;i++) {
            innerStreams[i].flush();
        }
    }

    public void write(int b) {
        for(int i=0;i<size;i++) {
            innerStreams[i].write(b);
        }
    }

    public void write(byte[] b, int off, int len) {
        for(int i=0;i<size;i++) {
            innerStreams[i].write(b, off, len);
        }
    }
}
