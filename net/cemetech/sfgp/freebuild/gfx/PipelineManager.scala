package net.cemetech.sfgp.freebuild.gfx

object PipelineManager {
	var callbacks:Map[String,RenderCallback] = Map[String,RenderCallback]()
	var shared:Map[String,Dependency] = Map[String,Dependency]()

}

class RenderCallback {
  def pre(program:GLSLProgram):Unit = {}
  def render(program:GLSLProgram):Unit = {}
  def post(program:GLSLProgram):Unit = {}
}

class Dependency() {
	
}

class Stage(shader:GLSLProgram, callback:RenderCallback, target:Option[FBO], dependencies:Map[String,Int]) {
  def render(stages:Map[String,Stage]):Unit = {
	  target match {
	 	  case None => if(FBOManager.boundNow != null ){ FBOManager.boundNow.unbind }
	 	  case Some(target) => target.bind
	  }
	  shader.use()
	  callback.pre(shader)
	  callback.render(shader)
	  callback.post(shader)
	  shader.unuse()
	  
  }
}

class Pipeline(stages:Map[String,Stage],order:Seq[String]) {
  def render():Unit = {
	  order.foreach{
	 	  stage => stages(stage).render(stages)
	  }
  }
}
