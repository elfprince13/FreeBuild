Don't bother with the C++/Python version anymore. The new cool stuff is Scala/Jython, and a little Java. Look at the .classpath to figure out what needs to be included, but off the top of my head, it's CSSBox, ANTLR4, libcrane (see my repository), GLG2D (my version), JNA, Jython 2.7, the Validator.nu HTML Parser, Slick-Util, and LWJGL. 

Recommend setting up scala-ide version of Eclipse, with the PyDev plugin for Jython.



If you still want to play with the C++ version, the original readme follows:
Needs libRocket, glfw, and Python2.7, and Boost.Python, all built statically. Links against libstdc++, not libc++. Also needs glload, but that's included because I had to tweak it for Mac. There are some other dependencies as well, but you should be able to figure them out based on what symbols it complains about. 
