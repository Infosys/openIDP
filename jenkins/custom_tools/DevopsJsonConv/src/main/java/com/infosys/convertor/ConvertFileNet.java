/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.FileNet;
import com.infosys.json.FileNetExport;
import com.infosys.json.FileNetExportClassDefinitionType;
import com.infosys.json.FileNetExportOtherType;
import com.infosys.json.FileNetExportPropertyType;
import com.infosys.json.FileNetImport;
import com.infosys.utilities.export.SavedManifest;
import com.infosys.utilities.imports.PairConfig;
import com.infosys.utilities.imports.PairConfig.Destination;
import com.infosys.utilities.imports.PairConfig.Source;

public class ConvertFileNet {
	private static final Logger logger = Logger.getLogger(ConvertFileNet.class);
	private FileNet fileNetJsonObj = new FileNet();
	private FileNetImport fileNetImportJsonObj = new FileNetImport();

	private void convertImport(String inputPath, String triggerId, String importFileName) throws JAXBException {
		String importFilePath = inputPath + "/" + importFileName;
		System.out.println("importFilePAth is  " + importFilePath);
		File importFile = new File(importFilePath);
		logger.info("file opend");
		logger.info("convert; importFilePAth=" + importFilePath);
		System.out.println("convert; importFilePAth =" + importFilePath);
		JAXBContext jaxbContextForPair = JAXBContext.newInstance(PairConfig.class);
		Unmarshaller jaxbUnmarshallerForPair = jaxbContextForPair.createUnmarshaller();
		PairConfig pairConfig = (PairConfig) jaxbUnmarshallerForPair.unmarshal(importFile);
		if (pairConfig.getSource().isEmpty() || pairConfig.getDestination().isEmpty()) {
			logger.info("PairConfig.xml - Source and Destination is empty... ");
			System.out.println("PairConfig.xml - Source and Destination is empty... ");
		} else {
			if (!pairConfig.getSource().isEmpty()) {
				Source source = pairConfig.getSource().get(0);
				System.out.println("Source" + source);
				fileNetImportJsonObj.setSource(source.getName());
				System.out.println("source name" + source.getName());
			}
			if (!pairConfig.getDestination().isEmpty()) {
				Destination destination = pairConfig.getDestination().get(0);
				fileNetImportJsonObj.setDestination(destination.getName());
				System.out.println("Destinaton name" + destination.getName());
				System.out.println("Set name" + fileNetImportJsonObj.getDestination());
			}
			fileNetImportJsonObj.setTriggerId(new Integer(triggerId));
		}
	}

	private void convertExport(String inputPath, String triggerId, String exportFileName) {
		try {
			String exportFilePath = inputPath;// + "\\"+ exportFileName;
			logger.info("exportFileName is " + exportFileName);
			File exportFile = new File(exportFilePath);
			logger.info("convert; exportFilePath=" + exportFilePath);
			JAXBContext jaxbContextForExport = JAXBContext.newInstance(SavedManifest.class);
			Unmarshaller jaxbUnmarshallerForExport = jaxbContextForExport.createUnmarshaller();
			SavedManifest savedManifest = (SavedManifest) jaxbUnmarshallerForExport.unmarshal(exportFile);
			FileNetExport fileNetExportJson = new FileNetExport();
			System.out.println("Before property file");
			fileNetExportJson = preparePropertyFile(fileNetExportJson, savedManifest, triggerId);
			System.out.println("property name is completed");
			System.out.println("calss file definition started");
			fileNetExportJson = prepareclassDefinition(fileNetExportJson, savedManifest, triggerId);
			System.out.println(
					"calss file definition ended with sizee" + fileNetExportJson.getClassDefinitionTypeList().size());
			fileNetExportJson = prepareOthers(fileNetExportJson, savedManifest, triggerId);
			fileNetJsonObj.setFileNetExport(fileNetExportJson);
			fileNetJsonObj.setTriggerId(new Integer(triggerId));
			fileNetJsonObj.setFileNetImport(fileNetImportJsonObj);
			fileNetJsonObj.setEnv("");
			logger.info("Report Converted Successfully..!!");
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
	}

	private FileNetExport preparePropertyFile(FileNetExport fileNetExportJson, SavedManifest savedManifest,
			String triggerId) {
		if (savedManifest.getPropertyTemplates().isEmpty()) {
			logger.info("Report Converted Successfully..!!");
		}
		List<SavedManifest.PropertyTemplates> propertyTemplates = savedManifest.getPropertyTemplates();
		System.out.println("Property list size " + savedManifest.getPropertyTemplates().size());
		for (SavedManifest.PropertyTemplates local_propetyTemplates : propertyTemplates) {
			List<SavedManifest.PropertyTemplates.PropertyTemplate> popertyTemplateList = local_propetyTemplates
					.getPropertyTemplate();
			List<FileNetExportPropertyType> propertyListJson = new ArrayList<>();
			for (SavedManifest.PropertyTemplates.PropertyTemplate local_propetyTemplate : popertyTemplateList) {
				FileNetExportPropertyType jsonPropertyTemplateObj = new FileNetExportPropertyType();
				jsonPropertyTemplateObj.setId(local_propetyTemplate.getId());
				jsonPropertyTemplateObj.setName(local_propetyTemplate.getName());
				jsonPropertyTemplateObj.setObjectStore(local_propetyTemplate.getObjectStore());
				System.out.println("Property Type is " + jsonPropertyTemplateObj.getObjectType());
				propertyListJson.add(jsonPropertyTemplateObj);
			}
			fileNetExportJson.setPropertyTypeList(propertyListJson);
			fileNetExportJson.setTriggerId(triggerId);
			fileNetExportJson.setEnviornment("");
			System.out.println(fileNetExportJson.getPropertyTypeList().toString());
		}
		return fileNetExportJson;
	}

	private FileNetExport prepareclassDefinition(FileNetExport fileNetExportJson, SavedManifest savedManifest,
			String triggerId) {
		if (savedManifest.getClassDefinitions().isEmpty()) {
			logger.info("Report Converted Successfully..!!");
		}
		System.out.println("after if prepareclassDefinition");
		List<SavedManifest.ClassDefinitions> classTemplates = savedManifest.getClassDefinitions();
		for (SavedManifest.ClassDefinitions local_classTemplates : classTemplates) {
			List<SavedManifest.ClassDefinitions.ClassDefinition> classDefinitionList = local_classTemplates
					.getClassDefinition();
			List<FileNetExportClassDefinitionType> classDefinationJson = new ArrayList<>();
			for (SavedManifest.ClassDefinitions.ClassDefinition local_classTemplate : classDefinitionList) {
				FileNetExportClassDefinitionType jsonclassDefinationObj = new FileNetExportClassDefinitionType();
				jsonclassDefinationObj.setId(local_classTemplate.getId());
				jsonclassDefinationObj.setName(local_classTemplate.getName());
				jsonclassDefinationObj.setObjectStore(local_classTemplate.getObjectStore());
				classDefinationJson.add(jsonclassDefinationObj);
			}
			fileNetExportJson.setClassDefinitionTypeList(classDefinationJson);
			fileNetExportJson.setTriggerId(triggerId);
			fileNetExportJson.setEnviornment("");
			System.out.println(fileNetExportJson.getClassDefinitionTypeList().toString());
		}
		return fileNetExportJson;
	}

	private FileNetExport prepareOthers(FileNetExport fileNetExportJson, SavedManifest savedManifest,
			String triggerId) {
		if (savedManifest.getOthers().isEmpty()) {
			logger.info("Report Converted Successfully..!!");
		}
		List<SavedManifest.Others> othersTemplates = savedManifest.getOthers();
		for (SavedManifest.Others local_othersTemplates : othersTemplates) {
			List<SavedManifest.Others.Other> otherList = local_othersTemplates.getOther();
			List<FileNetExportOtherType> otherListJson = new ArrayList<>();
			for (SavedManifest.Others.Other local_otherTemplate : otherList) {
				FileNetExportOtherType jsonOtherObj = new FileNetExportOtherType();
				jsonOtherObj.setId(local_otherTemplate.getId());
				jsonOtherObj.setName(local_otherTemplate.getName());
				jsonOtherObj.setObjectStore(local_otherTemplate.getObjectStore());
				otherListJson.add(jsonOtherObj);
			}
			fileNetExportJson.setOtherTypeList(otherListJson);
			fileNetExportJson.setTriggerId(triggerId);
			fileNetExportJson.setEnviornment("");
		}
		return fileNetExportJson;
	}

	public FileNet convert(String expoertDataFilePath, String importDataFilePath, String triggerId, String exportFile,
			String importFile) {
		System.out.println("inside convert");
		try {
			System.out.println("inside convert try");
			logger.info("expoertDataFilePath is" + expoertDataFilePath);
			logger.info("importDataFilePath is" + importDataFilePath);
			System.out.println(triggerId + exportFile + importFile);
			if (!expoertDataFilePath.endsWith("NA")) {
				logger.info("expoertDataFilePath is available inside convert filenet");
				convertExport(expoertDataFilePath, triggerId, exportFile);
			}
			if (!importDataFilePath.endsWith("NA")) {
				logger.info("importDataFilePath is available  inside convert filenet");
				convertImport(importDataFilePath, triggerId, "PairConfig.xml");
			}
		} catch (Exception e) {
			logger.error("Conversion error for " + importDataFilePath + "and" + expoertDataFilePath + "Error: " + e);
		}
		return fileNetJsonObj;
	}
}
