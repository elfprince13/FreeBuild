package net.cemetech.sfgp.freebuild.physics

import com.badlogic.gdx.math._
import com.badlogic.gdx.physics.bullet._
import com.badlogic.gdx.physics.bullet.collision._
import com.badlogic.gdx.physics.bullet.dynamics._
import com.badlogic.gdx.physics.bullet.linearmath._

import scala.compat.Platform

import com.badlogic.gdx.utils.Disposable;

class PhysTest{
	val GROUND_FLAG = (1<<8).toShort
	val OBJECT_FLAG = (1<<9).toShort
	val ALL_FLAG = -1.toShort;

	class MyContactListener extends ContactListener {
		override def onContactAdded (userValue0:Int, partId0:Int, index0:Int, match0:Boolean, userValue1:Int, partId1:Int, index1:Int, match1:Boolean) = {
			if (match0){ Console.println("match0: ", userValue0) }	
			if (match1){ Console.println("match1: ", userValue1) }	
			true
		}
	}

	class MyMotionState(var transform:Matrix4 = null) extends btMotionState {
		override def getWorldTransform (worldTrans:Matrix4 ) = {
			worldTrans.set(transform)
		}
		override def setWorldTransform (worldTrans:Matrix4 ) = {
			transform.set(worldTrans)
		}
	}

	object GameObject{
		class Constructor(var transform:Matrix4, val shape:btCollisionShape, mass:Float) extends Disposable {
			var localInertia = new Vector3
			if (mass > 0f){ shape.calculateLocalInertia(mass, localInertia)	}
			else { localInertia.set(0, 0, 0) }
			val constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, shape, localInertia)
			
			def construct () = new GameObject(transform, constructionInfo)
			override def dispose () = {
				shape.dispose()
				constructionInfo.dispose()
			}
		}
	}

	class GameObject(var transform:Matrix4, constructionInfo:btRigidBody.btRigidBodyConstructionInfo) extends Disposable {
		val motionState = new MyMotionState(transform)
		val body = new btRigidBody(constructionInfo)
		body.setMotionState(motionState)

		override def dispose () = {
			body.dispose()
			motionState.dispose()
		}
	}
	
	Bullet.init()
	val objKeys = Array("ground","sphere","box","cone","capsule","cylinder")
	var constructors = Map[String, GameObject.Constructor](objKeys(0) -> new GameObject.Constructor((new Matrix4()), new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f)), 0f),
			objKeys(1) -> new GameObject.Constructor(new Matrix4, new btSphereShape(0.5f), 1f),
			objKeys(2) -> new GameObject.Constructor(new Matrix4, new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), 1f),
			objKeys(3) -> new GameObject.Constructor(new Matrix4, new btConeShape(0.5f, 2f), 1f),
			objKeys(4) -> new GameObject.Constructor(new Matrix4, new btCapsuleShape(.5f, 1f), 1f),
			objKeys(5) -> new GameObject.Constructor(new Matrix4, new btCylinderShape(new Vector3(.5f, 1f, .5f)), 1f))

	val collisionConfig = new btDefaultCollisionConfiguration
	val dispatcher = new btCollisionDispatcher(collisionConfig)
	val broadphase = new btDbvtBroadphase
	val constraintSolver = new btSequentialImpulseConstraintSolver
	val dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig)
	dynamicsWorld.setGravity(new Vector3(0, -10f, 0))
	val contactListener = new MyContactListener
	var instances = List[GameObject]()
	val obj = constructors("ground").construct
	obj.body.setCollisionFlags(obj.body.getCollisionFlags | btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT)
	instances = instances :+ obj
	dynamicsWorld.addRigidBody(obj.body)
	obj.body.setContactCallbackFlag(GROUND_FLAG)
	obj.body.setContactCallbackFilter(0)
	obj.body.setActivationState(CollisionConstants.DISABLE_DEACTIVATION)

	def spawn () = {
		val obj = constructors(objKeys(1 + MathUtils.random(objKeys.size - 2))).construct
		obj.transform.setFromEulerAngles(MathUtils.random(360f), MathUtils.random(360f), MathUtils.random(360f))
		obj.transform.trn(MathUtils.random(-2.5f, 2.5f), 9f, MathUtils.random(-2.5f, 2.5f))
		obj.body.proceedToTransform(obj.transform)
		obj.body.setUserValue(instances.size)
		obj.body.setCollisionFlags(obj.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK)
		instances = instances :+ obj
		dynamicsWorld.addRigidBody(obj.body)
		obj.body.setContactCallbackFlag(OBJECT_FLAG)
		obj.body.setContactCallbackFilter(GROUND_FLAG)
	}

	var spawnTimer = 0f
	var angle = 0f
	var speed = 90f;
	def tick (dt:Long) = {
		val delta = Math.min(1f / 30f, dt)
		angle = (angle + delta * speed) % 360f
		instances(0).transform.setTranslation(0, MathUtils.sinDeg(angle) * 2.5f, 0f)
		dynamicsWorld.stepSimulation(delta, 5, 1f/60f)
		spawnTimer = spawnTimer - delta
		if (spawnTimer < 0) {
			spawn()
			spawnTimer = 1.5f
		}
	}

	def dispose () = {
		instances.foreach(_.dispose())
		instances = List[GameObject]()
		constructors.values.foreach(_.dispose())
		constructors =  Map[String, GameObject.Constructor]()
		dynamicsWorld.dispose()
		constraintSolver.dispose()
		broadphase.dispose()
		dispatcher.dispose()
		collisionConfig.dispose()
		contactListener.dispose()
	}
}

object Test {
	def main(args:Array[String]) = {
		Console.println("hi")
		val t = new PhysTest

		val start = Platform.currentTime
		Stream.continually(Platform.currentTime).takeWhile(_ - start <= 2000).foldLeft(start){
		case(last, now) => t.tick(now - last)
				Thread.sleep(18)
				now
		}
		Console.println("Ran for ", Platform.currentTime - start)
		t.dispose()
		Console.println("bye")
	}

}