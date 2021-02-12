package dev.xdark.asminline

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardOpenOption
import java.nio.file.attribute.BasicFileAttributes

class AsmInlinePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.tasks.withType(JavaCompile) { compile ->
                compile.doLast {
                    def dest = compile.destinationDir.toPath()
                    def classpath = compile.classpath.getFiles()
                    def urls = classpath.collect { it.toURI().toURL() }
                    urls += dest.toUri().toURL()
                    URLClassLoader.newInstance(urls.toArray(new URL[0]), this.getClass().getClassLoader())
                            .withCloseable { loader ->
                                //noinspection GroovyAssignabilityCheck
                                Files.walkFileTree(dest, new SimpleFileVisitor<Path>() {
                                    @Override
                                    FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                        def name = file.getFileName().toString()
                                        if (name.endsWith(".class")) {
                                            def bytes = Files.readAllBytes(file)
                                            def reader = new ClassReader(bytes)
                                            def tmp = new ClassWriter(reader, 0)
                                            def inliner = new AsmInliner(tmp, loader)
                                            reader.accept(inliner, 0)
                                            if (inliner.rewrite) {
                                                def writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES) {
                                                    @Override
                                                    protected ClassLoader getClassLoader() {
                                                        return loader
                                                    }
                                                }
                                                new ClassReader(tmp.toByteArray()).accept(writer, 0)
                                                Files.write(file, writer.toByteArray(), StandardOpenOption.TRUNCATE_EXISTING)
                                            }
                                        }
                                        return super.visitFile(file, attrs)
                                    }
                                })
                            }
                }
            }
        }
    }
}
