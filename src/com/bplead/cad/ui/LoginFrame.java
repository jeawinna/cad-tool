package com.bplead.cad.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.util.PropertiesUtils;

public class LoginFrame extends AbstractFrame {

	class OptionPanel extends AbstractPanel {

		private static final long serialVersionUID = 2106959274498664555L;
		private final String LOGIN_BUTTON_DISPLAY = "login";
		private final String CANCEL_BUTTON_DISPLAY = "cancel";

		@Override
		protected void initComponents() {
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
		private PromptTextField.PromptTextFieldDimension dimension = new PromptTextField().new PromptTextFieldDimension(
				55, 350, 25);

		private String getCachePwd() {
			return "20095000";
		}

		private String getCacheUser() {
			return "20095000";
		}

		private String getHost() {
			return PropertiesUtils.readProperty(HOST_URL);
		}

		@Override
		protected void initComponents() {
			// set box layout
			GroupLayout layout = new GroupLayout(this);
			setLayout(layout);

			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// set preferred size
			setPreferredSize(new Dimension(500, 200));

			// ~ add components
			JLabel reminder = new JLabel(getResourceMap().getString(getResourceName(REMINDER_LABEL_DISPLAY)));

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
			GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
			hGroup.addGap(5);
			hGroup.addGroup(layout.createParallelGroup().addComponent(host.getPrompt()).addComponent(user.getPrompt())
					.addComponent(pwd.getPrompt()));
			hGroup.addGap(5);
			hGroup.addGroup(layout.createParallelGroup().addComponent(reminder).addComponent(host.getText())
					.addComponent(user.getText()).addComponent(pwd.getText()).addComponent(remeberme));
			hGroup.addGap(5);
			layout.setHorizontalGroup(hGroup);

			GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
			vGroup.addGap(10);
			vGroup.addGroup(layout.createParallelGroup().addComponent(reminder));
			vGroup.addGap(10);
			vGroup.addGroup(layout.createParallelGroup().addComponent(host.getPrompt()).addComponent(host.getText()));
			vGroup.addGap(10);
			vGroup.addGroup(layout.createParallelGroup().addComponent(user.getPrompt()).addComponent(user.getText()));
			vGroup.addGap(10);
			vGroup.addGroup(layout.createParallelGroup().addComponent(pwd.getPrompt()).addComponent(pwd.getText()));
			vGroup.addGap(10);
			vGroup.addGroup(layout.createParallelGroup().addComponent(remeberme));
			vGroup.addGap(10);
			layout.setVerticalGroup(vGroup);
		}

		private boolean isRemeberme() {
			return true;
		}

		private boolean isHostEditable() {
			return Boolean.parseBoolean(PropertiesUtils.readProperty(HOST_EDITABLE));
		}
	}

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

	private void initialize(String title) {
		setVisible(true);
		setTitle(title);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(new Dimension(550, 300));
	}

	@Override
	public void newInstance(String title) {
		logger.info("initialize frame...");
		// initialize frame
		initialize(title);

		logger.info("initialize server content...");
		// initialize server content
		add(new ServerPanel(), BorderLayout.CENTER);

		logger.info("initialize option content...");
		// initialize option content
		add(new OptionPanel(), BorderLayout.SOUTH);

		// validate the whole frame
		logger.info("initialize validate...");
		validate();

		logger.info("initialize completed...");
	}
}
