package dev.xdark.asminline;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import org.objectweb.asm.ClassVisitor;
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
  private static final MethodType BLOCK_TYPE = MethodType.methodType(Void.TYPE, AsmBlock.class);
  private static final Lookup LOOKUP;
  private final ClassLoader loader;

  public AsmInliner(ClassVisitor classVisitor, ClassLoader loader) {
    super(Opcodes.ASM9, classVisitor);
    this.loader = loader;
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
                    Class<?> klass = loader.loadClass(handle.getOwner().replace('/', '.'));
                    AsmBlock block = new VisitingAsmBlock(this);
                    LOOKUP.findStatic(klass, handle.getName(), BLOCK_TYPE).invokeExact(block);
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

  static {
    try {
      Field field = Lookup.class.getDeclaredField("IMPL_LOOKUP");
      field.setAccessible(true);
      LOOKUP = (Lookup) field.get(null);
    } catch (IllegalAccessException | NoSuchFieldException ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }
}
