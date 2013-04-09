//
//  GFXBufferManager.h
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/8/13.
//
//

#ifndef __FreeBuild__GFXBufferManager__
#define __FreeBuild__GFXBufferManager__

#include <iostream>
#include <boost/shared_ptr.hpp>
#include <boost/unordered_set.hpp>
#include <glload/gl_all.hpp>
#include "GFXGLObject.h"

#endif /* defined(__FreeBuild__GFXBufferManager__) */

using namespace boost;



namespace GFX{
	class BufferManager{
	private:
		static shared_ptr<BufferManager> manager;
		unordered_set<shared_ptr<GLObject>> managed;
	public:
		virtual ~BufferManager();
		void resizeBuffers(int w, int h);
		void manageObject(shared_ptr<GLObject>);
		size_t unmanageObject(shared_ptr<GLObject>);
		
		inline static shared_ptr<BufferManager> getManager(){
			if(!manager.get()){
				manager = shared_ptr<BufferManager>(new BufferManager);
			}
			return manager;
		}
		
		static void resizeCallback(int w, int h){ return getManager()->resizeBuffers(w,h);	}

	};
	
};