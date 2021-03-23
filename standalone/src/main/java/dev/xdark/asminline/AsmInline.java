package dev.xdark.asminline;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class AsmInline {

  public static void main(String[] args) throws IOException {
    byte[] buffer = new byte[16384];
    ByteArrayOutputStream baos = new ByteArrayOutputStream(16384);
    int r;
    for (String input : args) {
      Path path = Paths.get(input);
      Path tmp = Files.createTempFile("asminline", ".jar");
      Files.copy(path, tmp, StandardCopyOption.REPLACE_EXISTING);
      try (JarFile jar = new JarFile(tmp.toFile());
          URLClassLoader loader = URLClassLoader
              .newInstance(new URL[]{tmp.toUri().toURL()}, AsmInline.class.getClassLoader());
          JarOutputStream jos = new JarOutputStream(
              Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING))) {
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
          JarEntry entry = entries.nextElement();
          String name = entry.getName();
          byte[] bytes;
          try (InputStream in = jar.getInputStream(entry)) {
            while ((r = in.read(buffer)) != -1) {
              baos.write(buffer, 0, r);
            }
            bytes = baos.toByteArray();
          }
          if (name.endsWith(".class")) {
            bytes = PatchProcessor.processBytes(loader, bytes);
          }
          JarEntry copy = new JarEntry(name);
          copy.setTime(entry.getTime());
          copy.setCreationTime(entry.getCreationTime());
          copy.setLastAccessTime(entry.getLastAccessTime());
          copy.setLastModifiedTime(entry.getLastModifiedTime());
          copy.setComment(entry.getComment());
          copy.setExtra(entry.getExtra());
          jos.putNextEntry(copy);
          jos.write(bytes);
        }
      }
    }
  }
}
