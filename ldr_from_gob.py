#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys, os
from math import sin,cos
from numpy import matrix,eye,array

types = {
		1 : "item",
		3 : "trigger",
		4 : "portculy",
		5 : "brick",
		6 : "minifig"
		}
colors = {'grey' : 7, 'base' : 7, 'mediumblue' : 73} # will read these from file

pieces = {'staticbrick1x4x6window' : '6160.dat', 'staticBrick1x4x5window' : '4347c01.dat', 
	'staticplate32x32' : '3811.dat', 'staticpinetreebrick':'3471.dat',
	'staticwing3x6' : '2419.dat', 'staticarch1x5x4I' : '30099.dat', 'staticarch1x8x2round' : '3308.dat'} # will read these file


lines = []

with open(sys.argv[1],'r') as gob:
	for line in gob.readlines():
		line = line.strip()
		words = line.split(" ")
		try:
			kind = words[0]
			if int(kind) == 5:
				oline = "1 %d %f %f %f %f %f %f %f %f %f %f %f %f %s"
				#print "beginning read"
				pl_rotsav = words[1:4]
				pl_name = "" if words[4] == "0" else words[4].replace("§"," ")
				pl_position = [float(w) for w in words[5:8]]
				pl_rotation = [float(w) for w in words[8:12]]
				pl_scale = [float(w) for w in words[12:15]]
				pl_datablock = words[15]
				words = words[16:]
				#print "realigning words"
				owner = "local" if words[0] == "0" else words[0]
				skinname = 'base' if words[1] == "0" else words[1]
				#print "rotating"
				theta = pl_rotation[-1] * 3.14159265 / 180
				a_s = sin(theta)
				a_c = cos(theta)
				a_C = 1 - a_c
				a_x,a_y,a_z = pl_rotation[:3]
				#print "building matrix"
				tge_to_ldraw_axes_mat = matrix([
					[1,	0,	0,	0],
					[0,	0,	-1,	0],
					[0,	1,	0,	0],
					[0,	0,	0,	1]
					])

				ldraw_to_tge_axes_mat = matrix([
					[1,	0,	0,	0],
					[0,	0,	1,	0],
					[0,	-1,	0,	0],
					[0,	0,	0,	1]
					])

				scale_mat = matrix([
					[pl_scale[0],	0,	0,	0],
					[0,	pl_scale[1],	0,	0],
					[0,	0,	pl_scale[2],	0],
					[0,	0,		0,	1],
					])
				#print "scale",scale_mat
				rot_mat = matrix([
						[a_x*a_x*a_C + a_c,	a_x*a_y*a_C - a_z*a_s,	a_x*a_z*a_C + a_y*a_s,	0],
						[a_y*a_x*a_C + a_z*a_s,	a_y*a_y*a_C + a_c,	a_y*a_z*a_C - a_x*a_s,	0],
						[a_z*a_x*a_C - a_y*a_s,	a_z*a_y*a_C + a_x*a_s,	a_z*a_z*a_C + a_c,	0],
						[0,			0,			0,			1]
						])
				#print "rotation",rot_mat
				tge_to_ldu_mat = matrix([
					[1,	0,	0,	0],
					[0,	1,	0,	0],
					[0,	0,	1,	0],
					[0,	0,	0,	1]
					])
				trans_mat = matrix([
					[1,0,0,40*pl_position[0]],
					[0,1,0,40*pl_position[1]],
					[0,0,1,40*pl_position[2]],
					[0,0,0,1]
					])
				#print "translation",trans_mat
				final_mat = tge_to_ldraw_axes_mat*tge_to_ldu_mat*trans_mat*scale_mat*rot_mat*ldraw_to_tge_axes_mat# fix_axes_mat*scale_mat*rot_mat*tge_to_ldu_mat*trans_mat
				#print "converting"
				color = colors[skinname]
				#print "..colors done"
				#print final_mat,final_mat[:3,:]
				[a,b,c,x],[d,e,f,y],[g,h,i,z] = array(final_mat[:3,:])
				#print "..coords done"
				name = pieces[pl_datablock]#"6111.dat"
				#print "..name done"
				lines += [oline % (color,x,y,z,a,b,c,d,e,f,g,h,i,name)]
				#print "..line done"
			else:
				print "Skipping %s: %s" % (types[int(words[0])], line)
		except ValueError:
			print "Can't read line, invalid: %s (%s, %s, %s, %s)" % (line)
		except KeyError:
			print "Skipping unknown color or brick: %s" % (line)
		

ofile = "%s-port.ldr" % (sys.argv[1])
with open(ofile,'w') as ldr:
	ldr.write("""0 %s
0 Name: %s
0 Author: %s
0 // Automatically converted from a TBM/TOB save file
%s
""" % (sys.argv[2],ofile,sys.argv[3],"\n".join(lines)))
