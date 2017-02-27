package pack1.ActorAlgorithm.SearchContracts.Search;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResultItem {

	public String title;
	@XmlElement
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String id;
	@XmlElement
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String url;
	@XmlElement
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String thumbnailsource;
	@XmlElement
	public String getThumbnailsource() {
		return thumbnailsource;
	}
	public void setThumbnailsource(String thumbnailsource) {
		this.thumbnailsource = thumbnailsource;
	}
	
}