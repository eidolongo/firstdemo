package demo.script;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;

public class ScriptWriter {

	private BufferedWriter bw;
	private StringWriter sw;
	private FileWriter fw;
	private JTextArea jta = null;

	/**
	 * @param file
	 * 保存脚本的文件
	 * @param append
	 * 脚本是否附加到文件结尾
	 * @throws IOException
	 */
	public ScriptWriter(File file,boolean append) throws IOException {
		this.fw = new FileWriter(file,append);
		this.bw = new BufferedWriter(fw,20);
	}

	/**
	 * 不指定保存脚本的文件，则自动生成一个缓冲区保存脚本
	 */
	public ScriptWriter() {
		this.sw = new StringWriter();
		this.bw = new BufferedWriter(this.sw);
	}

	/**
	 * 根据鼠标事件生成脚本，写入内部缓冲区或指定的文件，并将脚本内容附加到指定的textarea
	 * @param me
	 * 鼠标事件，用于生成脚本内容
	 * @return
	 * 生成的脚本内容
	 */
	public String writeScript(MouseEvent me) {
		
		String script = this.makeScript(me);
		
		if(this.jta!=null){
			this.jta.append(script+"\r\n");
		}
//		PrintWriter pw = new PrintWriter(this.bw);
//		pw.println(script);
			try {
				
				this.bw.write(script,0,script.length());
				this.bw.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//this.bw.newLine();
//		System.out.println("sw:"+this.sw.toString());
		return script;
	}
	
	public String getAllScript(){
		if(this.sw != null)
			return this.sw.toString();
		else return null;
	}
	
	public void setTextArea(JTextArea area){
		this.jta  = area;
	}
	
	public void finishWriting(){
		try {
			System.out.println("finish script");
			this.bw.flush();
//			this.bw.close();
//			this.fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String makeScript(MouseEvent me){
		int x= me.getX();
		int y = me.getY();
		Object source = me.getSource();
		
		String script = "class="+source.getClass().getName()+" x="+x+" y="+y;
		
		if(source instanceof javax.swing.AbstractButton){
			script = script+(" tag="+((AbstractButton)source).getText());
			script = script+" superclass=javax.swing.AbstractButton";
		}
		else if(source instanceof javax.swing.JFrame){
			script=script+(" tag="+((javax.swing.JFrame)source).getTitle());
			script=script+" superclass=javax.swing.JFrame";
		}
		script = script+" hash="+source.hashCode();
		
		return script;
	}
}
