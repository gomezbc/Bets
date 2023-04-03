package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Forecast {
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer  forecastNumber;
	private String description;
	private double gain;
	
	@XmlIDREF
	private Question question;
	
	 @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	 private Vector<Bet> bets = new Vector<Bet>();
	 
	 
	 
	public Forecast() {
		super();
	}
	
	public Forecast (String description, double gain, Question question) {
		super();
		this.description = description; 
		this.gain = gain; 
		this.question = question;
	}

	
	public Bet addBet(String user, double betMoney, Forecast forecast) {
		Bet b = new Bet(user,  betMoney, forecast);
		bets.add(b);
		return b;
	}
	
	
	public boolean DoesBetExists(double a) {
		if(this.getBets() == null) {
			return false; }
		for (Bet b:this.getBets()){
			if(b.getBetMoney() == a)
				return true;
		}
		return false;
	}
	
	

	public Vector<Bet> getBets() {
		return bets;
	}



	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public Integer getForecastNumber() {
		return forecastNumber;
	}
	
	public void setForecastNumber(Integer forecastNumber) {
		this.forecastNumber = forecastNumber;
	}
	
	public String toString(){
		return forecastNumber+";" + description + ";"+ Double.toString(gain);
	}
	
	
}
