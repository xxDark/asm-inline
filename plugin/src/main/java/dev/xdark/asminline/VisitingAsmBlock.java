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
    visitor.visitInsn(NOP);
    return this;
  }

  @Override
  public AsmBlock $null() {
    visitor.visitInsn(ACONST_NULL);
    return this;
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
    visitor.visitLdcInsn(value);
    return this;
  }

  @Override
  public AsmBlock ldc(Class<?> type) {
    visitor.visitLdcInsn(Type.getType(type));
    return this;
  }

  @Override
  public AsmBlock ldc(Type type) {
    visitor.visitLdcInsn(type);
    return this;
  }

  @Override
  public AsmBlock ldc(MethodType type) {
    Class<?>[] $parameters = type.parameterArray();
    int j = $parameters.length;
    Type[] parameters = new Type[j];
    while (j-- > 0) {
      parameters[j] = Type.getType($parameters[j]);
    }
    visitor.visitLdcInsn(Type.getMethodType(Type.getType(type.returnType()), parameters));
    return this;
  }

  @Override
  public AsmBlock ldc(Handle handle) {
    visitor.visitLdcInsn(handle);
    return this;
  }

  @Override
  public AsmBlock ldc(ConstantDynamic constantDynamic) {
    visitor.visitLdcInsn(constantDynamic);
    return this;
  }

  @Override
  public AsmBlock iload(int idx) {
    visitor.visitVarInsn(ILOAD, idx);
    return this;
  }

  @Override
  public AsmBlock lload(int idx) {
    visitor.visitVarInsn(LLOAD, idx);
    return this;
  }

  @Override
  public AsmBlock fload(int idx) {
    visitor.visitVarInsn(FLOAD, idx);
    return this;
  }

  @Override
  public AsmBlock dload(int idx) {
    visitor.visitVarInsn(DLOAD, idx);
    return this;
  }

  @Override
  public AsmBlock aload(int idx) {
    visitor.visitVarInsn(ALOAD, idx);
    return this;
  }

  @Override
  public AsmBlock iaload() {
    visitor.visitInsn(IALOAD);
    return this;
  }

  @Override
  public AsmBlock laload() {
    visitor.visitInsn(LALOAD);
    return this;
  }

  @Override
  public AsmBlock faload() {
    visitor.visitInsn(FALOAD);
    return this;
  }

  @Override
  public AsmBlock daload() {
    visitor.visitInsn(DALOAD);
    return this;
  }

  @Override
  public AsmBlock aaload() {
    visitor.visitInsn(AALOAD);
    return this;
  }

  @Override
  public AsmBlock baload() {
    visitor.visitInsn(BALOAD);
    return this;
  }

  @Override
  public AsmBlock caload() {
    visitor.visitInsn(CALOAD);
    return this;
  }

  @Override
  public AsmBlock saload() {
    visitor.visitInsn(SALOAD);
    return this;
  }

  @Override
  public AsmBlock istore(int idx) {
    visitor.visitVarInsn(ISTORE, idx);
    return this;
  }

  @Override
  public AsmBlock lstore(int idx) {
    visitor.visitVarInsn(LSTORE, idx);
    return this;
  }

  @Override
  public AsmBlock fstore(int idx) {
    visitor.visitVarInsn(FSTORE, idx);
    return this;
  }

  @Override
  public AsmBlock dstore(int idx) {
    visitor.visitVarInsn(DSTORE, idx);
    return this;
  }

  @Override
  public AsmBlock astore(int idx) {
    visitor.visitVarInsn(ASTORE, idx);
    return this;
  }

  @Override
  public AsmBlock iastore() {
    visitor.visitInsn(IASTORE);
    return this;
  }

  @Override
  public AsmBlock lastore() {
    visitor.visitInsn(LASTORE);
    return this;
  }

  @Override
  public AsmBlock fastore() {
    visitor.visitInsn(FASTORE);
    return this;
  }

  @Override
  public AsmBlock dastore() {
    visitor.visitInsn(DASTORE);
    return this;
  }

  @Override
  public AsmBlock aastore() {
    visitor.visitInsn(AASTORE);
    return this;
  }

  @Override
  public AsmBlock bastore() {
    visitor.visitInsn(BASTORE);
    return this;
  }

  @Override
  public AsmBlock castore() {
    visitor.visitInsn(CASTORE);
    return this;
  }

  @Override
  public AsmBlock sastore() {
    visitor.visitInsn(SASTORE);
    return this;
  }

  @Override
  public AsmBlock pop() {
    visitor.visitInsn(POP);
    return this;
  }

  @Override
  public AsmBlock pop2() {
    visitor.visitInsn(POP2);
    return this;
  }

  @Override
  public AsmBlock dup() {
    visitor.visitInsn(DUP);
    return this;
  }

  @Override
  public AsmBlock dupx1() {
    visitor.visitInsn(DUP_X1);
    return this;
  }

  @Override
  public AsmBlock dupx2() {
    visitor.visitInsn(DUP_X2);
    return this;
  }

  @Override
  public AsmBlock dup2() {
    visitor.visitInsn(DUP2);
    return this;
  }

  @Override
  public AsmBlock dup2x1() {
    visitor.visitInsn(DUP2_X1);
    return this;
  }

  @Override
  public AsmBlock dup2x2() {
    visitor.visitInsn(DUP2_X2);
    return this;
  }

  @Override
  public AsmBlock swap() {
    visitor.visitInsn(SWAP);
    return this;
  }

  @Override
  public AsmBlock iadd() {
    visitor.visitInsn(IADD);
    return this;
  }

  @Override
  public AsmBlock ladd() {
    visitor.visitInsn(LADD);
    return this;
  }

  @Override
  public AsmBlock fadd() {
    visitor.visitInsn(FADD);
    return this;
  }

  @Override
  public AsmBlock dadd() {
    visitor.visitInsn(DADD);
    return this;
  }

  @Override
  public AsmBlock isub() {
    visitor.visitInsn(ISUB);
    return this;
  }

  @Override
  public AsmBlock lsub() {
    visitor.visitInsn(LSUB);
    return this;
  }

  @Override
  public AsmBlock fsub() {
    visitor.visitInsn(FSUB);
    return this;
  }

  @Override
  public AsmBlock dsub() {
    visitor.visitInsn(DSUB);
    return this;
  }

  @Override
  public AsmBlock imul() {
    visitor.visitInsn(IMUL);
    return this;
  }

  @Override
  public AsmBlock lmul() {
    visitor.visitInsn(LMUL);
    return this;
  }

  @Override
  public AsmBlock fmul() {
    visitor.visitInsn(FMUL);
    return this;
  }

  @Override
  public AsmBlock dmul() {
    visitor.visitInsn(DMUL);
    return this;
  }

  @Override
  public AsmBlock idiv() {
    visitor.visitInsn(IDIV);
    return this;
  }

  @Override
  public AsmBlock ldiv() {
    visitor.visitInsn(LDIV);
    return this;
  }

  @Override
  public AsmBlock fdiv() {
    visitor.visitInsn(FDIV);
    return this;
  }

  @Override
  public AsmBlock ddiv() {
    visitor.visitInsn(DDIV);
    return this;
  }

  @Override
  public AsmBlock irem() {
    visitor.visitInsn(IREM);
    return this;
  }

  @Override
  public AsmBlock lrem() {
    visitor.visitInsn(LREM);
    return this;
  }

  @Override
  public AsmBlock frem() {
    visitor.visitInsn(FREM);
    return this;
  }

  @Override
  public AsmBlock drem() {
    visitor.visitInsn(DREM);
    return this;
  }

  @Override
  public AsmBlock ineg() {
    visitor.visitInsn(INEG);
    return this;
  }

  @Override
  public AsmBlock lneg() {
    visitor.visitInsn(LNEG);
    return this;
  }

  @Override
  public AsmBlock fneg() {
    visitor.visitInsn(FNEG);
    return this;
  }

  @Override
  public AsmBlock dneg() {
    visitor.visitInsn(DNEG);
    return this;
  }

  @Override
  public AsmBlock ishl() {
    visitor.visitInsn(ISHL);
    return this;
  }

  @Override
  public AsmBlock lshl() {
    visitor.visitInsn(LSHL);
    return this;
  }

  @Override
  public AsmBlock ishr() {
    visitor.visitInsn(ISHR);
    return this;
  }

  @Override
  public AsmBlock lshr() {
    visitor.visitInsn(LSHR);
    return this;
  }

  @Override
  public AsmBlock iushr() {
    visitor.visitInsn(IUSHR);
    return this;
  }

  @Override
  public AsmBlock lushr() {
    visitor.visitInsn(LUSHR);
    return this;
  }

  @Override
  public AsmBlock iand() {
    visitor.visitInsn(IAND);
    return this;
  }

  @Override
  public AsmBlock land() {
    visitor.visitInsn(LAND);
    return this;
  }

  @Override
  public AsmBlock ior() {
    visitor.visitInsn(IOR);
    return this;
  }

  @Override
  public AsmBlock lor() {
    visitor.visitInsn(LOR);
    return this;
  }

  @Override
  public AsmBlock ixor() {
    visitor.visitInsn(IXOR);
    return this;
  }

  @Override
  public AsmBlock lxor() {
    visitor.visitInsn(LXOR);
    return this;
  }

  @Override
  public AsmBlock iinc(int idx, int value) {
    visitor.visitIincInsn(idx, value);
    return this;
  }

  @Override
  public AsmBlock i2l() {
    visitor.visitInsn(I2L);
    return this;
  }

  @Override
  public AsmBlock i2f() {
    visitor.visitInsn(I2F);
    return this;
  }

  @Override
  public AsmBlock i2d() {
    visitor.visitInsn(I2D);
    return this;
  }

  @Override
  public AsmBlock l2i() {
    visitor.visitInsn(L2I);
    return this;
  }

  @Override
  public AsmBlock l2f() {
    visitor.visitInsn(L2F);
    return this;
  }

  @Override
  public AsmBlock l2d() {
    visitor.visitInsn(L2D);
    return this;
  }

  @Override
  public AsmBlock f2i() {
    visitor.visitInsn(F2I);
    return this;
  }

  @Override
  public AsmBlock f2l() {
    visitor.visitInsn(F2L);
    return this;
  }

  @Override
  public AsmBlock f2d() {
    visitor.visitInsn(F2D);
    return this;
  }

  @Override
  public AsmBlock d2i() {
    visitor.visitInsn(D2I);
    return this;
  }

  @Override
  public AsmBlock d2l() {
    visitor.visitInsn(D2L);
    return this;
  }

  @Override
  public AsmBlock d2f() {
    visitor.visitInsn(D2F);
    return this;
  }

  @Override
  public AsmBlock i2b() {
    visitor.visitInsn(I2B);
    return this;
  }

  @Override
  public AsmBlock i2c() {
    visitor.visitInsn(I2C);
    return this;
  }

  @Override
  public AsmBlock i2s() {
    visitor.visitInsn(I2S);
    return this;
  }

  @Override
  public AsmBlock lcmp() {
    visitor.visitInsn(LCMP);
    return this;
  }

  @Override
  public AsmBlock fcmpl() {
    visitor.visitInsn(FCMPL);
    return this;
  }

  @Override
  public AsmBlock fcmpg() {
    visitor.visitInsn(FCMPG);
    return this;
  }

  @Override
  public AsmBlock dcmpl() {
    visitor.visitInsn(DCMPL);
    return this;
  }

  @Override
  public AsmBlock dcmpg() {
    visitor.visitInsn(DCMPG);
    return this;
  }

  @Override
  public AsmBlock ifeq(Label label) {
    visitor.visitJumpInsn(IFEQ, label);
    return this;
  }

  @Override
  public AsmBlock ifne(Label label) {
    visitor.visitJumpInsn(IFNE, label);
    return this;
  }

  @Override
  public AsmBlock iflt(Label label) {
    visitor.visitJumpInsn(IFLT, label);
    return this;
  }

  @Override
  public AsmBlock ifge(Label label) {
    visitor.visitJumpInsn(IFGE, label);
    return this;
  }

  @Override
  public AsmBlock ifgt(Label label) {
    visitor.visitJumpInsn(IFGT, label);
    return this;
  }

  @Override
  public AsmBlock ifle(Label label) {
    visitor.visitJumpInsn(IFLE, label);
    return this;
  }

  @Override
  public AsmBlock icmpeq(Label label) {
    visitor.visitJumpInsn(IF_ICMPEQ, label);
    return this;
  }

  @Override
  public AsmBlock icmpne(Label label) {
    visitor.visitJumpInsn(IF_ICMPNE, label);
    return this;
  }

  @Override
  public AsmBlock icmplt(Label label) {
    visitor.visitJumpInsn(IF_ICMPLT, label);
    return this;

  }

  @Override
  public AsmBlock icmpge(Label label) {
    visitor.visitJumpInsn(IF_ICMPGE, label);
    return this;
  }

  @Override
  public AsmBlock icmpgt(Label label) {
    visitor.visitJumpInsn(IF_ICMPGT, label);
    return this;
  }

  @Override
  public AsmBlock icmple(Label label) {
    visitor.visitJumpInsn(IF_ICMPLE, label);
    return this;
  }

  @Override
  public AsmBlock acmpeq(Label label) {
    visitor.visitJumpInsn(IF_ACMPEQ, label);
    return this;
  }

  @Override
  public AsmBlock acmpne(Label label) {
    visitor.visitJumpInsn(IF_ACMPNE, label);
    return this;
  }

  @Override
  public AsmBlock $goto(Label label) {
    visitor.visitJumpInsn(GOTO, label);
    return this;
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
    visitor.visitInsn(IRETURN);
    return this;
  }

  @Override
  public AsmBlock lreturn() {
    visitor.visitInsn(LRETURN);
    return this;
  }

  @Override
  public AsmBlock freturn() {
    visitor.visitInsn(FRETURN);
    return this;
  }

  @Override
  public AsmBlock dreturn() {
    visitor.visitInsn(DRETURN);
    return this;
  }

  @Override
  public AsmBlock $return() {
    visitor.visitInsn(RETURN);
    return this;
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
    visitor.visitInsn(ARRAYLENGTH);
    return this;
  }

  @Override
  public AsmBlock athrow() {
    visitor.visitInsn(ATHROW);
    return this;
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
    visitor.visitInsn(MONITORENTER);
    return this;
  }

  @Override
  public AsmBlock monitorexit() {
    visitor.visitInsn(MONITOREXIT);
    return this;
  }

  @Override
  public AsmBlock multinewarray(String desc, int dimensions) {
    visitor.visitMultiANewArrayInsn(desc, dimensions);
    return this;
  }

  @Override
  public AsmBlock ifnull(Label label) {
    visitor.visitJumpInsn(IFNULL, label);
    return this;
  }

  @Override
  public AsmBlock ifnonnull(Label label) {
    visitor.visitJumpInsn(IFNONNULL, label);
    return this;
  }

  private static String internalName(Class<?> c) {
    return c.getName().replace('.', '/');
  }
}
