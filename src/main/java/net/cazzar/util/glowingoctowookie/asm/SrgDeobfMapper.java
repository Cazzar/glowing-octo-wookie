package net.cazzar.util.glowingoctowookie.asm;

import net.cazzar.util.glowingoctowookie.MappingParser;
import net.cazzar.util.glowingoctowookie.internal.FieldInfo;
import net.cazzar.util.glowingoctowookie.internal.MethodInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

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

                    Map.Entry<MethodInfo, MethodInfo> infoEntry = MappingParser.getInstance().findMethod(insn.name, insn.desc);
                    MethodInfo info = (infoEntry == null) ? null : infoEntry.getKey();
                    if (info != null) {
                        insn.desc = info.getSignature();
                        insn.name = info.getName();
                        insn.owner = info.getOwner();
                    }
                    insnList.add(insn);
                } else if (insnNode instanceof FieldInsnNode) {
                    FieldInsnNode insn = (FieldInsnNode) insnNode;
                    Map.Entry<FieldInfo, FieldInfo> field = MappingParser.getInstance().findField(insn.name);
                    FieldInfo info = (field == null) ? null : field.getKey();

                    if (info != null) {
                        insn.name = info.getName();
                        insn.owner = info.getOwner();
                    }
                    insnList.add(insn);
                }
                else {
                    insnList.add(insnNode);
                }
            }

            Map.Entry<MethodInfo, MethodInfo> method1 = MappingParser.getInstance().findMethod(method.name, method.signature);
            if (method1 != null) {
                method.name = method1.getKey().getName();
                method.desc = method1.getKey().getSignature();
            }

            method.instructions = insnList;
        }

        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);

        //CheckClassAdapter.verify(reader, true, new PrintWriter(System.err));

        return writer.toByteArray();
    }
}
