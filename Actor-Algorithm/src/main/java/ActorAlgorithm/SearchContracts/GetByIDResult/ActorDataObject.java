package main.java.ActorAlgorithm.SearchContracts.GetByIDResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActorDataObject {
	public String id;
	@XmlElement
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String type;
	@XmlElement
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String title;
	@XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String description;
	@XmlElement
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String image;
	@XmlElement
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public Iterable<String> mediaLinks;
	@XmlElement
	public Iterable<String> getMediaLinks() {
		return mediaLinks;
	}
	public void setMediaLinks(Iterable<String> mediaLinks) {
		this.mediaLinks = mediaLinks;
	}
	
	public Iterable<FilmographyDataObject> filmography;
	@XmlElement
	public Iterable<FilmographyDataObject> getFilmography() {
		return filmography;
	}
	public void setFilmography(Iterable<FilmographyDataObject> filmography) {
		this.filmography = filmography;
	}
	
	public Iterable<String> occupation;
	@XmlElement
	public Iterable<String> getOccupation() {
		return occupation;
	}
	public void setOccupation(Iterable<String> occupation) {
		this.occupation = occupation;
	}
}
	
	