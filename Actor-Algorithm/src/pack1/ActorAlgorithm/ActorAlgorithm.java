package pack1.ActorAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import pack1.ActorAlgorithm.SearchContracts.Search.FullSearchResult;
import pack1.ActorAlgorithm.SearchContracts.Search.SearchResultItem;

public class ActorAlgorithm {
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	public static Queue<Actor> actorsToSearch = new LinkedList<Actor>();
	public static Queue<Movie> moviesToSearch = new LinkedList<Movie>();
	public static HashMap<String, Actor> actorsAlreadySearched = new HashMap<String, Actor>(ActorInitialCapacity);
	public static HashMap<String, Movie> moviesAlreadySearched = new HashMap<String, Movie>(MovieInitialCapacity);

	public static void main (String[] args) throws IOException, JAXBException {
	    System.out.println("hello world");
	    SearchResultItem actorSearchResult = searchActorByName("");
	    
	    String id = actorSearchResult.ID;
	    getActorFilmIds(id);
	}
	
	private static SearchResultItem getActorFilmIds(String actorId) throws IOException, JAXBException
	{
		String uri ="http://imdb.wemakesites.net/api/" + actorId + "?api_key=7d27ef57-ffa6-4140-8992-3a52dae6e68d";

		URL url = new URL(uri);
		HttpURLConnection connection =
		    (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		JAXBContext jc = JAXBContext.newInstance(FullSearchResult.class);
		InputStream xml = connection.getInputStream();
		FullSearchResult actorByIdResult =
		    (FullSearchResult) jc.createUnmarshaller().unmarshal(xml);
		
		SearchResultItem firstActorSearchResult = actorByIdResult.Data.Results.Names.iterator().next();
		
		
		
		connection.disconnect();
		
		return firstActorSearchResult;
	}
	
	private static SearchResultItem searchActorByName(String name) throws IOException, JAXBException
	{
		String uri ="http://imdb.wemakesites.net/api/search?q=" + name + "nicolascage&api_key=7d27ef57-ffa6-4140-8992-3a52dae6e68d";

		URL url = new URL(uri);
		HttpURLConnection connection =
		    (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		JAXBContext jc = JAXBContext.newInstance(FullSearchResult.class);
		InputStream xml = connection.getInputStream();
		FullSearchResult fullActorSearchResult =
		    (FullSearchResult) jc.createUnmarshaller().unmarshal(xml);
		
		SearchResultItem firstActorSearchResult = fullActorSearchResult.Data.Results.Names.iterator().next();
		
		connection.disconnect();
		
		return firstActorSearchResult;
	}
	
	
}
