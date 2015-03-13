package com.collaborativeeclipse.cct.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.internal.TagInternal;
import com.collaborativeeclipse.cct.internal.TargetInternal;

public class TagPaser {
	private DocumentBuilder builder;

	public TagPaser() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
	}

	public String createXml(ITag tag) {
		Document document = builder.newDocument();
		Element root = document.createElement("tag");

		Element target = document.createElement("target");
		target.appendChild(document.createTextNode(tag.getTarget()
				.getHandleIdentifier()));
		root.appendChild(target);

		Element data = document.createElement("data");
		data.appendChild(document.createTextNode(tag.getData()));
		root.appendChild(data);

		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(bos);
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);

			return bos.toString();
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ITag parserXml(String xmlStr) {
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		try {
			Document doc = builder.parse(is);
			TagInternal tag = new TagInternal();

			NodeList values = doc.getChildNodes();
			for (int i = 0; i < values.getLength(); i++) {
				Node value = values.item(i);

				if ("target".equals(value.getNodeName())) {
					TargetInternal target = new TargetInternal();
					target.setHandleIdentifier(value.getTextContent());
					tag.setTarget(target);
				} else if ("data".equals(value.getNodeName())) {
					tag.setData(value.getTextContent());
				}
			}

			return tag;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
