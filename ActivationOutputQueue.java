class ActivationOutputQueue extends ActivationQueue {
    public void fetch(Frame frame) {
        delegate.output(frame);
    }
}
