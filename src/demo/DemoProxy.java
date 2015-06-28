package demo;


import javax.swing.JTextArea;

import demo.init.GUICreator;
import demo.init.ListenerSetter;
import demo.init.MyClassLoader;


public class DemoProxy{

	private MyClassLoader loader;
	
	private static DemoProxy demo = new DemoProxy();
	private String testClassPath = null;
	private String className = null;
	private JTextArea jta=null;
	
	//"com.demo.jcomponents.ImageTest"

	
	
	private DemoProxy(){
		
	}
		
	
	 void initBeforeTest(){
		this.loader = new MyClassLoader(demo.getTestClassPath());
	}
	
	public static DemoProxy getInstance(){
		return demo;
	}
	
	

	public String getTestClassPath() {
		return this.testClassPath;
	}

	protected void setTestClassPath(String testClassPath) {
		this.testClassPath = testClassPath;
	}

	public String getClassName() {
		return this.className;
	}

	protected void setClassName(String className) {
		this.className = className;
	}

	public MyClassLoader getLoader() {
		return loader;
	}
	
	public JTextArea getScriptTextArea(){
		return this.jta;
	}
	
	protected void setScriptTextArea(JTextArea jta){
		this.jta = jta;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
