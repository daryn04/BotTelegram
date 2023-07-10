import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ParserXML {
    private String host;
    private int port;
    private String name;
    private String username;
    private String password;

    public ParserXML() {
    }
    public void readConfigFile(String path) {
        File configFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Crea un oggetto DocumentBuilderFactory
        DocumentBuilder builder = null; // Crea un oggetto DocumentBuilder per il parsing del file XML
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = null; // Effettua il parsing del file XML e ottieni un oggetto Document
        try {
            document = builder.parse(configFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element root = document.getDocumentElement(); // Ottieni il nodo radice
        NodeList nodeList = root.getChildNodes(); // Ottieni la lista dei nodi figlio della radice
        for (int i = 0; i < nodeList.getLength(); i++) { // Itera sui nodi figlio e trova i valori desiderati
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getTagName().equals("host")) { // Estrarre il valore in base al nome del tag
                    String host = element.getTextContent();
                    setHost(host);
                }
                else if (element.getTagName().equals("port")) {
                    int port = Integer.parseInt(element.getTextContent());
                    setPort(port);
                }
                else if (element.getTagName().equals("name")) {
                    String name = element.getTextContent();
                    setName(name);
                }
                else if (element.getTagName().equals("username")) {
                    String username = element.getTextContent();
                    setUsername(username);
                }
                else if (element.getTagName().equals("password")) {
                    String password = element.getTextContent();
                    setPassword(password);
                }
            }
        }
    }
    private void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

