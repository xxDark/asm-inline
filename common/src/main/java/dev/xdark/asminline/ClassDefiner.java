package dev.xdark.asminline;


public interface ClassDefiner {

  ClassDefiner INSTANCE = VersionUtil.java9 ? new Java9ClassDefiner() : new Java8ClassDefiner();

  Class<?> defineClass(ClassLoader loader, byte[] classBytes, int off, int len);
}
