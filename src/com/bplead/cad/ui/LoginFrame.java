package com.bplead.cad.ui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.util.PropertiesUtils;

public class LoginFrame extends AbstractFrame {

	private static final Logger logger = Logger.getLogger(LoginFrame.class);
	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new LoginFrame().newInstance("Test");
		logger.info("ready...");
	}

	private final String HOST_URL = "host.url";
	private final String HOST_EDITABLE = "host.editable";

	public LoginFrame() {
		super(LoginFrame.class);
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
		// initialize server content
		add(new ServerPanel(this), BorderLayout.CENTER);

		logger.info("initialize option content...");
		// initialize option content
		add(new OptionPanel(this), BorderLayout.SOUTH);

		// validate the whole frame
		logger.info("initialize validate...");
		validate();

		logger.info("initialize completed...");
	}

	class OptionPanel extends AbstractPanel {

		private static final long serialVersionUID = 2106959274498664555L;
		private final String LOGIN_BUTTON_DISPLAY = "login";
		private final String CANCEL_BUTTON_DISPLAY = "cancel";

		public OptionPanel(Container parent) {
			super(parent);
		}

		@Override
		public double getHorizontalProportion() {
			return 0.9d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.2d;
		}

		@Override
		protected void initialize() {
			// ~ add buttons
			JButton login = new JButton(getResourceMap().getString(getResourceName(LOGIN_BUTTON_DISPLAY)));
			add(login);

			JButton cancel = new JButton(getResourceMap().getString(getResourceName(CANCEL_BUTTON_DISPLAY)));
			add(cancel);
		}
	}

	class ServerPanel extends AbstractPanel {

		private static final long serialVersionUID = -5325225997433722990L;
		private final String TITLE = "title";
		private final String HOST_LABEL_DISPLAY = "host";
		private final String USER_LABEL_DISPLAY = "user";
		private final String PWD_LABEL_DISPLAY = "password";
		private final String REMINDER_LABEL_DISPLAY = "reminder";
		private final String REMEBERME_DISPLAY = "remeberme";
		private final double LABEL_PROPORTION = 0.1d;
		private final double TEXT_PROPORTION = 0.6d;
		private final double HEIGHT_PROPORTION = 0.1d;
		private final double HGAP_PROPORTION = 0.02d;
		private final double VGAP_PROPORTION = 0.05d;
		private int hGap;
		private int vGap;
		private PromptTextField.PromptTextFieldDimension dimension;

		public ServerPanel(Container parent) {
			super(parent);
		}

		@Override
		public double getHorizontalProportion() {
			return 0.9d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.7d;
		}

		private String getCachePwd() {
			return "20095000";
		}

		private String getCacheUser() {
			return "20095000";
		}

		private String getHost() {
			return PropertiesUtils.readProperty(HOST_URL);
		}

		private boolean isHostEditable() {
			return Boolean.parseBoolean(PropertiesUtils.readProperty(HOST_EDITABLE));
		}

		private boolean isRemeberme() {
			return true;
		}

		@Override
		protected void initialize() {
			// set box layout
			GroupLayout layout = new GroupLayout(this);
			setLayout(layout);

			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// ~ add components
			JLabel reminder = new JLabel(getResourceMap().getString(getResourceName(REMINDER_LABEL_DISPLAY)));

			dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField host = new PromptTextField(getResourceMap().getString(getResourceName(HOST_LABEL_DISPLAY)),
					getHost(), dimension);
			host.setEditable(isHostEditable());

			PromptTextField user = new PromptTextField(getResourceMap().getString(getResourceName(USER_LABEL_DISPLAY)),
					getCacheUser());

			PromptTextField pwd = new PromptTextField(
					new JLabel(getResourceMap().getString(getResourceName(PWD_LABEL_DISPLAY))),
					new JPasswordField(getCachePwd()), dimension);

			JCheckBox remeberme = new JCheckBox(getResourceMap().getString(getResourceName(REMEBERME_DISPLAY)));
			remeberme.setSelected(isRemeberme());

			// ~ use group layout
			hGap = ((Double) (getPreferredSize().width * HGAP_PROPORTION)).intValue();
			GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
			hGroup.addGap(hGap);
			hGroup.addGroup(layout.createParallelGroup().addComponent(host.getPrompt()).addComponent(user.getPrompt())
					.addComponent(pwd.getPrompt()));
			hGroup.addGap(hGap);
			hGroup.addGroup(layout.createParallelGroup().addComponent(reminder).addComponent(host.getText())
					.addComponent(user.getText()).addComponent(pwd.getText()).addComponent(remeberme));
			hGroup.addGap(hGap);
			layout.setHorizontalGroup(hGroup);

			vGap = ((Double) (getPreferredSize().height * VGAP_PROPORTION)).intValue();
			GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			vGroup.addGap(vGap);
			vGroup.addGroup(layout.createParallelGroup().addComponent(reminder));
			vGroup.addGap(vGap);
			vGroup.addGroup(layout.createParallelGroup().addComponent(host.getPrompt()).addComponent(host.getText()));
			vGroup.addGap(vGap);
			vGroup.addGroup(layout.createParallelGroup().addComponent(user.getPrompt()).addComponent(user.getText()));
			vGroup.addGap(vGap);
			vGroup.addGroup(layout.createParallelGroup().addComponent(pwd.getPrompt()).addComponent(pwd.getText()));
			vGroup.addGap(vGap);
			vGroup.addGroup(layout.createParallelGroup().addComponent(remeberme));
			vGroup.addGap(vGap);
			layout.setVerticalGroup(vGroup);
		}
	}
}
