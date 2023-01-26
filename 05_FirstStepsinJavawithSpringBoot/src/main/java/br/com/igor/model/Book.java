package br.com.igor.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	@Column(name = "author", nullable = false, length = 255)
	private String author;
	
	@Column(name = "lauch_date", nullable = false, columnDefinition = "DATE")
	private LocalDate laucheDate;
	
	@Column(name ="price",precision = 10, scale = 2)
	private Double price;
	
	@Column(name = "text",nullable = false, length = 255)
	private String text;
	
	public Book() {
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

	public LocalDate getLaucheDate() {
		return laucheDate;
	}

	public void setLaucheDate(LocalDate laucheDate) {
		this.laucheDate = laucheDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, laucheDate, price, text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && id == other.id && Objects.equals(laucheDate, other.laucheDate)
				&& Objects.equals(price, other.price) && Objects.equals(text, other.text);
	}  
	 
	
	
	
	
	

}
