package domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Bet {
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer betNumber;
	private User user;
	private float betMoney;
	
	@XmlIDREF
	private Forecast forecast;
	
	
	public Bet (User user, float betMoney, Forecast forecast) {
		this.user = user;
		this.betMoney = betMoney;
		this.forecast = forecast;
	}


	public Integer getBetNumber() {
		return betNumber;
	}


	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public double getBetMoney() {
		return betMoney;
	}


	public void setBetMoney(float betMoney) {
		this.betMoney = betMoney;
	}


	public Forecast getForecast() {
		return forecast;
	}


	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}
	
	
	
	

}