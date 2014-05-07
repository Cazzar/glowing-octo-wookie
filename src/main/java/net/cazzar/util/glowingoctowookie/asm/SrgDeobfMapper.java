package net.cazzar.util.glowingoctowookie.asm;

import net.cazzar.util.glowingoctowookie.MappingParser;
import net.cazzar.util.glowingoctowookie.internal.FieldInfo;
import net.cazzar.util.glowingoctowookie.internal.MethodInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Map;

public class SrgDeobfMapper {
    public static byte[] remap(byte[] bytes) {
        ClassReader reader = new ClassReader(bytes);

        ClassNode node = new ClassNode();
        reader.accept(node, 0);

        Map<String, String> classSrgToMcp = MappingParser.getInstance().getClasses();

        //replace the proper superclass if need be.
        if (classSrgToMcp.containsKey(node.superName)) node.superName = classSrgToMcp.get(node.superName);

        final Map<MethodInfo, MethodInfo> methods = MappingParser.getInstance().getMethods().inverse();
        final Map<FieldInfo, FieldInfo> fields = MappingParser.getInstance().getFields().inverse();

        for (MethodNode method : node.methods) {
            InsnList insnList = new InsnList();
            final ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
            while (iterator.hasNext()) {
                AbstractInsnNode insnNode = iterator.next();

                if (insnNode instanceof MethodInsnNode) {
                    MethodInsnNode insn = (MethodInsnNode) insnNode;

                    MethodInfo info = new MethodInfo(insn.owner, insn.name, insn.desc);
                    System.out.println(info);
                    System.out.printf("desc = %s, name = %s, owner = %s", insn.desc, insn.name, insn.owner);
                    if (methods.containsKey(info)) {
                        info = methods.get(info);
                        System.out.println(info);

                        insn.desc = info.getSignature();
                        insn.name = info.getName();
                        insn.owner = info.getOwner();
                        System.out.printf("desc = %s, name = %s, owner = %s", insn.desc, insn.name, insn.owner);
                    }
                    insnList.add(insn);
                }
                else {
                    insnList.add(insnNode);
                }
            }
            method.instructions = insnList;
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        node.accept(writer);

        CheckClassAdapter.verify(reader, true, new PrintWriter(System.err));

        return writer.toByteArray();
    }
}
