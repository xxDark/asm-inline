package jdk.internal.misc;

import java.security.ProtectionDomain;

public final class Unsafe {

  private static final Unsafe theUnsafe = null;

  private Unsafe() {
  }

  public Class<?> defineClass(String name, byte[] b, int off, int len,
      ClassLoader loader,
      ProtectionDomain protectionDomain) {
    throw new UnsupportedOperationException("Compile stub");
  }
}
