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

#endif /* defined(__FreeBuild__GFXGLObject__) */

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
		virtual void unbind() = 0;
		
	};
	typedef void (GLObject::*DeleteHook)();
	typedef void (GLObject::*ResizeHook)(int w, int h);
	typedef void (GLObject::*BindHook)(GLenum target);
	typedef void (GLObject::*UnbindHook)();
};
