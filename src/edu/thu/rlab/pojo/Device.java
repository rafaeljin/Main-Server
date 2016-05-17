package edu.thu.rlab.pojo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Device implements Runnable{

	
	public static enum STATE{USING, AVAILABLE, BROKEN};
	
	private String id;		//ip + port
	
	private int usbPort;
	
	private String ip;  //usb interface address
	
	private int tcpPort;		//allocate by usb interface = tcpportbase + usbPort;
	
	private String location;  //box information
	
	private long lastHeartBeatTime;
	
	public int timeoutCount; //when it increase to some value ,delete the device

	
	private STATE state;
	
	private User user;		//when state = using;
	
	private long lastOpertaionTime;
	private long startUseTime;
	
	private Socket socket;
	
	private BufferedOutputStream out;
	private BufferedInputStream in;
	
	private byte[] buf = new byte[1024];
	
	public Device(String id, String ip, int usbPort, int tcpPort, STATE state) {
		super();
		this.id = id;
		this.ip = ip;
		this.usbPort = usbPort;
		this.tcpPort = tcpPort;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsbPort(int usbPort) {
		this.usbPort = usbPort;
	}

	public int getUsbPort() {
		return usbPort;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getLastHeartBeatTime() {
		return lastHeartBeatTime;
	}

	public void setLastHeartBeatTime(long lastHeartBeatTime) {
		this.lastHeartBeatTime = lastHeartBeatTime;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLastOpertaionTime(long lastOpertaionTime) {
		this.lastOpertaionTime = lastOpertaionTime;
	}

	public long getLastOpertaionTime() {
		return lastOpertaionTime;
	}

	public void setStartUseTime(long startUseTime) {
		this.startUseTime = startUseTime;
	}

	public long getStartUseTime() {
		return startUseTime;
	}

	public int connect(){
		try{
			socket = new Socket(this.getIp(), this.getTcpPort());
			out = new BufferedOutputStream(socket.getOutputStream());
			in = new BufferedInputStream(socket.getInputStream());
		}catch(Exception e){
			return -1;
		}
		return 0;
	}
	public int disconnect(){
		try{
			out.close();
			in.close();
			socket.close();
			out = null;
			in = null;
			socket = null;
		}catch(Exception e){
			return -1;
		}
		return 0;
	}
	public int sysInit(){
		int ret;
		DeviceCmd dc = new DeviceCmd();
		dc.setType(DeviceCmd.TYPE.OpenUsbByAddr);
		dc.setUsbPort((byte)usbPort);
		ret = dc.execute(this);
		if(ret < 0) {
			return -1;
		}
		
		dc.setType(DeviceCmd.TYPE.ResetUsb);
		ret = dc.execute(this);
		if(ret < 0) {
			return -1;
		}
		
		//should download fpga first after change code in get deviceinfo so call userInit
		if (this.userInit() < 0){
			return -1;
		}
		dc.setType(DeviceCmd.TYPE.GetDeviceInfo);
		ret = dc.execute(this);
		if(ret < 0) {
			return -1;
		}
		return 0;
	}
	public int userInit(){
		int ret;
		DeviceCmd dc = new DeviceCmd();
		dc.setType(DeviceCmd.TYPE.DownloadFpgaFromFlash);
		dc.setFlashSelector((byte)0);
		dc.setFpgaSelector((byte)0);
		ret = dc.execute(this);
		if(ret < 0) {
			return -1;
		}
		return 0;
	}
	
	public int update(){
		DeviceCmd dc = new DeviceCmd();
		dc.setType(DeviceCmd.TYPE.GetDeviceInfo);
		dc.execute(this);
		return 0;
	}
	public void write(byte b) throws IOException{
		buf[0] = b;
		out.write(buf, 0, 1);
	}
	public void write(int i) throws IOException{
		buf[0] = (byte)i;
		buf[1] = (byte)(i>>8);
		buf[2] = (byte)(i>>16);
		buf[3] = (byte)(i>>24);
		out.write(buf, 0, 4);
	}
	public void write(byte[] buf, int offSet, int len) throws IOException{
		out.write(buf, offSet, len);
	}
	public void flush() throws IOException{
		out.flush();
	}
	public int read(byte[] buf, int offSet, int len) throws IOException{
		return in.read(buf, offSet, len);
	}

	public void run() {
	}
}
