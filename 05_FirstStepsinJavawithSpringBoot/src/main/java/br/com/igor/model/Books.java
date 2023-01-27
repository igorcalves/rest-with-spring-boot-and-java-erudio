package br.com.igor.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Books implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@Column(name = "author", nullable = false, length = 255)
	private String author;
	
	@Column(name = "launch_date", nullable = false, columnDefinition = "DATE")
	private LocalDate launchDate;
	
	@Column(name ="price",precision = 10, scale = 2)
	private Double price;
	
	@Column(name = "title",nullable = false, length = 255)
	private String title;
	
	public Books() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public LocalDate getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDate laucheDate) {
		this.launchDate = laucheDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String text) {
		this.title = text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, launchDate, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Books other = (Books) obj;
		return Objects.equals(author, other.author) && id == other.id && Objects.equals(launchDate, other.launchDate)
				&& Objects.equals(price, other.price) && Objects.equals(title, other.title);
	}  
	 
	
	
	
	
	

}
