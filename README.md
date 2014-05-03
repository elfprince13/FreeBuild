Getting Started With FreeBuild
==============================

1. [Vision](#vision)
2. [Getting your IDE](#getting-your-ide)
3. [Getting the sources](#getting-the-sources)
4. [Getting the dependencies](#getting-the-dependencies)
5. [Setting up your workspace](#setting-up-your-workspace)
6. [Finding your way around the source tree](#finding-your-way-around-the-source-tree)


Vision
------
Imagine being able to build any Lego creation you can imagine without running out of pieces. Imagine being able to build a mech, or a spaceship, or a tank, and immediately jumping into the pilot's seat for a fight to the death. Imagine building secret bases under the kitchen stove, and towns in the cupboards, and then leveling them, or dashing through the streets of a medieval village playing capture the flag.

Anyone should be able to build a world, and anyone should be able to build a game in that world. We want to support both of those things.

Core principles: Fun, Free, Extensible.

To get a sense of what we're aiming for, [try downloading the old TGE 1.5.2 based release](https://drive.google.com/file/d/0B1mbObopzHjwc05Ccmp2azR1N1U/edit?usp=sharing). Vehicles and weapons and players still need to be modeled externally, and we don't have LDraw support yet, but the awesome building and modding mechanics are there. Here are also some inspiring screenshots of days gone by, to inspire us to strive for new heights of greatness:

![screencapturecontest](https://farm3.staticflickr.com/2776/4115534041_e3a2b8aa82.jpg)

![halberd](https://farm3.staticflickr.com/2730/4105415428_436a50b59d.jpg)

![jail](https://farm3.staticflickr.com/2719/4099406359_db35205a7c.jpg)

![observation deck](https://farm3.staticflickr.com/2590/4094847892_227f51761f.jpg)

![blimp](https://farm8.staticflickr.com/7263/13896309876_c8b533f820.jpg)

![cemetech towers](https://farm4.staticflickr.com/3710/13919423693_d2046aa019.jpg)

![space station 1](https://farm3.staticflickr.com/2936/13919804764_c54105e988.jpg)

![space station 2](https://farm3.staticflickr.com/2932/13896366276_0653aaa5ed.jpg)

[Back to top](#getting-started-with-freebuild)

Getting your IDE
----------------
I'm going to assume use of Eclipse, because (unfortunately) it's the best IDE for JVM languages.

1. [Get the 3.0.x](http://scala-ide.org/download/sdk.html) branch of the Scala IDE, this is prepackaged with Eclipse
2. [Get the PyDev](http://pydev.org/manual_101_install.html) plugin by installing from the update site.
3. [Get the Antlr4](https://github.com/jknack/antlr4ide) plugin by installing from that update site. Antlr4 grammars are used for LDraw files, and for the in-development shader editor. If it complains about the link to the update site, make sure the domain name is raw.github.com and not some typo thereupon. The official readme for this plugin keeps listing the wrong site.


[Back to top](#getting-started-with-freebuild)

Getting the sources
-------------------
There are several relevant GitHub repos for the main project. You'll need a Git client. On OS X, I'm generally happy with the official GUI client for basic needs, but not sure how it is on other platforms. The command line version isn't that hard to figure out, and Google can provide some good explanations of how it works. Either way, you'll need to check them out as follows (and it will probably be beneficial to fork copies of the ones you'll be working on into your personal GitHub account).

1. [FreeBuild](https://github.com/elfprince13/FreeBuild) contains the main engine sources, and the scripts (_Note: you're here already_). Also contains the grammars for the LDraw parser. Check this out first. Then make a subdirectory in FreeBuildJ named `shared-libs`.
2. [GLSL-Shader-Editor](https://github.com/elfprince13/GLSL-Shader-Editor) contains the source and grammar for the GLSL shader editor that will be integrated into the engine. It should be checked out into `shared-libs`.
3. [libcrane](https://github.com/elfprince13/libcrane) is a native library to find the OS-appropriate folders for storing various sorts of runtime data. It should be checked out into `shared-libs`.
4. [glg2d](https://github.com/elfprince13/glg2d) is my fork of another glg2d. Both are used for OpenGL-accelerated rendering of code that uses the Java2D api, but the original targets JOGL, and we need LWJGL. It should be checked out into `shared-libs`.

[Back to top](#getting-started-with-freebuild)

Getting the dependencies
------------------------
1. [Download the ANTLRv4 runtime](http://www.antlr.org/download.html), drop it into `shared-libs`.
2. [Download jsyntaxpane](https://code.google.com/p/jsyntaxpane/), and drop the jar into `shared-libs`.
3. The shader editor depends on [multisplitpane](https://today.java.net/article/2006/03/22/multisplitpane-splitting-without-nesting#resources) from `org.jdesktop.swingx`. Unzip it, and copy the `org` folder from `MultiSplit/src` into `GLSL-Shader-Editor/multisplitpane`
4. Download the [Jython 2.7 standalone jar](http://www.jython.org/downloads.html) and place it into `shared-libs`.
5. Download [JNA](https://github.com/twall/jna), place the jar into `shared-libs`.
6. Download the [Validator.nu parser](http://about.validator.nu/htmlparser/), unzip, and drag `htmlparser-1.4.jar` into `shared-libs`.
 * Easy way: [grab the JAR file here](https://drive.google.com/folderview?id=0B1mbObopzHjwTzI1Szhva1FFbnM&usp=sharing) and put it in `shared-libs/CSSBoxLib`.
 * Hard way: From the [CSSBox](http://cssbox.sourceforge.net/) project, you'll need CSSBox and jStyleParser. jStyleParser is included with CSSBox, so download that first. Unzip it, rename the lib folder to CSSBoxLib, and drag the CSSBox jar file from the parent directory as well. Put this entire directory into your `shared-libs`. We may also end up using SwingBox later on, depending on decisions made during the UI design process, but skip this for now. Then, grab [JarJar](http://code.google.com/p/jarjar/), and [the ANT build task from here](https://drive.google.com/folderview?id=0B1mbObopzHjwTzI1Szhva1FFbnM&usp=sharing) to make `cssbox-complete.jar` yourself[/list]
7. From [LWJGL](http://lwjgl.org/download.php), grab the newest release, unzip it, and place the whole folder in `shared-libs`
8. Get [slick-util.jar](http://slick.ninjacave.com/slick-util/), and put it into `shared-libs` too!

A number of these libraries come with src distributions as well as the jar file. If you end up needing to debug something within them, you'll want to have it handy. If it seems unlikely that you'll be working on that part of the engine, probably no need to keep it around.

[Back to top](#getting-started-with-freebuild)

Setting up your workspace
-------------------------

* Build libcrane. Unless something weird happens, you should never need to do this again. On a Unix-y system, you probably just need to run make. On a Windows machine, you should be able to use the included VS project. `testcrane.cpp` probably won't build on OS X. Don't worry about it. You just need the dynamic library.
* Open the Eclipse that has Scala IDE/PyDev/AntlrIDE.
* File -> Import -> General -> Existing Projects into Workspace
* Browse to select the FreeBuild root directory. Make sure "Search for nested projects is checked", and "Copy projects into workspace" is unchecked. Select the four listed projects: FreeBuildJ, GLSLParser, LDrawParser, and lwjglg2d. Press Finish.
* Open the Eclipse preferences, go to PyDev, then Interpreters, then click Jython Interpreter.
* Click "New", enter "Jython 2.7 beta" as the interpreter name, and browse to select the standalone jar in `shared-libs`.
* It will complain that it couldn't find the Python stdlib. Press "Proceed anyways", then select the new interpreter from the list, and switch to the Libraries tab below. Press "New Jar/Zips", and select the standalone jar again. Press Open, Apply, and Ok.
* In the sidebar, now select Java, Build path, and User Libraries. You'll now add 5 User Libaries. For each, you'll press "New", enter the designated name, make sure "System library" is **not** checked, and press OK. Then press Add JARs, add the JARs directed, and follow any additional steps listed.
	1. ANTLRv4 -> Add your `antlr-runtime` jar from `shared-libs` (or `antlr-complete` if you downloaded that instead)
	2. CSSBox -> Add the `cssbox-complete.jar` that you either downloaded or built yourself. CSSBox depends on a conflicting version of antlr, so that script repackages dependencies nicely to avoid making a mess. Otherwise you'll likely get an error along the lines of

			Exception in thread "main" Traceback (most recent call last):
			  File "/Users/thomas/FreeBuild/FreeBuildJ/scripts/ui/__init__.py", line 36, in configure_ui
			    main_menu.init(driver.getUiHandle(),dim)
			  File "/Users/thomas/FreeBuild/FreeBuildJ/scripts/ui/main_menu.py", line 34, in init
			    da.addStyleSheet(None, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT) #use the standard style sheet
				at cz.vutbr.web.csskit.antlr.CSSLexer.nextToken(CSSLexer.java:313)
				at org.antlr.runtime.BufferedTokenStream.fetch(BufferedTokenStream.java:143)
				at org.antlr.runtime.BufferedTokenStream.sync(BufferedTokenStream.java:137)
				at org.antlr.runtime.CommonTokenStream.setup(CommonTokenStream.java:137)
				at org.antlr.runtime.CommonTokenStream.LT(CommonTokenStream.java:94)
				at cz.vutbr.web.csskit.antlr.CSSParser.stylesheet(CSSParser.java:655)
				at cz.vutbr.web.csskit.antlr.CSSParserFactory$SourceType$2.getAST(CSSParserFactory.java:81)
				at cz.vutbr.web.csskit.antlr.CSSParserFactory.feedParser(CSSParserFactory.java:375)
				at cz.vutbr.web.csskit.antlr.CSSParserFactory.createParser(CSSParserFactory.java:342)
				at cz.vutbr.web.csskit.antlr.CSSParserFactory.parse(CSSParserFactory.java:229)
				at cz.vutbr.web.csskit.antlr.CSSParserFactory.parse(CSSParserFactory.java:258)
				at cz.vutbr.web.css.CSSFactory.parse(CSSFactory.java:369)
				at org.fit.cssbox.css.DOMAnalyzer.addStyleSheet(DOMAnalyzer.java:380)
				at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
				at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
				at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
				at java.lang.reflect.Method.invoke(Method.java:606)
			
			java.lang.NoSuchFieldError: java.lang.NoSuchFieldError: EOF_TOKEN

	3. JNA -> Add your jna jar from `shared-libs`.
	4. jsyntaxpane -> Add your jsyntaxpane jar from `shared-libs`.
	5. LWJGL -> Add all of your jars from `shared-libs/lwjgl/jar`. Then expand the entry for `lwjgl.jar`, double click on Native library location, and select the correct subfolder of `shared-libs/lwjgl/native/` for your platform.
* If you get compile-time errors at this stage (before having changed any code yourself), you may wish to refresh each project by right clicking and selecting refresh. If you still get them, come ask here for help.
* To run, you should be able to right-click on `net.cemetech.sfgp.freebuild.Main.scala`, and Run or Debug as a Scala application. If you get run-time errors, that's okay, we're still working =) You can also add additional Run + Debug configurations at your leisure, using the Run menu.
* Browse through `Main.scala` to understand how the application is launched and what options are available (for example, how to change the main script file at runtime). Browse through libcrane and `net.cemetech.sfgp.freebuild.platform.ConventionMinder.scala` to figure out where log files are being stored.

[Back to top](#getting-started-with-freebuild)

Finding your way around the source tree
-----------------------------------------
Within the primary FreeBuildJ directory there are three hierarchies of folders that are important to understand as a developer.

### Scala code ###
`net.cemetech.sfgp.freebuild` is the package containing the main engine code. It is divided into several classes and subpackages

* `Main.scala` -> this file is in charge of constructing a Jython interpreter and the logging service, and choosing a main script to run, which will return an `AbstractDriver` subclass, whose `mainloop` will be executed.
* `console` -> contains an implementation of the multiplexed logging service and a class representing the Jython interpreter. You shouldn't have to pay it much mind, unless you want to subscribe a new `OutputStream` listening to standard out/standard err (e.g. for a GUI based console).
* `platform` -> any platform-specific code related to the engine should go here. As of now, this is only `ConventionMinder`, which is the binding to libcrane, for finding things like user data directories.
* `gfx` -> this contains everything related to rendering. It's under active development and things might change. But look here if you need to see how rendering stuff works.
* `drivers` -> this contains implementations of "main loops" that the engine might want to run. Since the world-representation should be separate from rendering (to cleanly separate dedicated servers from clients, or whatever odd utilities someone might want to construct), we all subclass `AbstractDriver` here. It will probably be useful to added a `DedicatedDriver` sometime soon, for net + physics debugging. Any new drivers will need to be registered, probably by adding a line to `Main.scala` (where `GFXDriver` is currently registered), unless we come up with something cleaner. `GFXDriver` currently consists of a UI rendering loop.

Eventually we'll need to add other subpackages for at least net-code and physics, as well as classes representing the non-physics related abstractions of in-game objects, and stuff like that.

### Jython code ###

* `main.py` -> implements a bunch of utility functions, and also initiates the reading of certain preferences (for now), and reads command line arguments
* `scripts` -> contains a class implementing a `DOMSource` on top of the Validator.nu parser, to work around CSSBox defaulting to NekoHTML. This probably doesn't need to be a top level script, but we haven't organized everything else yet. Also contains an `__init__.py` that should probably be a template for most subpackages, as it handles things in a nice dynamic fashion.
* `scripts.ui` -> contains the code that sets up all of the UI handles used by `GFXDriver`, plus a module defining a representation for key bindings that trigger actions, and another module that sets up our dummy main menu. Any scripts related to user interaction should go here (though possibly eventually with better organization).
* `scripts.prefs` -> the `__init__.py` for `scripts.prefs` subpackages are a little bit more elaborate. They define symbols that should be exported by that subpackage, and read a global configuration dictionary to decide which module in that package should be chosen to export them from. Right now we only have keybindings (which is more or less a dummy/holdover, from brief flirtation with librocket), and fonts, which just provides a handle to a directory full of fonts that we want to use. The full preferences system isn't set up yet, but between `main.py` and this you should have a good flavor for how it will work.

Eventually, we'll need to add `server` + `client` subpackages, plus probably a `common`.

###data###
Sounds, models, textures, and ui related stuff will go in `data` (as do preferences). Add directories as needed, in an organized + hierarchical fashion. As with scripts, the top of these hierarchies should probably be related to server/client dichotomies, plus some stuff in common.


[Back to top](#getting-started-with-freebuild)

