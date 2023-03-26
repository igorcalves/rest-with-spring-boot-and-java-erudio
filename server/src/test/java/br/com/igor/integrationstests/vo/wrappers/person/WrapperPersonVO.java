package br.com.igor.integrationstests.vo.wrappers.person;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WrapperPersonVO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private PersonEmbeddedVO enbedded;

	public WrapperPersonVO() {}

	public PersonEmbeddedVO getEnbedded() {
		return enbedded;
	}

	public void setEnbedded(PersonEmbeddedVO enbedded) {
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
		WrapperPersonVO other = (WrapperPersonVO) obj;
		return Objects.equals(enbedded, other.enbedded);
	}
	
	
	


}
