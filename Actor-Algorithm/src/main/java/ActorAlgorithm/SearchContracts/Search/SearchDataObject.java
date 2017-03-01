package main.java.ActorAlgorithm.SearchContracts.Search;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchDataObject {
	public SearchResultsObject results;
	@XmlElement
	public SearchResultsObject getResults() {
		return results;
	}
	public void setResults(SearchResultsObject results) {
		this.results = results;
	}

}
	
	