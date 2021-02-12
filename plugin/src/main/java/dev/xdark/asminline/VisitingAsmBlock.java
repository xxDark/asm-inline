package dev.xdark.asminline;

import static org.objectweb.asm.Opcodes.*;

import java.lang.invoke.MethodType;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public final class VisitingAsmBlock implements AsmBlock {

  private final MethodVisitor visitor;

  public VisitingAsmBlock(MethodVisitor visitor) {
    this.visitor = visitor;
  }

  @Override
  public AsmBlock nop() {
    return visitInsn(NOP);
  }

  @Override
  public AsmBlock $null() {
    return visitInsn(ACONST_NULL);
  }

  @Override
  public AsmBlock $int(int value) {
    MethodVisitor visitor = this.visitor;
    if (value >= -1 && value <= 5) {
      visitor.visitInsn(value + 3);
    } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
      visitor.visitIntInsn(BIPUSH, value);
    } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
      visitor.visitIntInsn(SIPUSH, value);
    } else {
      visitor.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public AsmBlock $long(long value) {
    MethodVisitor visitor = this.visitor;
    if (value == 0L || value == 1L) {
      visitor.visitInsn((int) (value + 9));
    } else {
      visitor.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public AsmBlock $float(float value) {
    MethodVisitor visitor = this.visitor;
    if (value == 0.0F || value == 1.0F || value == 2.0F) {
      visitor.visitInsn((int) value + 11);
    } else {
      visitor.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public AsmBlock $double(double value) {
    MethodVisitor visitor = this.visitor;
    if (value == 0.0 || value == 1.0) {
      visitor.visitInsn((int) value + 14);
    } else {
      visitor.visitLdcInsn(value);
    }
    return this;
  }

  @Override
  public AsmBlock ldc(String value) {
    return visitLdcInsn(value);
  }

  @Override
  public AsmBlock ldc(Class<?> type) {
    return visitLdcInsn(Type.getType(type));
  }

  @Override
  public AsmBlock ldc(Type type) {
    return visitLdcInsn(type);
  }

  @Override
  public AsmBlock ldc(MethodType type) {
    Class<?>[] $parameters = type.parameterArray();
    int j = $parameters.length;
    Type[] parameters = new Type[j];
    while (j-- > 0) {
      parameters[j] = Type.getType($parameters[j]);
    }
    return visitLdcInsn(Type.getMethodType(Type.getType(type.returnType()), parameters));
  }

  @Override
  public AsmBlock ldc(Handle handle) {
    return visitLdcInsn(handle);
  }

  @Override
  public AsmBlock ldc(ConstantDynamic constantDynamic) {
    return visitLdcInsn(constantDynamic);
  }

  @Override
  public AsmBlock iload(int idx) {
    return visitVarInsn(ILOAD, idx);
  }

  @Override
  public AsmBlock lload(int idx) {
    return visitVarInsn(LLOAD, idx);
  }

  @Override
  public AsmBlock fload(int idx) {
    return visitVarInsn(FLOAD, idx);
  }

  @Override
  public AsmBlock dload(int idx) {
    return visitVarInsn(DLOAD, idx);
  }

  @Override
  public AsmBlock aload(int idx) {
    return visitVarInsn(ALOAD, idx);
  }

  @Override
  public AsmBlock iaload() {
    return visitInsn(IALOAD);
  }

  @Override
  public AsmBlock laload() {
    return visitInsn(LALOAD);
  }

  @Override
  public AsmBlock faload() {
    return visitInsn(FALOAD);
  }

  @Override
  public AsmBlock daload() {
    return visitInsn(DALOAD);
  }

  @Override
  public AsmBlock aaload() {
    return visitInsn(AALOAD);
  }

  @Override
  public AsmBlock baload() {
    return visitInsn(BALOAD);
  }

  @Override
  public AsmBlock caload() {
    return visitInsn(CALOAD);
  }

  @Override
  public AsmBlock saload() {
    return visitInsn(SALOAD);
  }

  @Override
  public AsmBlock istore(int idx) {
    return visitVarInsn(ISTORE, idx);
  }

  @Override
  public AsmBlock lstore(int idx) {
    return visitVarInsn(LSTORE, idx);
  }

  @Override
  public AsmBlock fstore(int idx) {
    return visitVarInsn(FSTORE, idx);
  }

  @Override
  public AsmBlock dstore(int idx) {
    return visitVarInsn(DSTORE, idx);
  }

  @Override
  public AsmBlock astore(int idx) {
    return visitVarInsn(ASTORE, idx);
  }

  @Override
  public AsmBlock iastore() {
    return visitInsn(IASTORE);
  }

  @Override
  public AsmBlock lastore() {
    return visitInsn(LASTORE);
  }

  @Override
  public AsmBlock fastore() {
    return visitInsn(FASTORE);
  }

  @Override
  public AsmBlock dastore() {
    return visitInsn(DASTORE);
  }

  @Override
  public AsmBlock aastore() {
    return visitInsn(AASTORE);
  }

  @Override
  public AsmBlock bastore() {
    return visitInsn(BASTORE);
  }

  @Override
  public AsmBlock castore() {
    return visitInsn(CASTORE);
  }

  @Override
  public AsmBlock sastore() {
    return visitInsn(SASTORE);
  }

  @Override
  public AsmBlock pop() {
    return visitInsn(POP);
  }

  @Override
  public AsmBlock pop2() {
    return visitInsn(POP2);
  }

  @Override
  public AsmBlock dup() {
    return visitInsn(DUP);
  }

  @Override
  public AsmBlock dupx1() {
    return visitInsn(DUP_X1);
  }

  @Override
  public AsmBlock dupx2() {
    return visitInsn(DUP_X2);
  }

  @Override
  public AsmBlock dup2() {
    return visitInsn(DUP2);
  }

  @Override
  public AsmBlock dup2x1() {
    return visitInsn(DUP2_X1);
  }

  @Override
  public AsmBlock dup2x2() {
    return visitInsn(DUP2_X2);
  }

  @Override
  public AsmBlock swap() {
    return visitInsn(SWAP);
  }

  @Override
  public AsmBlock iadd() {
    return visitInsn(IADD);
  }

  @Override
  public AsmBlock ladd() {
    return visitInsn(LADD);
  }

  @Override
  public AsmBlock fadd() {
    return visitInsn(FADD);
  }

  @Override
  public AsmBlock dadd() {
    return visitInsn(DADD);
  }

  @Override
  public AsmBlock isub() {
    return visitInsn(ISUB);
  }

  @Override
  public AsmBlock lsub() {
    return visitInsn(LSUB);
  }

  @Override
  public AsmBlock fsub() {
    return visitInsn(FSUB);
  }

  @Override
  public AsmBlock dsub() {
    return visitInsn(DSUB);
  }

  @Override
  public AsmBlock imul() {
    return visitInsn(IMUL);
  }

  @Override
  public AsmBlock lmul() {
    return visitInsn(LMUL);
  }

  @Override
  public AsmBlock fmul() {
    return visitInsn(FMUL);
  }

  @Override
  public AsmBlock dmul() {
    return visitInsn(DMUL);
  }

  @Override
  public AsmBlock idiv() {
    return visitInsn(IDIV);
  }

  @Override
  public AsmBlock ldiv() {
    return visitInsn(LDIV);
  }

  @Override
  public AsmBlock fdiv() {
    return visitInsn(FDIV);
  }

  @Override
  public AsmBlock ddiv() {
    return visitInsn(DDIV);
  }

  @Override
  public AsmBlock irem() {
    return visitInsn(IREM);
  }

  @Override
  public AsmBlock lrem() {
    return visitInsn(LREM);
  }

  @Override
  public AsmBlock frem() {
    return visitInsn(FREM);
  }

  @Override
  public AsmBlock drem() {
    return visitInsn(DREM);
  }

  @Override
  public AsmBlock ineg() {
    return visitInsn(INEG);
  }

  @Override
  public AsmBlock lneg() {
    return visitInsn(LNEG);
  }

  @Override
  public AsmBlock fneg() {
    return visitInsn(FNEG);
  }

  @Override
  public AsmBlock dneg() {
    return visitInsn(DNEG);
  }

  @Override
  public AsmBlock ishl() {
    return visitInsn(ISHL);
  }

  @Override
  public AsmBlock lshl() {
    return visitInsn(LSHL);
  }

  @Override
  public AsmBlock ishr() {
    return visitInsn(ISHR);
  }

  @Override
  public AsmBlock lshr() {
    return visitInsn(LSHR);
  }

  @Override
  public AsmBlock iushr() {
    return visitInsn(IUSHR);
  }

  @Override
  public AsmBlock lushr() {
    return visitInsn(LUSHR);
  }

  @Override
  public AsmBlock iand() {
    return visitInsn(IAND);
  }

  @Override
  public AsmBlock land() {
    return visitInsn(LAND);
  }

  @Override
  public AsmBlock ior() {
    return visitInsn(IOR);
  }

  @Override
  public AsmBlock lor() {
    return visitInsn(LOR);
  }

  @Override
  public AsmBlock ixor() {
    return visitInsn(IXOR);
  }

  @Override
  public AsmBlock lxor() {
    return visitInsn(LXOR);
  }

  @Override
  public AsmBlock iinc(int idx, int value) {
    visitor.visitIincInsn(idx, value);
    return this;
  }

  @Override
  public AsmBlock i2l() {
    return visitInsn(I2L);
  }

  @Override
  public AsmBlock i2f() {
    return visitInsn(I2F);
  }

  @Override
  public AsmBlock i2d() {
    return visitInsn(I2D);
  }

  @Override
  public AsmBlock l2i() {
    return visitInsn(L2I);
  }

  @Override
  public AsmBlock l2f() {
    return visitInsn(L2F);
  }

  @Override
  public AsmBlock l2d() {
    return visitInsn(L2D);
  }

  @Override
  public AsmBlock f2i() {
    return visitInsn(F2I);
  }

  @Override
  public AsmBlock f2l() {
    return visitInsn(F2L);
  }

  @Override
  public AsmBlock f2d() {
    return visitInsn(F2D);
  }

  @Override
  public AsmBlock d2i() {
    return visitInsn(D2I);
  }

  @Override
  public AsmBlock d2l() {
    return visitInsn(D2L);
  }

  @Override
  public AsmBlock d2f() {
    return visitInsn(D2F);
  }

  @Override
  public AsmBlock i2b() {
    return visitInsn(I2B);
  }

  @Override
  public AsmBlock i2c() {
    return visitInsn(I2C);
  }

  @Override
  public AsmBlock i2s() {
    return visitInsn(I2S);
  }

  @Override
  public AsmBlock lcmp() {
    return visitInsn(LCMP);
  }

  @Override
  public AsmBlock fcmpl() {
    return visitInsn(FCMPL);
  }

  @Override
  public AsmBlock fcmpg() {
    return visitInsn(FCMPG);
  }

  @Override
  public AsmBlock dcmpl() {
    return visitInsn(DCMPL);
  }

  @Override
  public AsmBlock dcmpg() {
    return visitInsn(DCMPG);
  }

  @Override
  public AsmBlock ifeq(Label label) {
    return visitJumpInsn(IFEQ, label);
  }

  @Override
  public AsmBlock ifne(Label label) {
    return visitJumpInsn(IFNE, label);
  }

  @Override
  public AsmBlock iflt(Label label) {
    return visitJumpInsn(IFLT, label);
  }

  @Override
  public AsmBlock ifge(Label label) {
    return visitJumpInsn(IFGE, label);
  }

  @Override
  public AsmBlock ifgt(Label label) {
    return visitJumpInsn(IFGT, label);
  }

  @Override
  public AsmBlock ifle(Label label) {
    return visitJumpInsn(IFLE, label);
  }

  @Override
  public AsmBlock icmpeq(Label label) {
    return visitJumpInsn(IF_ICMPEQ, label);
  }

  @Override
  public AsmBlock icmpne(Label label) {
    return visitJumpInsn(IF_ICMPNE, label);
  }

  @Override
  public AsmBlock icmplt(Label label) {
    return visitJumpInsn(IF_ICMPLT, label);
  }

  @Override
  public AsmBlock icmpge(Label label) {
    return visitJumpInsn(IF_ICMPGE, label);
  }

  @Override
  public AsmBlock icmpgt(Label label) {
    return visitJumpInsn(IF_ICMPGT, label);
  }

  @Override
  public AsmBlock icmple(Label label) {
    return visitJumpInsn(IF_ICMPLE, label);
  }

  @Override
  public AsmBlock acmpeq(Label label) {
    return visitJumpInsn(IF_ACMPEQ, label);
  }

  @Override
  public AsmBlock acmpne(Label label) {
    return visitJumpInsn(IF_ACMPNE, label);
  }

  @Override
  public AsmBlock $goto(Label label) {
    return visitJumpInsn(GOTO, label);
  }

  @Override
  public AsmBlock tableswitch(int min, int max, Label dflt, Label... labels) {
    visitor.visitTableSwitchInsn(min, max, dflt, labels);
    return this;
  }

  @Override
  public AsmBlock lookupswitch(int[] keys, Label dflt, Label... labels) {
    visitor.visitLookupSwitchInsn(dflt, keys, labels);
    return this;
  }

  @Override
  public AsmBlock ireturn() {
    return visitInsn(IRETURN);
  }

  @Override
  public AsmBlock lreturn() {
    return visitInsn(LRETURN);
  }

  @Override
  public AsmBlock freturn() {
    return visitInsn(FRETURN);
  }

  @Override
  public AsmBlock dreturn() {
    return visitInsn(DRETURN);
  }

  @Override
  public AsmBlock $return() {
    return visitInsn(RETURN);
  }

  @Override
  public AsmBlock getstatic(String owner, String name, String desc) {
    visitor.visitFieldInsn(GETSTATIC, owner, name, desc);
    return this;
  }

  @Override
  public AsmBlock putstatic(String owner, String name, String desc) {
    visitor.visitFieldInsn(PUTSTATIC, owner, name, desc);
    return this;
  }

  @Override
  public AsmBlock getfield(String owner, String name, String desc) {
    visitor.visitFieldInsn(GETFIELD, owner, name, desc);
    return this;
  }

  @Override
  public AsmBlock putfield(String owner, String name, String desc) {
    visitor.visitFieldInsn(PUTFIELD, owner, name, desc);
    return this;
  }

  @Override
  public AsmBlock getstatic(Class<?> owner, String name, String desc) {
    return getstatic(internalName(owner), name,  desc);
  }

  @Override
  public AsmBlock putstatic(Class<?> owner, String name, String desc) {
    return putstatic(internalName(owner), name,  desc);
  }

  @Override
  public AsmBlock getfield(Class<?> owner, String name, String desc) {
    return getfield(internalName(owner), name,  desc);
  }

  @Override
  public AsmBlock putfield(Class<?> owner, String name, String desc) {
    return putfield(internalName(owner), name,  desc);
  }

  @Override
  public AsmBlock getstatic(String owner, String name, Class<?> desc) {
    return getstatic(owner, name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock putstatic(String owner, String name, Class<?> desc) {
    return putstatic(owner, name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock getfield(String owner, String name, Class<?> desc) {
    return getfield(owner, name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock putfield(String owner, String name, Class<?> desc) {
    return putfield(owner, name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock getstatic(Class<?> owner, String name, Class<?> desc) {
    return getstatic(internalName(owner), name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock putstatic(Class<?> owner, String name, Class<?> desc) {
    return putstatic(internalName(owner), name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock getfield(Class<?> owner, String name, Class<?> desc) {
    return getfield(internalName(owner), name,  fieldDescriptor(desc));
  }

  @Override
  public AsmBlock putfield(Class<?> owner, String name, Class<?> desc) {
    return putfield(internalName(owner), name,  internalName(desc));
  }

  @Override
  public AsmBlock invokevirtual(String owner, String name, String desc) {
    visitor.visitMethodInsn(INVOKEVIRTUAL, owner, name, desc, false);
    return this;
  }

  @Override
  public AsmBlock invokespecial(String owner, String name, String desc) {
    visitor.visitMethodInsn(INVOKESPECIAL, owner, name, desc, false);
    return this;
  }

  @Override
  public AsmBlock invokestatic(String owner, String name, String desc) {
    visitor.visitMethodInsn(INVOKESTATIC, owner, name, desc, false);
    return this;
  }

  @Override
  public AsmBlock invokeinterface(String owner, String name, String desc) {
    visitor.visitMethodInsn(INVOKEINTERFACE, owner, name, desc, true);
    return this;
  }

  @Override
  public AsmBlock invokevirtual(String owner, String name, MethodType type) {
    return invokevirtual(owner, name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokestatic(String owner, String name, MethodType type) {
    return invokestatic(owner, name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokeinterface(String owner, String name, MethodType type) {
    return invokeinterface(owner, name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokevirtual(Class<?> owner, String name, String desc) {
    return invokevirtual(internalName(owner), name, desc);
  }

  @Override
  public AsmBlock invokespecial(Class<?> owner, String name, String desc) {
    return invokespecial(internalName(owner), name, desc);
  }

  @Override
  public AsmBlock invokestatic(Class<?> owner, String name, String desc) {
    return invokestatic(internalName(owner), name, desc);
  }

  @Override
  public AsmBlock invokevirtual(Class<?> owner, String name, MethodType type) {
    return invokevirtual(internalName(owner), name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokespecial(Class<?> owner, String name, MethodType type) {
    return invokespecial(internalName(owner), name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokestatic(Class<?> owner, String name, MethodType type) {
    return invokestatic(internalName(owner), name, type.toMethodDescriptorString());
  }

  @Override
  public AsmBlock invokedynamic(String name, String desc, Handle bootstrap, Object... args) {
    visitor.visitInvokeDynamicInsn(name, desc, bootstrap, args);
    return this;
  }

  @Override
  public AsmBlock $new(String type) {
    visitor.visitTypeInsn(NEW, type);
    return this;
  }

  @Override
  public AsmBlock newarray(int type) {
    visitor.visitIntInsn(NEWARRAY, type);
    return this;
  }

  @Override
  public AsmBlock newarray(String type) {
    visitor.visitTypeInsn(ANEWARRAY, type);
    return this;
  }

  @Override
  public AsmBlock arraylength() {
    return visitInsn(ARRAYLENGTH);
  }

  @Override
  public AsmBlock athrow() {
    return visitInsn(ATHROW);
  }

  @Override
  public AsmBlock checkcast(String type) {
    visitor.visitTypeInsn(CHECKCAST, type);
    return this;
  }

  @Override
  public AsmBlock $instanceof(String type) {
    visitor.visitTypeInsn(INSTANCEOF, type);
    return this;
  }

  @Override
  public AsmBlock monitorenter() {
    return visitInsn(MONITORENTER);
  }

  @Override
  public AsmBlock monitorexit() {
    return visitInsn(MONITOREXIT);
  }

  @Override
  public AsmBlock multinewarray(String desc, int dimensions) {
    visitor.visitMultiANewArrayInsn(desc, dimensions);
    return this;
  }

  @Override
  public AsmBlock ifnull(Label label) {
    return visitJumpInsn(IFNULL, label);
  }

  @Override
  public AsmBlock ifnonnull(Label label) {
    return visitJumpInsn(IFNONNULL, label);
  }

  @Override
  public AsmBlock label(Label label) {
    visitor.visitLabel(label);
    return this;
  }

  @Override
  public AsmBlock $try(Label start, Label end, Label handler, String type) {
    visitor.visitTryCatchBlock(start, end, handler, type);
    return this;
  }

  @Override
  public AsmBlock line(int line, Label start) {
    visitor.visitLineNumber(line, start);
    return this;
  }

  @Override
  public AsmBlock parameter(String name, int access) {
    visitor.visitParameter(name, access);
    return this;
  }

  private AsmBlock visitInsn(int opcode) {
    visitor.visitInsn(opcode);
    return this;
  }

  private AsmBlock visitJumpInsn(int opcode, Label label) {
    visitor.visitJumpInsn(opcode, label);
    return this;
  }

  private AsmBlock visitVarInsn(int opcode, int var) {
    visitor.visitVarInsn(opcode, var);
    return this;
  }

  private AsmBlock visitLdcInsn(Object value) {
    visitor.visitLdcInsn(value);
    return this;
  }

  private static String internalName(Class<?> c) {
    return c.getName().replace('.', '/');
  }

  private static String fieldDescriptor(Class<?> c) {
    if (c.isPrimitive()) {
      return getPrimitiveInternal(c);
    }
    String name = c.getName().replace('.', '/');
    if (c.isArray()) {
      return name;
    }
    return 'L' + name + ';';
  }

  private static String getPrimitiveInternal(Class<?> c) {
    if (c == Long.TYPE) {
      return "J";
    } else if (c == Double.TYPE) {
      return "D";
    } else if (c == Integer.TYPE) {
      return "I";
    } else if (c == Float.TYPE) {
      return "F";
    } else if (c == Short.TYPE) {
      return "S";
    } else if (c == Character.TYPE) {
      return "C";
    } else if (c == Byte.TYPE) {
      return "B";
    } else if (c == Boolean.TYPE) {
      return "Z";
    } else {
      throw new IllegalArgumentException("Unknown primitive: " + c);
    }
  }
}
