package com.bplead.cad.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.layout.DefaultGroupLayout;
import com.bplead.cad.util.PropertiesUtils;

public class LoginFrame extends AbstractFrame implements ActionListener {

	private static final Logger logger = Logger.getLogger(LoginFrame.class);
	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new LoginFrame().newInstance(null);
		logger.info("ready...");
	}

	private final static String HOST_EDITABLE = "host.editable";
	private final static String HOST_URL = "host.url";
	private final String LOGIN_BUTTON_DISPLAY = "login";

	public LoginFrame() {
		super(LoginFrame.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public double getHorizontalProportion() {
		return 0.35d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.35d;
	}

	@Override
	public void initialize() {
		logger.info("initialize server content...");
		add(ServerPanel.newInstance(this));

		logger.info("initialize option content...");
		Option login = Option.newInstance(LOGIN_BUTTON_DISPLAY, null, this);
		add(OptionPanel.newInstance(this, Arrays.asList(login, Option.newCancelOption(this))));

		logger.info("initialize completed...");
	}

	static class ServerPanel extends AbstractPanel {

		private static final long serialVersionUID = -5325225997433722990L;
		private PromptTextField.PromptTextFieldDimension dimension;
		private final double HEIGHT_PROPORTION = 0.1d;
		private int hGap;
		private final double HGAP_PROPORTION = 0.02d;
		private final String HOST_LABEL_DISPLAY = "host";
		private final double LABEL_PROPORTION = 0.1d;
		private final String PWD_LABEL_DISPLAY = "password";
		private final String REMEBERME_DISPLAY = "remeberme";
		private final String REMINDER_LABEL_DISPLAY = "reminder";
		private final double TEXT_PROPORTION = 0.8d;
		private final String TITLE = "title";
		private final String USER_LABEL_DISPLAY = "user";
		private int vGap;
		private final double VGAP_PROPORTION = 0d;

		public static ServerPanel newInstance(Container parent) {
			ServerPanel panel = initialize(ServerPanel.class);
			panel.initialize(parent);
			return panel;
		}

		private String getCachePwd() {
			return "20095000";
		}

		private String getCacheUser() {
			return "20095000";
		}

		@Override
		public double getHorizontalProportion() {
			return 0.9d;
		}

		private String getHost() {
			return PropertiesUtils.readProperty(HOST_URL);
		}

		@Override
		public double getVerticalProportion() {
			return 0.7d;
		}

		@Override
		protected void initialize() {
			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(TITLE), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// ~ add components
			JLabel reminder = new JLabel(getResourceMap().getString(REMINDER_LABEL_DISPLAY));
			reminder.setForeground(Color.RED);

			dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField host = new PromptTextField(getResourceMap().getString(HOST_LABEL_DISPLAY), getHost(),
					dimension);
			host.setEditable(isHostEditable());

			PromptTextField user = new PromptTextField(getResourceMap().getString(USER_LABEL_DISPLAY), getCacheUser(),
					dimension);

			PromptTextField pwd = new PromptTextField(new JLabel(getResourceMap().getString(PWD_LABEL_DISPLAY)),
					new JPasswordField(getCachePwd()), dimension);

			JCheckBox remeberme = new JCheckBox(getResourceMap().getString(REMEBERME_DISPLAY));
			remeberme.setSelected(isRemeberme());

			// ~ performance hGap and vGap
			hGap = ((Double) (getPreferredSize().width * HGAP_PROPORTION)).intValue();
			vGap = ((Double) (getPreferredSize().height * VGAP_PROPORTION)).intValue();
			logger.debug("hGap:" + hGap + ",vGap:" + vGap);

			logger.info("use default group layout...");
			// ~ use default group layout
			DefaultGroupLayout layout = new DefaultGroupLayout(this, hGap, vGap);
			layout.addComponent(reminder).addComponent(host).addComponent(user).addComponent(pwd)
					.addComponent(remeberme).layout();
		}

		private boolean isHostEditable() {
			return Boolean.parseBoolean(PropertiesUtils.readProperty(HOST_EDITABLE));
		}

		private boolean isRemeberme() {
			return true;
		}
	}
}
