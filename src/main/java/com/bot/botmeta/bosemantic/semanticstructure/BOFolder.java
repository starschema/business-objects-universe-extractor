package com.bot.botmeta.bosemantic.semanticstructure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Model")
@XmlAccessorType(XmlAccessType.FIELD)
public class BOFolder 
{
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "namespace")
	private String namespace;
	
	@XmlElement(name = "parentName")
	private String parentName;
	
	@XmlElement(name = "path")
	private String path;
	
	
	@XmlElementWrapper(name = "attributes")
	@XmlElement(name = "attribute")
	private List<Dimension> attributes;
		
	
	@XmlElementWrapper(name = "measures")
	@XmlElement(name = "measure")
	private List<Measure> measures;
	
	@XmlElementWrapper(name="filters")
	@XmlElement(name="filter")
	private List<Filter> filters;
		

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}	

	public List<Dimension> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Dimension> attributes) {
		this.attributes = attributes;
	}

	public List<Measure> getMeasures() {
		return measures;
	}

	public void setMeasures(List<Measure> measure) {
		this.measures = measure;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	
	
}
