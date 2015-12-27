package com.bot.botmeta.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import com.google.gson.Gson;

public class ConversionUtility {

	private static Logger logger = Logger.getLogger(ConversionUtility.class);

	public static <T> T xmlToObject(String path, Class<T> classObj)
			throws IOException, JAXBException, SAXException {

		BufferedReader bfr = null;
		BufferedWriter bfw = null;
		JAXBContext jaxbContext = null;
		T objectT = null;
		try {
			jaxbContext = JAXBContext.newInstance(classObj);
			File file = new File(path);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			objectT = (T) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			logger.error(e.toString());
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		finally {
			if (bfr != null) {
				bfr.close();
			}

			if (bfw != null) {
				bfw.close();
			}
		}

		return objectT;
	}

	public static void objectToJSON(Object object, String path) {
		try {
			Gson gson = new Gson();
			String json = gson.toJson(object);
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
