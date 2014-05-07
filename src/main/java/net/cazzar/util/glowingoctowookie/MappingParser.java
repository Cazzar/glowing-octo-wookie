package net.cazzar.util.glowingoctowookie;

import com.google.common.collect.HashBiMap;
import net.cazzar.util.glowingoctowookie.internal.FieldInfo;
import net.cazzar.util.glowingoctowookie.internal.MethodInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class MappingParser {
    private static MappingParser ourInstance = new MappingParser();
    HashBiMap<String, String> classes = HashBiMap.create();
    HashBiMap<FieldInfo, FieldInfo> fields = HashBiMap.create();
    HashBiMap<MethodInfo, MethodInfo> methods = HashBiMap.create();

    public static MappingParser getInstance() {
        return ourInstance;
    }

    private MappingParser() {
        File mcpToSrg = new File(Main.getConfig(), "conf/mcp-srg.srg");

        try {
            for (String line : Files.readAllLines(mcpToSrg.toPath(), Charset.defaultCharset())) {
                if (line == null || line.isEmpty())
                    continue;

                String[] parts = line.split(" ");

                if (parts[0].equals("CL:"))
                    classes.put(parts[1], parts[2]);
                else if (parts[0].equals("FD:"))
                    fields.put(FieldInfo.get(parts[1]), FieldInfo.get(parts[2]));
                else if (parts[0].equals("MD:"))
                    methods.put(MethodInfo.get(parts[1], parts[2]), MethodInfo.get(parts[3], parts[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashBiMap<String, String> getClasses() {
        return classes;
    }

    public HashBiMap<FieldInfo, FieldInfo> getFields() {
        return fields;
    }

    public HashBiMap<MethodInfo, MethodInfo> getMethods() {
        return methods;
    }
}
