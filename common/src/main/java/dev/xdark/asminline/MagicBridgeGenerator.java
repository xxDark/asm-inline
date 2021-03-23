package dev.xdark.asminline;

import javax.swing.JOptionPane;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

final class MagicBridgeGenerator {

  static final String CLASS_NAME;

  private MagicBridgeGenerator() {
  }

  static {
    boolean java9 = VersionUtil.java9;
    String actualClass =
        java9 ? "jdk/internal/reflect/MagicAccessorImpl" : "sun/reflect/MagicAccessorImpl";
    CLASS_NAME =
        (java9 ? "jdk/internal/reflect/MagicAccessorBridge" : "sun/reflect/MagicAccessorBridge")
            + "$AsmInline$" + System.nanoTime();
    // Generate bridge
    ClassWriter writer = new ClassWriter(0);
    writer
        .visit(java9 ? Opcodes.V9 : Opcodes.V1_8, Opcodes.ACC_PUBLIC, CLASS_NAME, null, actualClass,
            null);
    MethodVisitor init = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
    init.visitCode();
    init.visitVarInsn(Opcodes.ALOAD, 0);
    init.visitMethodInsn(Opcodes.INVOKESPECIAL, actualClass, "<init>", "()V", false);
    init.visitInsn(Opcodes.RETURN);
    init.visitEnd();
    byte[] bytes = writer.toByteArray();
    ClassDefiner.INSTANCE.defineClass(null, bytes, 0, bytes.length);
  }
}
