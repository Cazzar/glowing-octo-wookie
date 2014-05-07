package net.cazzar.util.glowingoctowookie;

import com.google.common.collect.HashBiMap;
import net.cazzar.util.glowingoctowookie.internal.FieldInfo;
import net.cazzar.util.glowingoctowookie.internal.MethodInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class MappingParser {
    private static MappingParser ourInstance = new MappingParser();

    Map<String, String> classes = HashBiMap.create();
    Map<FieldInfo, FieldInfo> fields = HashBiMap.create();
    Map<MethodInfo, MethodInfo> methods = HashBiMap.create();

    public static MappingParser getInstance() {
        return ourInstance;
    }

    private MappingParser() {
        File mcpToSrg = new File(Main.getConfig(), "conf/mcp-srg.srg");

        try {
            for (String line : Files.readAllLines(mcpToSrg.toPath())) {
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
}
