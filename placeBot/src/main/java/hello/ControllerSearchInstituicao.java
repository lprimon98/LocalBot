package hello;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.pengrad.telegrambot.model.Update;

public class ControllerSearchInstituicao implements ControllerSearch {

	private View view;

	public ControllerSearchInstituicao(Model model, View view) {
		this.view = view; // connection Controller -> View
	}

	public void search(Update update) {
		view.sendTypingMessage(update);

	}

	@SuppressWarnings({ })
	public void searchAPI(String latlon, String categoria) throws IOException {
		String limitePesquisa = "3";

		String url;

		url = "https://api.foursquare.com/v2/venues/search?ll=" + latlon
				+ "&client_id=WJCBZKPRLMVOR51PALKM3JOUH2EKTW154YHXGGTKLGWLCH01"
				+ "&client_secret=CAKZAZQIPOKGPGZOCNTMMPLBAUBHDS4K5PBBHPCVYLGLMMO2" + "&v=20180609" + "&query="
				+ categoria + "&limit=" + limitePesquisa;

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print in String
			JSONObject myresponse = new JSONObject(response.toString());
			System.out.println(myresponse);
			JSONObject APIresponse = new JSONObject(myresponse.getJSONObject("response").getJSONObject("venues"));
			System.out.println(APIresponse);
			//JSONObject locais = (JSONObject) myresponse.get("venues");
			//Object name = APIresponse.get("name");
			//System.out.println(locais.toString());
			//Instituicao inst = new Instituicao(name, coordenadas)
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
