package com.bplead.cad.ui;

import com.bplead.cad.bean.client.Temporary;
import com.bplead.cad.util.ClientUtils;

import priv.lee.cad.model.ServerClientTemporary;
import priv.lee.cad.ui.AbstractLoginFrame;
import priv.lee.cad.util.Assert;

public class LoginFrame extends AbstractLoginFrame {

	public static void main(String[] args) {
		new LoginFrame().activate();
	}

	public LoginFrame() {
		super(LoginFrame.class, Temporary.class);
	}

	private static final long serialVersionUID = 2220682440023001808L;

	@Override
	public void startNextFrame() {
		new CADMainFrame().activate();
	}

	@Override
	public ServerClientTemporary toTemporary() {
		Assert.hasText(server.host.getTextContent(), LoginFrame.class.getName() + "_1");
		Assert.hasText(server.user.getTextContent(), LoginFrame.class.getName() + "_2");
		Assert.hasText(server.pwd.getTextContent(), LoginFrame.class.getName() + "_3");

		Temporary temporary = new Temporary(server.host.getTextContent(), server.user.getTextContent(),
				server.pwd.getTextContent(), server.remeberme.isSelected());
		ClientUtils.temprary = temporary;
		return temporary;
	}
}
