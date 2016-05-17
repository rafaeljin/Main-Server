package edu.thu.rlab.pojo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class DeviceCmd {
	public static enum TYPE {
		ResetExam(0x3f), ResetCpld(0x3a), OpenUsbByAddr(0x37), ResetUsb(0x39), GetRegs(
				0xe2), GetDBStatus(0x35), SetDBStatus(0xe0), SetDataBus(0xe1), SendCycles(
				0xe3), FlipCycles(0xe4), WriteFpgaToFlash(0x32), DownloadFpgaFromFlash(
				0x34), DownloadFpgaFromUsb(0x3c), WriteRam(0x30), ReadRam(0x31), GetDeviceInfo(
				0x40), SetDeviceInfo(0x41);
		private final int code;

		TYPE(int code) {
			this.code = code;
		}

		public byte getCode() {
			return (byte) this.code;
		}
	};

	private TYPE type;

	private static final int BufSize = 1024;

	private byte[] buf = new byte[BufSize];

	private int[] regs = new int[256];
	private int clock;
	private int dataBus;
	private int dataBusMask;
	private byte dbStatus;
	public String fileName;
	private int startAddress;
	private int endAddress;
	private byte usbPort;
	private byte[] ram;
	private String data;
	private byte flashSelector;
	private byte fpgaSelector;
	// return file
	public InputStream is;
	public boolean returnFile;

	public int execute(Device device) {

		int ret = 0;
		try {

			int len = 0;
			if (TYPE.WriteFpgaToFlash.equals(type)
					|| TYPE.DownloadFpgaFromUsb.equals(type)
					|| (TYPE.WriteRam.equals(type) && null != fileName)) {
				is = new FileInputStream(new File(fileName));
				len = is.available();
				startAddress = 0;
				endAddress = len - 1;
			} else if (TYPE.WriteRam.equals(type) && null != data) {
				ram = new byte[len];
				for (int i = 0; i < len; i++) {
				}
				is = new ByteArrayInputStream(ram);
			} else if (TYPE.ReadRam.equals(type)) {
				len = endAddress - startAddress + 1;
			}
			device.write(type.getCode());

			switch (type) {
			case ResetExam:
			case ResetCpld:
			case ResetUsb:
			case GetDBStatus:
			case GetRegs:
			case GetDeviceInfo:
				break;
			case OpenUsbByAddr:
				device.write(usbPort);
				break;
			case SetDBStatus:
				device.write(dbStatus);
				break;
			case SetDataBus:
				device.write(dataBusMask);
				device.write(dataBus);
				break;
			case SendCycles:
			case FlipCycles:
				device.write(clock);
				break;
			case WriteFpgaToFlash:
				device.write(flashSelector);
				device.write(len);
				break;
			case DownloadFpgaFromFlash:
				device.write(flashSelector);
				device.write(fpgaSelector);
				break;
			case DownloadFpgaFromUsb:
				device.write(fpgaSelector);
				device.write(len);
				break;
			case WriteRam:
				device.write(len);
				device.write(startAddress);
				device.write(endAddress);
				break;
			case ReadRam:
				device.write(len);
				device.write(startAddress);
				device.write(endAddress);
				break;
			case SetDeviceInfo:
				break;
			default:
				break;
			}
			device.flush();
			ret = device.read(buf, 0, 1);
			//for stream, 0xff represents end-of-file
			if(ret < 0) {
				buf[0] = (byte) ret;
			}
			ret = buf[0];
			
			switch (type) {
			case GetDBStatus:
				device.read(buf, 1, 1);
				dbStatus = buf[1];
				break;
			case GetDeviceInfo:
				device.read(buf, 1, 2);
				device.setLocation("Box:" + byteToInt(buf[2]) + "Device:"
						+ byteToInt(buf[1]));
				break;
			case WriteFpgaToFlash:
			case DownloadFpgaFromUsb:
			case WriteRam:
				while ((len = is.read(buf)) > 0) {
					device.write(buf, 0, len);
				}
				device.flush();
				ret = device.read(buf, 0, 1);
				if(ret < 0) {
					buf[0] = (byte) ret;
				}
				ret = buf[0];
				break;
			case ReadRam:
				ram = new byte[len];
				ram[0] = buf[0];
				int offset = 1;
				int length;
				while (offset < len
						&& (length = device.read(buf, 0, BufSize)) > 0) {
					System.arraycopy(buf, 0, ram, offset, length);
					offset += length;
				}
				device.write((byte) 0);
				device.flush();
				ret = device.read(buf, 0, 1);
				if(ret < 0) {
					buf[0] = (byte) ret;
				}
				ret = buf[0];
				
				if (returnFile) {
					is = new ByteArrayInputStream(ram);
				}
				break;
			case GetRegs:
				device.read(buf, 0, BufSize);
				for (int i = 0; i < 256; i++) {
					regs[i] = byteToInt(buf[i * 4 + 0])
							+ (byteToInt(buf[i * 4 + 1]) << 8)
							+ (byteToInt(buf[i * 4 + 2]) << 16)
							+ (byteToInt(buf[i * 4 + 3]) << 24);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	private int byteToInt(byte b) {
		return (b + 256) % 256;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public int getDataBus() {
		return dataBus;
	}

	public void setDataBus(int dataBus) {
		this.dataBus = dataBus;
	}

	public void setDataBusMask(int dataBusMask) {
		this.dataBusMask = dataBusMask;
	}

	public int getDataBusMask() {
		return dataBusMask;
	}

	public byte getDbStatus() {
		return dbStatus;
	}

	public void setDbStatus(byte dbStatus) {
		this.dbStatus = dbStatus;
	}

	public int[] getRegs() {
		return regs;
	}

	public void setRam(byte[] ram) {
		this.ram = ram;
	}

	public byte[] getRam() {
		return ram;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}

	public int getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(int endAddress) {
		this.endAddress = endAddress;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setReturnFile(boolean returnFile) {
		this.returnFile = returnFile;
	}

	public void setUsbPort(byte usbPort) {
		this.usbPort = usbPort;
	}

	public void setFpgaSelector(byte fpgaSelector) {
		this.fpgaSelector = fpgaSelector;
	}

	public void setFlashSelector(byte flashSelector) {
		this.flashSelector = flashSelector;
	}

}
