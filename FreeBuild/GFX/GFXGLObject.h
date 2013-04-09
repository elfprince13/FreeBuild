//
//  GFXGLObject.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/8/13.
//
//

#ifndef __FreeBuild__GFXGLObject__
#define __FreeBuild__GFXGLObject__

#include <iostream>
#include <glload/gl_all.hpp>
#include <boost/shared_ptr.hpp>


using namespace boost;
namespace GFX{
	
	class GLObject{
	protected:
		shared_ptr<GLuint> name;
		bool bound;
		GLenum target;
	public:
		GLObject();
		virtual ~GLObject();
		virtual void deleteObj() = 0;
		virtual void handleResize(int w, int h) = 0;
		virtual void bind(GLenum target) = 0;
		// Note that the result of calling this function on subclasses with a pointer
		// to a different subclass is undefined and will probably do bad things
		// But we're making a compromise to allow better flow of OpenGL commands
		virtual void unbind(shared_ptr<GLObject> rebind=shared_ptr<GLObject>()) = 0;
		
	};
	typedef void (GLObject::*DeleteHook)();
	typedef void (GLObject::*ResizeHook)(int w, int h);
	typedef void (GLObject::*BindHook)(GLenum target);
	typedef void (GLObject::*UnbindHook)(shared_ptr<GLObject> rebind);
};


#endif /* defined(__FreeBuild__GFXGLObject__) */