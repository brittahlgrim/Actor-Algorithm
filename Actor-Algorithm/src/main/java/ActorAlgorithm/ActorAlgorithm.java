package main.java.ActorAlgorithm;

import java.io.IOException;
import java.util.*;

import javax.xml.bind.JAXBException;


public class ActorAlgorithm {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	public static Queue<String> actorsToSearch = new LinkedList<String>();
	public static Queue<String> moviesToSearch = new LinkedList<String>();
	public static LinkedHashSet<String> actorsAlreadySearched = new LinkedHashSet<String>(ActorInitialCapacity);
	public static LinkedHashSet<String> moviesAlreadySearched = new LinkedHashSet<String>(MovieInitialCapacity);

	public static void main (String[] args) throws IOException, JAXBException {
	    //input
	    System.out.println("Enter an actors name: ");
	    Scanner key= new Scanner(System.in);
	    String actor1 = key.nextLine();
	    System.out.println("Enter an actor's name: ");
	    String actor2 = key.nextLine();
	    
	    String[] films = APIService.GetMoviesFromActorName(name);
	    for(int i = 0; i < films.length; i ++)
	    {
	    	moviesToSearch.add(films[i]);
	    	System.out.println(films[i]);
	    }
	    while (!moviesToSearch.isEmpty())
	    {
	    	String film = moviesToSearch.remove();
	    	moviesAlreadySearched.add(film);
	    	String[] actors = APIService.GetActorsFromMovieID(film);
	    	for(int i = 0; i < actors.length; i++)
	    	{
	    		if (!actorsAlreadySearched.contains(actors[i]))
	    		{
	    			actorsToSearch.add(actors[i]);
	    		}
	    		System.out.println(actors[i]);
	    	}
	    }
	    while (!actorsToSearch.isEmpty())
	    {
	    	String actor = actorsToSearch.remove();
	    	actorsAlreadySearched.add(actor);
	    	String[] secondFilms = APIService.GetMoviesFromActorName(actor);
	    	for(int i = 0; i < secondFilms.length; i ++)
	    	{
	    		if (!moviesAlreadySearched.contains(secondFilms[i]))
	    		{
	    			moviesToSearch.add(secondFilms[i]);
	    		}
	    		System.out.println(secondFilms[i]);
	    	}
	    }
	}
	
	
	
}
