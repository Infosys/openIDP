/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.JsonClass;
import com.infosys.utilities.fxcop.FxCopReport;
import com.infosys.utilities.fxcop.Targets;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Messages.Message;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Messages.Message.Issue;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces.Namespace;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member;

public class ConvertFxCop {
	private static final Logger logger = Logger.getLogger(ConvertCoverage.class);
	private static final String CRITICALERROR = "criticalerror";
	private static final String CRITICALWARNING = "criticalwarning";
	private static final String ERROR = "error";
	private static final String FXCOP = "fxCop";
	private static final String MEDIUM = "medium";
	private static final String HIGH = "high";
	private static final String LOW = "low";

	private ConvertFxCop() {
	}

	public static List<CodeAnalysis> convert(String inputPath) {
		List<CodeAnalysis> ca = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(FxCopReport.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			FxCopReport c = (FxCopReport) jaxbUnmarshaller.unmarshal(file);
			if (c.getTargets() == null || c.getTargets().getTarget() == null) {
				logger.info("Report Converted Successfully..!!");
				return ca;
			}
			List<Targets.Target> t1 = c.getTargets().getTarget();
			for (Targets.Target t2 : t1) {
				if (t2.getModules() == null || t2.getModules().getModule() == null)
					continue;
				List<Targets.Target.Modules.Module> mod1 = t2.getModules().getModule();
				iterateModule(mod1, ca);
			}
			logger.info("Report Converted Successfully..!!");
			return ca;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return ca;
	}

	private static void iterateModule(List<Module> mod1, List<CodeAnalysis> ca) {
		for (Targets.Target.Modules.Module mod2 : mod1) {
			if (mod2.getMessages() != null && mod2.getMessages().getMessage() != null) {
				List<Targets.Target.Modules.Module.Messages.Message> m1 = mod2.getMessages().getMessage();
				iterateMessage(m1, mod2, ca);
			}
			if (mod2.getNamespaces() != null && mod2.getNamespaces().getNamespace() != null) {
				List<Targets.Target.Modules.Module.Namespaces.Namespace> nam1 = mod2.getNamespaces()
						.getNamespace();
				iterateNS(mod2, nam1, ca);
			}
		}
	}

	private static void iterateNS(Module mod2, List<Namespace> nam1, List<CodeAnalysis> ca) {
		for (Targets.Target.Modules.Module.Namespaces.Namespace nam2 : nam1) {
			if (!(nam2.getTypes() != null && nam2.getTypes().getType() != null))
				continue;
			List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type> typ1 = nam2.getTypes()
					.getType();
			for (Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type typ2 : typ1) {
				if ((typ2.getMessages() != null && typ2.getMessages().getMessage() != null)) {
					List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message> msg1 = typ2
							.getMessages().getMessage();
					for (Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message msg2 : msg1) {
						if (msg2.getIssue() == null)
							continue;
						Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Messages.Message.Issue i1 = msg2
								.getIssue();
						////
						CodeAnalysis c1 = getCodeAnalysisObject();
						String lev = i1.getLevel();
						if (mod2.getName() != null) {
							String name = mod2.getName();
							Integer pos = name.lastIndexOf('.');
							if (pos != -1) {
								name = name.substring(0, pos);
							}
							c1.setId(name.replace(".", "_"));
						}
						c1.setMessage(i1.getValue());
						c1.setcategory(FXCOP);
						if (msg2.getCategory() != null) {
							c1.setruleName(msg2.getCategory());
						}
						if (lev.toLowerCase().contains(ERROR) || lev.toLowerCase().contains(CRITICALERROR)) {
							c1.setSeverity(HIGH);
						} else if (lev.equalsIgnoreCase(CRITICALWARNING)) {
							c1.setSeverity(MEDIUM);
						} else {
							c1.setSeverity(LOW);
						}
						updateCA(c1, ca);
						////
					}
				}
				/////
				if (!(typ2.getMembers() != null && typ2.getMembers().getMember() != null))
					continue;
				List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member> mem1 = typ2
						.getMembers().getMember();
				iterateMembers(mod2, mem1, ca);
			}
		}
	}

	private static void iterateMembers(Module mod2, List<Member> mem1, List<CodeAnalysis> ca) {
		for (Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member mem2 : mem1) {
			if (!(mem2.getMessages() != null && mem2.getMessages().getMessage() != null))
				continue;
			List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message> msg1 = mem2
					.getMessages().getMessage();
			for (Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message msg2 : msg1) {
				if (msg2.getIssue() == null)
					continue;
				List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue> i1 = msg2
						.getIssue();
				iterateIssues(mod2, i1, msg2, ca);
			}
		}
	}

	private static void iterateIssues(Module mod2,
			List<Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue> i1,
			Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message msg2,
			List<CodeAnalysis> ca) {
		for (Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type.Members.Member.Messages.Message.Issue i2 : i1) {
			CodeAnalysis c1 = getCodeAnalysisObject();
			String lev = i2.getLevel();
			if (mod2.getName() != null) {
				String name = mod2.getName();
				Integer pos = name.lastIndexOf('.');
				if (pos != -1) {
					name = name.substring(0, pos);
				}
				c1.setId(name.replace(".", "_"));
			}
			c1.setMessage(i2.getValue());
			c1.setcategory(FXCOP);
			if (i2.getLine() != null) {
				c1.setLine(i2.getLine().toString());
			}
			if (msg2.getCategory() != null) {
				c1.setruleName(msg2.getCategory());
			}
			if (lev.toLowerCase().contains(ERROR) || lev.toLowerCase().contains(CRITICALERROR)) {
				c1.setSeverity(HIGH);
			} else if (lev.equalsIgnoreCase(CRITICALWARNING)) {
				c1.setSeverity(MEDIUM);
			} else {
				c1.setSeverity(LOW);
			}
			updateCA(c1, ca);
		}
	}

	private static CodeAnalysis getCodeAnalysisObject() {
		return new CodeAnalysis();
	}

	private static void updateCA(CodeAnalysis c1, List<CodeAnalysis> ca) {
		int flag = 0;
		if (!ca.isEmpty()) {
			for (CodeAnalysis i : ca) {
				if (i.getId().equals(c1.getId()) && i.getMessage().equals(c1.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {
			ca.add(c1);
		}
	}

	private static void iterateMessage(List<Message> m1, Module mod2, List<CodeAnalysis> ca) {
		for (Targets.Target.Modules.Module.Messages.Message m2 : m1) {
			Issue i2 = m2.getIssue();
			CodeAnalysis c1 = getCodeAnalysisObject();
			String lev = i2.getLevel();
			if (mod2.getName() != null) {
				String name = mod2.getName();
				Integer pos = name.lastIndexOf('.');
				if (pos != -1) {
					name = name.substring(0, pos);
				}
				c1.setId(name.replace(".", "_"));
			}
			c1.setMessage(i2.getValue());
			c1.setcategory(FXCOP);
			c1.setLine(null);
			if (m2.getCategory() != null) {
				c1.setruleName(m2.getCategory());
			}
			if (lev.toLowerCase().contains(ERROR) || lev.toLowerCase().contains(CRITICALERROR)) {
				c1.setSeverity(HIGH);
			} else if (lev.equalsIgnoreCase(CRITICALWARNING)) {
				c1.setSeverity(MEDIUM);
			} else {
				c1.setSeverity(LOW);
			}
			addCA(ca, c1);
		}
	}

	private static void addCA(List<CodeAnalysis> caList, CodeAnalysis c1) {
		int flag = 0;
		if (!caList.isEmpty()) {
			for (CodeAnalysis i : caList) {
				if (i.getId().equals(c1.getId()) && i.getMessage().equals(c1.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {
			caList.add(c1);
		}
	}

	public static void main(String[] args) throws IOException {
		List<CodeAnalysis> c ;
		c = ConvertFxCop.convert("D:\\CIWorkspace\\workspace\\AnalysisResult_fxCop.xml");
		JsonClass json = new JsonClass();
		json.setCodeAnalysis(c);
		ObjectMapper mapper = new ObjectMapper();
		// Object to JSON in file
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("D:\\check.json"), json);
	}
}
