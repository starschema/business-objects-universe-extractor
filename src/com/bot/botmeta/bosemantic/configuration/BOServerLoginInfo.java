package com.bot.botmeta.bosemantic.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bo-server-login-info")
@XmlAccessorType(XmlAccessType.FIELD)
public class BOServerLoginInfo {
	
	@XmlElement(name = "server-ip")
	private String serverIP;

	@XmlElement(name = "server-port")
	private String serverPort;
	
	@XmlElement(name = "user-name")
	private String userName;
	
	@XmlElement(name = "password")
	private String password;
	
	@XmlElement(name = "user-type")
	private String userType;
	
	@XmlElement(name = "authentication")
	private String authentication;
	

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

}
