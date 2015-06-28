package demo.init;

import demo.listener.ActionEventListener;

public class ListenerSetter {

	private static ActionEventListener ael;
	
	public static void initEventListener(){
		ael = ActionEventListener.getInstance();
	}
}
