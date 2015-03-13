package com.collaborativeeclipse.im.views;

import com.collaborativeeclipse.im.IMCore;


public class Controler {

	private static Controler controler;

	private String username;

	private String password;

	private Controler() {

	}

	public static Controler getControler() {
		if (controler == null) {
			controler = new Controler();
		}

		return controler;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public void login() {
		if (username == null) {
			return;
		}
		if (username.trim().length() == 0) {
			return;
		}
		if (password == null) {
			return;
		}
		if (password.trim().length() == 0) {
			return;
		}

		IMCore.getCore().login(username, password);
	}

}
