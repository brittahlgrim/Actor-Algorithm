package main.java.ActorAlgorithm;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.json.JsonException;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.xml.bind.JAXBException;

import info.movito.themoviedbapi.*;

import org.jgrapht.*;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;

import com.mxgraph.layout.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;


public class ActorAlgorithm {
	static String apikey = "d507f15dec6b145abf66f17a69cb71c2";
	
	private final static int ActorInitialCapacity = 10000;
	private final static int MovieInitialCapacity = 10000;
	
	private final Dimension DEFAULT_SIZE = new Dimension(1000, 500);
	private GraphDisplay display;
	private boolean enableGraph = true;

    
	public static ListenableGraph<String, DefaultEdge> graph = new ListenableUndirectedGraph<>(DefaultEdge.class);
	
	
	public class GraphDisplay extends JApplet {
		private static final long serialVersionUID = 1L;
		private final Dimension DEFAULT_SIZE = new Dimension(1000, 1000);
		private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
		
		public void init() {
			jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(graph);
			
			getContentPane().add(new mxGraphComponent(jgxAdapter));
			resize(DEFAULT_SIZE);
		}
		
		public void resetLayout() {
			mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter);
			jgxAdapter.getStylesheet().getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");
			layout.setHorizontal(false);
			layout.setNodeDistance(120);
			layout.execute(jgxAdapter.getDefaultParent());
		}
	}
	
	
	
	
		public static void main (String[] args) throws IOException, JAXBException 
	{
		UserInterface ui = new UserInterface();
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setResizable(false);
		ui.setSize(400, 400);
		ui.setVisible(true);
	}
		
		public void init(String actor1, String actor2) throws JsonException, IOException {

			if (enableGraph) {
				display = new GraphDisplay();
				display.init();
				JFrame frame = new JFrame();
				frame.getContentPane().add(display);
				frame.setTitle("Visual Graph");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setPreferredSize(DEFAULT_SIZE);
				frame.pack();
				frame.setVisible(true);
			}
			//RunFromFile();
			RunFromConsole(actor1, actor2);
			
		}
	
	public void RunFromConsole(String actor1, String actor2) throws JsonException, IOException
	{
	    //input
	 
//		Scanner key= new Scanner(System.in);
//	    System.out.println("Enter an actors name: ");
//	    String actor1Name = key.nextLine();
//	    
//	    System.out.println("Enter an actor's name: ");
//	    String actor2Name = key.nextLine();
//	    key.close();
	  
		RunProgram(actor1, actor2);
	}
	
	public void RunFromFile() throws FileNotFoundException
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
	
	public int RunProgram(String actor1Name, String actor2Name) throws JsonException, IOException
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
	    graph.addVertex(actor1.name);
	    while(!actorsToSearch.isEmpty() && !found)
	    {
	    	actorsAlreadySearched.addAll(actorsToSearch);
	    	actorsForMovie.addAll(actorsToSearch);
	    	actorsToSearch.clear();
	    	
	    	while(!actorsForMovie.isEmpty() && !found)
	    	{
	    		
		    	moviesToSearch.addAll(APIService.GetMoviesFromActor(peopleApi, actorsForMovie.remove(), graph));
	    		while(!moviesToSearch.isEmpty() && !found)
	    			
	    		{
	    			Movie currentMovie = moviesToSearch.remove();
	    			if(!moviesAlreadySearched.contains(currentMovie))
	    			{
	    				//System.out.println("Movie added: " + currentMovie);
	    				moviesAlreadySearched.add(currentMovie);
		    			actorsFromMovie = APIService.GetActorsFromMovie(moviesApi, currentMovie, graph);
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
	    if (enableGraph) {
	    	display.resetLayout();
	    }
	    
	    if (found)
	    	return iteration;
	    return -1;
	}
}
