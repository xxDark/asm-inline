package dev.xdark.asminline;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

final class Java8ClassDefiner implements ClassDefiner {

  private static final Unsafe UNSAFE;

  @Override
  public Class<?> defineClass(ClassLoader loader, byte[] classBytes, int off, int len) {
    return UNSAFE.defineClass(null, classBytes, off, len, loader, null);
  }

  static {
    try {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      UNSAFE = (Unsafe) field.get(null);
    } catch (IllegalAccessException | NoSuchFieldException ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }
}
