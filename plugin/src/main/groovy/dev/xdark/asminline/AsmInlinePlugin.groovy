package dev.xdark.asminline

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class AsmInlinePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.tasks.withType(Jar) { jar ->
                jar.doLast {
                    def path = jar.archiveFile.get().getAsFile()
                    def tmp = File.createTempFile("asminline", ".jar")
                    URL[] urls = [path.toURI().toURL()]
                    URLClassLoader.newInstance(urls, this.getClass().getClassLoader())
                            .withCloseable { loader ->
                                new JarFile(path).withCloseable { inJar ->
                                    new JarOutputStream(new FileOutputStream(tmp)).withCloseable { out ->
                                        def entries = inJar.entries()
                                        while (entries.hasMoreElements()) {
                                            def entry = entries.nextElement()
                                            def entryName = entry.name
                                            out.putNextEntry(new JarEntry(entryName))
                                            def bytes = inJar.getInputStream(entry).bytes
                                            if (!entryName.endsWith(".class")) {
                                                out.write(bytes)
                                            } else {
                                                def reader = new ClassReader(bytes)
                                                def writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES) {
                                                    @Override
                                                    protected ClassLoader getClassLoader() {
                                                        return loader
                                                    }
                                                }
                                                reader.accept(new AsmInliner(writer, loader), 0)
                                                out.write(writer.toByteArray())
                                            }
                                        }
                                    }
                                }
                            }

                    path.delete()
                    tmp.renameTo(path)
                }
            }
        }
    }
}
