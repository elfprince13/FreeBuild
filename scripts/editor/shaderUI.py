from net.cemetech.sfgp.glsl.editor import GLSLEditorPane
    
import os, os.path
from java.awt import EventQueue, BorderLayout
from java.awt.event import ActionEvent, KeyEvent, ActionListener
from java.lang import Runnable, IllegalStateException
from javax.swing import JMenu, JMenuBar, JMenuItem, KeyStroke, JSeparator, JFrame, WindowConstants
from net.cemetech.sfgp.glsl.compile import CompilerImpl, LinkerTaskSpec, CompilerTaskSpec, TaskResult

from java.util.concurrent import LinkedBlockingQueue,FutureTask, Callable

from org.lwjgl.opengl import Display
from net.cemetech.sfgp.freebuild.gfx import GFX, Shader, GLSLProgram, ShaderManager
from net.cemetech.sfgp.freebuild.console import StringLoggingService

class NativeCompilerTask(CompilerTaskSpec):
    def __init__(self, copySpec):
        CompilerTaskSpec.__init__(self,copySpec.getKind(), copySpec.getSrc())
        self.logger = StringLoggingService(False)
    
    def call(self):
        shader = Shader(self.src, self.kind)
        return TaskResult(shader.shaderId(), "")
    
class NativeLinkerTask(LinkerTaskSpec):
    def __init__(self, copySpec):
        CompilerTaskSpec.__init__(self,copySpec.getShaders())
    
    def call(self):
        prog = GLSLProgram()
        shaders = {Shader(r.resultId) : False for r in self.shaders}
        prog.attach(shaders)
        prog.link()
        # Need to re-construct the inputs as shaders somehow.
        # This will require refactoring
        # For now, a dummy test
        return TaskResult(prog.progId(), "")
    
class CleanupTask(Callable):
    def __init__(self, peerCon, result):
        self.peerCon = peerCon
        self.result = result
        
    def call(self):
        try:
            self.peerCon(self.result.resultId).delete()
            return True
        except LWJGLException, e:
            e.printStackTrace()
            return False

class NativeCompiler(CompilerImpl):
    def __init__(self):
        self.tasks = LinkedBlockingQueue()
    
    def waitOnTask(self, task):
        fT = FutureTask(task)
        self.tasks.put(fT)
        return fT.get()
    
    def compileShader(self, compileTask):
        # This leaks! We need some cleanup flag of some kind
        return self.waitOnTask(NativeCompilerTask(compileTask))
    
    def linkProgram(self, linkTask):
        return self.waitOnTask(NativeLinkerTask(linkTask))
    
    def cleanResult(self, peerCon, result):
        return self.waitOnTask(CleanupTask(peerCon, result))
        
    def cleanCompileResult(self, result):
        self.cleanResult(Shader, result)
        
    def cleanLinkResult(self, result):
        self.cleanResult(GLSLProgram, result)
    
    
def menu_with_accelerator(menuText, accelerator_pair):
    menu = JMenuItem(menuText)
    menu.setAccelerator(KeyStroke.getKeyStroke(*accelerator_pair))
    return menu
    
class RunnableEditor(Runnable):
    def __init__(self,ldPath):
        self.ldPath = ldPath
        # If we go back to this route, 
        # we should have some way to get the compiler reference in other threads
        self.compiler = CompilerImpl
        
    def run(self):
        makeEditorFrame(self.ldPath, self.compiler)
        
def makeEditorFrame(ldPath, compiler):
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
    editor = GLSLEditorPane("",ldPath,compiler)
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
    compiler = NativeCompiler()
    ldPath = os.path.join(os.getcwd(),"data/shaders")
    if not os.path.isdir(ldPath):
        ldPath = os.getcwd()
    makeEditorFrame(ldPath, compiler)
    return compiler
