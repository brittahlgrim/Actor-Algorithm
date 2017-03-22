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
	    boolean found = false;
	    int iteration = 0;
	    Queue<String> actorsFromMovie = new LinkedList<String>();
	    Queue<String> actorsForMovie = new LinkedList<String>();
	    
	    actorsToSearch.add(actor1);
	    while(!actorsToSearch.isEmpty() && !found)
	    {
	    	if(actorsToSearch.contains(actor2))
	    		found = true;
	    	actorsAlreadySearched.addAll(actorsToSearch);
	    	actorsForMovie.addAll(actorsToSearch);
	    	actorsToSearch.clear();
	    	
	    	while(!actorsForMovie.isEmpty())
	    	{
		    	moviesToSearch.addAll(APIService.GetMoviesFromActorName(actorsForMovie.remove()));
	    		while(!moviesToSearch.isEmpty())
	    		{
	    			String currentMovie = moviesToSearch.remove();
	    			if(!moviesAlreadySearched.contains(currentMovie))
	    			{
	    				System.out.println("Movie added: " + currentMovie);
	    				moviesAlreadySearched.add(currentMovie);
		    			actorsFromMovie = APIService.GetActorsFromMovieID(currentMovie);
		    			while(!actorsFromMovie.isEmpty())
		    			{
		    				String currentActor = actorsFromMovie.remove();
		    				if(!actorsAlreadySearched.contains(currentActor))
		    				{
		    					actorsAlreadySearched.add(currentActor);
		    					actorsToSearch.add(currentActor);
		    				}
		    			}
	    			}
	    		}
	    	}
	    	iteration++;
	    }
	    
	    System.out.println("number of iterations: " + iteration);
	}
	
	
	
}
