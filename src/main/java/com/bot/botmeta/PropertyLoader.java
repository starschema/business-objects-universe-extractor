package com.bot.botmeta;

import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader 
{
	  static Properties properties;
	
	  private static Properties defaultProps;
	  static 
	  {		  
		  try 
		  {
			  defaultProps = new Properties();			  
			  InputStream in = PropertyLoader.class.getClassLoader().getResourceAsStream("config.properties");
			  defaultProps.load(in);			  
			  in.close();
		  } 
		  catch (Exception e) 
		  {
			  e.printStackTrace();
		  }
	  }
	  
	  public static String getProperty(String key) 
	  {
		  return defaultProps.getProperty(key);
	  }
}
