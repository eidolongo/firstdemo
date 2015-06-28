package demo.init;

import static java.lang.System.out;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends URLClassLoader{
	
	
	
	public MyClassLoader(String jarPath){
		super(new URL[0],ClassLoader.getSystemClassLoader());
		File jarFile = new File(jarPath);
		this.addUrl(jarFile);
	}
	
	public MyClassLoader(URL[] classUrl){
		super(classUrl,ClassLoader.getSystemClassLoader());
	}

	public void addUrl( String filepath){
		this.addUrl(new File(filepath));
	}
	
	public void addUrl( File file){
		try {
			this.addUrl(file.toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	public void addUrl( URL url){
		super.addURL(url);
	}
	
	/* 
	 * @para
	 * name必须为类的完整路径，如java.lang.Object
	 * @see java.net.URLClassLoader#findClass(java.lang.String)
	 */
	public Class findClass(String name) throws ClassNotFoundException{
		Class c = this.findLoadedClass(name);
		if(c == null)
			return super.findClass(name);
		else
			return c;
	}
}
