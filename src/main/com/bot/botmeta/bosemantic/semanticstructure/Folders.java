package com.bot.botmeta.bosemantic.semanticstructure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "folders")
@XmlAccessorType(XmlAccessType.FIELD)

public class Folders 
{
	@XmlElement(name = "folder")
	private List<BOFolder> folders;
	
	@XmlElement(name = "universeId")
	private int UniverseId;

	public int getUniverseId() {
		return UniverseId;
	}

	public void setUniverseId(int universeId) {
		UniverseId = universeId;
	}

	public List<BOFolder> getFolders() {
		return folders;
	}

	public void setFolders(List<BOFolder> folders) {
		this.folders = folders;
	}
		
}
