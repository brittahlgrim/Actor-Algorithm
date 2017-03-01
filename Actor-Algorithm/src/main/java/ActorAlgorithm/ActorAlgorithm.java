package main.java.ActorAlgorithm;

import java.io.IOException;
import java.util.*;

import javax.xml.bind.JAXBException;


public class ActorAlgorithm {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	public static Queue<Actor> actorsToSearch = new LinkedList<Actor>();
	public static Queue<Movie> moviesToSearch = new LinkedList<Movie>();
	public static HashMap<String, Actor> actorsAlreadySearched = new HashMap<String, Actor>(ActorInitialCapacity);
	public static HashMap<String, Movie> moviesAlreadySearched = new HashMap<String, Movie>(MovieInitialCapacity);

	public static void main (String[] args) throws IOException, JAXBException {
	    System.out.println("hello world");
	    String name = "nicolascage";
	    
	    String[] films = APIService.GetActorMoviesFromName(name);
	    for(int i = 0; i < films.length; i ++)
	    {
	    	System.out.println(films[i]);
	    }
	}
	
	
	
}
