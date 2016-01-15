package com.bot.botmeta.bosemantic.semanticstructure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "filter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {
	
	@XmlElement(name = "Id")
	private String Id;
	
	@XmlElement(name = "Name")
	private String Name;

	@XmlElement(name = "Type")
	private String Type;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}	

}
