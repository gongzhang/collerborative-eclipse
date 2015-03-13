package com.collaborativeeclipse.im.views;

import org.eclipse.swt.widgets.Label;

public class LabelInfo {

	private String userInfo = "";

	private String projectInfo = "";

	private String itemInfo = "";

	private String loginUser = "";

	private Label label;

	public static LabelInfo labelInfo;

	private LabelInfo() {
	}

	public static LabelInfo getLabelInfo() {
		if (labelInfo == null) {
			labelInfo = new LabelInfo();
		}

		return labelInfo;
	}

	public void setLabel(Label label) {
		this.label = label;

		update();
	}

	public void update() {
		if (label == null) {
			return;
		}

		StringBuffer buffer = new StringBuffer("");
		buffer.append("project:" + projectInfo + "\n");
		buffer.append("item:" + itemInfo + "\n");
		if (loginUser != null && loginUser.trim().length() > 0) {
			buffer.append("login as:" + loginUser + "\n");
		}
		if (userInfo != null && userInfo.trim().length() > 0) {
			buffer.append("user:" + userInfo + "\n");
		}

		label.setText(buffer.toString());
		label.update();
	}

	public void setUser(String newUser) {
		this.userInfo = newUser;

		update();
	}

	public String getUser() {
		return userInfo;
	}

	public void setProject(String newProject) {
		this.projectInfo = newProject;

		update();
	}

	public String getProject() {
		return projectInfo;
	}

	public void setItem(String item) {
		this.itemInfo = item;

		update();
	}

	public String getItem() {
		return itemInfo;
	}

	public void setLoginUser(String user) {
		this.loginUser = user;

		update();

	}
}
