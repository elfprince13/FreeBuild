package net.cemetech.sfgp.freebuild.console

import java.io._

import java.util.logging.Logger
import java.util.logging.Level
import net.cemetech.sfgp.freebuild.platform.ConventionMinder

object Console {
  private val outInternal = new MultiplexedOutputStream
  private val errInternal = new MultiplexedOutputStream
  
  private var retainedOut:PrintStream = null
  private var retainedErr:PrintStream = null
  private var siezed:Boolean = false
  
  val logHandler = new GenericLoggingService(outInternal, errInternal)
  LSManager.ps = new PrintStream(outInternal)
  
  val logger = Logger.getLogger(ConventionMinder.getPackageString)
  logger.setUseParentHandlers(false)
  logger.addHandler(logHandler)
  
  val out = new AutologgingOutputStream(logger,Level.INFO)
  val err = new AutologgingOutputStream(logger,Level.SEVERE)
  
  def subscribe(stream:OutputStream,err:Boolean=false) = {
	  if(err){
	 	  errInternal.subscribe(stream)
	  } else {
	 	  outInternal.subscribe(stream)
	  }
  }
  
  def unsubscribe(stream:OutputStream,err:Boolean=false) = {
	  if(err){
	 	  errInternal.unsubscribe(stream)
	  } else {
	 	  outInternal.unsubscribe(stream)
	  }
  }
  
  def siezeSystemStreams = {
    if(!siezed){
      retainedOut = System.out
      retainedErr = System.err
      
      outInternal.subscribe(retainedOut)
      errInternal.subscribe(retainedErr)
      
      System.setOut(new PrintStream(out))
      System.setErr(new PrintStream(err))
      siezed = true
    }
  }
  
  def releaseSystemStreams = {
    if(siezed){
      System.setOut(retainedOut)
      System.setErr(retainedErr)
      
      outInternal.unsubscribe(retainedOut)
      errInternal.unsubscribe(retainedErr)
      
      retainedOut = null
      retainedErr = null
      siezed = false
    }
  }
}