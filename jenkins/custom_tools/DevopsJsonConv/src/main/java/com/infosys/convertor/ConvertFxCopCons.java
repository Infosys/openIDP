/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.infosys.json.FxCopReportJson;
import com.infosys.utilities.fxcop.FxCopReport;

public class ConvertFxCopCons {

	private static Integer high = 0;
	private static Integer medium = 0;
	private static Integer low = 0;

	/**
	 * 
	 * returns fxcopreportjson type object after parsing fxcopcons report
	 * 
	 * @param inputPath
	 * @param json
	 * @return
	 */
	public static FxCopReportJson convert(String inputPath) {

		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FxCopReport.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FxCopReportJson fxj = new FxCopReportJson();
			FxCopReport c = (FxCopReport) jaxbUnmarshaller.unmarshal(file);

			List<FxCopReport.Targets.Target> t1 = c.getTargets().getTarget();

			for (FxCopReport.Targets.Target t2 : t1) {
				if (t2.getModules() == null || t2.getModules().getModule() == null)
					continue;
				List<FxCopReport.Targets.Target.Modules.Module> mod1 = t2.getModules().getModule();
				for (FxCopReport.Targets.Target.Modules.Module mod2 : mod1) {
					if (mod2.getMessages() != null) {
						List<FxCopReport.Targets.Target.Modules.Module.Messages.Message> msg1 = mod2.getMessages()
								.getMessage();
						for (FxCopReport.Targets.Target.Modules.Module.Messages.Message msg2 : msg1) {
							FxCopReport.Targets.Target.Modules.Module.Messages.Message.Issue iss = msg2.getIssue();
							String lvl = iss.getLevel();
							if (lvl.equalsIgnoreCase("criticalerror") || lvl.equalsIgnoreCase("error")) {
								high++;

							} else if (lvl.equalsIgnoreCase("warning")) {
								low++;

							} else if (lvl.equalsIgnoreCase("criticalwarning")) {
								medium++;

							}
						}
					}

					if (mod2.getNamespaces() != null) {
						List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace> nsp1 = mod2.getNamespaces()
								.getNamespace();
						for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace nsp2 : nsp1) {
							if (nsp2.getTypes() != null) {
								List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type> typ1 = nsp2
										.getTypes().getType();
								for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type typ2 : typ1) {
									if (typ2.getMessages() != null) {
										List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message> nmsg1 = typ2
												.getMessages().getMessage();
										for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message nmsg2 : nmsg1) {
											FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message.Issue iss = nmsg2
													.getIssue();

											String lvl1 = iss.getLevel();
											if (lvl1.equalsIgnoreCase("criticalerror")
													|| lvl1.equalsIgnoreCase("error")) {
												high++;

											} else if (lvl1.equalsIgnoreCase("warning")) {
												low++;

											} else if (lvl1.equalsIgnoreCase("criticalwarning")) {
												medium++;

											}
										}

									}
									if (typ2.getMembers() != null) {
										List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member> nmbr1 = typ2
												.getMembers().getMember();
										for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member nmbr2 : nmbr1) {
											if (nmbr2.getMessages() != null) {
												List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message> nmmsg1 = nmbr2
														.getMessages().getMessage();
												for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message nmmsg2 : nmmsg1) {
													List<FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue> iss1 = nmmsg2
															.getIssue();
													for (FxCopReport.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue iss2 : iss1) {
														String lvl2 = iss2.getLevel();
														if (lvl2.equalsIgnoreCase("criticalerror")
																|| lvl2.equalsIgnoreCase("error")) {
															high++;

														} else if (lvl2.equalsIgnoreCase("warning")) {
															low++;

														} else if (lvl2.equalsIgnoreCase("criticalwarning")) {
															medium++;

														}
													}
												}

											}

										}
									}
								}
							}
						}
					}
				}

			}
			fxj.setHigh(high);
			fxj.setLow(low);
			fxj.setMedium(medium);
			return fxj;

		} catch (Exception e) {

			FxCopReportJson fj = new FxCopReportJson();
			fj = null;
			return fj;
		}

	}
}
