package ru.tinkoff.gradle.jarjar

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import ru.tinkoff.gradle.jarjar.task.CleanJarJarTask
import ru.tinkoff.gradle.jarjar.task.JarJarTask

/**
 * @author Dmitriy Tarasov
 * @author Daniel Goncharov
 */
class JarJarPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create('jarJar', JarJarPluginExtension)

        project.configurations {
            jarJar
        }

        project.dependencies {
            jarJar(project.jarJar.jarJarDependency)
        }

        project.task('preJarJar', type: Copy) {
            from(project.configurations.jarJar)
            into('build/libs')
            rename { fileName ->
                fileName + '.original'
            }
            exclude('jarjar*')
            duplicatesStrategy(DuplicatesStrategy.EXCLUDE)
        }

        project.task('jarJar', type: JarJarTask, dependsOn: project.tasks.preJarJar)

        project.task('cleanJarJar', type: CleanJarJarTask, dependsOn: project.tasks.jarJar)

        project.tasks.whenTaskAdded { task ->
            if (task.name.startsWith("compile")) {
                task.dependsOn 'cleanJarJar'
            }
        }
    }
}
