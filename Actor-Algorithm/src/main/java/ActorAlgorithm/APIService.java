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
import java.util.List;
import java.util.Queue;
import javax.json.*;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.people.*;


public class APIService {
	static String apikey = "d507f15dec6b145abf66f17a69cb71c2";
	/*
	 * Input:
	 * 		name - Name of the actor to search for. The format should be "Firstname Lastname"
	 * 
	 * Output:
	 * 		actor - Actor object containing the actor's name and id
	 */
	public static Actor GetActorFromName(TmdbSearch searchApi, String name) throws JsonException, IOException
	{
		TmdbPeople.PersonResultsPage results = searchApi.searchPerson(name, false, 1);
		Actor actor = new Actor();
		actor.name = results.getResults().get(0).getName();
		actor.id = results.getResults().get(0).getId();
		return actor;
	}
	

	/*
	 * Input:
	 * 		peopleApi - API object to use to get the actor information
	 * 		actor - Actor to look up. 
	 * 
	 * Output:
	 * 		films - Movie array containing the films the actor has starred in.
	 */
	public static Queue<Movie> GetMoviesFromActor(TmdbPeople peopleApi, Actor actor) throws JsonException, IOException
	{
		PersonCredits credits = peopleApi.getPersonCredits(actor.id);
		List<PersonCredit> creditList = credits.getCast();
		PersonCredit credit;
		Queue<Movie> films = new LinkedList<Movie>();
		for(int i = 0; i < creditList.size(); i++) {
			credit = creditList.get(i);
			Movie nextMovie = new Movie();
			nextMovie.sourceActor = actor;
			nextMovie.id = credit.getMovieId();
			nextMovie.name = credit.getMovieTitle();
			films.add(nextMovie);
		}
		return films;
	}
	
	/*
	 * Input:
	 * 		moviesApi - API object to use to get the movie information
	 * 		movie - Movie to look up. 
	 * 
	 * Output:
	 * 		actors - Array containing the actors starring in the film
	 */
	public static Queue<Actor> GetActorsFromMovie(TmdbMovies moviesApi, Movie movie) throws JsonException, IOException
	{
		Credits credits = moviesApi.getCredits(movie.id);
		List<PersonCast> castList = credits.getCast();
		PersonCast castMember;
		Queue<Actor> actors = new LinkedList<Actor>();
		for(int i = 0; i < castList.size(); i++) {
			castMember = castList.get(i);
			Actor nextActor = new Actor();
			nextActor.sourceMovie = movie;
			nextActor.id = castMember.getId();
			nextActor.name = castMember.getName();
			actors.add(nextActor);
		}
		return actors;
	}
	
}
