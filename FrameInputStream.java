import java.io.PipedInputStream;
import java.io.IOException;

public class FrameInputStream extends PipedInputStream {
    protected PipedInputStream innerStream;
    
    public FrameInputStream() {
        innerStream = new PipedInputStream;
    }

    public void connect(FrameOutputStream frameOutputStream) {
        PipedOutputStream stream = frameOutputStream.getPipedOutputStream();
        innerStream.connect(stream);
    }
}
