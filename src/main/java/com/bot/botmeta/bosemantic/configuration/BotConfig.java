package com.bot.botmeta.bosemantic.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bot-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class BotConfig 
{
	@XmlElement(name = "universe-name")
	private String universeName;
	
	@XmlElement(name = "universe-parent-folder")
	private String universeExportFolder;
	
	@XmlElement(name = "local-universe-path")
	private String localUniversePath;
	
	@XmlElement(name = "output-path")
	private String outputPath;
	
	@XmlElement(name = "bo-server-login-info")
	private BOServerLoginInfo boServerLoginInfo;
	
	public String getUniverseName() {
		return universeName;
	}

	public void setUniverseName(String universeName) {
		this.universeName = universeName;
	}

	public String getUniverseExportFolder() {
		return universeExportFolder;
	}

	public void setUniverseExportFolder(String universeExportFolder) {
		this.universeExportFolder = universeExportFolder;
	}

	public String getLocalUniversePath() {
		return localUniversePath;
	}

	public void setLocalUniversePath(String localUniversePath) {
		this.localUniversePath = localUniversePath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public BOServerLoginInfo getBoServerLoginInfo() {
		return boServerLoginInfo;
	}

	public void setBoServerLoginInfo(BOServerLoginInfo boServerLoginInfo) {
		this.boServerLoginInfo = boServerLoginInfo;
	}
	
}
