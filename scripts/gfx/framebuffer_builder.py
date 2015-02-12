from org.lwjgl import BufferUtils, LWJGLException
from org.lwjgl.opengl import Display
from net.cemetech.sfgp.freebuild.gfx import FBO, BufferObject, Texture, GFX
from constants import attachment_points, tex_targets, formats, internal_formats, types
import json

from scala import Option



# Each framebuffer we deserialize must have the following top-level properties:
# * width (number) - defaults to 1 for non-fixed, must be specified otherwise
# * height (number) - defaults to 1 for non-fixed, must be specified otherwise
# * attachments (dictionary)
# Optionally, they may also have fixed_dims, specifying which of width and/or
# height should be interpreter as a fixed pixel size, rather than a fraction
# of Display dimensions.
#
# Each attachment should be keyed by attachment point, and contain
# instructions for building an image to render to

# Each texture must specify the following:
# * target, defaults to GL_TEXTURE_2D
# * format, and internal_format
# * type



def texture_from_description(desc, width, height, suppress_unbind=False):
    target = tex_targets[desc.get('target', 'GL_TEXTURE_2D')]
    format = formats[desc['format']]
    internal_format = internal_formats[desc['internal_format']]
    ttype = types[desc['type']]
    # TODO - add support for texture parameters - clamping, filters, etc
    
    if target == tex_targets['GL_TEXTURE_2D']:
        tex = Texture(0, target)
        tex.bind()
        tex.allocate2D(0, internal_format, width, height, 0, format, ttype, Option.apply(None))
        if not suppress_unbind: tex.unbind()
        return tex
    else:
        raise LWJGLException("FreeBuild Texture objects only know how to allocate 2D textures")
    
def framebuffer_from_description(description, suppress_unbind=False):
    fixed_dims = description.get('fixed_dims', ())
    width = (int(description.get('width', 1) * Display.getWidth())
             if 'width' not in fixed_dims else description['width']) 
    height = (int(description.get('height', 1) * Display.getHeight())
              if 'height' not in fixed_dims else description['height'])
    attachments = {attachment_points[a] : 
                   # thunk to save state changes
                   (lambda desc, w, h:(lambda: texture_from_description(desc, w, h, True)))(t, width, height)
                   for a, t in description['attachments'].items()}
    
    buffer = FBO(0)
    buffer.bind()
    for attachment, texgen in attachments.items():
        tex = texgen()
        buffer.attach2D(tex, attachment, 0, True)
        tex.unbind()
    if not suppress_unbind: buffer.unbind()
    return buffer
        
def framebuffer_from_json(path):
    with open(path, 'r') as desc_handle:
        json_data = json.load(desc_handle)
        return lambda: framebuffer_from_description(json_data)
    
def exec_test(path):
    fb = framebuffer_from_json(path)()
    try:
        fb.bind()
        fb.check()
    except LWJGLException as e:
        import traceback, os.path
        top = traceback.extract_stack()[-1]
        print ', '.join([str(e), os.path.basename(top[0]), str(top[1])])
        print "Couldn't make our framebuffer, checking for OpenGL errors"
        GFX.checkNoGLErrors("Error creating textures for inferred rendering:")
    finally:
        fb.unbind()
        fb.delete()
        
