import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Delete

class GsonRepackagePlugin implements Plugin<Project> {

    void apply(Project project) {

        project.configurations {
            patch
            [compile, testCompile]*.exclude module: 'gson'
        }

        project.dependencies {
            patch('com.googlecode.jarjar:jarjar:1.3')
        }

        project.task("applyPatch", dependsOn: 'downloadPatchLib') << {
            if (!project.file('libs/gson-patched.jar').exists()) {
                project.ant {
                    taskdef name: "jarjar", classname: "com.tonicsystems.jarjar.JarJarTask", classpath: project.configurations.patch.asPath
                    jarjar(jarfile: 'libs/gson-patched.jar') {
                        zipfileset(src: "libs/gson-${project.gsonVersion}.jar")
                        rule pattern: "com.google.gson.**", result: "ru.tcsbank.wallet.gson.patched.@1"
                    }
                }
            }
        }


        project.task("downloadPatchLib", type: Copy) {
            into('libs')
            from(project.configurations.patch)
            exclude('jarjar*')
            duplicatesStrategy(DuplicatesStrategy.EXCLUDE)
        }

        project.tasks.whenTaskAdded { task ->
            if (task.name.startsWith("compile")) {
                task.dependsOn 'cleanupDownloadedPatchLib'
            }
        }

        project.task("cleanupDownloadedPatchLib", type: Delete, dependsOn: 'applyPatch') {
            delete "libs/gson-${project.gsonVersion}.jar"
        }
    }
}
