/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;


import com.infosys.json.JsonClass;
import com.infosys.jsonschema.SCMInfo;
import com.infosys.utilities.changeset.ChangeSet;
import com.infosys.utilities.changeset.ChangeSet.Item;

public class ConvertSCMInfo {
	private static final Logger logger = Logger.getLogger(ConvertSCMInfo.class);

	private ConvertSCMInfo() {
	}

	public static JsonClass convert(String inputPath, JsonClass jsonClass, String app) {
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(ChangeSet.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ChangeSet c = (ChangeSet) jaxbUnmarshaller.unmarshal(file);
			List<SCMInfo> listInfo = null;
			if (c.getItem() != null) {
				List<ChangeSet.Item> item = c.getItem();
				listInfo = iterateItem(item, app, c);
			}
			jsonClass.setScmInfo(listInfo);	
			
			logger.info("Report Converted Successfully..!!");
			logger.info(jsonClass.toString());
			System.out.println(jsonClass.toString());
			return jsonClass;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return jsonClass;
	}

	private static List<SCMInfo> iterateItem(List<ChangeSet.Item> item, String app,
			ChangeSet c) {
		List<SCMInfo> listInfo = new ArrayList<>();
		try {
			
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			for (ChangeSet.Item i : item) {
				if (i.getAffectedPath() == null)
					continue;
				List<String> clas = i.getAffectedPath();
				boolean flag = false;
				for (String cls : clas) {
					if (cls != null) {
						flag = true;
						break;
					}
				}
				// Java file has been modified
				if (flag) {
					SCMInfo bi = getSCMInfoObject();
					setDateFunc(dateFormatter, i, bi);
					setCommitMsgFunc(i, bi);
					if (bi.getCommitMessage().equals("")) {
						bi.setCommitMessage("File Checked In");
					}
					setIdFunc(i, bi, app);
					setUserFunc(i, bi);
					setVersionFunc(i, bi);
					setRemoteUrlFunc(c, bi, app);
					
					setAffectedPath(i,bi) ;
					setscmUrl( c,  bi,  app);
					
					listInfo.add(bi);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in SCM info conversion");
		}
		if (listInfo.isEmpty())
			return null;
		return listInfo;
	}

	private static void setscmUrl(ChangeSet c, SCMInfo bi, String app) {
		try {
			if (c.getScmurl() != null) {
				
				System.out.println(c.getScmurl());
				bi.setScmUrl(c.getScmurl());
				
			}
		} catch (Exception e) {
			System.out.println("Exception in URL generation for SCM" + e.getMessage());
		}
	}

	private static void setRemoteUrlFunc(ChangeSet c, SCMInfo bi, String app) {
		try {
			if (c.getScmurl() != null) {
				String temp = "";
				String temp2 = "";
				temp = "commits/" + bi.getId().replace(app + "_", "");
				temp = c.getScmurl().substring(0, c.getScmurl().lastIndexOf('.')) + "/" + temp + "/";
				temp2 = temp.substring(0, temp.indexOf("@") + 1);
				temp = temp.replace(temp2, "http://");
				
				System.out.println(temp);
				System.out.println(c.getScmurl());
				bi.setRemoteUrl(temp);
				System.out.println("SCM URL added");
			}
		} catch (Exception e) {
			System.out.println("Exception in URL generation for SCM" + e.getMessage());
		}
	}

	private static SCMInfo getSCMInfoObject() {
		return new SCMInfo();
	}

	private static void setDateFunc(DateFormat dateFormatter, Item i, SCMInfo bi) {
		if (i.getTimestamp() != null) {
			Date date = new Date(i.getTimestamp());
			bi.setLastModified(dateFormatter.format(date));
		}
	}

	private static void setCommitMsgFunc(Item i, SCMInfo bi) {
		if (i.getComment() != null) {
			bi.setCommitMessage(i.getComment());
		}
	}

	private static void setIdFunc(Item i, SCMInfo bi, String app) {
		if (i.getCommitId() != null)
			bi.setId(app + "_" + i.getCommitId());
		else
			bi.setId(app);
	}

	private static void setUserFunc(Item i, SCMInfo bi) {
		if (i.getAuthor().getFullName() != null) {
			bi.setLastModifiedBy(i.getAuthor().getFullName());
		}
	}

	private static void setAffectedPath(Item i, SCMInfo bi) {
		if (i.getAffectedPath()!= null) {
			
			List<String> clas = i.getAffectedPath();
//			boolean flag = false;
			for (String cls : clas) {

				System.out.println(cls);
					bi.setGetAffectedPath(cls);				}
			
		}
	}
	
	
	private static void setVersionFunc(Item i, SCMInfo bi) {
		if (i.getCommitId() != null)
			bi.setLatestFileVersion(i.getCommitId());
	}
}
