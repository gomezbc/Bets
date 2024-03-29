package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
 
@Entity
public class User {
	@Id
	private String dni;
	private String name;
	private String apellido;
	private float saldo;
	private String passwd;
	private String username;
	private boolean isAdmin;
	private Long creditCard;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	 private List<Bet> bets = new ArrayList<>();
	
	public User(String username, String passwd, String dni, String name, String apellido, boolean isAdmin) {
		this.setDni(dni);
		this.setUsername(username);
		this.setPasswd(passwd);
		this.setName(name);
		this.setApellido(apellido);
		this.setAdmin(isAdmin);
	}
	
	public Bet addBet(float betMoney, Forecast forecast) {
		Bet b = new Bet(this, betMoney, forecast);
		if(bets==null) bets = new ArrayList<>();
		bets.add(b);
		return b;
	}
	
	public Bet DoesBetExists(Integer forecastNumber) {
		if(this.getBets() == null) {
			return null; }
		for (Bet b:this.getBets()){
			if(b.getForecast().getForecastNumber().equals(forecastNumber)) {
				return b;
			}		
		}
		return null;
	}
	
	
	public void removeBet(Integer betNumber) {
		for(int i=0; i<this.getBets().size(); i++) {
			if(this.getBets().get(i).getBetNumber().equals(betNumber)) {
				this.getBets().remove(i);
				return;
			}
		}
	}
	
	public List<Bet> getBets() {
		return bets;
	}
	
	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}

	public boolean checkCredentials(String tryPasswd) {
		return this.passwd.equals(tryPasswd);
	}
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the creditCard
	 */
	public Long getCreditCard() {
		return creditCard;
	}

	/**
	 * @param creditCard the creditCard to set
	 */
	public void setCreditCard(Long creditCard) {
		this.creditCard = creditCard;
	}
	
	
}