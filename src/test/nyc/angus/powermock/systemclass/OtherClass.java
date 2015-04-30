package nyc.angus.powermock.systemclass;

import java.net.NetworkInterface;
import java.net.SocketException;

public class OtherClass {
	public boolean isUp(final NetworkInterface netInterface) throws SocketException {
		return netInterface.isUp();
	}
}
