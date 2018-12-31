
package com.infosys.utilities.coveragejacoco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.infosys.utilities.coveragejacoco.Report.Sessioninfo;

public class ReportTest {

	@Test
	public void testEquals()
	{
		Report report=new Report();
		
		Sessioninfo sessioninfo=new Sessioninfo();
		
		sessioninfo.setDump(BigDecimal.valueOf(1.0));
		sessioninfo.setId("id");
		sessioninfo.setStart(BigDecimal.valueOf(1.0));
		
		report.setName("name");
		report.setSessioninfo(sessioninfo);
		
		assertEquals("name",report.getName());
		assertEquals(BigDecimal.valueOf(1.0),report.getSessioninfo().getDump());
		assertEquals("id",report.getSessioninfo().getId());
		assertEquals(BigDecimal.valueOf(1.0),report.getSessioninfo().getDump());
		assertEquals(0,report.getCounter().size());
		assertEquals(0,report.getPackage().size());
		
		Report.Package packageO=new Report.Package();
		packageO.setName("name");
		assertEquals(0, packageO.getClazz().size());
		assertEquals(0, packageO.getCounter().size());
		assertEquals(0, packageO.getSourcefile().size());
		assertEquals("name", packageO.getName());
		
		Report.Package.Class clazz=new Report.Package.Class();
		clazz.setName("name");
		
		assertEquals("name", clazz.getName());
		assertEquals(0,clazz.getCounter().size() );
		assertEquals(0,clazz.getMethod().size() );
		
		Report.Package.Class.Method method=new Report.Package.Class.Method();
		method.setDesc("desc");
		method.setLine(1);
		method.setName("name");
		
		assertEquals("desc", method.getDesc());
		assertEquals((Integer)1, method.getLine());
		assertEquals("name", method.getName());
		assertEquals(0,method.getCounter().size());
		
		Report.Package.Class.Method.Counter counter=new Report.Package.Class.Method.Counter();
		
		counter.setCovered(1);
		counter.setMissed(1);
		counter.setType("type");
		
		assertEquals((Integer)1, counter.getCovered());
		assertEquals((Integer)1, counter.getMissed());
		assertEquals("type", counter.getType());
		
		Report.Package.Class.Counter counter1=new Report.Package.Class.Counter();
		counter1.setCovered(1);
		counter1.setMissed(1);
		counter1.setType("type");
		
		assertEquals((Integer)1, counter1.getCovered());
		assertEquals((Integer)1, counter1.getMissed());
		assertEquals("type", counter1.getType());
		
		Report.Package.Sourcefile sourcefile=new Report.Package.Sourcefile();
		sourcefile.setName("name");
		
		assertEquals("name", sourcefile.getName());
		assertEquals(0, sourcefile.getCounter().size());
		assertEquals(0, sourcefile.getLine().size());
		
		Report.Package.Sourcefile.Line line=new Report.Package.Sourcefile.Line();
		line.setCb(1);
		line.setCi(1);
		line.setMb(1);
		line.setMi(1);
		line.setNr(1);
		assertEquals((Integer)1, line.getCb());
		assertEquals((Integer)1, line.getCi());
		assertEquals((Integer)1, line.getMb());
		assertEquals((Integer)1, line.getMi());
		assertEquals((Integer)1, line.getNr());
		
		Report.Package.Sourcefile.Counter counter2=new Report.Package.Sourcefile.Counter();
		counter2.setCovered(1);
		counter2.setMissed(1);
		counter2.setType("type");
		
		assertEquals((Integer)1, counter2.getCovered());
		assertEquals((Integer)1, counter2.getMissed());
		assertEquals("type", counter2.getType());
		
		Report.Package.Counter counter3=new Report.Package.Counter();
		counter3.setCovered(1);
		counter3.setMissed(1);
		counter3.setType("type");
		
		assertEquals((Integer)1, counter3.getCovered());
		assertEquals((Integer)1, counter3.getMissed());
		assertEquals("type", counter3.getType());
		
		Report.Counter counter4=new Report.Counter();
		counter4.setCovered(1);
		counter4.setMissed(1);
		counter4.setType("type");
		
		assertEquals((Integer)1, counter4.getCovered());
		assertEquals((Integer)1, counter4.getMissed());
		assertEquals("type", counter4.getType());
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		Report report=new Report();
		
		Sessioninfo sessioninfo=new Sessioninfo();
		
		sessioninfo.setDump(BigDecimal.valueOf(1.0));
		sessioninfo.setId("id");
		sessioninfo.setStart(BigDecimal.valueOf(1.0));
		
		report.setName("name");
		report.setSessioninfo(sessioninfo);
		
		assertNotEquals("name2",report.getName());
		assertNotEquals(BigDecimal.valueOf(1.2),report.getSessioninfo().getDump());
		assertNotEquals("id2",report.getSessioninfo().getId());
		assertNotEquals(BigDecimal.valueOf(1.2),report.getSessioninfo().getDump());
		assertNotEquals(2,report.getCounter().size());
		assertNotEquals(2,report.getPackage().size());
		
		Report.Package packageO=new Report.Package();
		packageO.setName("name");
		assertNotEquals(2, packageO.getClazz().size());
		assertNotEquals(2, packageO.getCounter().size());
		assertNotEquals(2, packageO.getSourcefile().size());
		assertNotEquals("name2", packageO.getName());
		
		Report.Package.Class clazz=new Report.Package.Class();
		clazz.setName("name");
		
		assertNotEquals("name2", clazz.getName());
		assertNotEquals(2,clazz.getCounter().size() );
		assertNotEquals(2,clazz.getMethod().size() );
		
		Report.Package.Class.Method method=new Report.Package.Class.Method();
		method.setDesc("desc");
		method.setLine(1);
		method.setName("name");
		
		assertNotEquals("desc2", method.getDesc());
		assertNotEquals((Integer)2, method.getLine());
		assertNotEquals("name2", method.getName());
		assertNotEquals(2,method.getCounter().size());
		
		Report.Package.Class.Method.Counter counter=new Report.Package.Class.Method.Counter();
		
		counter.setCovered(2);
		counter.setMissed(2);
		counter.setType("type2");
		
		assertNotEquals((Integer)2, counter.getCovered());
		assertNotEquals((Integer)2, counter.getMissed());
		assertNotEquals("type2", counter.getType());
		
		Report.Package.Class.Counter counter1=new Report.Package.Class.Counter();
		counter1.setCovered(1);
		counter1.setMissed(1);
		counter1.setType("type");
		
		assertNotEquals((Integer)2, counter1.getCovered());
		assertNotEquals((Integer)2, counter1.getMissed());
		assertNotEquals("type2", counter1.getType());
		
		Report.Package.Sourcefile sourcefile=new Report.Package.Sourcefile();
		sourcefile.setName("name");
		
		assertNotEquals("name2", sourcefile.getName());
		assertNotEquals(2, sourcefile.getCounter().size());
		assertNotEquals(2, sourcefile.getLine().size());
		
		Report.Package.Sourcefile.Line line=new Report.Package.Sourcefile.Line();
		line.setCb(1);
		line.setCi(1);
		line.setMb(1);
		line.setMi(1);
		line.setNr(1);
		assertNotEquals((Integer)2, line.getCb());
		assertNotEquals((Integer)2, line.getCi());
		assertNotEquals((Integer)2, line.getMb());
		assertNotEquals((Integer)2, line.getMi());
		assertNotEquals((Integer)2, line.getNr());
		
		Report.Package.Sourcefile.Counter counter2=new Report.Package.Sourcefile.Counter();
		counter2.setCovered(1);
		counter2.setMissed(1);
		counter2.setType("type");
		
		assertNotEquals((Integer)2, counter2.getCovered());
		assertNotEquals((Integer)2, counter2.getMissed());
		assertNotEquals("type2", counter2.getType());
		
		Report.Package.Counter counter3=new Report.Package.Counter();
		counter3.setCovered(1);
		counter3.setMissed(1);
		counter3.setType("type");
		
		assertNotEquals((Integer)2, counter3.getCovered());
		assertNotEquals((Integer)2, counter3.getMissed());
		assertNotEquals("type2", counter3.getType());
		
		Report.Counter counter4=new Report.Counter();
		counter4.setCovered(1);
		counter4.setMissed(1);
		counter4.setType("type");
		
		assertNotEquals((Integer)2, counter4.getCovered());
		assertNotEquals((Integer)2, counter4.getMissed());
		assertNotEquals("type2", counter4.getType());
		
		
	}
}
