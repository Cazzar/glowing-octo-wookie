package net.cazzar.util.glowingoctowookie;

import com.google.common.io.Files;
import net.cazzar.util.glowingoctowookie.asm.SrgDeobfMapper;
import net.cazzar.util.glowingoctowookie.internal.MethodInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Main {
    private static File config;

    public static void main(String[] args) throws IOException {
        config = new File(args[0]);
        MappingParser.getInstance();

        byte[] bytes = Files.toByteArray(new File(args[1]));

        new File("out.class").createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream("out.class");
        fileOutputStream.write(SrgDeobfMapper.remap(bytes));
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
