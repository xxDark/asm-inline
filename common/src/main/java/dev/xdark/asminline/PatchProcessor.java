package dev.xdark.asminline;

import com.sun.nio.zipfs.JarFileSystemProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public final class PatchProcessor {

  private PatchProcessor() {
  }

  public static byte[] processBytes(ClassLoader loader, byte[] bytes) {
    ClassReader reader = new ClassReader(bytes);
    Map<MethodInfo, ClassWriter> methods = new HashMap<>();
    reader.accept(new BlocksCollector(methods), 0);
    if (methods.isEmpty()) {
      return bytes;
    }
    ClassWriter rewriter = new ClassWriter(reader, 0);
    AsmInliner inliner = new AsmInliner(rewriter, loader, methods);
    reader.accept(inliner, 0);
    if (inliner.rewrite) {
      ClassWriter writer = new LoaderBoundClassWriter(ClassWriter.COMPUTE_FRAMES, loader);
      AsmUtil.copySymbolTable(rewriter, writer);
      new ClassReader(rewriter.toByteArray()).accept(writer, 0);
      return writer.toByteArray();
    }
    return bytes;
  }

  public static void processDirectoryTree(ClassLoader loader, Path dir) throws IOException {
    Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileVisitResult result = super.visitFile(file, attrs);
        if (file.getFileName().toString().endsWith(".class")) {
          byte[] bytes = Files.readAllBytes(file);
          byte[] rewrite = processBytes(loader, bytes);
          if (rewrite != bytes) {
            Files.write(file, rewrite, StandardOpenOption.TRUNCATE_EXISTING);
          }
        }
        return result;
      }
    });
  }

  public static void processFileSystem(ClassLoader loader, FileSystem fs) throws IOException {
    processDirectoryTree(loader, fs.getPath("/"));
  }

  public static void processURI(ClassLoader loader, URI uri) throws IOException {
    try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
      processFileSystem(loader, fs);
    }
  }

  public static void processArchive(ClassLoader loader, Path archive) throws IOException {
    processURI(loader, archive.toUri());
  }

  public static void processArchive(ClassLoader loader, File archive)
      throws IOException, URISyntaxException {
    processURI(loader, repairURI(archive.toPath()));
  }

  private static URI repairURI(Path path) throws URISyntaxException {
    URI uri = path.toUri();
    return new URI("jar", uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(),
        uri.getQuery(), uri.getFragment());
  }

  static {
    try {
      FileSystemProvider.installedProviders();
      Field field = FileSystemProvider.class.getDeclaredField("installedProviders");
      field.setAccessible(true);
      List<FileSystemProvider> providers = new ArrayList<>(
          (List<FileSystemProvider>) field.get(null));
      providers.add(0, new JarFileSystemProvider());
      field.set(null, Collections.unmodifiableList(providers));
    } catch (IllegalAccessException | NoSuchFieldException ex) {
      throw new ExceptionInInitializerError(ex);
    }
  }
}
