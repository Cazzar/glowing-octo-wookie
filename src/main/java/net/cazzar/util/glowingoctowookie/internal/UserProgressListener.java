package net.cazzar.util.glowingoctowookie.internal;

public class UserProgressListener implements IProgressListener {
    int items, completed = 0;
    @Override
    public double getProgress() {
        return ((double) completed / items) * 100;
    }

    @Override
    public void setMax(int items) {
        this.items = items;
    }

    @Override
    public void itemCompleted() {
        completed++;
    }
}
