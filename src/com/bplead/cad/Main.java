package com.bplead.cad;

import java.awt.EventQueue;

import com.bplead.cad.ui.LoginFrame;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame().activate();
			}
		});
	}
}
