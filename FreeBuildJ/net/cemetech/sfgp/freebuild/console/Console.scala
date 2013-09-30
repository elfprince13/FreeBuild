package net.cemetech.sfgp.freebuild.console

import java.io._

object Console {
  val out = new MultiplexedOutputStream
  val err = new MultiplexedOutputStream
  
  private var retainedOut:PrintStream = null
  private var retainedErr:PrintStream = null
  private var siezed:Boolean = false
  
  def siezeSystemStreams = {
    if(!siezed){
      retainedOut = System.out
      retainedErr = System.err
      
      out.subscribe(retainedOut)
      err.subscribe(retainedErr)
      
      System.setOut(new PrintStream(out))
      System.setErr(new PrintStream(err))
      siezed = true
    }
  }
  
  def releaseSystemStreams = {
    if(siezed){
      System.setOut(retainedOut)
      System.setErr(retainedErr)
      
      out.unsubscribe(retainedOut)
      err.unsubscribe(retainedErr)
      
      retainedOut = null
      retainedErr = null
      siezed = false
    }
  }
}