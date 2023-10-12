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


	/**
	 * Get the  number of the bet
	 * 
	 * @return the bet number
	 */
	public Integer getBetNumber() {
		return betNumber;
	}


	/**
	 * Set the bet number to a bet
	 * 
	 * @param betNumber to be setted
	 */
	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}


	/**
	 * Get the User of the bet
	 * 
	 * @return the bet
	 */
	public User getUser() {
		return user;
	}


	
	/**
	 * Set the User to a bet
	 * 
	 * @param user to be setted
	 */
	public void setUser(User user) {
		this.user = user;
	}


	
	/**
	 * Get the  amount of money of the bet.
	 * 
	 * @return the amount of money of the bet
	 */
	public double getBetMoney() {
		return betMoney;
	}


	
	/**
	 * Set the bet money to a bet
	 * 
	 * @param betMoney to be setted
	 */
	public void setBetMoney(float betMoney) {
		this.betMoney = betMoney;
	}


	
	/**
	 * Get the forecast of the bet
	 * 
	 * @return the bet's forecast
	 */
	public Forecast getForecast() {
		return forecast;
	}

	

	/**
	 * Set the forecast to a bet
	 * 
	 * @param forecast to be setted
	 */
	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bet other = (Bet) obj;
		return this.betNumber != other.betNumber;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}