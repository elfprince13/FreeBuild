#!/usr/bin/env python
import subprocess,re, os.path, os, shutil
import sys

from urllib2 import Request, urlopen
from urllib import unquote_plus
from urlparse import urlparse
from contextlib import closing
import zipfile, StringIO
from lxml import etree

arg_sub_expr = re.compile("\$(\d+)")

def repodir(repo_url):
	return os.path.splitext(os.path.basename(urlparse(repo_url).path))[0]

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
SKIP_PLUGINS = True
SKIP_SHARED = False

MVN_PROJECT			= "pom.xml"
MVN_XMLNS_URL		= "http://maven.apache.org/POM/4.0.0"
MVN_NSMAP			= {'pom' : MVN_XMLNS_URL}

SCALA_ECLIPSE_PATH = "/Applications/scala-eclipse/Eclipse.app/Contents/MacOS/eclipse"
ECLIPSE_JAVA_HOME = "/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home/jre"
ECLIPSE_WORKSPACE = "/Users/thomas/Documents/scala-workspace-temp"
#KEYTOOL_PATH = "keytool"
#PY_DEV_CERT_URL = "http://pydev.org/pydev_certificate.cer"
ANTLR4_URL 		= "http://www.antlr.org/download/antlr-4.4-complete.jar"
JSYNTAXPANE_URL	= "https://jsyntaxpane.googlecode.com/files/jsyntaxpane-0.9.4.jar"
JYTHON_URL		= "http://search.maven.org/remotecontent?filepath=org/python/jython-standalone/2.7-b3/jython-standalone-2.7-b3.jar"
JNA_URL			= "https://maven.java.net/content/repositories/releases/net/java/dev/jna/jna/4.1.0/jna-4.1.0.jar"
VALIDATOR_NU_URL= "http://about.validator.nu/htmlparser/htmlparser-1.4.zip"
VALIDATOR_NU_BIN= "htmlparser-1.4/htmlparser-1.4.jar"
LWJGL_URL		= "http://downloads.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip"
SLICK_URL		= "http://slick.ninjacave.com/slick-util.jar"
MSP_URL			= "https://today.java.net/sites/all/modules/pubdlcnt/pubdlcnt.php?file=/today/2006/03/23/MultiSplit.zip&nid=219663"

MSP_SUBDIR		= "MultiSplit/src/org"

SHADER_REPO		= "https://github.com/elfprince13/GLSL-Shader-Editor.git"
CRANE_REPO		= "https://github.com/elfprince13/libcrane.git"
GLG2D_REPO		= "https://github.com/elfprince13/glg2d.git"
CSS_REPO_1		= "https://github.com/radkovo/jStyleParser.git"
CSS_REPO_2		= "https://github.com/radkovo/CSSBox.git"

INSTALL_ARGS	= ["$0","-nosplash","-data","$1","-application","org.eclipse.equinox.p2.director","-repository","$2","-installIU","$3"]
#KEYTOOL_ARGS	= ["sudo","$0","-import","-file","$1","-alias","PyDevBrainwy","-keystore","$2"]
GIT_ARGS		= ["$0","clone","$1"]
PROJECT_IMPORT_ARGS	= ["$0","-nosplash","-data","$1","-application", "org.eclipse.cdt.managedbuilder.core.headlessbuild","-import","$2"]

PLUGINS_TO_INSTALL = [
	("http://pydev.org/updates",
	 "org.python.pydev.feature.feature.group"),
	("http://download.eclipse.org/tools/gef/updates/releases/",
	 "org.eclipse.draw2d.feature.group"),
	("http://download.eclipse.org/modeling/tmf/xtext/updates/composite/releases/",
	 "org.eclipse.xtext.sdk.feature.group"),
	("http://dl.bintray.com/jknack/antlr4ide/",
	 "antlr4ide.sdk.feature.group"),
	("http://repo1.maven.org/maven2/.m2e/connectors/m2eclipse-antlr/0.15.0/N/0.15.0.201405281449/",
	 "org.sonatype.m2e.antlr.feature.feature.group")
]

FREEBUILD_DIR	= os.getcwd()
FREEBUILD_SUBPROJECTS	= ["LDrawparser","FreeBuildJ"]

PROJECTS_TO_IMPORT = [
	os.path.join(ECLIPSE_WORKSPACE,repodir(CSS_REPO_1)),
	os.path.join(ECLIPSE_WORKSPACE,repodir(CSS_REPO_2)),
	os.path.join(ECLIPSE_WORKSPACE,repodir(GLG2D_REPO)),
	os.path.join(ECLIPSE_WORKSPACE,repodir(SHADER_REPO)),
] + [os.path.join(FREEBUILD_DIR,proj_dir) for proj_dir in FREEBUILD_SUBPROJECTS]

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

if __name__ == '__main__':
	if not SKIP_PLUGINS:
		print " ".join(arg_sub(INSTALL_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,*[",".join(l) for l in zip(*PLUGINS_TO_INSTALL)]))
		code = subprocess.call(arg_sub(INSTALL_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,*[",".join(l) for l in zip(*PLUGINS_TO_INSTALL)]))
		#code = 0
		if code:
			print "eclipse exited with code",code
			raise RuntimeError("Couldn't install a plugin(s)!")
	print('time for dependencies and stuff')
		
	os.chdir("FreeBuildJ")
	if not os.path.isdir("shared-libs"):
		os.mkdir("shared-libs")
	os.chdir("shared-libs")
	if not SKIP_SHARED:
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
		if code: raise RuntimeError("Couldn't fetch repo")
		
	os.chdir(repodir(SHADER_REPO))
	if not os.path.isdir("multisplitpane"):
		os.mkdir("multisplitpane")
	os.chdir("multisplitpane")
	
	with zipfile.ZipFile(StringIO.StringIO(fetch(MSP_URL,False,expected_ext=".zip")),'r') as msp_zip:
		print "Extracting src...\t",
		sys.stdout.flush()
		msp_zip.extractall(".",[name for name in msp_zip.namelist() if len(name) >= len(MSP_SUBDIR) and name[:len(MSP_SUBDIR)]==MSP_SUBDIR])
		junk,src = os.path.split(MSP_SUBDIR)
		shutil.move(MSP_SUBDIR,src)
		while junk:
			shutil.rmtree(junk)
			junk, src = os.path.split(junk)
		print "done."
	
	os.chdir(ECLIPSE_WORKSPACE)
	
	os.chdir(repodir(CRANE_REPO))
	code = subprocess.call(["make"])
	if code: raise RuntimeError("Couldn't make libcrane")
	
	os.chdir(ECLIPSE_WORKSPACE)
	os.chdir(repodir(CSS_REPO_1))
	with open(MVN_PROJECT,'r') as pom_h:
		pom = etree.fromstring(pom_h.read())
		version_path = etree.XPath("/pom:project/pom:version",namespaces=MVN_NSMAP)
		version = version_path(pom)[0].text
		
	os.chdir(ECLIPSE_WORKSPACE)
	os.chdir(repodir(CSS_REPO_2))
	with open(MVN_PROJECT,'r') as pom_h:
		pom = etree.fromstring(pom_h.read())
		dep_version_path = etree.XPath('/pom:project/pom:dependencies/pom:dependency/pom:version[../pom:artifactId/text() = "jstyleparser"]',namespaces=MVN_NSMAP)
		req_version_tag = dep_version_path(pom)[0]
		if req_version_tag.text != version:
			print "Warning, CSSBox requested a different version of jStyleParser than was checked out"
			print "This is probably just an oversight on the part of the repo maintainers"
			print "We will attempt to correct the",MVN_PROJECT
			req_version_tag.text = version
	with open(MVN_PROJECT,'w') as pom_h:
		pom_h.write(etree.tostring(pom))
		
	os.chdir(ECLIPSE_WORKSPACE)
	
	#for project_dir in PROJECTS_TO_IMPORT:
	#	print " ".join(arg_sub(PROJECT_IMPORT_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,project_dir))
	#	code = subprocess.call(arg_sub(PROJECT_IMPORT_ARGS,SCALA_ECLIPSE_PATH,ECLIPSE_WORKSPACE,project_dir))
	#	if code:
	#			print "eclipse exited with code",code
	#			raise RuntimeError("Couldn't import project %s!" % project_dir)
			
	# need to patch in Jython version and User Libraries
	

