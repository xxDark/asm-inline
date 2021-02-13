package dev.xdark.asminline;

import java.util.Map;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public final class AsmInliner extends ClassVisitor {

  private static final Type ASM_BLOCK = Type
      .getMethodType(Type.VOID_TYPE, Type.getType(AsmBlock.class));
  private static final Handle LAMBDA_FACTORY_HANDLE = new Handle(
      Opcodes.H_INVOKESTATIC,
      "java/lang/invoke/LambdaMetafactory",
      "metafactory",
      "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
      false
  );
  private final ClassLoader loader;
  private final Map<MethodInfo, ClassWriter> methods;
  private String name;
  boolean rewrite;

  public AsmInliner(ClassVisitor classVisitor, ClassLoader loader,
      Map<MethodInfo, ClassWriter> methods) {
    super(Opcodes.ASM9, classVisitor);
    this.loader = loader;
    this.methods = methods;
  }

  @Override
  public void visit(int version, int access, String name, String signature, String superName,
      String[] interfaces) {
    this.name = name;
    super.visit(version, access, name, signature, superName, interfaces);
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
      String[] exceptions) {
    return new MethodVisitor(Opcodes.ASM9,
        super.visitMethod(access, name, descriptor, signature, exceptions)) {
      @Override
      public void visitMethodInsn(int opcode, String owner, String name, String descriptor,
          boolean isInterface) {
        if (opcode == Opcodes.INVOKESTATIC && owner.equals("dev/xdark/asminline/AsmBlock")
            && "inline".equals(name)
            && "(Ljava/util/function/Consumer;)V".equals(descriptor)) {
          return;
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
      }

      @Override
      public void visitInvokeDynamicInsn(String name, String descriptor,
          Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        if (LAMBDA_FACTORY_HANDLE.equals(bootstrapMethodHandle)) {
          if (bootstrapMethodArguments.length > 2) {
            Object o = bootstrapMethodArguments[1];
            if (o instanceof Handle) {
              Handle handle = (Handle) o;
              if (handle.getTag() == Opcodes.H_INVOKESTATIC) {
                Type type = Type.getMethodType(handle.getDesc());
                if (ASM_BLOCK.equals(type)) {
                  try {
                    String methodName = handle.getName();
                    Class<?> klass = generateInlineClass(new MethodInfo(methodName, Constants.BLOCK_TYPE_DESC));
                    AsmBlock block = new VisitingAsmBlock(this);
                    LookupUtil.lookup().findStatic(klass, methodName, Constants.BLOCK_TYPE)
                        .invokeExact(block);
                    AsmInliner.this.rewrite = true;
                  } catch (Throwable e) {
                    throw new RuntimeException(e);
                  }
                  return;
                }
              }
            }
          }
        }
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle,
            bootstrapMethodArguments);
      }
    };
  }

  private Class<?> generateInlineClass(MethodInfo info) {
    ClassWriter writer = methods.get(info);
    if (writer == null) {
      throw new IllegalArgumentException("Unknown method: " + name + '.' + info.name + info.desc);
    }
    byte[] classBytes = writer.toByteArray();
    return ClassDefiner.INSTANCE.defineClass(loader, classBytes, 0, classBytes.length);
  }
}
