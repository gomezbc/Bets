package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Forecast {
	@Id
	@GeneratedValue
	private Integer  forecastNumber;
	private String description;
	private float gain;
	private Question question;
	
	public Forecast (String description, float gain, Question question) {
		this.description = description; 
		this.gain = gain; 
		this.question = question;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
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
	
	@Override
	public String toString() {
		return forecastNumber+";"+description+";"+gain;
	}
	
	
}
