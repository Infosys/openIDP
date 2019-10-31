package com.infosys.convertor;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.JsonClass;

import com.infosys.utilities.fortify.ReportDefinition;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection.SubSection;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection.SubSection.IssueListing;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection.SubSection.IssueListing.Chart;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection.SubSection.IssueListing.Chart.GroupingSection;
import com.infosys.utilities.fortify.ReportDefinition.ReportSection.SubSection.IssueListing.Chart.GroupingSection.Issue;
import com.infosys.utilities.sahi.Suites;

public class ConvertFortify {
	private static final Logger logger = Logger.getLogger(ConvertFortify.class);

	public static List<CodeAnalysis> convert(String inputPath, JsonClass json, List<CodeAnalysis> listCA) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;

		try {
			jaxbContext = JAXBContext.newInstance(ReportDefinition.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ReportDefinition report = (ReportDefinition) jaxbUnmarshaller.unmarshal(file);
			List<ReportSection> reportSection = report.getReportSection();
			for (int i = 0; i < reportSection.size(); i++) {
				logger.info(reportSection.get(i).getTitle());
				if (reportSection.get(i).getTitle() != null) {
					if (reportSection.get(i).getTitle().equalsIgnoreCase("Results Outline")) {
						List<SubSection> subSection = reportSection.get(i).getSubSection();
						for (int j = 0; j < subSection.size(); j++) {
							if (subSection.get(j).getTitle() != null) {
								if (subSection.get(j).getTitle()
										.equalsIgnoreCase("Vulnerability Examples by Category")) {
									IssueListing issueList = subSection.get(j).getIssueListing();
									Chart chart = issueList.getChart();
									List<GroupingSection> groupingSection = chart.getGroupingSection();
									for (int k = 0; k < groupingSection.size(); k++) {
										List<Issue> issue = groupingSection.get(k).getIssue();
										for (int l = 0; l < issue.size(); l++) {

											if (issue != null) {
												CodeAnalysis codeAnalysis = new CodeAnalysis();
												if (issue.get(l).getIid() != null) {
													codeAnalysis.setId(issue.get(l).getIid());
												}
												if (issue.get(l).getFriority() != null) {
													logger.info("severity "+issue.get(l).getFriority());
													if(issue.get(l).getFriority().equalsIgnoreCase("Critical")) {
														codeAnalysis.setSeverity("High");
													}
													else {
														codeAnalysis.setSeverity(issue.get(l).getFriority());
													}
													logger.info("codeAnalysis.getSeverity "+ codeAnalysis.getSeverity());
												}
												if (issue.get(l).getAbstract() != null) {
													codeAnalysis.setMessage(issue.get(l).getAbstract());
												}
//										if(issue.get(l).getPrimary().getLineStart()!=null ) {
//										
//											codeAnalysis.setLine(new String(issue.get(l).getPrimary().getLineStart());
//										}
//												if (issue.get(l).getRuleID() != null) {
//													codeAnalysis.setRuleName(issue.get(l).getRuleID());											
//												}
												if (issue.get(l).getCategory() != null) {
													codeAnalysis.setRuleName(issue.get(l).getCategory());
												logger.info("rule name"+codeAnalysis.getRuleName());	
												}
												codeAnalysis.setCategory("Fortify");
												if (issue.get(l).getKingdom() != null) {
													codeAnalysis.setRecommendation(issue.get(l).getKingdom());
												}
												if (issue.get(l).getPrimary().getFilePath() != null) {
													codeAnalysis.setClassName(issue.get(l).getPrimary().getFilePath());
												}
												codeAnalysis.setType("Security");
												listCA.add(codeAnalysis);
											}

										}

									}
								}
							}
						}
					}
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return listCA;
	}
}
