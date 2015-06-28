package demo.listener;

import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.awt.EventQueue;

import demo.DemoProxy;
import demo.init.GUICreator;
import demo.init.ListenerSetter;
import demo.init.MyClassLoader;
import demo.script.ScriptReader;
import demo.script.ScriptReplayer;
import demo.script.ScriptWriter;

public class ActionEventListener implements AWTEventListener {

	private static ActionEventListener singleton = null;// ��֤����ֻ����ʼ��һ��

//	private LinkedList<AWTEvent> eventStore = new LinkedList<AWTEvent>();

	private ScriptWriter w = null;
	private ScriptReplayer sr = null;

	public static ActionEventListener getInstance() {
		if (singleton == null) {
			singleton = new ActionEventListener();
		}

		Toolkit.getDefaultToolkit().addAWTEventListener(singleton,
				AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
		return singleton;
	}

	public static void removeEventListener() {
		Toolkit.getDefaultToolkit().removeAWTEventListener(singleton);
	}

	private ActionEventListener() {
		w = new ScriptWriter();
		w.setTextArea(DemoProxy.getInstance().getScriptTextArea());
	}

	public void eventDispatched(AWTEvent event) {
		// TODO Auto-generated method stub
		// System.out.println(event.getSource() ) ;//��ӡ�¼�Դ

		switch (event.getID()) {

		case MouseEvent.MOUSE_CLICKED:
			System.out.println("click");

			w.writeScript((MouseEvent) event);
			break;
		case WindowEvent.WINDOW_OPENED:
			System.out.println("open");
			break;
		case WindowEvent.WINDOW_CLOSING:
			System.out.println("close");
			w.finishWriting();
//			System.out.println("sw:" + w.getAllScript());

			removeEventListener();
//			Window window =(Window)event.getSource();
//			window.
			this.replay();
			// case WindowEvent.WINDOW_LOST_FOCUS:
			// System.out.println("lose");
			// this.replay();
		default:
			break;
		}

	}

	public void replay() {
		
		
		this.sr = new ScriptReplayer(DemoProxy.getInstance().getScriptTextArea().getText(), 1000);
		this.sr.init();
		Thread thr = (new Thread(this.sr));
		try {
			//�ȴ���ǰ�̣߳��˴���EventDispatcher�̣߳�һ��ʱ��󣬲������µ��߳���ʾ�طŽ���
			//Ϊ�˷�ֹrobot�ڽ������֮ǰ�Ϳ�ʼ��ѯ���沢������꣬�����ᵼ���쳣
			thr.join(10000);
			thr.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
