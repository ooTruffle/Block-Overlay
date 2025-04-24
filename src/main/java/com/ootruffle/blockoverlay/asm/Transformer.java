package com.ootruffle.blockoverlay.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class Transformer implements IClassTransformer {
   public Transformer() {
   }

   public byte[] transform(String name, String transformedName, byte[] bytes) {
      return transformedName.equals("net.minecraft.client.renderer.EntityRenderer") ? this.transform(bytes) : bytes;
   }

   private byte[] transform(byte[] bytes) {
      ClassReader classReader = new ClassReader(bytes);
      ClassNode classNode = new ClassNode();
      classReader.accept(classNode, 0);
      classNode.methods.stream().filter((method) -> method.name.equals("a") && method.desc.equals("(IFJ)V")).findFirst().ifPresent(this::transform);
      ClassWriter classWriter = new ClassWriter(2);
      classNode.accept(classWriter);
      return classWriter.toByteArray();
   }

   private void transform(MethodNode method) {
      boolean foundTarget = false;
      LabelNode target = null;

      for(AbstractInsnNode node : method.instructions.toArray()) {
         if (node instanceof LabelNode) {
            target = (LabelNode)node;
         } else if (node.getOpcode() == 18 && ((LdcInsnNode)node).cst instanceof String && ((LdcInsnNode)node).cst.equals("destroyProgress")) {
            foundTarget = true;
            break;
         }
      }

      if (foundTarget && target != null) {
         AbstractInsnNode node;
         do {
            node = target.getNext();
            method.instructions.remove(node);
         } while(node.getOpcode() != 184 || !((MethodInsnNode)node).name.equals("k") || !((MethodInsnNode)node).desc.equals("()V"));

         System.out.println("Transformed Block Overlay");
      }

   }
}
