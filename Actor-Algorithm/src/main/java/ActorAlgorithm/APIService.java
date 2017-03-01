package main.java.ActorAlgorithm;

/*
 * Most of the following code is taken from this Stack Overflow post:
 * http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.json.*;


public class APIService {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	private static String search = "http://imdb.wemakesites.net/api/search?q=%s&api_key=%s";
	private static String getById = "http://imdb.wemakesites.net/api/%s?api_key=%s";
	
	public static String[] GetActorMoviesFromName(String name) throws JsonException, IOException
	{
		JsonObject searchResult = SearchAPIForString(name);
		String actorId = GetFirstSearchResultId(searchResult);
		JsonObject informationById = GetInformationById(actorId);
		String[] films = GetFilmsFromActorObject(informationById);
		return films;
	}
	
	private static JsonObject readJsonFromUrl(String uri) throws IOException, JsonException {
		InputStream is = new URL(uri).openStream();
		try {
			JsonReaderFactory factory = Json.createReaderFactory(null);
			JsonReader reader1 = factory.createReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			JsonObject json = reader1.readObject();

			return json;
		} finally {
			//connection.disconnect();
			is.close();
		}
	}

	private static JsonObject SearchAPIForString(String query) throws JsonException, IOException
	{
		String uri =String.format(search, query, apikey);
		JsonObject result = readJsonFromUrl(uri.toString());
		return result;
	}
	
	private static String GetFirstSearchResultId(JsonObject searchResult)
	{
		JsonObject data = searchResult.getJsonObject("data");
		JsonObject results = data.getJsonObject("results");
		JsonArray names = results.getJsonArray("names");
		JsonObject firstResult = names.getJsonObject(0);
		String id = firstResult.getString("id");
		
		return id;	
	}

	public static JsonObject GetInformationById(String id) throws JsonException, IOException
	{
		String uri =String.format(getById, id, apikey);
		JsonObject result = readJsonFromUrl(uri.toString());
		return result;
	}
	
	public static String[] GetFilmsFromActorObject(JsonObject actorResult)
	{
		String filmUrlPrefix = "http://www.imdb.com/title/";
		
		JsonObject data = actorResult.getJsonObject("data");
		JsonArray filmography = data.getJsonArray("filmography");
		int numberOfFilms = filmography.size();
		String[] films = new String[numberOfFilms];
		
		for(int i = 0; i < numberOfFilms; i ++)
		{
			JsonObject film = filmography.getJsonObject(i);
			String filmInfo = film.getString("info");
			String filmId = filmInfo.substring(filmUrlPrefix.length()).split("/")[0];
			
			films[i] = filmId;
		}

		return films;
	}
}
