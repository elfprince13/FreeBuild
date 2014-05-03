from org.lwjgl import BufferUtils
from org.lwjgl.opengl import Display
from net.cemetech.sfgp.freebuild.gfx import FBO, BufferObject, Texture
from constants import attachment_points, tex_targets, formats, internal_formats, types
import json



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



def texture_from_description(desc, width, height):
    target = tex_targets[desc.get('target','GL_TEXTURE_2D')]
    format = formats[desc['format']]
    internal_format = internal_formats[desc['internal_format']]
    ttype = formats[desc['type']]
    
def framebuffer_from_description(description):
    fixed_dims = description.get('fixed_dims', ())
    width = (int(description.get('width', 1) * Display.getWidth())
             if 'width' not in fixed_dims else description['width']) 
    height = (int(description.get('height', 1) * Display.getHeight())
              if 'height' not in fixed_dims else description['height'])
    attachments = {attachment_points[a] : 
                   texture_from_description(t, width, height) 
                   for a, t in description['attachments'].items()}
        
def framebuffer_from_json(path):
    with open(path, 'r') as desc_handle:
        return framebuffer_from_description(json.load(desc_handle))
        
