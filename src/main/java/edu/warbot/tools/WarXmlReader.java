package edu.warbot.tools;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WarXmlReader {

    public static Document openXmlFile(String filePath) throws ParserConfigurationException, SAXException, IOException {
        Document doc;
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = docBuilder.parse(new InputSource(new StringReader(trimXmlFile(new File(filePath)))));
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static Document getDocumentFromInputStream(InputStream input) throws SAXException, IOException, ParserConfigurationException {
        Document doc;
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        doc = docBuilder.parse(new InputSource(new StringReader(trimXmlFile(input))));
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static String trimXmlFile(File file) {
        try {
            return trimXmlFile(new InputStreamReader(new FileInputStream(file), "UTF8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String trimXmlFile(InputStream input) {
        try {
            return trimXmlFile(new InputStreamReader(input, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String trimXmlFile(Reader reader) {
        StringBuffer result = new StringBuffer();
        try {
            BufferedReader buffReader = new BufferedReader(reader);
            String line;
            while ((line = buffReader.readLine()) != null)
                result.append(line.trim());
            buffReader.close();
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getFirstStringResultOfXPath(Document document, String expressionXPath) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return xPath.compile(expressionXPath).evaluate(document).trim();
        } catch (XPathExpressionException e) {
            System.err.println("Wrong XPath : " + expressionXPath);
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getAllStringResultOfXPath(Document document, String expressionXPath) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        ArrayList<String> toReturn = new ArrayList<>();
        try {
            NodeList nodes = (NodeList) xPath.compile(expressionXPath).evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++)
                toReturn.add(nodes.item(i).getFirstChild().getNodeValue());
        } catch (XPathExpressionException e) {
            System.err.println("Wrong XPath : " + expressionXPath);
            e.printStackTrace();
        }
        return toReturn;
    }

    public static HashMap<String, String> getNodesFromXPath(Document document, String xPathExpression) {
        HashMap<String, String> toReturn = new HashMap<>();
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList ln = (NodeList) xPath.compile(xPathExpression).evaluate(document, XPathConstants.NODESET);
            ln = ln.item(0).getChildNodes();
            Node node;
            for (int i = 0; i < ln.getLength(); i++) {
                if (ln.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    node = ln.item(i);
                    toReturn.put(node.getNodeName(), node.getFirstChild().getNodeValue());
                }
            }
        } catch (XPathExpressionException e) {
            System.err.println("Wrong xpath : " + xPathExpression);
            e.printStackTrace();
        } catch (DOMException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

    public static HashMap<String, String> getNodesFromNodeList(Document document, NodeList nodeList) {
        HashMap<String, String> toReturn = new HashMap<>();
        Node node;
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                node = nodeList.item(i);
                toReturn.put(node.getNodeName(), node.getFirstChild().getNodeValue());
            }
        }
        return toReturn;
    }
}
