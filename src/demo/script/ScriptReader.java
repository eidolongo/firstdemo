package demo.script;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class ScriptReader {

	private BufferedReader br;
	
	
	private String regx = " ";
	
	public ScriptReader(String scripts){
		this.br = new BufferedReader(new StringReader(scripts));
	}
	
	public ScriptReader(File scriptFile) throws FileNotFoundException{
		this.br = new BufferedReader(new FileReader(scriptFile));
	}
	
	public HashMap<String,String> readScript(){
		try {
			HashMap<String,String> script = new HashMap<String,String>();
			String item = br.readLine();
			if(item==null)
				return null;
			
			String[] keyValue = item.split(this.regx);
			for(String s:keyValue){
				int i = s.indexOf('=');
				if(i<0)continue;
				script.put(s.substring(0, i), s.substring(i+1));
			}
			return script;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
