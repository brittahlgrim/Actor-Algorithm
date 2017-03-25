package main.java.ActorAlgorithm;

import java.io.IOException;
import java.util.*;

import javax.xml.bind.JAXBException;


public class ActorAlgorithm {
	static String apikey = "7d27ef57-ffa6-4140-8992-3a52dae6e68d";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	public static Queue<String> actorIdsToSearch = new LinkedList<String>();
	public static Queue<String> moviesToSearch = new LinkedList<String>();
	public static LinkedHashSet<String> actorsAlreadySearched = new LinkedHashSet<String>(ActorInitialCapacity);
	public static LinkedHashSet<String> moviesAlreadySearched = new LinkedHashSet<String>(MovieInitialCapacity);

	public static void main (String[] args) throws IOException, JAXBException {
	    //input
	    Queue<String> actorsFromMovie = new LinkedList<String>();
	    Queue<String> actorsForMovie = new LinkedList<String>();
	    boolean found = false;
	    int iteration = 0;

	    Scanner key= new Scanner(System.in);
	    System.out.println("Enter an actors name: ");
	    String actor1 = key.nextLine();
	    
	    System.out.println("Enter an actor's name: ");
	    String actor2 = key.nextLine();
	    key.close();
	    
	    String actor1Id = APIService.GetActorIDFromName(actor1.toLowerCase().replaceAll("\\s", "+"));
	    String actor2Id = APIService.GetActorIDFromName(actor2.toLowerCase().replaceAll("\\s", "+"));
	    
	    actorIdsToSearch.add(actor1Id);
	    while(!actorIdsToSearch.isEmpty() && !found)
	    {
	    	if(actorIdsToSearch.contains(actor2Id))
	    	{
	    		found = true;
	    		break;
	    	}
	    	actorsAlreadySearched.addAll(actorIdsToSearch);
	    	actorsForMovie.addAll(actorIdsToSearch);
	    	actorIdsToSearch.clear();
	    	
	    	while(!actorsForMovie.isEmpty())
	    	{
		    	moviesToSearch.addAll(APIService.GetMoviesFromActorId(actorsForMovie.remove()));
	    		while(!moviesToSearch.isEmpty())
	    		{
	    			String currentMovie = moviesToSearch.remove();
	    			if(!moviesAlreadySearched.contains(currentMovie))
	    			{
	    				//System.out.println("Movie added: " + currentMovie);
	    				moviesAlreadySearched.add(currentMovie);
		    			actorsFromMovie = APIService.GetActorsFromMovieID(currentMovie);
		    			while(!actorsFromMovie.isEmpty())
		    			{
		    				String currentActor = actorsFromMovie.remove();
		    				if(currentActor == actor2Id){
		    					found = true;
		    					break;
		    				}
		    				if(!actorsAlreadySearched.contains(currentActor))
		    				{
		    					actorsAlreadySearched.add(currentActor);
		    					actorIdsToSearch.add(currentActor);
		    				}
		    			}
	    			}
	    		}
	    	}
	    	iteration++;
	    	System.out.println(iteration);
	    }
	    
	    System.out.println("number of iterations: " + iteration);
	}
	
	
	
}
