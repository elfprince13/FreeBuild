import json
from framebuffer_builder import framebuffer_from_description
from scripts.util.graphs import topsort
import os, os.path

from net.cemetech.sfgp.freebuild.gfx import Shader

from scala import Option

def shader_from_bundle(bundle_path,suppress_unbind=False):
    bundle_contents = os.listdir(bundle_path)
    shader_stages = {'vert' : "", 'geom' : "", 'frag' : ""}
    for file in bundle_contents:
        name,ext = os.path.splitext(file)
        if ext and ext[1:] in shader_stages:
            shader_stages[ext[1:]] = os.path.join(bundle_path,file)
    return lambda: Shader(shader_stages['vert'],shaders_stages['frag'],shader_stages['geom'])
    
def stage_from_description(src_path,description):
    shader = shader_from_bundle(os.path.join(dir,description["shader_bundle"]))()
    target = Option.apply(None if description.get('target','display') else framebuffer_from_description(description))

def pipeline_from_description(src_path,description):
    dir,file = os.path.split(src_path)
    name,ext = os.path.splitext(file)
    stages = {}
    

def pipeline_from_json(path):
    with open(path, 'r') as desc_handle:
        json_data = json.load(desc_handle)
        return lambda: pipeline_from_description(path,json_data)
