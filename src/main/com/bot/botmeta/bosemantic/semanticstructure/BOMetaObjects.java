package com.bot.botmeta.bosemantic.semanticstructure;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bometaobjects")
@XmlAccessorType(XmlAccessType.FIELD)
public class BOMetaObjects
{

	@XmlElement(name = "bometaobjects")
	private Folders folders;

	public Folders getFolders() {
		return folders;
	}

	public void setFolders(Folders folders) {
		this.folders = folders;
	}

	public BOMetaObjects()
	{
		super();
	}

	

}
