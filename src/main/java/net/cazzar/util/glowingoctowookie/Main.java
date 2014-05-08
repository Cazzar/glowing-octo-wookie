package net.cazzar.util.glowingoctowookie;

import net.cazzar.util.glowingoctowookie.internal.MethodInfo;
import net.cazzar.util.glowingoctowookie.internal.UserProgressListener;
import net.cazzar.util.glowingoctowookie.util.JarParser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Main {
    private static File config;

    public static void main(String[] args) throws IOException {
        config = new File(args[0]);
        MappingParser.getInstance();

//        byte[] bytes = Files.toByteArray(new File(args[1]));

        new File("out.jar").createNewFile();
        JarParser.parseJar(new File(args[1]), new File("out.jar"), new UserProgressListener());
//        FileOutputStream fileOutputStream = new FileOutputStream("out.class");
//        fileOutputStream.write(SrgDeobfMapper.remap(bytes));
    }

    public static File getConfig() {
        return config;
    }

    public static MethodInfo searchMap(Map<MethodInfo, MethodInfo> map, String name) {
        for (MethodInfo info : map.keySet()){
            if (info.getName().equals(name)) return info;
        }

        return null;
    }
}
