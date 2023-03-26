package br.com.igor.integrationstests.vo.wrappers.books;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperBookVO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@JsonProperty("_embedded")
	private BookEmbeddedVO enbedded;


	public WrapperBookVO() {}


	public BookEmbeddedVO getEnbedded() {
		return enbedded;
	}


	public void setEnbedded(BookEmbeddedVO enbedded) {
		this.enbedded = enbedded;
	}


	@Override
	public int hashCode() {
		return Objects.hash(enbedded);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WrapperBookVO other = (WrapperBookVO) obj;
		return Objects.equals(enbedded, other.enbedded);
	}



	
}
