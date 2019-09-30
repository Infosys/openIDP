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
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandlerFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CopyFolderUtilityTest {
	@Spy
	@InjectMocks
	private CopyFolderUtility copyFolderUtility;

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

		httpUrlStreamHandler = new HttpUrlStreamHandler();
		given(urlStreamHandlerFactory.createURLStreamHandler("http")).willReturn(httpUrlStreamHandler);
	}

	@Before
	public void reset() {
		httpUrlStreamHandler.resetConnections();
	}
	
	@Test
	public void copyFolderTest() {
		try {
			SSLUtilities.trustAllHostnames();
			SSLUtilities.trustAllHttpsCertificates();
			
			String href = "https://google.com";

			URLConnection urlConnection = mock(URLConnection.class);
			httpUrlStreamHandler.addConnection(new URL(href), urlConnection);
			byte[] expectedImageBytes = "Any String you want".getBytes();
			InputStream imageInputStream = new ByteArrayInputStream(expectedImageBytes);
			Mockito.when(urlConnection.getInputStream()).thenReturn(imageInputStream);
			
			Mockito.when((Matchers.any(File.class)).isFile()).thenReturn(true);
			
			File file = new File("test");
			File file1 = new File("test");
			Boolean temp=copyFolderUtility.copyFolder(file, file1, "string", "string", "string");
			assertEquals(false,temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
