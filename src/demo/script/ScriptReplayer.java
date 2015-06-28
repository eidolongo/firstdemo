package demo.script;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.swing.JFrame;

import demo.DemoProxy;
import demo.init.GUICreator;
import demo.init.MyClassLoader;

public class ScriptReplayer implements Runnable{

	private ScriptReader sr = null;
	private MyRobot myrobot;
	private int delay;
	private ScriptReplayer(int delay) {
		try {
			this.myrobot = new MyRobot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.delay = delay;

		
	}

	public ScriptReplayer(String scripts,int delay) {
		this(delay);
		this.sr = new ScriptReader(scripts);
		
	}

	public ScriptReplayer(File scriptFile, int delay) {
		this(delay);
		try {
			this.sr = new ScriptReader(scriptFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void init(){
		DemoProxy demo = DemoProxy.getInstance();
		
        GUICreator gui = new GUICreator(demo.getLoader(), demo.getClassName());
        gui.start();
	}

	private void startReplay() {
		if (this.sr == null)
			return;

		HashMap<String, String> scriptItem = null;
		while ((scriptItem = this.sr.readScript()) != null) {
			try {
				Thread.sleep(this.delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.myrobot.doMouseScript(scriptItem);

		}
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.startReplay();
	}

	

	

}
