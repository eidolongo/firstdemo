package demo.script;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;

import demo.DemoProxy;
import demo.SuperClassStr;
import demo.init.MyClassLoader;

public class MyRobot extends Robot {

	private MyClassLoader loader = DemoProxy.getInstance().getLoader();
	
	public MyRobot() throws AWTException {
		// TODO Auto-generated constructor stub
		this.setAutoDelay(100);

	}

	public MyRobot(GraphicsDevice screen) throws AWTException {
		super(screen);
		this.setAutoDelay(100);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���ݵõ��Ľű�Ԫ�أ��������е����ݣ��õ���Ҫ���Ե������Ϣ����ִ�У��������������¼�
	 * 
	 * @param scriptItem
	 *            �ű�Ԫ�أ�Ҳ���ű��ļ��е�һ�У�����һ���û�����
	 */
	public void doMouseScript(HashMap<String, String> scriptItem) {
		this.waitForIdle();
		if (scriptItem.get("superclass")!=null && scriptItem.get("superclass").equals("javax.swing.AbstractButton")) {
			Component comp;
			comp = this.findComponent(scriptItem.get("tag"), scriptItem
					.get("class"), scriptItem.get("superclass"));
			if (comp != null) {
				this.mouseMove(comp.getLocationOnScreen().x
						+ Integer.parseInt(scriptItem.get("x")), comp
						.getLocationOnScreen().y
						+ Integer.parseInt(scriptItem.get("y")));
				this.mousePress(InputEvent.BUTTON1_MASK);
				this.mouseRelease(InputEvent.BUTTON1_MASK);
			}
		}
	}


	/**
	 * ���ǲ����������ڷ���
	 * 
	 * @param tag
	 *            ��Ϊ����������ġ�����ֵ��
	 * @param className
	 *            ���������������
	 * @param superClass
	 *            ����������ĸ���
	 * @return
	 * �ҵ���������Ҳ����򷵻�null
	 */
	private Component findComponent(String tag, String className,
			String superClass) {
		Frame[] jframes = (Frame[]) JFrame.getFrames();
		Component button;
		for (Frame temp : jframes) {
			// getFrames�����᷵������frame������������Ļ�ϵģ���˱����ж�frame�Ƿ�����Ļ����
			// �������������Ļ����ʱ�����
			if (!temp.isValid() || !temp.isShowing())
				continue;
			// ���ݸ��࣬������Ӧ�Ĳ��ҷ���
			//
			if (superClass.equals(SuperClassStr.AbstractButton)) {

				try {
					if ((button = this.getButton(temp,
							this.loader.findClass(className), tag)) != null) {
						return button;
					}
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
				// ����������϶���ĸ��࣬��ô����null
			} else {
				return null;
			}
		}
		return null;
	}

	private Frame getFrame(String title) {
		Frame[] jframes = (Frame[]) JFrame.getFrames();
		for (int i = 0; i < jframes.length; i++) {
			if (jframes[i].getTitle().equalsIgnoreCase(title))
				return jframes[i];
		}
		return null;

	}

	/**
	 * ʵ��ʵ�ֲ��ҷ������࣬���õݹ�ķ�������cont����Ϊ���ڵ�����������֧�����ط���text�����İ�ť
	 * 
	 * @param cont
	 *            ��ʼ�����ĸ��ڵ�
	 * @param tag
	 *            �����ж��Ƿ�ΪҪ���ҵİ�ť���
	 * @return
	 */
	private AbstractButton getButton(Container cont, Class classType, String tag) {
		// ��ø��ڵ�������ӽڵ�
		Component[] coms = cont.getComponents();
		for (int i = 0; i < coms.length; i++) {
			// �����ǰ�ӽڵ㲻��Ҫ�ҵ�������ͣ���ô�����Ƿ�ΪContainer����Ϊ��Container�����ڲ����ܻ����ӽڵ㣬��Ҫ�ݹ����
			if (!(coms[i].getClass().equals(classType))) {
				if (!(coms[i] instanceof Container))
					continue;
				else {
					AbstractButton jb;
					if ((jb = this.getButton((Container) (coms[i]), classType,
							tag)) != null)
						return jb;
				}
			} else {
				// ����ű��иð�ť����Ϊ�գ��򿴿���ǰ���ҵİ�ť�����Ƿ�ҲΪ��
				if (tag.equals("null")) {
					System.out.println("find button text:"
							+ ((AbstractButton) coms[i]).getText());
					if (((AbstractButton) coms[i]).getText() == null) {
						return (AbstractButton) coms[i];
					} else {
						return null;
					}
					// ��֮������ű���Ϊnull���� ��ǰ��ť����Ҳ����Ϊ�գ����������
				} else {
					if (((AbstractButton) coms[i]).getText() == null) {
						return null;
					} else {
						if (((AbstractButton) coms[i]).getText().equals(tag))
							return (AbstractButton) coms[i];
						else
							return null;
					}
				}
			}
		}
		return null;
	}

}
