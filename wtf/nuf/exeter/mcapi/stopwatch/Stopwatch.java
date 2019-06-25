package wtf.nuf.exeter.mcapi.stopwatch;

public class Stopwatch {
    private long previousMS;

    public Stopwatch() {
        reset();
    }

    public boolean hasCompleted(long milliseconds) {
        return getCurrentMS() - this.previousMS >= milliseconds;
    }

    public void reset() {
        this.previousMS = getCurrentMS();
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
}