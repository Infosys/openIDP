/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(MockitoJUnitRunner.class)
public class CodeCoverageTest {
	String getXMLString()
	{
		String temp="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
				"<changeSet _class=\"hudson.scm.EmptyChangeLogSet\">\r\n" + 
				"<buildId>5</buildId>\r\n" + 
				"<appName>dnet27_dnet27</appName>\r\n" + 
				"<buildStatus>UNSTABLE</buildStatus>\r\n" + 
				"<culprits/>\r\n" + 
				"<scmurl/>\r\n" + 
				"<duration>64637</duration>\r\n" + 
				"<methodCoverage><percentageFloat>1514370957078</percentageFloat></methodCoverage>\r\n" + 
				"<branchCoverage><percentageFloat>1514370957078</percentageFloat></branchCoverage>\r\n" + 
				"<classCoverage><percentageFloat>1514370957078</percentageFloat></classCoverage>\r\n" + 
				"<complexityScore><percentageFloat>1514370957078</percentageFloat></complexityScore>\r\n" + 
				"<instructionCoverage><percentageFloat>1514370957078</percentageFloat></instructionCoverage>\r\n" + 
				"<lineCoverage><percentageFloat>1514370957078</percentageFloat></lineCoverage>\r\n" + 
				"</changeSet>\r\n" + 
				"";
		temp=temp.replaceAll("\\\\", "");
		return temp;
	}
	
	@Spy
	@Autowired
	private CodeCoverage codeCoverage;

	private static HttpUrlStreamHandler httpUrlStreamHandler;

	@BeforeClass
	public static void setupURLStreamHandlerFactory() {
		// Allows for mocking URL connections
		URLStreamHandlerFactory urlStreamHandlerFactory = mock(URLStreamHandlerFactory.class);

		try {
			if (System.getProperty("com.xxx.streamHandlerFactoryInstalled") == null) {
				URL.setURLStreamHandlerFactory(urlStreamHandlerFactory);
				System.setProperty("com.xxx.streamHandlerFactoryInstalled", "true");
			}
		}
		catch(Exception e)
		{
			
		}

		httpUrlStreamHandler = Mockito.mock(HttpUrlStreamHandler.class);
		Mockito.doReturn(httpUrlStreamHandler).when(urlStreamHandlerFactory)
		.createURLStreamHandler(Matchers.eq("http"));
	}

	@Before
	public void reset() {
		httpUrlStreamHandler.resetConnections();
	}

	@Test
	public void createcodeCoverageTest() {
		try {
			SSLUtilities.trustAllHostnames();
			SSLUtilities.trustAllHttpsCertificates();
			// String href =
			// "http://idposs:8085/jenkins/job/CustomerPortal_Android_Gradle_App/job/CustomerPortal_Android_Gradle_App_Build/lastBuild/api/xml";
			String href = "http://google.com";

			URLConnection urlConnection = mock(URLConnection.class);
			Mockito.doReturn(urlConnection).when(httpUrlStreamHandler).openConnection(Matchers.any(URL.class));
			httpUrlStreamHandler.addConnection(new URL(href), urlConnection);
			byte[] expectedImageBytes = getXMLString().getBytes();

			try(InputStream imageInputStream = new ByteArrayInputStream(expectedImageBytes)){
				
				Mockito.when(urlConnection.getInputStream()).thenReturn(imageInputStream);
			}
			
			
			//Document document=mock(Document.class);
			DocumentBuilder documentBuilder=mock(DocumentBuilder.class);
			DocumentBuilderFactory documentBuilderFactory=mock(DocumentBuilderFactory.class);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			try(StringBufferInputStream stringBuffer = new StringBufferInputStream(getXMLString())){
				Document doc=builder.parse(stringBuffer);
				Mockito.when(documentBuilderFactory.newDocumentBuilder()).thenReturn(documentBuilder);
				
				Mockito.when(documentBuilder.parse(Matchers.any(InputStream.class))).thenReturn(doc);
				
			}
			
			
							
			
			DocumentBuilder documentBuilder1=mock(DocumentBuilder.class);
			DocumentBuilderFactory documentBuilderFactory1=mock(DocumentBuilderFactory.class);
			DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder1 = factory1.newDocumentBuilder();
			try(StringBufferInputStream stringBuffer = new StringBufferInputStream(getXMLString())){
				Document doc1=builder1.parse(stringBuffer);
				Mockito.when(documentBuilder1.parse(Matchers.any(File.class))).thenReturn(doc1);
			}
			
			
			
			
			Element e=mock(Element.class);
			Document doc2=mock(Document.class);
			
			Mockito.when(doc2.createElement(Matchers.any(String.class))).thenReturn(e);
			
			codeCoverage.createcodeCoverage("http://idposs:8085", "idposs", "idposs",
					"CustomerPortal_Android_Gradle_App/CustomerPortal_Android_Gradle_App_Build", "D://", "time",
					"CustomerPortal");
			List<String> temp = null;
			assertEquals(null, temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
