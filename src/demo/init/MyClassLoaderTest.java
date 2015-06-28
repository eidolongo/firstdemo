package demo.init;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JFrame;

import demo.DemoProxy;

import junit.framework.TestCase;

public class MyClassLoaderTest extends TestCase {
	
	private String jarPath;
	private MyClassLoader loader;

	protected void setUp() throws Exception {
		super.setUp();
		this.jarPath = "G:\\安装文件\\编程\\JAVA\\Junit\\junit-4.4.jar";
		this.loader = new MyClassLoader("H:/");
	}


	public void testAddUrlString() {
		this.loader.addUrl("D:\\Tomcat 5.5\\webapps\\stest\\WEB-INF\\classes\\");
		
	}

	public void testFindClassString() {
		try {
//			super.assertNotNull(this.loader.findClass("ForwardServlet"));
			
			ListenerSetter.initEventListener();
			
			Class c = this.loader.findClass("com.demo.jcomponents.ImageTest");
			
//			Class c = DemoFramework.class;
			
			Method main = c.getMethod("main", String[].class);
			Class[] pt = main.getParameterTypes();
			for(Class p:pt){
				System.out.println(p);
			}
			String[] args = {new String("ImageTest")};
			main.invoke(null, (Object)args);
			super.assertNotNull(c);
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
