#!/usr/bin/env python
import pexpect, subprocess,re, os.path, os
import sys

from urllib2 import Request, urlopen
from urllib import unquote_plus
from urlparse import urlparse
from contextlib import closing
import zipfile, StringIO


arg_sub_expr = re.compile("\$(\d+)")

def fetch(url,save_data=True,save_name=None,expected_ext=".jar"):
	req = Request(url)
	with closing(urlopen(req)) as response:
		print "Fetching",url,"...\t",
		sys.stdout.flush()
		data = response.read()
		print "done."
		if save_data:
			if save_name:
				fname = save_name
			else:
				urlparts = urlparse(url)
				urlpath = urlparts.path
				if expected_ext and os.path.splitext(urlpath)[1] == expected_ext:
					fname = os.path.basename(urlpath)
				else: # guess what we should call it by looking at the rest of the url
					for param in urlparts.query.split("&"):
						kvp = param.split("=")
						urlpath = kvp[-1]
						if expected_ext and os.path.splitext(urlpath)[1] == expected_ext:
							fname = os.path.basename(urlpath)
							break
					else:
						raise RuntimeError("Unable to name file downloaded from %s according to specification." % url)
			print "Saving as",fname
			with open(fname, 'wb') as f:
				f.write(data)
		else: return data

def arg_sub(args,*subs):
	return [a if not arg_sub_expr.match(a) else subs[int(arg_sub_expr.match(a).group(1))] for a in args]

GIT_PATH = "git"

SCALA_ECLIPSE_PATH = "/Applications/scala-eclipse/Eclipse.app/Contents/MacOS/eclipse"
ECLIPSE_JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk1.7.0_67.jdk/Contents/Home/jre"
ECLIPSE_WORKSPACE = "/Users/thomas/Documents/scala-workspace-temp"
#KEYTOOL_PATH = "keytool"
#PY_DEV_CERT_URL = "http://pydev.org/pydev_certificate.cer"
ANTLR4_URL 		= "http://www.antlr.org/download/antlr-4.4-complete.jar"
JSYNTAXPANE_URL	= "https://jsyntaxpane.googlecode.com/files/jsyntaxpane-0.9.4.jar"
JYTHON_URL		= "http://search.maven.org/remotecontent?filepath=org/python/jython-standalone/2.7-b2/jython-standalone-2.7-b2.jar"
JNA_URL			= "https://maven.java.net/content/repositories/releases/net/java/dev/jna/jna/4.1.0/jna-4.1.0.jar"
VALIDATOR_NU_URL= "http://about.validator.nu/htmlparser/htmlparser-1.4.zip"
VALIDATOR_NU_BIN= "htmlparser-1.4/htmlparser-1.4.jar"
LWJGL_URL		= "https://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.1/lwjgl-2.9.1.zip?r=https%3A%2F%2Fsourceforge.net%2Fprojects%2Fjava-game-lib%2Ffiles%2FOfficial%2520Releases%2FLWJGL%25202.9.1%2F"
SLICK_URL		= "http://slick.ninjacave.com/slick-util.jar"

SHADER_REPO		= "https://github.com/elfprince13/GLSL-Shader-Editor.git"
CRANE_REPO		= "https://github.com/elfprince13/libcrane.git"
GLG2D_REPO		= "https://github.com/elfprince13/glg2d"
CSS_REPO_1		= "https://github.com/radkovo/jStyleParser"
CSS_REPO_2		= "https://github.com/radkovo/CSSBox"

INSTALL_ARGS = ["$0","-nosplash","-data","$1","-application org.eclipse.equinox.p2.director","-repository","$2","-installIU","$3"]
#KEYTOOL_ARGS = ["sudo","$0","-import","-file","$1","-alias","PyDevBrainwy","-keystore","$2"]
GIT_ARGS = ["$0","clone","$1"]

PLUGINS_TO_INSTALL = [
	("http://pydev.org/updates",
	 "org.python.pydev.feature.feature.group"),
	("http://download.eclipse.org/tools/gef/updates/releases/",
	 "org.eclipse.draw2d.feature.group"),
	("http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/",
	 "org.eclipse.xtext.sdk.feature.group"),
	("http://dl.bintray.com/jknack/antlr4ide/",
	 "antlr4ide.sdk.feature.group"),
	#("http://download.eclipse.org/technology/m2e/releases/",
	# "org.eclipse.m2e.feature.feature.group"),
	("http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-antlr/0.15.0/N/0.15.0.201405281449/",
	 "org.sonatype.m2e.antlr.feature.feature.group")
]

#req = Request(PY_DEV_CERT_URL)
#with closing(urlopen(req)) as response:
#	fname = os.path.basename(urlparse(PY_DEV_CERT_URL).path)
#	
#	cert = response.read()
#	
#	with open(fname,'wb') as f:
#		f.write(cert)
#code = subprocess.call(arg_sub(KEYTOOL_ARGS, KEYTOOL_PATH,
#							   fname, os.path.join(ECLIPSE_JAVA_HOME,"lib/security/cacerts")))
#if code:
#	print "Warning: Couldn't import key."
#	print "Y/N? ",
#	inp = raw_input()
#	if not inp or inp.lower()[0] != 'y':
#		sys.exit(1)
#os.remove(fname)

#print " ".join(arg_sub(INSTALL_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,*[",".join(l) for l in zip(*PLUGINS_TO_INSTALL)]))
## Super weirdness: if I don't run with shell=True, it knows somehow and goes to GUI mode
#code = subprocess.call(" ".join(arg_sub(INSTALL_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,*[",".join(l) for l in zip(*PLUGINS_TO_INSTALL)])),shell=True)
code = 0
if code:
	print "eclipse exited with code",code
	raise RuntimeError("Couldn't install a plugin(s)!")
else:
	print('time for dependencies and stuff')
	
	os.chdir("FreeBuildJ")
	if not os.path.isdir("shared-libs"):
		os.mkdir("shared-libs")
	os.chdir("shared-libs")
	fetch(ANTLR4_URL)
	fetch(JSYNTAXPANE_URL)
	fetch(JYTHON_URL)
	fetch(JNA_URL)
	with zipfile.ZipFile(StringIO.StringIO(fetch(VALIDATOR_NU_URL,False)),'r') as parser_zip:
		if VALIDATOR_NU_BIN in parser_zip.namelist():
			print "Extracting...\t",
			sys.stdout.flush()
			with parser_zip.open(VALIDATOR_NU_BIN,'r') as vn_src:
				with open(os.path.basename(VALIDATOR_NU_BIN),'wb') as vn_dst:
					vn_dst.write(vn_src.read())
			print "done."
		else:
			print "Can't find JAR (%s) in archive:" % VALIDATOR_NU_BIN
			print "\n".join("\t%s" % f for f in sorted(parser_zip.namelist()))
			
	with zipfile.ZipFile(StringIO.StringIO(fetch(LWJGL_URL,False,expected_ext=".zip")),'r') as lwjgl_zip:
		print "Extracting all...\t",
		sys.stdout.flush()
		lwjgl_zip.extractall()
		print "done."
	
	fetch(SLICK_URL)
	
	print("Checking out REPOs")
	os.chdir(ECLIPSE_WORKSPACE)
	for repo in [SHADER_REPO, CRANE_REPO, GLG2D_REPO, CSS_REPO_1, CSS_REPO_2]:
		print " ".join(arg_sub(GIT_ARGS,GIT_PATH,repo))
		code = subprocess.call(arg_sub(GIT_ARGS,GIT_PATH,repo))
		
	
	
	
	
	
	
