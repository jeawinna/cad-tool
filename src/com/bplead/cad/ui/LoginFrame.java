package com.bplead.cad.ui;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.layout.DefaultGroupLayout;
import com.bplead.cad.util.PropertiesUtils;

public class LoginFrame extends AbstractFrame {

	private static final Logger logger = Logger.getLogger(LoginFrame.class);
	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new LoginFrame().newInstance(null);
		logger.info("ready...");
	}

	private final String HOST_EDITABLE = "host.editable";
	private final String HOST_URL = "host.url";

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
		add(new ServerPanel(this));

		logger.info("initialize option content...");
		add(new OptionPanel(this));

		logger.info("initialize completed...");
	}

	class OptionPanel extends AbstractPanel {

		private static final long serialVersionUID = 2106959274498664555L;
		private final String CANCEL_BUTTON_DISPLAY = "cancel";
		private final String LOGIN_BUTTON_DISPLAY = "login";

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

		public ServerPanel(Container parent) {
			super(parent);
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
					getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// ~ add components
			JLabel reminder = new JLabel(getResourceMap().getString(getResourceName(REMINDER_LABEL_DISPLAY)));
			reminder.setForeground(Color.RED);

			dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField host = new PromptTextField(getResourceMap().getString(getResourceName(HOST_LABEL_DISPLAY)),
					getHost(), dimension);
			host.setEditable(isHostEditable());

			PromptTextField user = new PromptTextField(getResourceMap().getString(getResourceName(USER_LABEL_DISPLAY)),
					getCacheUser(), dimension);

			PromptTextField pwd = new PromptTextField(
					new JLabel(getResourceMap().getString(getResourceName(PWD_LABEL_DISPLAY))),
					new JPasswordField(getCachePwd()), dimension);

			JCheckBox remeberme = new JCheckBox(getResourceMap().getString(getResourceName(REMEBERME_DISPLAY)));
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
