package pack1.ActorAlgorithm.SearchContracts.Search;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FullSearchResult {

	public String status;
	@XmlElement
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int code;
	@XmlElement
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public String message;
	@XmlElement
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String term;
	@XmlElement
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}

	public String searchUrl;
	@XmlElement
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public SearchDataObject data;
	@XmlElement
	public SearchDataObject getData() {
		return data;
	}
	public void setData(SearchDataObject data) {
		this.data = data;
	}
}
