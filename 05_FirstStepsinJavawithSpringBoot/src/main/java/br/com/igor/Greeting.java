package br.com.igor;

public class Greeting {
	
	private final long id;
	private final String content;
	
	
	public Greeting(Long id, String content) {
		this.id = id;
		this.content = content;
	}

	public Long getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	
	

}
