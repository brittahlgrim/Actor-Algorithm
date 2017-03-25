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
import java.util.LinkedList;
import java.util.Queue;

import javax.json.*;


public class APIService_Decomissioned {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	private static String search = "http://imdb.wemakesites.net/api/search?q=%s&api_key=%s";
	private static String getById = "http://imdb.wemakesites.net/api/%s?api_key=%s";
	
	/*
	 * Input:
	 * 		name - Name of the actor to search for. The format does not matter, as long as
	 * 				the first name comes before the last name. Ex. Nicolas Cage, nicolascage, nIc OlA sC aGe
	 * 
	 * Output:
	 * 		films - String array containing the IDs of the films the actor has starred in.
	 */
	public static Queue<String> GetMoviesFromActorName(String name) throws JsonException, IOException
	{
		name = name.replaceAll("\\s+","");
		name = name.toLowerCase();
		
		JsonObject searchResult = SearchAPIForString(name);
		String actorId = GetFirstSearchResultId(searchResult);
		JsonObject informationById = GetInformationById(actorId);
		Queue<String> films = GetFilmsFromActorObject(informationById);
		return films;
	}
	
	/*
	 * Input:
	 * 		movieId - ID of the movie to search for.
	 * 
	 * Output:
	 * 		actors - String array containing the names of the actors starring in the film.
	 */
	public static Queue<String> GetActorsFromMovieID(String movieId) throws JsonException, IOException
	{
		JsonObject informationById = GetInformationById(movieId);
		Queue<String> actors = GetActorsFromFilmObject(informationById);
		return actors;
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
	
	public static Queue<String> GetFilmsFromActorObject(JsonObject actorResult)
	{
		String filmUrlPrefix = "http://www.imdb.com/title/";
		
		JsonObject data = actorResult.getJsonObject("data");
		JsonArray filmography = data.getJsonArray("filmography");
		int numberOfFilms = filmography.size();
		Queue<String> films = new LinkedList<String>();;
		//String[] films = new String[numberOfFilms];
		
		for(int i = 0; i < numberOfFilms; i ++)
		{
			JsonObject film = filmography.getJsonObject(i);
			String filmInfo = film.getString("info");
			String filmId = filmInfo.substring(filmUrlPrefix.length()).split("/")[0];
			
			films.add(filmId);
		}

		return films;
	}
	
	public static Queue<String> GetActorsFromFilmObject(JsonObject filmResult)
	{
		JsonObject data = filmResult.getJsonObject("data");
		JsonArray cast = data.getJsonArray("cast");
		int numberOfActors = cast.size();
		Queue<String> actors = new LinkedList<String>();
		
		for(int i = 0; i < numberOfActors; i ++)
		{
			String actorName = cast.getString(i);
			actors.add(actorName);
		}
		return actors;
	}
}
