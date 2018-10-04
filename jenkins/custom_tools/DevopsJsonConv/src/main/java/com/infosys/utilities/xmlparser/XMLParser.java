/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.xmlparser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class XMLParser<T> {
	static {
		System.setProperty("javax.xml.accessExternalDTD", "");
	}
	private JAXBContext jax;
	private XMLReader saxxmlReader;
	private final Class<T> typeParameterClass;

	public XMLParser(Class<T> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
		this.jax = build();
		this.saxxmlReader = buildSAXXMLReaderInstance();
	}

	@SuppressWarnings("unchecked")
	public T parse(File file) throws IOException {
		FileReader f1 = null;
		try {
			f1 = new FileReader(file.getPath());
			InputSource is = new InputSource(f1);
			SAXSource source = new SAXSource(saxxmlReader, is);
			return (T) createUnmarshaller().unmarshal(source);
		} catch (Exception e) {
			throw new ParserException("Cannot parse " + file.getName(), e);
		} finally {
			if (f1 != null) {
				f1.close();
			}
		}
	}

	private Unmarshaller createUnmarshaller() throws JAXBException {
		jax = build();
		return jax.createUnmarshaller();
	}

	private JAXBContext build() {
		try {
			return JAXBContext.newInstance(typeParameterClass);
		} catch (JAXBException e) {
			throw new ParserException("Cannot create Parser instance", e);
		}
	}

	private XMLReader buildSAXXMLReaderInstance() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			spf.setFeature("http://xml.org/sax/features/validation", false);
			return spf.newSAXParser().getXMLReader();
		} catch (Exception e) {
			throw new ParserException("Cannot create Parser instance", e);
		}
	}
}
