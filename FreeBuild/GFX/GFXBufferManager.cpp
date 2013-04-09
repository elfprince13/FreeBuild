//
//  GFXBufferManager.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/8/13.
//
//

#include "GFXBufferManager.h"


shared_ptr<GFX::BufferManager> GFX::BufferManager::manager;
void GFX::BufferManager::resizeBuffers(int w, int h){
	std::cout << "Handling resize to " << w << "," << h << std::endl;
	for(unordered_set<shared_ptr<GLObject>>::iterator i = managed.begin(); i!=managed.end(); ++i){
		(*i)->handleResize(w,h);
	}
}
GFX::BufferManager::~BufferManager(){
	for(unordered_set<shared_ptr<GLObject>>::iterator i = managed.begin(); i!=managed.end(); ++i){
		(*i)->deleteObj(); // Shouldn't need to release?
	}
}

void GFX::BufferManager::manageObject(shared_ptr<GLObject> mo){
	managed.insert(mo);
}

size_t GFX::BufferManager::unmanageObject(shared_ptr<GLObject> eo){
	return managed.erase(eo);
}