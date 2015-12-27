package com.bot.botmeta;

import java.io.File;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import com.bot.botmeta.boextraction.ExtractBOBJMetaData;
import com.bot.botmeta.bosemantic.configuration.BotConfig;
import com.bot.botmeta.bosemantic.semanticstructure.BOMetaObjects;
import com.bot.botmeta.utility.ConversionUtility;

public class BOMetaDataExtraction
{
	Logger log = null;
	public static BotConfig botConfig;

	public static void main(String args[])
	{
		System.out.println("Business Object Univserse Extraction Started");

		String logFileName = PropertyLoader.getProperty("log_file_path") + "/BOTUniverseExtraction.log";
		System.setProperty("logFile.log", logFileName);
		BOMetaDataExtraction boExtraction = new BOMetaDataExtraction();
		boExtraction.log = Logger.getLogger(BOMetaDataExtraction.class);

		try
		{
			boExtraction.initialize(args);
		} catch (SAXException e1)
		{
			e1.printStackTrace();
		}

		try
		{
			boExtraction.ExtractBOMetaData();
		} catch (Exception e)
		{
			boExtraction.log.error("**Exception in Main -> " + e);
			boExtraction.log.error(e.getMessage(), e);
			System.out.println("Error In Univserse Extraction");
		}
	}

	private void initialize(String[] args) throws SAXException
	{
		if (args.length == 0)
		{

			System.out.println("Please provide the BOT Config Input XML file.");
			return;
		}

		String xmlString = args[0];

		try
		{

			botConfig = ConversionUtility.xmlToObject(xmlString, BotConfig.class);

		} catch (JAXBException e)
		{
			log.error(e.getMessage(), e);
		} catch (SAXException e)
		{
			log.error(e.getMessage(), e);
		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public void ExtractBOMetaData()
	{
		ExtractBOBJMetaData m_extract = new ExtractBOBJMetaData();
		BOMetaObjects metadataObjects = m_extract.getBOBJData();
		PersistBOMetaData(metadataObjects);
	}

	public void PersistBOMetaData(BOMetaObjects boMetaModel)
	{
		try
		{
			System.out.println("Saving BO Semantic data as JSON file at : " + BOMetaDataExtraction.botConfig.getOutputPath());
			String unxName = botConfig.getUniverseName().toUpperCase().replaceAll(".UNX", "");
			String filePath = BOMetaDataExtraction.botConfig.getOutputPath() + File.separator + unxName + ".json";
			ConversionUtility.objectToJSON(boMetaModel, filePath);
			System.out.println("Business Object Univserse Extraction Completed");

		} catch (Exception e)
		{

		}
	}

}
