package dev.xdark.asminline

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

import java.nio.file.Path

class AsmInlinePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.afterEvaluate {
            project.tasks.withType(JavaCompile) { compile ->
                compile.doLast {
                    def dest = compile.destinationDir.toPath() as Path
                    def classpath = compile.classpath.getFiles()
                    def urls = classpath.collect { it.toURI().toURL() }
                    urls += dest.toUri().toURL()
                    URLClassLoader.newInstance(urls as URL[], this.getClass().getClassLoader())
                            .withCloseable { loader ->
                                PatchProcessor.processDirectoryTree(loader, dest)
                            }
                }
            }
        }
    }
}
