package main.java.ActorAlgorithm.SearchContracts.GetByIDResult;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DonationDataObject {
	String Message;
	@XmlElement
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}

	String Donation_url;
	@XmlElement
	public String getDonation_url() {
		return Donation_url;
	}
	public void setDonation_url(String donation_url) {
		Donation_url = donation_url;
	}
}
