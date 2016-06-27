package edu.thu.rlab.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import edu.thu.rlab.pojo.Device;
import edu.thu.rlab.pojo.DeviceCmd;
import edu.thu.rlab.pojo.User;

public class DeviceDAO extends TimerTask {
	
	private Random random;  //randomly select a device to allocate

	private int tcpPortBase;

	private long deviceHeartBeatPeriod;// when timeout,device state will be set
	// to offline
	private long deleteOfflinePeriod; // delete offline device per
	// deleteOfflinePeriod

	private Map<String, Device> devicePool = new HashMap<String, Device>(0);


	private byte[] lock = new byte[0]; // for synchronize

	private Timer timer; // delete offline device timer

	public void setTcpPortBase(int tcpPortBase) {
		this.tcpPortBase = tcpPortBase;
	}

	public void setDeviceHeartBeatPeriod(long deviceHeartBeatPeriod) {
		this.deviceHeartBeatPeriod = deviceHeartBeatPeriod;
	}

	public void setDeleteOfflinePeriod(long deleteOfflinePeriod) {
		this.deleteOfflinePeriod = deleteOfflinePeriod;
	}

	public void init() {
		random = new Random();
		timer = new Timer();
		timer.schedule(this, 0, this.deleteOfflinePeriod);
	}

	public void updateDevicePool(String ip, byte[] deviceUSBPortArray, int count) {
		String deviceId;
		int tcpPort, usbPort;
		Device device;
		for (int i = 0; i < count; i++) {
			usbPort = deviceUSBPortArray[i];
			tcpPort = usbPort + tcpPortBase;
			deviceId = ip + ":" + tcpPort;
			device = devicePool.get(deviceId);
			
			if (null != device) {
				synchronized (lock) {
					device.setLastHeartBeatTime(System.currentTimeMillis());
					device.timeoutCount = 0;
				}
			} else {
				device = new Device(deviceId, ip, usbPort, tcpPort,
						Device.STATE.AVAILABLE, this);		
				device.connect();
				(new Thread(device)).start();
				synchronized (lock) {
					devicePool.put(deviceId, device);
				}
			}

		}
	}
	public void updateLastOperationTime(Device device){
		synchronized(lock){
			device.setLastOpertaionTime(System.currentTimeMillis());
		}
	}
	
	
	//just for synchronize ,invoked by thread of Device(the function run)
	public void afterDeviceSysInitFailed(Device device){
		synchronized (lock) {
			device.setState(Device.STATE.BROKEN);
			device.disconnect();
		}
	}

	public void execute(String deviceId, DeviceCmd deviceCmd){
		Device device;
		if(deviceId != null){
			device = this.devicePool.get(deviceId);
			deviceCmd.execute(device);
		}else{
			synchronized (lock) {
				Iterator<?> it = this.devicePool.values().iterator();
				while (it.hasNext()) {
					device = (Device) it.next();
					if(!Device.STATE.BROKEN.equals(device.getState())) {
						deviceCmd.execute(device);
					}
				}
			}
		}
	}

	public JSONArray findAll() {
		Device d;
		JSONArray da = new JSONArray();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		synchronized (lock) {
			Iterator<?> it = this.devicePool.values().iterator();
			while (it.hasNext()) {
				d = (Device) it.next();
				JSONObject dObj = new JSONObject();
				dObj.put("id", d.getId());
				dObj.put("ip", d.getIp().toString());
				dObj.put("port", d.getTcpPort());
				dObj.put("location", d.getLocation());
				date.setTime(d.getLastHeartBeatTime());
				dObj.put("lastHeartBeatTime", sdf.format(date));
				dObj.put("state", d.getState());
				if (d.getState().equals(Device.STATE.USING)) {
					dObj.put("userip", d.getUser().getLastLoginIp());
					dObj.put("username", d.getUser().getName());
					date.setTime(d.getStartUseTime());
					dObj.put("startUseTime", sdf.format(date));
					date.setTime(d.getLastOpertaionTime());
					dObj.put("lastOpTime", sdf.format(date));
				}
				da.add(dObj);
			}
		}
		return da;
	}

	public Device allocate(User user) {
		Device device = null;
		ArrayList<Device> allocatable = new ArrayList<Device>();
		synchronized (lock) {
			Collection<Device> devices = this.devicePool.values();
			Iterator<Device> it = devices.iterator();
			while (it.hasNext()) {
				device = it.next();
				if (Device.STATE.AVAILABLE.equals(device.getState()) && device.timeoutCount == 0) {
					allocatable.add(device);
				}
			}
			if(allocatable.size() > 0){
				device = allocatable.get(random.nextInt(allocatable.size()));
				device.setState(Device.STATE.USING);
				device.setUser(user);
				device.setStartUseTime(System.currentTimeMillis());
				device.setLastOpertaionTime(System.currentTimeMillis());
			}
		}
		return device;
	}

	public void free(Device device) {
		synchronized (lock) {
			device.setState(Device.STATE.AVAILABLE);
		}
	}
	
	@Override
	public void run() {
		synchronized (lock) {
			Device device;
			long current = System.currentTimeMillis();
			Collection<Device> devices = this.devicePool.values();
			Iterator it = devices.iterator();
			Set<String> toDelete = new HashSet(0);
			while (it.hasNext()) {
				device = (Device) it.next();

				if((current - device.getLastHeartBeatTime()) > deviceHeartBeatPeriod){
					device.timeoutCount ++;
					device.setLastHeartBeatTime(current);
					if(device.timeoutCount > 5) {
						toDelete.add(device.getId());
					}
				}
			}
			it = toDelete.iterator();
			while (it.hasNext()) {
				String str = (String) it.next();
				device = this.devicePool.remove(str);
				device = null;
			}
		}
	}
}
