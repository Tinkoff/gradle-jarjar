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
```
TODO instructions
```
License
-------
Plugin available under [MIT][2] license

[1]: https://code.google.com/p/jarjar/
[2]: http://opensource.org/licenses/MIT