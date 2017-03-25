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


public class APIService {
	static String apikey = "9d270680-755b-40d3-ac4d-c57394b2c077";
	private static String searchByActorName = "http://www.myapifilms.com/imdb/idIMDB?name=%s&token=%s&format=json&language=en-us&filmography=1&exactFilter=0&limit=1&bornDied=0&starSign=0&uniqueName=0&actorActress=0&actorTrivia=0&actorPhotos=0&actorVideos=0&salary=0&spouses=0&tradeMark=0&personalQuotes=0&starMeter=0&fullSize=0";
	private static String getActorByID = "http://www.myapifilms.com/imdb/idIMDB?idName=%s&token=%s&format=json&language=en-us&filmography=1&bornDied=0&starSign=0&uniqueName=0&actorActress=0&actorTrivia=0&actorPhotos=0&actorVideos=0&salary=0&spouses=0&tradeMark=0&fullSize=0";
	private static String getFilmographyById = "http://www.myapifilms.com/imdb/idIMDB?idIMDB=%s&token=%s&format=json&language=en-us&aka=0&business=0&seasons=0&seasonYear=0&technical=0&trailers=0&movieTrivia=0&awards=0&moviePhotos=0&movieVideos=0&actors=2&biography=0&actorActress=1&similarMovies=0&goofs=0&keyword=0&quotes=0&fullSize=0&companyCredits=0&filmingLocations=0";
	
	/*
	 * Input:
	 * 		name - Name of the actor to search for. The format does not matter, as long as
	 * 				the first name comes before the last name. Ex. Nicolas Cage, nicolascage, nIc OlA sC aGe
	 * 
	 * Output:
	 * 		films - String array containing the IDs of the films the actor has starred in.
	 */
	public static String GetActorIDFromName(String name) throws JsonException, IOException
	{
		
		JsonObject searchResult = SearchAPIForString(name);
		String actorId = GetFirstSearchResultId(searchResult);
		return actorId;
		//return GetMoviesFromActorId(actorId);
	}
	

	/*
	 * Input:
	 * 		id - id of the actor to look up. 
	 * 
	 * Output:
	 * 		films - String array containing the IDs of the films the actor has starred in.
	 */
	public static Queue<String> GetMoviesFromActorId(String actorId) throws JsonException, IOException
	{
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
		JsonObject informationById = GetFilmographyByID(movieId);
		Queue<String> actors = GetActorsFromFilmObject(informationById);
		return actors;
	}
	
	public static Queue<String> GetFilmsFromActorObject(JsonObject actorResult)
	{	
		JsonObject data = actorResult.getJsonObject("data");
		JsonObject names = data.getJsonArray("names").getJsonObject(0);
		JsonArray filmographies = names.getJsonArray("filmographies");
		int numberOfFilmographies = filmographies.size();

		Queue<String> films = new LinkedList<String>();
		for(int i = 0; i < numberOfFilmographies; i++)
		{
			JsonArray filmography = filmographies.getJsonObject(i).getJsonArray("filmography");
			for(int j = 0; j < filmography.size(); j ++)
			{
				JsonObject film = filmography.getJsonObject(j);
				String filmId = film.getString("imdbid");
				
				films.add(filmId);
			}
		}
		return films;
	}
	
	public static Queue<String> GetActorsFromFilmObject(JsonObject filmResult)
	{

		JsonObject data = filmResult.getJsonObject("data");
		JsonObject movie = data.getJsonArray("movies").getJsonObject(0);
		JsonArray cast = movie.getJsonArray("actors");
		
		int numberOfActors = cast.size();
		Queue<String> actors = new LinkedList<String>();
		
		for(int i = 0; i < numberOfActors; i ++)
		{
			String actorName = cast.getJsonObject(i).getString("actorId");
			actors.add(actorName);
		}
		return actors;
	}
	
	private static JsonObject GetInformationById(String id) throws JsonException, IOException
	{
		String uri = String.format(getActorByID, id, apikey);
		JsonObject result = readJsonFromUrl(uri.toString());
		return result;
	}
	private static JsonObject GetFilmographyByID(String id) throws JsonException, IOException
	{
		String uri = String.format(getFilmographyById, id, apikey);
		JsonObject result = readJsonFromUrl(uri.toString());
		return result;
	}
	private static JsonObject readJsonFromUrl(String uri) throws IOException, JsonException {
		InputStream is = new URL(uri).openStream();
		try {
			JsonReaderFactory factory = Json.createReaderFactory(null);
			JsonReader reader1 = factory.createReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			JsonObject json = reader1.readObject();

			return json;
		} finally {
			is.close();
		}
	}
	private static JsonObject SearchAPIForString(String query) throws JsonException, IOException
	{
		String uri =String.format(searchByActorName, query, apikey);
		JsonObject result = readJsonFromUrl(uri.toString());
		return result;
	}
	private static String GetFirstSearchResultId(JsonObject searchResult)
	{
		JsonObject data = searchResult.getJsonObject("data");
		JsonArray names = data.getJsonArray("names");
		JsonObject firstResult = names.getJsonObject(0);
		String id = firstResult.getString("idIMDB");
		
		return id;	
	}
}
