package com.bplead.cad.model;

import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;

import javax.swing.JMenuBar;

public interface StyleToolkit {

	public Font getFont();

	public Rectangle getScreenSize(GraphicsConfiguration configs);

	public JMenuBar getStandardMenuBar();

}
