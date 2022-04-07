import com.google.gson.*;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.logging.Logger;

public class JsonToXml extends DefaultHandler
{
    private static final String CLASS_NAME = JsonToXml.class.getName();
    private final static Logger LOG = Logger.getLogger(CLASS_NAME);

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            LOG.severe("No file to process. Usage is:" + "\njava JsonToXml <filename>");
            return;
        }
        File jsonFile = new File(args[0]);

        FileReader fileReader = null;

        try {
            fileReader = new FileReader(jsonFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Sale[] sales = gson.fromJson(fileReader, Sale[].class);

        Document doc = null;

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            doc = builder.newDocument();
            // crea elemento raiz
            Element rootElement = doc.createElement("root");
            doc.appendChild( rootElement );
        } catch (ParserConfigurationException e) {
            LOG.severe(e.getMessage());
            doc = null;
        }


        for (Sale sale: sales) {
            Node saleRecord = createSale(doc,sale);
            Element root = doc.getDocumentElement();
            root.appendChild(saleRecord);
        }

        prettyPrint(doc);
        saveDocument(doc);

    }

    private static Node createSale(Document doc, Sale sale) {

    Element saleRecord = doc.createElement("sale_record");

    saleRecord.setAttribute("id", String.valueOf(sale.id));
    saleRecord.setAttribute("first_name",sale.first_name);
    saleRecord.setAttribute("last_name",sale.last_name);
    saleRecord.setAttribute("sales", String.valueOf(sale.sales));
    saleRecord.setAttribute("state",sale.state);
    saleRecord.setAttribute("department", sale.department);
    
    return saleRecord;
    }

    public static final void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
        System.out.println( out.toString() );
    }

    public static final void saveDocument(Document xml) {
        Transformer tf = null;

        FileWriter out = null;
        try {
            tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");

            out = new FileWriter("outputDOM.xml");
            tf.transform(new DOMSource(xml), new StreamResult(out));
        } catch (IOException e) {
            LOG.severe(e.getMessage());
        } catch (TransformerException e) {
            LOG.severe(e.getMessage());
        }
    }

}
