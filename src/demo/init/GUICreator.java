package demo.init;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GUICreator extends Thread{
	
	private MyClassLoader loader;
	private String className;
	
	public GUICreator(MyClassLoader loader, String className){
		this.loader = loader;
		this.className = className;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			Class c = loader.findClass(this.className);
			Method main = c.getMethod("main", String[].class);
//			Class[] pt = main.getParameterTypes();
//			for(Class p:pt){
//				System.out.println(p);
//			}
			String[] args = {new String("ImageTest")};
			main.invoke(null, (Object)args);
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
