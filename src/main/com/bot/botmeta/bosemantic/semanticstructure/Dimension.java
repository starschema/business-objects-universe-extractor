package com.bot.botmeta.bosemantic.semanticstructure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "attribute")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dimension 
{
	@XmlElement(name = "Name")
	private String Name;	
	
	
	@XmlElement(name = "Id")
	private String Id;
	

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
}
