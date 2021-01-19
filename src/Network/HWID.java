package Network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;

public class HWID {
	private static String BytesToHex(byte[] Bytes) {
		StringBuilder SB = new StringBuilder();
		for (byte Byte : Bytes) {
			SB.append(String.format("%02X", Byte)); // %02X --> upper case hex
		}
		return SB.toString();
	}
	public static String GenerateHWID() {
		try {
			InetAddress Address = InetAddress.getLocalHost();
			NetworkInterface Network = NetworkInterface.getByInetAddress(Address);
			if (Network != null) {
				byte[] MacAddress = Network.getHardwareAddress();
				if (MacAddress != null) {
					// SHA-256 hash it
					MessageDigest Digest = MessageDigest.getInstance("SHA-256");
					byte[] Hash = Digest.digest(MacAddress);
					return BytesToHex(Hash);
				}
			}
		} catch (Exception ignore) {}
		return "_ERROR_";
	}
}
