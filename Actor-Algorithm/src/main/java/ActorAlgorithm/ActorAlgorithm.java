package main.java.ActorAlgorithm;

import java.io.IOException;
import java.util.*;

import javax.xml.bind.JAXBException;

import info.movito.themoviedbapi.*;


public class ActorAlgorithm {
	static String apikey = "d507f15dec6b145abf66f17a69cb71c2";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	public static Queue<Actor> actorsToSearch = new LinkedList<Actor>();
	public static Queue<Movie> moviesToSearch = new LinkedList<Movie>();
	public static LinkedHashSet<Actor> actorsAlreadySearched = new LinkedHashSet<Actor>(ActorInitialCapacity);
	public static LinkedHashSet<Movie> moviesAlreadySearched = new LinkedHashSet<Movie>(MovieInitialCapacity);

	public static void main (String[] args) throws IOException, JAXBException {
	    //input
	    Queue<Actor> actorsFromMovie = new LinkedList<Actor>();
	    Queue<Actor> actorsForMovie = new LinkedList<Actor>();
	    boolean found = false;
	    int iteration = 0;

	    Scanner key= new Scanner(System.in);
	    System.out.println("Enter an actors name: ");
	    String actor1Name = key.nextLine();
	    
	    System.out.println("Enter an actor's name: ");
	    String actor2Name = key.nextLine();
	    key.close();
	    
	    TmdbApi api = new TmdbApi(apikey);
	    TmdbMovies moviesApi = api.getMovies();
	    TmdbPeople peopleApi = api.getPeople();
	    TmdbSearch searchApi = api.getSearch();
	    
	    
	    Actor actor1 = APIService.GetActorFromName(searchApi, actor1Name);
	    Actor actor2 = APIService.GetActorFromName(searchApi, actor2Name);
	    
	    actor1.sourceMovie = null;
	    actorsToSearch.add(actor1);
	    while(!actorsToSearch.isEmpty() && !found)
	    {
	    	/*if(actorsToSearch.contains(actor2))
	    	{
	    		found = true;
	    		break;
	    	}*/
	    	actorsAlreadySearched.addAll(actorsToSearch);
	    	actorsForMovie.addAll(actorsToSearch);
	    	actorsToSearch.clear();
	    	
	    	while(!actorsForMovie.isEmpty())
	    	{
		    	moviesToSearch.addAll(APIService.GetMoviesFromActor(peopleApi, actorsForMovie.remove()));
	    		while(!moviesToSearch.isEmpty())
	    		{
	    			Movie currentMovie = moviesToSearch.remove();
	    			if(!moviesAlreadySearched.contains(currentMovie))
	    			{
	    				//System.out.println("Movie added: " + currentMovie);
	    				moviesAlreadySearched.add(currentMovie);
		    			actorsFromMovie = APIService.GetActorsFromMovie(moviesApi, currentMovie);
		    			while(!actorsFromMovie.isEmpty())
		    			{
		    				Actor currentActor = actorsFromMovie.remove();
		    				if(currentActor.id == actor2.id){
		    					found = true;
		    					actor2.sourceMovie = currentActor.sourceMovie;
		    					break;
		    				}
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
	    	System.out.println(iteration);
	    }
	    
	    System.out.println("number of iterations: " + iteration);
	    System.out.println("Actor: " + actor2.name);
	    Movie nextMovie = actor2.sourceMovie;
	    Actor nextActor;
	    while (nextMovie != null) {
	    	System.out.println("Movie: " + nextMovie.name);
	    	nextActor = nextMovie.sourceActor;
	    	System.out.println("Actor: " + nextActor.name);
	    	nextMovie = nextActor.sourceMovie;
	    }
	}
	
	
	
}
