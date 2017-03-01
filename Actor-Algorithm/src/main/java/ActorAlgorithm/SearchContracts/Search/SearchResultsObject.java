package main.java.ActorAlgorithm.SearchContracts.Search;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResultsObject {
	public Iterable<SearchResultItem> names;
	@XmlElement
	public Iterable<SearchResultItem> getNames() {
		return names;
	}
	public void setNames(Iterable<SearchResultItem> names) {
		this.names = names;
	}
	
	public Iterable<SearchResultItem> titles;
	@XmlElement
	public Iterable<SearchResultItem> getTitles() {
		return titles;
	}
	public void setTitles(Iterable<SearchResultItem> titles) {
		this.titles = titles;
	}

	public Iterable<SearchResultItem> keywords;
	@XmlElement
	public Iterable<SearchResultItem> getKeywords() {
		return keywords;
	}
	public void setKeywords(Iterable<SearchResultItem> keywords) {
		this.keywords = keywords;
	}

	public Iterable<SearchResultItem> companies;
	@XmlElement
	public Iterable<SearchResultItem> getCompanies() {
		return companies;
	}
	public void setCompanies(Iterable<SearchResultItem> companies) {
		this.companies = companies;
	}

}