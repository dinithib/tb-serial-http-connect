import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;


public class OpenDayTB extends JFrame {

	private JPanel contentPane;
	private JTextField txtPort;
	private JLabel lblPort;
	private JLabel lblDeviceid;
	private JTextField txtDeviceid;
	private JLabel lblDataLabel;
	private JTextField txtCo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpenDayTB frame = new OpenDayTB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OpenDayTB() {
		setTitle("TBSerialPublisher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblPort = new JLabel("PC USB Port:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.fill = GridBagConstraints.VERTICAL;
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.anchor = GridBagConstraints.EAST;
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 1;
		contentPane.add(lblPort, gbc_lblPort);
		
		txtPort = new JTextField();
		txtPort.setText("COM8");
		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.insets = new Insets(0, 0, 5, 0);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 2;
		gbc_txtPort.gridy = 1;
		contentPane.add(txtPort, gbc_txtPort);
		txtPort.setColumns(10);
		
		lblDeviceid = new JLabel("TB DeviceId:");
		GridBagConstraints gbc_lblDeviceid = new GridBagConstraints();
		gbc_lblDeviceid.anchor = GridBagConstraints.EAST;
		gbc_lblDeviceid.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeviceid.gridx = 1;
		gbc_lblDeviceid.gridy = 3;
		contentPane.add(lblDeviceid, gbc_lblDeviceid);
		
		txtDeviceid = new JTextField();
		txtDeviceid.setText("525c5570-af78-11e9-a365-2baa29c6edac");
		GridBagConstraints gbc_txtDeviceid = new GridBagConstraints();
		gbc_txtDeviceid.insets = new Insets(0, 0, 5, 0);
		gbc_txtDeviceid.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDeviceid.gridx = 2;
		gbc_txtDeviceid.gridy = 3;
		contentPane.add(txtDeviceid, gbc_txtDeviceid);
		txtDeviceid.setColumns(10);
		
		lblDataLabel = new JLabel("Data Label:");
		GridBagConstraints gbc_lblDataLabel = new GridBagConstraints();
		gbc_lblDataLabel.anchor = GridBagConstraints.EAST;
		gbc_lblDataLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblDataLabel.gridx = 1;
		gbc_lblDataLabel.gridy = 5;
		contentPane.add(lblDataLabel, gbc_lblDataLabel);
		
		txtCo = new JTextField();
		txtCo.setText("CO2");
		GridBagConstraints gbc_txtCo = new GridBagConstraints();
		gbc_txtCo.insets = new Insets(0, 0, 5, 0);
		gbc_txtCo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCo.gridx = 2;
		gbc_txtCo.gridy = 5;
		contentPane.add(txtCo, gbc_txtCo);
		txtCo.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				TelemetryHandler handler = new TelemetryHandler();
				handler.setDeviceId(txtDeviceid.getText());
				
				SerialReader serialreader =  new SerialReader();
				SerialReader.setPort(txtPort.getText());
				serialreader.setDataLabel(txtCo.getText());
				serialreader.setTelemetryHandler(handler);
				serialreader.publishData();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.gridx = 2;
		gbc_btnStart.gridy = 7;
		contentPane.add(btnStart, gbc_btnStart);
	}

}
