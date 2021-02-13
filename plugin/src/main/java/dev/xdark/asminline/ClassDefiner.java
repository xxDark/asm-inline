package dev.xdark.asminline;

import org.gradle.api.JavaVersion;

public interface ClassDefiner {

  ClassDefiner INSTANCE =
      JavaVersion.current().isJava9Compatible() ? new Java9ClassDefiner() : new Java8ClassDefiner();

  Class<?> defineClass(ClassLoader loader, byte[] classBytes, int off, int len);
}
