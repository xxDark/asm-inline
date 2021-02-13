package dev.xdark.asminline;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import org.objectweb.asm.ClassWriter;

public final class AsmUtil {

  private static final MethodHandle MH_GET_SYMBOL_TABLE;
  private static final MethodHandle MH_SET_SYMBOL_TABLE;

  private AsmUtil() { }

  public static void copySymbolTable(ClassWriter source, ClassWriter target) {
    try {
      MH_SET_SYMBOL_TABLE.invoke(target, MH_GET_SYMBOL_TABLE.invoke(source));
    } catch (Throwable t) {
      throw new AssertionError(t);
    }
  }

  static {
    try {
      Class<?> symbolTable = Class.forName("org.objectweb.asm.SymbolTable");
      Lookup lookup = LookupUtil.lookup();
      MH_GET_SYMBOL_TABLE = lookup.findGetter(ClassWriter.class, "symbolTable", symbolTable);
      MH_SET_SYMBOL_TABLE = lookup.findSetter(ClassWriter.class, "symbolTable", symbolTable);
    } catch (IllegalAccessException | ClassNotFoundException | NoSuchFieldException ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }
}
