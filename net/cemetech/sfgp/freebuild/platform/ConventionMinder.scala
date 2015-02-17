package net.cemetech.sfgp.freebuild.platform

import com.sun.jna.Platform
import com.sun.jna.Pointer
import com.sun.jna.Native
import com.sun.jna.Memory

import java.io.File
import com.sun.jna.NativeLibrary


object ConventionMinder {
  private val appHandleStr = "net.cemetech.sfgp.freebuild"
  private val appHandleCStr = appHandleStr + '\0'
  val fsep = File.separator
  
  def getSubpackageString(name:String) = {
	  s"$appHandleStr.$name"
  }
  def getPackageString() = {
	  appHandleStr
  }
  
  Native.register("crane")
  @native protected def getApplicationLogFile(buf:Pointer, maxl:Int, appName:Pointer):Unit
  @native protected def getApplicationDataDirectory(buf:Pointer, maxl:Int, appName:Pointer):Unit
  @native protected def getApplicationScriptsDirectory(buf:Pointer, maxl:Int, appName:Pointer):Unit
  @native protected def getUserHomeDirectory(buf:Pointer, maxl:Int):Unit
  @native protected def getTemporaryDirectory(buf:Pointer, maxl:Int):Unit
  
  
  def allocNativeString(l:Long):Memory = { new Memory(l) }
  def fillNativeString(m:Memory, sbuf:Array[Byte]):Memory = {
    m.write(0,sbuf,0,sbuf.length)
    m
  }
  
  def findLogPath():String = {
    val sbuf = appHandleCStr.getBytes("UTF-8")
    val mApp = fillNativeString(allocNativeString(sbuf.length),sbuf)
	val mRet = allocNativeString(1024)
	getApplicationLogFile(mRet,1024,mApp)
    mRet.getString(0)
  }
  
  def findScriptPath():String = {
    val sbuf = appHandleCStr.getBytes("UTF-8")
	val mApp = fillNativeString(allocNativeString(sbuf.length),sbuf)
	val mRet = allocNativeString(1024)
	getApplicationScriptsDirectory(mRet,1024,mApp)
    mRet.getString(0)
  }
  
  def findDataPath():String = {
    val sbuf = appHandleCStr.getBytes("UTF-8")
	val mApp = fillNativeString(allocNativeString(sbuf.length),sbuf)
	val mRet = allocNativeString(1024)
	getApplicationDataDirectory(mRet,1024,mApp)
    mRet.getString(0)
  }
  
  def findUserHome():String = {
    val mRet = allocNativeString(1024)
	getUserHomeDirectory(mRet,1024)
    mRet.getString(0)
  }
  
  def findTempPath():String = {
    val mRet = allocNativeString(1024)
	getTemporaryDirectory(mRet,1024)
    mRet.getString(0)
  }
}
