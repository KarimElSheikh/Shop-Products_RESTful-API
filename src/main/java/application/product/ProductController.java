package application.product;

import application.config.Filenames;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @RequestMapping(value="/products")
    public List<Product> getAllProducts() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(Filenames.productsFile));
            NodeList list = doc.getElementsByTagName("Product");
            List<Product> resList = new ArrayList<Product>();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getAttribute("id");
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String description = element.getElementsByTagName("Description").item(0).getTextContent().trim();
                    String sku = element.getElementsByTagName("sku").item(0).getTextContent();

                    Product nodeProduct = new Product(id, name, description, sku);
                    resList.add(nodeProduct);
                }
            }

            return resList;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return new ArrayList<Product>(0);
        }
    }

    @RequestMapping(value = "/products/{id}")
    public Product getProduct(@PathVariable String id) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try(FileReader reader = new FileReader(Filenames.pricesFile)) {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(Filenames.productsFile));
            NodeList list = doc.getElementsByTagName("Product");

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String elementId = element.getAttribute("id");

                    if(elementId.equals(id)) {

                        String name = element.getElementsByTagName("Name").item(0).getTextContent();
                        String description = element.getElementsByTagName("Description").item(0).getTextContent().trim();
                        String sku = element.getElementsByTagName("sku").item(0).getTextContent();
                        ArrayList<String> avaialablePrices = new ArrayList<>();

                        JSONParser jsonParser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

                        for (int j = 0; j < jsonArray.size(); j++) {

                            JSONObject entry = (JSONObject) jsonArray.get(j);
                            String entryId = (String) entry.get("id");

                            if(sku.equals(entryId)) {
                                JSONObject price = (JSONObject) entry.get("price");
                                avaialablePrices.add( price.get("value").toString().concat(" ").concat((String) price.get("currency")) );
                            }
                        }
                        return new Product(id, name, description, sku, avaialablePrices);
                    }
                }
            }

            return null;

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/price")
    public String getPriceOfProductAndUnit(@RequestParam("id") String id, @RequestParam("unit") String unit) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try(FileReader reader = new FileReader(Filenames.pricesFile)) {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(Filenames.productsFile));
            NodeList list = doc.getElementsByTagName("Product");

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    String elementId = element.getAttribute("id");

                    if(elementId.equals(id)) {

                        String name = element.getElementsByTagName("Name").item(0).getTextContent();
                        String description = element.getElementsByTagName("Description").item(0).getTextContent().trim();
                        String sku = element.getElementsByTagName("sku").item(0).getTextContent();
                        ArrayList<String> avaialablePrices = new ArrayList<>();

                        JSONParser jsonParser = new JSONParser();
                        JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

                        for (int j = 0; j < jsonArray.size(); j++) {

                            JSONObject entry = (JSONObject) jsonArray.get(j);
                            String entryId = (String) entry.get("id");
                            String entryUnit = (String) entry.get("unit");

                            if(sku.equals(entryId) && unit.equalsIgnoreCase(entryUnit)) {
                                JSONObject price = (JSONObject) entry.get("price");
                                return price.get("value").toString().concat(" ").concat((String) price.get("currency"));
                            }
                        }
                    }
                }
            }

            return null;

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/price_sku")
    public String getPriceOfProductAndUnitBySku(@RequestParam("sku") String sku, @RequestParam("unit") String unit) {
        try(FileReader reader = new FileReader(Filenames.pricesFile)) {

            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (int j = 0; j < jsonArray.size(); j++) {

                JSONObject entry = (JSONObject) jsonArray.get(j);
                String entryId = (String) entry.get("id");
                String entryUnit = (String) entry.get("unit");

                if(sku.equals(entryId) && unit.equalsIgnoreCase(entryUnit)) {
                    JSONObject price = (JSONObject) entry.get("price");
                    return price.get("value").toString().concat(" ").concat((String) price.get("currency"));
                }
            }

            return null;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
