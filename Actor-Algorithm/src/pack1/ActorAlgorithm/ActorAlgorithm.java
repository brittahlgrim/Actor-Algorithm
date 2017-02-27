package pack1.ActorAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import pack1.ActorAlgorithm.SearchContracts.GetByIDResult.FilmographyDataObject;
import pack1.ActorAlgorithm.SearchContracts.Search.FullSearchResult;
import pack1.ActorAlgorithm.SearchContracts.Search.SearchResultItem;

public class ActorAlgorithm {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	
	public static void main (String[] args) throws IOException, JAXBException {
	    System.out.println("hello world");
	    //SearchResultItem actorSearchResult = searchActorByName("nicolas cage");
	    searchActorByName("nicolas%20cage");
	   // String id = actorSearchResult.id;
	    //Iterable<FilmographyDataObject> films = getActorFilmIds(id);
	    
	    //films.forEach(film -> System.out.println(film.title));
	}
	
/*	private static Iterable<FilmographyDataObject> getActorFilmIds(String actorId) throws IOException, JAXBException
	{
		String uri ="http://imdb.wemakesites.net/api/" + actorId + "?api_key=" + apikey;

		URL url = new URL(uri);
		HttpURLConnection connection =
		    (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		JAXBContext jc = JAXBContext.newInstance(FullSearchResult.class);
		InputStream xml = connection.getInputStream();
		pack1.ActorAlgorithm.SearchContracts.GetByIDResult.FullSearchResult actorByIdResult =
		    (pack1.ActorAlgorithm.SearchContracts.GetByIDResult.FullSearchResult) jc.createUnmarshaller().unmarshal(xml);
		
		Iterable<FilmographyDataObject> result = actorByIdResult.Data.filmography;
		connection.disconnect();
		return result;
	}*/
	
	//private static SearchResultItem searchActorByName(String name) throws IOException, JAXBException
	private static Object searchActorByName(String name) throws IOException, JAXBException
	{
		String uri ="http://imdb.wemakesites.net/api/search?q=" + name + "&api_key=" + apikey;

		URL url = new URL(uri);
		HttpURLConnection connection =
		    (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		JAXBContext jc = JAXBContext.newInstance(Object.class);//FullSearchResult.class);
		InputStream xml = connection.getInputStream();
		Object x = jc.createUnmarshaller().unmarshal(xml);
		//FullSearchResult fullActorSearchResult =
		  //  (FullSearchResult) jc.createUnmarshaller().unmarshal(xml);
		
		//SearchResultItem firstActorSearchResult = fullActorSearchResult.data.results.names.iterator().next();
		
		connection.disconnect();

		return x;
		
		//return firstActorSearchResult;
	}
	
	
}
