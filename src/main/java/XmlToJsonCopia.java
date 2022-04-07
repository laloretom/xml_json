import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class XmlToJsonCopia extends DefaultHandler {

    private static final String CLASS_NAME = XmlToJsonCopia.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    private SAXParser parser = null;
    private SAXParserFactory spf;

    private double totalSales;
    private boolean inRecordSales;
    private boolean inId;
    private boolean inSales;
    private boolean inFirtsName;
    private boolean inLastName;
    private boolean inState;
    private boolean indepartment;

    private String curreElement;

    private JsonArray array;
    private JsonObject jsonObject;

    public XmlToJsonCopia(){
        super();
        spf = SAXParserFactory.newInstance();
        // verificar espacios de nombre
        spf.setNamespaceAware(true);
        // validar que el documento este bien formado (well formed)
        spf.setValidating(true);
    }

    private void process(File file) {
        try {
            // obtener un parser para verificar el documento
            parser = spf.newSAXParser();
            LOG.info("Parser object is: " + parser);
        } catch (SAXException | ParserConfigurationException e) {
            LOG.severe(e.getMessage());
            System.exit(1);
        }
        System.out.println("\nStarting parsing of " + file + "\n");
        try {
            // iniciar analisis del documento
            parser.parse(file, this);
        } catch (IOException | SAXException e) {
            LOG.severe(e.getMessage());
        }
    }

    public JsonArray getArray(){return array;}
    
    @Override
    public void startDocument() throws SAXException {
        array = new JsonArray();
    }

    @Override
    public void endDocument() throws SAXException {
        String jsonDoc = array.toString();
        System.out.println(jsonDoc);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName){
            case "sale_record":
                inRecordSales = true;
                jsonObject = new JsonObject();
                break;
            case "id":
                inId = true;
                System.out.print("\"id\":");
                break;
            case "first_name":
                inFirtsName = true;
                System.out.print("\"firstName\":");
                break;
            case "last_name":
                inLastName = true;
                System.out.print("\"lastName\":");
                break;
            case "sales":
                inSales = true;
                System.out.print("\"sales\":");
                break;
            case "state":
                inState = true;
                System.out.print("\"state\":");
                break;
            case "department":
                indepartment = true;
                System.out.print("\"departament\":");
                break;
        }
        curreElement = localName;
    }

    @Override
    public void characters(char[] bytes, int start, int length) throws SAXException {
        String data = new String(bytes,start,length);

        switch (curreElement){
            case "sale_record":
                break;
            case "id":
                inId = false;
                System.out.printf("\"%s\",%n",data);
                break;
            case "first_name":
                inFirtsName = false;
                System.out.printf("\"%s\",%n",data);
                break;
            case "last_name":
                inLastName = false;
                System.out.printf("\"%s\",%n",data);
                break;
            case "sales":
                inSales = false;
                System.out.printf("\"%s\",%n",data);
                break;
            case "state":
                inState = false;
                System.out.printf("\"%s\",%n",data);
                break;
            case "department":
                indepartment = false;
                System.out.printf("\"%s\"%n",data);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if( localName.equals("sale_record") )        {
            System.out.println("}");
            inSales = false;
        }
    }


    public static void main(String args[]) {
        if (args.length == 0) {
            LOG.severe("No file to process. Usage is:" + "\njava XmlToJsonL <filename>");
            return;
        }
        File xmlFile = new File(args[0]);
        XmlToJsonCopia handler = new XmlToJsonCopia();
        handler.process(xmlFile);

        try {
            PrintWriter ouput = new PrintWriter("output.json");
            JsonArray array = handler.getArray();
            String jsonDoc = array.toString();
            ouput.print(jsonDoc);
            ouput.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
