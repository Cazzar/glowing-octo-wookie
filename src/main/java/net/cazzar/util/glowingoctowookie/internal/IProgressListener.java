package net.cazzar.util.glowingoctowookie.internal;

public interface IProgressListener {
    public double getProgress();
    public void setMax(int items);
    public void itemCompleted();
}
