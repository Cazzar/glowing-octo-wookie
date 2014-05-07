package net.cazzar.util.glowingoctowookie;

import java.io.File;

public class Main {
    private static File config;

    public static void main(String[] args) {
        config = new File(args[0]);
        MappingParser.getInstance();
    }

    public static File getConfig() {
        return config;
    }
}
