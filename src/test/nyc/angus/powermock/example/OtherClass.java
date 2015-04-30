package nyc.angus.powermock.example;

import java.net.NetworkInterface;
import java.net.SocketException;

public class OtherClass {
	public boolean isUp(final NetworkInterface netInterface) throws SocketException {
		return netInterface.isUp();
	}
}
