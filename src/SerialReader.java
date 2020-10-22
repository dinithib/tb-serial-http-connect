


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import org.json.JSONObject;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialReader implements SerialPortEventListener {

	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static String PORT_NAMES[] = {"COM8"};

	private BufferedReader input;
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	float devicedata; 
	private String dataLabel;

	private TelemetryHandler handler;
	
	public void setDataLabel(String dataLabel){
		this.dataLabel =  dataLabel;
	}
	
	public static void setPort(String port){
		PORT_NAMES[0] =  port;
	}
	
	public void setTelemetryHandler(TelemetryHandler handler){
		this.handler =  handler;
	}
	
	public TelemetryHandler getTelemetryHandler( ){
		return  this.handler;
	}
	
	public void publishData() {

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();


		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}


	public void serialEvent(SerialPortEvent oEvent) {
		
		handler.initialize();
		
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				if(!inputLine.isEmpty()){
					devicedata = Float.parseFloat(inputLine);
					
					JSONObject obj =  new JSONObject();
					obj.accumulate(dataLabel, devicedata);
					
					handler.sendTelemetry(obj.toString());
					
				}
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}

	}


}
