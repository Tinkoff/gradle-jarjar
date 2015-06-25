Gradle JarJar Repackage Plugin
==============================

Plugin for gradle which allows you to repackage jar-libraries with different package names using [JarJar][1] tool.

In Android development there are some situations when library packaged inside your *.apk-file already exists on the device firmware.
In such case java class loader prefer device's library version instead of your own packaged inside an *.apk.
If you using newest version of library than that that exists on a device you can stuck in a trouble.
Your newest library version can have changed signatures which can cause a runtime exception.

For example on some HTC devices there are pre-installed GSON library for sereializing/desereializing to/from JSON.
And if you use newest GSON version in your own project you'll get runtime errors with this.

Installation
------------
Download the latest [version][2] or grab it via Gradle:

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'ru.tinkoff.gradle:jarjar:1.1.0'
    }
}

apply plugin: 'ru.tinkoff.gradle.jarjar'

dependencies {
    // Which artifacts should be repackaged
    jarJar 'com.google.code.gson:gson:2.3'

    // Repackaged jars will be placed here, adding them to classpath
    compile fileTree(dir: './build/libs', include: ['*.jar'])
}

jarJar {
    // OPTIONAL - jarjar artifact from Central Repository
    jarJarDependency 'com.googlecode.jarjar:jarjar:1.3'

    // Dependencies and related JarJar rules
    rules = ['gson-2.3.jar': 'com.google.gson.** ru.tinkoff.core.gson.@1']
}
```

License
-------
Plugin available under [MIT][3] license

[1]: https://code.google.com/p/jarjar/
[2]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22ru.tinkoff.gradle%22%20AND%20a%3A%22jarjar%22
[3]: http://opensource.org/licenses/MIT