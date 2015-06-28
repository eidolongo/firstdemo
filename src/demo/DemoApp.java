package demo;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

import demo.init.GUICreator;
import demo.init.ListenerSetter;
import demo.script.ScriptReplayer;

 class DemoApp extends JFrame{

	public DemoApp(){
		initialize();
	}
	
	private void startTest(){
		
		String classpath = this.getTestClassPath();
		if( classpath == null || classpath.trim().equals("")){
			JOptionPane.showMessageDialog(this, "classpath不能为空");
			return;
		}
		String classname = this.getClassName();
		if( classname == null || classname.trim().equals("")){
			JOptionPane.showMessageDialog(this, "class不能为空");
			return;
		}
		
		DemoProxy demo = DemoProxy.getInstance();
		demo.setClassName(this.getClassName());
		demo.setTestClassPath(this.getTestClassPath());
		demo.setScriptTextArea(this.jTextArea);
		demo.initBeforeTest();
        
		if(this.getJTextArea().getText().trim().equals("")){
			//如果文本区中无脚本，则重新开始录制脚本
			GUICreator gui = new GUICreator(demo.getLoader(), demo.getClassName());
	        gui.start();
	        ListenerSetter.initEventListener();
		}else{
			//若有脚本，则只进行重放
			System.out.println(DemoProxy.getInstance().getScriptTextArea().getText());
			ScriptReplayer sr = new ScriptReplayer(DemoProxy.getInstance().getScriptTextArea().getText(), 1000);
			sr.init();
			Thread thr = (new Thread(sr));
			thr.start();
//			try {
//				//等待当前线程（此处是EventDispatcher线程）一段时间后，才启动新的线程显示重放界面
//				//为了防止robot在界面出来之前就开始查询界面并操作鼠标，那样会导致异常
//				thr.join(1000);
//				thr.start();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
        
	}
	
	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setText("保存");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser();
					int rVal = jfc.showSaveDialog(DemoApp.this);
					if(rVal == JFileChooser.APPROVE_OPTION){
						saveScriptFile(jfc.getSelectedFile());
					}
				}
			});
		}
		return saveButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        new DemoApp();
	}
	
	private JPanel jPanel = null;

	private JMenuBar jJMenuBar = null;

	private JMenu jMenu = null;

	private JMenuItem jMenuItem = null;

	private JMenu jMenu1 = null;

	private JTextArea jTextArea = null;

	private JPanel jPanel1 = null;

	private JLabel jLabel = null;

	private JTextField classpathTextField = null;

	private JLabel jLabel1 = null;

	private JTextField jTextField1 = null;

	private JButton jButton = null;

	private JPanel jPanel3 = null;

	private JTextField jTextField2 = null;

	private JButton jButton1 = null;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel2 = null;

	private JButton saveButton = null;

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new java.awt.Dimension(531,282));
        this.setJMenuBar(getJJMenuBar());
        this.setContentPane(getJPanel());
        this.setTitle("Demo");
        this.setVisible(true);
			
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJPanel2(), java.awt.BorderLayout.CENTER);
			jPanel.add(getJPanel3(), java.awt.BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.setPreferredSize(new java.awt.Dimension(13,20));
			jJMenuBar.add(getJMenu());
			jJMenuBar.add(getJMenu1());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setName("");
			jMenu.setText("文件");
			jMenu.add(getJMenuItem());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("退出");
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenu1	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("帮助");
		}
		return jMenu1;
	}

	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setPreferredSize(new java.awt.Dimension(60,18));
			jTextArea.setEditable(true);
			jTextArea.setColumns(25);
			jTextArea.setWrapStyleWord(true);
			jTextArea.setLineWrap(true);
			jTextArea.setSize(new java.awt.Dimension(350,307));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints11.ipady = 0;
			gridBagConstraints11.insets = new java.awt.Insets(8,4,0,0);
			gridBagConstraints11.ipadx = 0;
			gridBagConstraints11.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new java.awt.Insets(8,4,0,5);
			gridBagConstraints4.gridy = 4;
			gridBagConstraints4.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridy = 3;
			gridBagConstraints3.ipadx = 15;
			gridBagConstraints3.ipady = 10;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.insets = new java.awt.Insets(0,0,5,0);
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new java.awt.Insets(15,4,0,102);
			gridBagConstraints2.gridy = 2;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.ipadx = 136;
			gridBagConstraints1.ipady = 10;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new java.awt.Insets(0,4,0,77);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			jLabel1 = new JLabel();
			jLabel1.setText("class:");
			jLabel = new JLabel();
			jLabel.setText("classpath:");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setPreferredSize(new java.awt.Dimension(150,94));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GUI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.add(jLabel, gridBagConstraints);
			jPanel1.add(getClasspathTextField(), gridBagConstraints1);
			jPanel1.add(jLabel1, gridBagConstraints2);
			jPanel1.add(getJTextField1(), gridBagConstraints3);
			jPanel1.add(getSaveButton(), gridBagConstraints11);
			jPanel1.add(getJButton(), gridBagConstraints4);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getClasspathTextField() {
		if (classpathTextField == null) {
			classpathTextField = new JTextField();
			classpathTextField.setPreferredSize(new java.awt.Dimension(4,15));
			classpathTextField.setText("H:/");
		}
		return classpathTextField;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(4,15));
			jTextField1.setText("com.demo.jcomponents.ImageTest");
		}
		return jTextField1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("运行");
			jButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					startTest();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
//	private JPanel getJPanel2() {
//		if (jPanel2 == null) {
//			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
//			gridBagConstraints9.fill = java.awt.GridBagConstraints.BOTH;
//			gridBagConstraints9.gridx = -1;
//			gridBagConstraints9.gridy = -1;
//			gridBagConstraints9.weightx = 1.0;
//			gridBagConstraints9.weighty = 1.0;
//			gridBagConstraints9.insets = new java.awt.Insets(5,5,5,5);
//		}
//		return jPanel2;
//	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new BorderLayout());
			jPanel3.setPreferredSize(new java.awt.Dimension(446,30));
			jPanel3.add(getJTextField2(), java.awt.BorderLayout.CENTER);
			jPanel3.add(getJButton1(), java.awt.BorderLayout.EAST);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField2() {
		if (jTextField2 == null) {
			jTextField2 = new JTextField();
			jTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);
			jTextField2.setPreferredSize(new java.awt.Dimension(125,25));
			jTextField2.setEditable(false);
			jTextField2.setColumns(30);
		}
		return jTextField2;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("选择脚本文件");
			
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser jfc = new JFileChooser();
					int rVal = jfc.showOpenDialog(DemoApp.this);
					if(rVal == JFileChooser.APPROVE_OPTION){
						jTextField2.setText(jfc.getSelectedFile().getName());
						String scriptText = readScriptFile(jfc.getSelectedFile());
						getJTextArea().setText(scriptText);
					}
					
				}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jScrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			jScrollPane.setViewportView(getJTextArea());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new BorderLayout());
			jPanel2.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jPanel2.add(getJPanel1(), java.awt.BorderLayout.EAST);
		}
		return jPanel2;
	}
	
	private String readScriptFile(File f){
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuffer sb = new StringBuffer();
			String temp;
			while( (temp=br.readLine())!= null){
				sb.append(temp+"\n");
			}
			br.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void saveScriptFile(File target){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(target));
			bw.write(this.getJTextArea().getText());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	 String getTestClassPath() {
		return this.classpathTextField.getText();
	}

	void setTestClassPath(String testClassPath) {
		this.classpathTextField.setText(testClassPath);
	}

	String getClassName() {
		return this.jTextField1.getText();
	}

	void setClassName(String className) {
		this.jTextField1.setText(className);
	}
}
