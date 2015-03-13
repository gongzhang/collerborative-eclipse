package com.collaborativeeclipse.im.views;

import org.eclipse.swt.widgets.Text;

public class Console {

	private static Text console;

	public static void print(Object o) {
		if (console != null) {
			console.append(o.toString());
		}
	}
	
	public static void println(Object o){
		if(console!=null){
			console.append(o.toString());
			console.append("\n");
		}
	}

	public static void setConsole(Text newConsole) {
		console = newConsole;
	}
}
