package ru.tinkoff.gradle.jarjar

/**
 * @author Dmitriy Tarasov
 */
class JarJarPluginExtension {

    String jarJarDependency = 'com.googlecode.jarjar:jarjar:1.3'

    Map<String, String> rules = [:]

}