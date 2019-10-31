package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.infosys.utilities.EntryHome;

public class ConvertMVNDependencies {

	private static final Logger logger = Logger.getLogger(EntryHome.class);

	public static List<String> extractData(String filePath, String[] args) { 
		// TODO Auto-generated method stub
		File file = new File(filePath);
		List<String> depList = new ArrayList<String>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			@SuppressWarnings("resource")
			int count = 0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {

				count++;
				if (count > 2 && line.length()!=0) {
					depList.add(line);
					
				}
			}
			
		}
		
		catch (Exception e) { 
			logger.error(e);

		}
		return depList;

	}

}
