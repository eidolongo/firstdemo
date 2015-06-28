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
	 * 根据得到的脚本元素，分析其中的内容，得到需要测试的组件信息，并执行，这里仅限于鼠标事件
	 * 
	 * @param scriptItem
	 *            脚本元素，也即脚本文件中的一行，代表一个用户动作
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
	 * 这是查找组件的入口方法
	 * 
	 * @param tag
	 *            作为待查找组件的“特征值”
	 * @param className
	 *            待查找组件的类型
	 * @param superClass
	 *            待查找组件的父类
	 * @return
	 * 找到的组件，找不到则返回null
	 */
	private Component findComponent(String tag, String className,
			String superClass) {
		Frame[] jframes = (Frame[]) JFrame.getFrames();
		Component button;
		for (Frame temp : jframes) {
			// getFrames方法会返回所有frame，包括不在屏幕上的，因此必须判断frame是否在屏幕上了
			// 否则获得组件的屏幕坐标时会出错
			if (!temp.isValid() || !temp.isShowing())
				continue;
			// 根据父类，调用相应的查找方法
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
				// 如果都不符合定义的父类，那么返回null
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
	 * 实际实现查找方法的类，采用递归的方法，以cont参数为根节点搜索整个分支，返回符合text参数的按钮
	 * 
	 * @param cont
	 *            开始搜索的根节点
	 * @param tag
	 *            用于判断是否为要查找的按钮组件
	 * @return
	 */
	private AbstractButton getButton(Container cont, Class classType, String tag) {
		// 获得根节点的所有子节点
		Component[] coms = cont.getComponents();
		for (int i = 0; i < coms.length; i++) {
			// 如果当前子节点不是要找的组件类型，那么看看是否为Container，因为是Container则它内部可能还有子节点，需要递归查找
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
				// 如果脚本中该按钮文字为空，则看看当前查找的按钮文字是否也为空
				if (tag.equals("null")) {
					System.out.println("find button text:"
							+ ((AbstractButton) coms[i]).getText());
					if (((AbstractButton) coms[i]).getText() == null) {
						return (AbstractButton) coms[i];
					} else {
						return null;
					}
					// 反之，如果脚本不为null，则 当前按钮文字也不能为空，且两者相等
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
