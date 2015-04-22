import java.io.*;
import java.util.*;
import javax.comm.*;

public class ComPortWriter {
	private Enumeration portList;
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private OutputStream outputStream;
    
    public ComPortWriter() {
    	portList = CommPortIdentifier.getPortIdentifiers();
    	
    	while (portList.hasMoreElements()) {
    		portId = (CommPortIdentifier) portList.nextElement();
    		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
    			System.out.println("****************We're in the first if");
    			System.out.println(portId.getName());
    			if (portId.getName().equals("COM3")) {
    				System.out.println("****************We're in the second if");
    				try {
                        serialPort = (SerialPort)
                            portId.open("ComPortWriter", 2000);
                    } catch (PortInUseException e) {
                    	e.printStackTrace();
                    }
    				
    				try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
    				
    				if(outputStream == null) {
    					System.out.println("**************outputStream is null");
    				}
    				
    				try {
                        serialPort.setSerialPortParams(115200,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                    	e.printStackTrace();
                    }
    			}
    		}
    	}    	
    }
    
    public ComPortWriter(String portNumber) {
    	portList = CommPortIdentifier.getPortIdentifiers();
    	
    	while (portList.hasMoreElements()) {
    		portId = (CommPortIdentifier) portList.nextElement();
    		if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
    			if (portId.getName().equals(portNumber)) {
    				try {
                        serialPort = (SerialPort)
                            portId.open("ComPortWriter", 2000);
                    } catch (PortInUseException e) {
                    	e.printStackTrace();
                    }
    				
    				try {
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                    	e.printStackTrace();
                    }
    				
    				if(outputStream == null) {
    					System.out.println("**************outputStream is null");
    				}
    				
    				try {
                        serialPort.setSerialPortParams(115200,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                    	e.printStackTrace();
                    }
    			}
    		}
    	}
    }
    
    public void sendMessage(String character) {
    	try {
            //outputStream.write(character.getBytes());
    		String test = "Hello World";
    		outputStream.write(test.getBytes());
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

}
