
package com.infosys.utilities.fxcop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.infosys.utilities.fxcop.FxCopReport.Exceptions;
import com.infosys.utilities.fxcop.FxCopReport.Localized;
import com.infosys.utilities.fxcop.FxCopReport.Rules;
import com.infosys.utilities.fxcop.Targets.Target;
import com.infosys.utilities.fxcop.Targets.Target.Modules;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Messages;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Messages.Message;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Messages.Message.Issue;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces.Namespace;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces.Namespace.Types;
import com.infosys.utilities.fxcop.Targets.Target.Modules.Module.Namespaces.Namespace.Types.Type;

public class FxCopReportTest {

	@Test
	public void testEquals()
	{
		FxCopReport fxCopReport=new FxCopReport();
		
		Targets targets=new Targets();
		
		Rules rules=new Rules();
		
		
		Localized localized=new Localized();
		Exceptions exceptions=new Exceptions();
		Exceptions.Exception exception=new Exceptions.Exception();
		exception.setCategory("value");
		exception.setCheckId("value");
		exception.setExceptionMessage("value");
		exception.setKeyword("value");
		exception.setKind("value");
		exception.setStackTrace("value");
		exception.setTarget("value");
		exception.setType("value");
		exception.setTypeName("value");
		exceptions.setException(exception);
		
		fxCopReport.setExceptions(exceptions);
		fxCopReport.setLocalized(localized);
		fxCopReport.setRules(rules);
		fxCopReport.setTargets(targets);
		fxCopReport.setVersion((float)1);
		
		assertEquals((float)1,(float)fxCopReport.getVersion(),0.01);
		assertEquals(null,fxCopReport.getLocalized().string);
		assertEquals(null,fxCopReport.getRules().rule);
		assertEquals(null,fxCopReport.getTargets().target);
		assertEquals("value",fxCopReport.getExceptions().getException().getCategory());
		assertEquals("value",fxCopReport.getExceptions().getException().getCheckId());
		assertEquals("value",fxCopReport.getExceptions().getException().getExceptionMessage());
		assertEquals("value",fxCopReport.getExceptions().getException().getKeyword());
		assertEquals("value",fxCopReport.getExceptions().getException().getKind());
		assertEquals("value",fxCopReport.getExceptions().getException().getStackTrace());
		assertEquals("value",fxCopReport.getExceptions().getException().getTarget());
		assertEquals("value",fxCopReport.getExceptions().getException().getType());
		assertEquals("value",fxCopReport.getExceptions().getException().getTypeName());
		
		Target target=new Target();
		Modules modules=new Modules();
		
		target.setModules(modules);
		target.setName("value");
		assertEquals("value",target.getName());
		assertEquals(null,target.getModules().module);
		
		Module module=new Module();
		Messages messages=new Messages();
		
		Namespaces namespaces=new Namespaces();
		List<Namespace> namespaceList=new ArrayList<>();
		Namespace namespace=new Namespace();
		Types types=new Types();
		
		namespace.setName("name");
		namespace.setTypes(types);
		namespaceList.add(namespace);
		namespaces.setNamespace(namespaceList);
		module.setMessages(messages);
		module.setName("name");
		module.setNamespaces(namespaces);
		
		assertEquals("name", namespace.getName());
		assertEquals(null, namespace.getTypes().type);
		assertEquals(null,module.getMessages().message);
		assertEquals("name",module.getName());
		
		assertEquals(1,module.getNamespaces().getNamespace().size() );
		
		Message message=new Message();
		Issue issue=new Issue();
		issue.setCertainty(new Byte((byte) 1));
		issue.setLevel("value");
		issue.setName("value");
		issue.setValue("value");
		
		message.setCategory("value");
		message.setCheckId("value");
		message.setCreated("value");
		message.setFixCategory("value");
		message.setIssue(issue);
		message.setStatus("value");
		message.setTypeName("value");
		
		assertEquals("value", message.getCategory());
		assertEquals("value", message.getCheckId());
		assertEquals("value", message.getCreated());
		assertEquals("value", message.getFixCategory());
		assertEquals("value", message.getStatus());
		assertEquals("value", message.getTypeName());
		assertEquals("value", message.getIssue().getLevel());
		assertEquals("value", message.getIssue().getName());
		assertEquals("value", message.getIssue().getValue());
		assertEquals(new Byte((byte) 1),  message.getIssue().getCertainty());
		
		Type type=new Type();
		Type.Messages messages1=new Type.Messages();
		Type.Members members=new Type.Members();
		
		type.setAccessibility("value");
		type.setExternallyVisible("value");
		type.setKind("value");
		type.setName("value");
		type.setMembers(members);
		type.setMessages(messages1);
		
		assertEquals("value", type.getAccessibility());
		assertEquals("value", type.getExternallyVisible());
		assertEquals("value", type.getKind());
		assertEquals("value", type.getName());
		assertEquals(0, type.getMembers().getMember().size());
		assertEquals(null, type.getMessages().getMessage());
		
		Type.Messages.Message message1=new Type.Messages.Message();
		
		Type.Messages.Message.Issue issue1=new Type.Messages.Message.Issue();
		issue1.setCertainty(new Byte((byte) 1));
		issue1.setLevel("value");
		issue1.setName("value");
		issue1.setValue("value");
		
		message1.setCategory("value");
		message1.setCheckId("value");
		message1.setCreated("value");
		message1.setFixCategory("value");
		message1.setIssue(issue1);
		message1.setStatus("value");
		message1.setTypeName("value");
		
		assertEquals("value", message1.getCategory());
		assertEquals("value", message1.getCheckId());
		assertEquals("value", message1.getCreated());
		assertEquals("value", message1.getFixCategory());
		assertEquals("value", message1.getStatus());
		assertEquals("value", message1.getTypeName());
		assertEquals("value", message1.getIssue().getLevel());
		assertEquals("value", message1.getIssue().getName());
		assertEquals("value", message1.getIssue().getValue());
		assertEquals(new Byte((byte) 1),  message1.getIssue().getCertainty());
		
		Type.Members.Member member=new Type.Members.Member();
		Type.Members.Member.Messages messages2=new Type.Members.Member.Messages();
		
		member.setAccessibility("value");
		member.setExternallyVisible("value");
		member.setKind("value");
		member.setMessages(messages2);
		member.setName("value");
		member.setStatic("value");
		
		assertEquals("value", member.getAccessibility());
		assertEquals("value", member.getExternallyVisible());
		assertEquals("value", member.getKind());
		assertEquals("value", member.getName());
		assertEquals("value", member.getStatic());
		assertEquals(0, member.getMessages().getMessage().size());
		
		Type.Members.Member.Messages.Message message2=new Type.Members.Member.Messages.Message();
		
		
		message2.setCategory("value");
		message2.setCheckId("value");
		message2.setCreated("value");
		message2.setFixCategory("value");
		message2.setId("value");
		message2.setStatus("value");
		message2.setTypeName("value");
		
		assertEquals("value", message2.getCategory());
		assertEquals("value", message2.getCheckId());
		assertEquals("value", message2.getCreated());
		assertEquals("value", message2.getFixCategory());
		assertEquals("value", message2.getStatus());
		assertEquals("value", message2.getTypeName());
		assertEquals("value", message2.getId());
		assertEquals(0,  message2.getIssue().size());
		
		Type.Members.Member.Messages.Message.Issue issue2=new Type.Members.Member.Messages.Message.Issue();
		issue2.setCertainty(new Byte((byte) 1));
		issue2.setFile("value");
		issue2.setLevel("value");
		issue2.setLine(new Byte((byte) 1));
		issue2.setName("value");
		issue2.setPath("value");
		issue2.setValue("value");
		
		assertEquals(new Byte((byte) 1),issue2.getCertainty());
		assertEquals("value",issue2.getFile());
		assertEquals("value",issue2.getLevel());
		assertEquals("value",issue2.getName());
		assertEquals("value",issue2.getPath());
		assertEquals("value",issue2.getValue());
		assertEquals(new Byte((byte) 1),issue2.getLine());
		
		
	}
	@Test
	public void testNotEquals()
	{
		FxCopReport fxCopReport=new FxCopReport();
		
		Targets targets=new Targets();
		
		Rules rules=new Rules();
		
		
		Localized localized=new Localized();
		Exceptions exceptions=new Exceptions();
		Exceptions.Exception exception=new Exceptions.Exception();
		exception.setCategory("value");
		exception.setCheckId("value");
		exception.setExceptionMessage("value");
		exception.setKeyword("value");
		exception.setKind("value");
		exception.setStackTrace("value");
		exception.setTarget("value");
		exception.setType("value");
		exception.setTypeName("value");
		exceptions.setException(exception);
		
		fxCopReport.setExceptions(exceptions);
		fxCopReport.setLocalized(localized);
		fxCopReport.setRules(rules);
		fxCopReport.setTargets(targets);
		fxCopReport.setVersion((float)1);
		
		assertNotEquals((float)2,(float)fxCopReport.getVersion(),0.01);
		assertNotEquals(2,fxCopReport.getLocalized().string);
		assertNotEquals(2,fxCopReport.getRules().rule);
		assertNotEquals(2,fxCopReport.getTargets().target);
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getCategory());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getCheckId());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getExceptionMessage());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getKeyword());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getKind());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getStackTrace());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getTarget());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getType());
		assertNotEquals("value2",fxCopReport.getExceptions().getException().getTypeName());
		
		Target target=new Target();
		Modules modules=new Modules();
		
		target.setModules(modules);
		target.setName("value");
		assertNotEquals("value2",target.getName());
		assertNotEquals(2,target.getModules().module);
		
		Module module=new Module();
		Messages messages=new Messages();
		
		Namespaces namespaces=new Namespaces();
		List<Namespace> namespaceList=new ArrayList<>();
		Namespace namespace=new Namespace();
		Types types=new Types();
		
		namespace.setName("name");
		namespace.setTypes(types);
		namespaceList.add(namespace);
		namespaces.setNamespace(namespaceList);
		module.setMessages(messages);
		module.setName("name");
		module.setNamespaces(namespaces);
		
		assertNotEquals("name2", namespace.getName());
		assertNotEquals(2, namespace.getTypes().type);
		assertNotEquals(2,module.getMessages().message);
		assertNotEquals("name2",module.getName());
		
		assertEquals(1,module.getNamespaces().getNamespace().size() );
		
		Message message=new Message();
		Issue issue=new Issue();
		issue.setCertainty(new Byte((byte) 1));
		issue.setLevel("value");
		issue.setName("value");
		issue.setValue("value");
		
		message.setCategory("value");
		message.setCheckId("value");
		message.setCreated("value");
		message.setFixCategory("value");
		message.setIssue(issue);
		message.setStatus("value");
		message.setTypeName("value");
		
		assertNotEquals("value2", message.getCategory());
		assertNotEquals("value2", message.getCheckId());
		assertNotEquals("value2", message.getCreated());
		assertNotEquals("value2", message.getFixCategory());
		assertNotEquals("value2", message.getStatus());
		assertNotEquals("value2", message.getTypeName());
		assertNotEquals("value2", message.getIssue().getLevel());
		assertNotEquals("value2", message.getIssue().getName());
		assertNotEquals("value2", message.getIssue().getValue());
		assertNotEquals(new Byte((byte) 2),  message.getIssue().getCertainty());
		
		Type type=new Type();
		Type.Messages messages1=new Type.Messages();
		Type.Members members=new Type.Members();
		
		type.setAccessibility("value");
		type.setExternallyVisible("value");
		type.setKind("value");
		type.setName("value");
		type.setMembers(members);
		type.setMessages(messages1);
		
		assertNotEquals("value2", type.getAccessibility());
		assertNotEquals("value2", type.getExternallyVisible());
		assertNotEquals("value2", type.getKind());
		assertNotEquals("value2", type.getName());
		assertNotEquals(2, type.getMembers().getMember().size());
		assertNotEquals(2, type.getMessages().getMessage());
		
		Type.Messages.Message message1=new Type.Messages.Message();
		
		Type.Messages.Message.Issue issue1=new Type.Messages.Message.Issue();
		issue1.setCertainty(new Byte((byte) 1));
		issue1.setLevel("value");
		issue1.setName("value");
		issue1.setValue("value");
		
		message1.setCategory("value");
		message1.setCheckId("value");
		message1.setCreated("value");
		message1.setFixCategory("value");
		message1.setIssue(issue1);
		message1.setStatus("value");
		message1.setTypeName("value");
		
		assertNotEquals("value2", message1.getCategory());
		assertNotEquals("value2", message1.getCheckId());
		assertNotEquals("value2", message1.getCreated());
		assertNotEquals("value2", message1.getFixCategory());
		assertNotEquals("value2", message1.getStatus());
		assertNotEquals("value2", message1.getTypeName());
		assertNotEquals("value2", message1.getIssue().getLevel());
		assertNotEquals("value2", message1.getIssue().getName());
		assertNotEquals("value2", message1.getIssue().getValue());
		assertNotEquals(new Byte((byte) 2),  message1.getIssue().getCertainty());
		
		Type.Members.Member member=new Type.Members.Member();
		Type.Members.Member.Messages messages2=new Type.Members.Member.Messages();
		
		member.setAccessibility("value");
		member.setExternallyVisible("value");
		member.setKind("value");
		member.setMessages(messages2);
		member.setName("value");
		member.setStatic("value");
		
		assertNotEquals("value", member.getAccessibility());
		assertNotEquals("value2", member.getExternallyVisible());
		assertNotEquals("value2", member.getKind());
		assertNotEquals("value2", member.getName());
		assertNotEquals("value2", member.getStatic());
		assertNotEquals(2, member.getMessages().getMessage().size());
		
		Type.Members.Member.Messages.Message message2=new Type.Members.Member.Messages.Message();
		
		
		message2.setCategory("value");
		message2.setCheckId("value");
		message2.setCreated("value");
		message2.setFixCategory("value");
		message2.setId("value");
		message2.setStatus("value");
		message2.setTypeName("value");
		
		assertNotEquals("value2", message2.getCategory());
		assertNotEquals("value2", message2.getCheckId());
		assertNotEquals("value2", message2.getCreated());
		assertNotEquals("value2", message2.getFixCategory());
		assertNotEquals("value2", message2.getStatus());
		assertNotEquals("value2", message2.getTypeName());
		assertNotEquals("value2", message2.getId());
		assertNotEquals(2,  message2.getIssue().size());
		
		Type.Members.Member.Messages.Message.Issue issue2=new Type.Members.Member.Messages.Message.Issue();
		issue2.setCertainty(new Byte((byte) 1));
		issue2.setFile("value");
		issue2.setLevel("value");
		issue2.setLine(new Byte((byte) 1));
		issue2.setName("value");
		issue2.setPath("value");
		issue2.setValue("value");
		
		assertNotEquals(new Byte((byte) 1),issue2.getCertainty());
		assertNotEquals("value2",issue2.getFile());
		assertNotEquals("value2",issue2.getLevel());
		assertNotEquals("value2",issue2.getName());
		assertNotEquals("value2",issue2.getPath());
		assertNotEquals("value2",issue2.getValue());
		assertNotEquals(new Byte((byte) 2),issue2.getLine());
		
		
	}
}
