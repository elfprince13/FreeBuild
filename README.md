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

1. [Get the 4.0.x](http://scala-ide.org/download/sdk.html) branch of the Scala IDE (for Scala 2.11), this is prepackaged with Eclipse
2. Run it at least once to setup a workspace folder.

[Back to top](#getting-started-with-freebuild)

Getting the sources
-------------------
There are several relevant GitHub repos for the main project.
You'll need a Git client. On OS X, I'm generally happy with the official GUI client for basic needs, but not sure how it is on other platforms.
The command line version isn't that hard to figure out, and Google can provide some good explanations of how it works.
You'll need to check them out as follows (and it will probably be beneficial to fork copies of the ones you'll be working on into your personal GitHub account), **but don't do it quite yet**!

1. [FreeBuild](https://github.com/elfprince13/FreeBuild) contains the main engine sources, and the scripts (_Note: you're here already_).
2. [LDrawParser](https://github.com/elfprince13/LDrawParser) contains the grammars for the LDraw parser, which will eventually feed all of our 3d models.
3. [GLSL-Shader-Editor](https://github.com/elfprince13/GLSL-Shader-Editor) contains the source and grammar for the GLSL shader editor that will be integrated into the engine.
4. [libcrane](https://github.com/elfprince13/libcrane) is a native library to find the OS-appropriate folders for storing various sorts of runtime data.

What you will want to check out first are the [Helper Scripts](https://github.com/elfprince13/FreeBuild-HelperScripts). Clone this repository into your Eclipse workspace folder, and proceed to the next section.

[Back to top](#getting-started-with-freebuild)

Getting the dependencies
------------------------
1. Open up `autosetup.py` from the Helper Scripts folder, and edit the variables at the top to match your machine settings.
You'll also need to change the repository URLs if you chose to fork any of the repos above.
Unless you've already setup Eclipse for FreeBuild in the past, make sure that both `SKIP_PLUGINS` and `SKIP_SHARED` and set to `False`.
2. Run `autosetup.py`.
If you did everything right so far, it'll run to completion, if you hit errors along the way, adjust the configuration variables to match your machine.
Once the Eclipse plugins have been installed successfully, you will save a lot of time by setting `SKIP_PLUGIN=True` for further runs of the script.
3. If you have trouble building libcrane automatically, you might need to stage a hand intervention at this point. On a Unix-y system, you probably just need to run make. On a Windows machine, you should be able to use the included VS project. `testcrane.cpp` probably won't build on OS X. Don't worry about it. You just need the dynamic library.
4. You should now have a number of repositories downloaded into your workspace directory.
5. Additionally, FreeBuild-HelperScripts/shared-libs will be populated with jars of common dependencies. A number of these libraries come with src distributions as well as the jar file. If you end up needing to debug something within them, you'll want to have it handy. If it seems unlikely that you'll be working on that part of the engine, probably no need to keep it around.

[Back to top](#getting-started-with-freebuild)

Setting up your workspace
-------------------------

* Open the Eclipse that has Scala IDE/PyDev/AntlrIDE.
* File -> Import -> General -> Existing Projects into Workspace
* Browse to select the workspce directory. Make sure "Search for nested projects is checked", and "Copy projects into workspace" is unchecked. Select all of the listed projects: CSSBox, FreeBuild, FreeBuild-HelperScripts GLSL-Shader-Editor, jStyleParser, LDrawParser, and libcrane. Press Finish.
* Open the Eclipse preferences, go to PyDev, then Interpreters, then click Jython Interpreter.
* Click "New", enter "Jython 2.7 beta" as the interpreter name, and browse to select the standalone jar in `shared-libs`.
* It will complain that it couldn't find the Python stdlib. Press "Proceed anyways", then select the new interpreter from the list, and switch to the Libraries tab below. Press "New Jar/Zips", and select the standalone jar again. Press Open, Apply, and Ok.
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
* `boxui` -> contains for code for a user-interface layer built on CSSBox (and implementation of an OpenGL-driven renderer for HTML/CSS UI elements)
* `console` -> contains an implementation of the multiplexed logging service and a class representing the Jython interpreter. You shouldn't have to pay it much mind, unless you want to subscribe a new `OutputStream` listening to standard out/standard err (e.g. for a GUI based console).
* `drivers` -> this contains implementations of "main loops" that the engine might want to run. Since the world-representation should be separate from rendering (to cleanly separate dedicated servers from clients, or whatever odd utilities someone might want to construct), we all subclass `AbstractDriver` here. It will probably be useful to added a `DedicatedDriver` sometime soon, for net + physics debugging. Any new drivers will need to be registered, probably by adding a line to `Main.scala` (where `GFXDriver` is currently registered), unless we come up with something cleaner. `GFXDriver` currently consists of a UI rendering loop.
* `gfx` -> this contains everything related to rendering. It's under active development and things might change. But look here if you need to see how rendering stuff works.
* `platform` -> any platform-specific code related to the engine should go here. As of now, this is only `ConventionMinder`, which is the binding to libcrane, for finding things like user data directories.

Eventually we'll need to add other subpackages for at least net-code and physics, as well as classes representing the non-physics related abstractions of in-game objects, and stuff like that.

### Jython code ###

* `main.py` -> implements a bunch of utility functions, and also initiates the reading of certain preferences (for now), and reads command line arguments
* `scripts` -> contains a class implementing a `DOMSource` on top of the Validator.nu parser, to work around CSSBox defaulting to NekoHTML. This probably doesn't need to be a top level script, but we haven't organized everything else yet. Also contains an `__init__.py` that should probably be a template for most subpackages, as it handles things in a nice dynamic fashion.
* `scripts.gfx` -> contains the code used for our configurable graphics pipeline (still in the works)
* `scripts.ui` -> contains the code that sets up all of the UI handles used by `GFXDriver`, plus a module defining a representation for key bindings that trigger actions, and another module that sets up our dummy main menu. Any scripts related to user interaction should go here (though possibly eventually with better organization).
* `scripts.util` -> contains currently only a topological sort algorithm to be used for dependency analysis, among other things. Can be populated with other utilities that don't appear to belong elsewhere.
* `scripts.prefs` -> the `__init__.py` for `scripts.prefs` subpackages are a little bit more elaborate. They define symbols that should be exported by that subpackage, and read a global configuration dictionary to decide which module in that package should be chosen to export them from. Right now we only have keybindings (which is more or less a dummy/holdover, from brief flirtation with librocket), and fonts, which just provides a handle to a directory full of fonts that we want to use. The full preferences system isn't set up yet, but between `main.py` and this you should have a good flavor for how it will work.

Eventually, we'll need to add `server` + `client` subpackages, plus probably a `common`.

###data###
Sounds, models, textures, and ui related stuff will go in `data` (as do preferences). Add directories as needed, in an organized + hierarchical fashion. As with scripts, the top of these hierarchies should probably be related to server/client dichotomies, plus some stuff in common.


[Back to top](#getting-started-with-freebuild)

