package net.cazzar.util.glowingoctowookie.util;

import net.cazzar.util.glowingoctowookie.asm.SrgDeobfMapper;
import net.cazzar.util.glowingoctowookie.internal.IProgressListener;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class JarParser {
    public static void parseJar(File in, File out, IProgressListener listener) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        ZipOutputStream outZip = new ZipOutputStream(fileOutputStream);


        ZipFile zipFile = new ZipFile(in);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        listener.setMax(zipFile.size());

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            byte[] bytes = IOUtils.toByteArray(zipFile.getInputStream(entry));

            if (entry.isDirectory()) {
                outZip.putNextEntry(entry);
                outZip.write(bytes);
            } else if (entry.getName().endsWith(".class")) {
                //we need to ASM this biatch!
                ZipEntry outEntry = new ZipEntry(entry.getName());
                //outEntry.setSize(bytes.length);
                outZip.putNextEntry(outEntry);
//                System.out.printf("Remapping %s (%s)%n", entry.getName(), bytes.length);
                outZip.write(SrgDeobfMapper.remap(bytes));

            } else {
                ZipEntry outEntry = new ZipEntry(entry.getName());
                //outEntry.setSize(bytes.length);
                outZip.putNextEntry(outEntry);
                outZip.write(bytes);
            }

            outZip.closeEntry();
            listener.itemCompleted();
            System.out.printf("Percentage completed: %.2f%n", listener.getProgress());
        }

        outZip.finish();
//        outZip.flush();
        outZip.close();
    }
}
