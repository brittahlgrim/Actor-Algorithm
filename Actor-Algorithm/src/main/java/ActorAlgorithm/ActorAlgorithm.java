package main.java.ActorAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.json.JsonException;
import javax.xml.bind.JAXBException;

import info.movito.themoviedbapi.*;


public class ActorAlgorithm {
	static String apikey = "d507f15dec6b145abf66f17a69cb71c2";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
		public static void main (String[] args) throws IOException, JAXBException 
	{
		RunFromFile();
		//RunFromConsole();
	}
	
	public static void RunFromConsole() throws JsonException, IOException
	{
	    //input
	    Scanner key= new Scanner(System.in);
	    System.out.println("Enter an actors name: ");
	    String actor1Name = key.nextLine();
	    
	    System.out.println("Enter an actor's name: ");
	    String actor2Name = key.nextLine();
	    key.close();
	    
	    int numLinks = RunProgram(actor1Name, actor2Name);
	}
	
	public static void RunFromFile() throws FileNotFoundException
	{
		// The name of the file to open.
        String fileName = "testingInput.txt";
        String actor1Name = null;
        String actor2Name = null;

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(new File(fileName));

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            while((line = bufferedReader.readLine()) != null) {
            	actor1Name = line;
            	actor2Name = bufferedReader.readLine();
            	
            	RunProgram(actor1Name, actor2Name);
            }
            
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");
        }
		
	}
	
	public static int RunProgram(String actor1Name, String actor2Name) throws JsonException, IOException
	{
		Queue<Actor> actorsFromMovie = new LinkedList<Actor>();
	    Queue<Actor> actorsForMovie = new LinkedList<Actor>();
	    Queue<Actor> actorsToSearch = new LinkedList<Actor>();
		Queue<Movie> moviesToSearch = new LinkedList<Movie>();
		LinkedHashSet<Actor> actorsAlreadySearched = new LinkedHashSet<Actor>(ActorInitialCapacity);
		LinkedHashSet<Movie> moviesAlreadySearched = new LinkedHashSet<Movie>(MovieInitialCapacity);

	    boolean found = false;
	    int iteration = 0;
	    long time;
	    long startTime = System.currentTimeMillis();
	    
	    TmdbApi api = new TmdbApi(apikey);
	    TmdbMovies moviesApi = api.getMovies();
	    TmdbPeople peopleApi = api.getPeople();
	    TmdbSearch searchApi = api.getSearch();
	    
	    Actor actor1 = APIService.GetActorFromName(searchApi, actor1Name);
	    Actor actor2 = APIService.GetActorFromName(searchApi, actor2Name);
	    
	    actor1.sourceMovie = null;
	    actor2.sourceMovie = null;
	    actorsToSearch.add(actor1);
	    while(!actorsToSearch.isEmpty() && !found)
	    {
	    	actorsAlreadySearched.addAll(actorsToSearch);
	    	actorsForMovie.addAll(actorsToSearch);
	    	actorsToSearch.clear();
	    	
	    	while(!actorsForMovie.isEmpty() && !found)
	    	{
		    	moviesToSearch.addAll(APIService.GetMoviesFromActor(peopleApi, actorsForMovie.remove()));
	    		while(!moviesToSearch.isEmpty() && !found)
	    		{
	    			Movie currentMovie = moviesToSearch.remove();
	    			if(!moviesAlreadySearched.contains(currentMovie))
	    			{
	    				//System.out.println("Movie added: " + currentMovie);
	    				moviesAlreadySearched.add(currentMovie);
		    			actorsFromMovie = APIService.GetActorsFromMovie(moviesApi, currentMovie);
		    			while(!actorsFromMovie.isEmpty() && !found)
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
	    
//	    System.out.println("number of iterations: " + iteration);
//	    System.out.println("Actor: " + actor2.name);
	    
	    long finishTime = System.currentTimeMillis();
	    time = finishTime - startTime;
	    
	    System.out.println("Actor 1: " + actor1Name + "\tActor2: " + actor2Name);
	    if(found) System.out.println("Number of links: " + iteration);
	    else System.out.println("No connection found");
	    System.out.println("number of movies searched: " + moviesAlreadySearched.size());
	    System.out.println("number of actors searched: " + actorsAlreadySearched.size());
	    Movie nextMovie = actor2.sourceMovie;
	    Actor nextActor;
	    while (nextMovie != null) {
	    	System.out.println("Movie: " + nextMovie.name);
	    	nextActor = nextMovie.sourceActor;
	    	System.out.println("Actor: " + nextActor.name);
	    	nextMovie = nextActor.sourceMovie;
	    }
	    System.out.println("Time to complete: " + time);
	    System.out.println();
	    if (found)
	    	return iteration;
	    return -1;
	}
}
