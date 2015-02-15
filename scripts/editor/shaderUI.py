from net.cemetech.sfgp.glsl.editor import GLSLEditorPane
    
import os
from java.awt import EventQueue, BorderLayout
from java.awt.event import ActionEvent, KeyEvent, ActionListener
from java.lang import Runnable, IllegalStateException
from javax.swing import JMenu, JMenuBar, JMenuItem, KeyStroke, JSeparator, JFrame, WindowConstants
from net.cemetech.sfgp.glsl.editor import CompilerImpl

from org.lwjgl.opengl import Display
from net.cemetech.sfgp.freebuild.gfx import GFX, Shader, GLSLProgram, ShaderManager

class NativeCompiler(CompilerImpl):
    def __init__(self):
        pass
    
    
def menu_with_accelerator(menuText, accelerator_pair):
    menu = JMenuItem(menuText)
    menu.setAccelerator(KeyStroke.getKeyStroke(*accelerator_pair))
    return menu
    
class RunnableEditor(Runnable):
    def __init__(self,ldPath):
        self.ldPath = ldPath
        
    def run(self):
        mb = JMenuBar()
        
        file = JMenu("File")
        edit = JMenu("Edit")
        run = JMenu("Run")
        
        newMenu = menu_with_accelerator("New",(KeyEvent.VK_N,ActionEvent.META_MASK))
        file.add(newMenu)
               
        open = menu_with_accelerator("Open",(KeyEvent.VK_O,ActionEvent.META_MASK))
        file.add(open)
        
        save = menu_with_accelerator("Save",(KeyEvent.VK_S,ActionEvent.META_MASK))
        file.add(save)
        
        file.add(JSeparator());
        
        resetPipe = menu_with_accelerator("Reset Pipeline",(KeyEvent.VK_N,ActionEvent.META_MASK | ActionEvent.SHIFT_MASK))
        file.add(resetPipe)
                
        openPipe = menu_with_accelerator("Open Pipeline",(KeyEvent.VK_O,ActionEvent.META_MASK | ActionEvent.SHIFT_MASK))
        file.add(openPipe)
                
        compile = menu_with_accelerator("Compile",(KeyEvent.VK_ENTER, ActionEvent.META_MASK))
        run.add(compile)
                
        mb.add(file)
        mb.add(edit)
        mb.add(run)
                
        f = JFrame("SFGP Shader Editor")
        f.setJMenuBar(mb)
        c = f.getContentPane()
        c.setLayout(BorderLayout())
        editor = GLSLEditorPane("",self.ldPath,NativeCompiler())
        c.add(editor, BorderLayout.CENTER)
        c.doLayout()
        
        f.setSize(1000, 700);
        f.setVisible(True);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                
        class EditorActionListener(ActionListener):
            def makeRelay(srcObj):
                return (lambda e: editor.actionPerformed(ActionEvent(srcObj, e.getID(), e.getActionCommand())))
            editorActions = {
                save : (lambda e: editor.saveCurrent()),
                compile : (lambda e: editor.compileCurrent()),
                open : makeRelay(editor.openShader),
                newMenu : makeRelay(editor.newShader),
                openPipe : makeRelay(editor.openPipeline),
                resetPipe : makeRelay(editor.resetPipeline)
                            }
            def actionPerformed(self, e):
                editorActions = EditorActionListener.editorActions
                evtSrc = e.getSource()
                if evtSrc in editorActions:
                    editorActions[evtSrc](e)
                else:
                    raise IllegalStateException("Imaginary menu item registered an ActionEvent: " + evtSrc)
        menuListener = EditorActionListener()
        compile.addActionListener(menuListener);
        newMenu.addActionListener(menuListener);
        open.addActionListener(menuListener);
        save.addActionListener(menuListener);
        resetPipe.addActionListener(menuListener);
        openPipe.addActionListener(menuListener);
    
def init_editor():
    Display.releaseContext()
    
    ldPath = os.getcwd()
    EventQueue.invokeLater(RunnableEditor(ldPath))
