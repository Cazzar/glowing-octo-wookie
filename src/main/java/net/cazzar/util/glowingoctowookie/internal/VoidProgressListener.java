package net.cazzar.util.glowingoctowookie.internal;

public class VoidProgressListener implements IProgressListener {
    public double getProgress() {
        return 0;
    }
    public void setMax(int items) {}
    public void itemCompleted() {}
}
