package Network;

import Network.xxtea.XXTEA;
import Network.mjson.Json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Server {
	private static final String PublicEncryptionKey = "LOBOS-DOODLE-JUMP";
	private static final String NetworkRequestKey = "cmei5830xnal60fn39zbg"; // kind of redundant, but it's fine
	private static final String ClientHWID = HWID.GenerateHWID();
	private static final String BaseURL = "https://apcsa-lobos-dj.azurewebsites.net/db.php";

	private static Json GlobalData;	// {"bob":123,} aka max score of everyone
	private static Json PlayerData; // {"scores":{1,2,3}, highscore:..., more stats}
	private static boolean RegisteredUser = false;

	private static Json Request(Json Object) throws Exception {
		Object.set("hwid", ClientHWID);
		try {
			URL NewURL = new URL(BaseURL + "?key=" + NetworkRequestKey + "&data=" + URLEncoder.encode(XXTEA.encryptToBase64String(Object.toString(), PublicEncryptionKey), StandardCharsets.UTF_8.toString()));
			URLConnection Connection = NewURL.openConnection();
			try (BufferedReader Input = new BufferedReader(new InputStreamReader(Connection.getInputStream()))) {
				String Data = Input.readLine();
				System.out.println(Data);

				// Decrypt
				String DecryptedData = XXTEA.decryptBase64StringToString(Data, ClientHWID);
				System.out.println(DecryptedData);

				// Attempt to decode to JSON
				return Json.read(DecryptedData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static boolean DoesHWIDExist() {
		return ClientHWID != "_ERROR_";
	}

	public static boolean IsExistingUser() {
		return (PlayerData != null && PlayerData.has("exists") && PlayerData.at("exists").asBoolean());
	}

	public static String GetName() {
		return (IsExistingUser() && PlayerData.has("name")) ? PlayerData.at("name").asString() : "";
	}

	public static boolean GetInit() {
		Json JsonRequest = Json.object("type", "get");
		try {
			Json Result = Request(JsonRequest);

			if (!Result.has("playerdata") || !Result.has("globaldata")) {
				return false;
			} else {
				PlayerData = Result.at("playerdata");
				GlobalData = Result.at("globaldata");
			}
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean RegisterName(String DesiredName) {
		Json JsonRequest = Json.object("type", "register", "name", DesiredName);
		try {
			Json Result = Request(JsonRequest);

			if (!Result.has("status")) {
				return false;
			} else {
				if (Result.at("status").asInteger() == 1) {
					PlayerData.set("exists", true);
				} else {
					if (!(Result.has("reason") && Result.at("reason").asString().equals("name taken"))) {
						return false;	// no other errors should happen
					}
				}
			}
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
		return true;
	}
}
