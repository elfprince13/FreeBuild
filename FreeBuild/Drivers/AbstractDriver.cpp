//
//  AbstractDriver.cpp
//  FreeBuild
//
//  Created by Thomas Dickerson on 4/2/13.
//
//

#include "AbstractDriver.h"
#include "PyInterpreter.h"


AbstractDriver::AbstractDriver() : pcon(NULL) , settings(){;}
AbstractDriver::~AbstractDriver(){;}


void AbstractDriver::setConsole(shared_ptr<Console::Python> npc){this->pcon = npc;}
shared_ptr<Console::Python> AbstractDriver::getConsole(){return this->pcon;}