package chapt16;

public enum ParserEnum {

    PRODUCTS, CATEGORY, SUBCATEGORY, PRODUCT, PRODUCER, MODEL, YEAR, COLOR, NOTAVAILABLE, COST, NAME

}

package chapt16;

import java.io.InputStream;

public abstract class ProductParser {

    public abstract void parse(InputStream input);

    public void writeTitle() {

        System.out.println("Products:");

    }

    public void writeCategoryStart(String name) {

        System.out.println("Category: " + name.trim());

    }

    public void writeCategoryEnd() {

        System.out.println();

    }

    public void writeSubcategoryStart(String name) {

        System.out.println("Subcategory: " + name.trim());

    }

    public void writeSubcategoryEnd() {

        System.out.println();

    }

    public void writeProductStart() {

        System.out.println(" Product Start ");

    }

    public void writeProductEnd() {

        System.out.println(" Product End ");

    }

    public void writeProductFeatureStart(String name) {

        switch (ParserEnum.valueOf(name.toUpperCase())) {

            case PRODUCER:

                System.out.print("Provider: ");

                break;

            case MODEL:

                System.out.print("Model: ");

                break;

            case YEAR:

                System.out.print("Date of issue: ");

                break;

            case COLOR:

                System.out.print("Color: ");

                break;

            case NOTAVAILABLE:

                System.out.print("Not available");

                break;

            case COST:

                System.out.print("Cost: ");

                break;

        }

    }

    public void writeProductFeatureEnd() {

        System.out.println();

    }

    public void writeText(String text) {

        System.out.print(text.trim());

    }

}

package chapt16;

        import javax.xml.stream.XMLInputFactory;

        import javax.xml.stream.XMLStreamConstants;

        import javax.xml.stream.XMLStreamException;

        import javax.xml.stream.XMLStreamReader;

        import java.io.InputStream;

public class StAXProductParser extends ProductParser {

    // реализация абстрактного метода из суперкласса для разбора потока

    public void parse(InputStream input) {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        try {

            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);

            process(reader);

        } catch (XMLStreamException e) {

            e.printStackTrace();

        }

    }

    // метод, управляющий разбором потока

    public void process(XMLStreamReader reader)

            throws XMLStreamException {

        String name;

        while (reader.hasNext()) {

            // определение типа "прочтённого" элемента (тега)

            int type = reader.next();

            switch (type) {

                case XMLStreamConstants.START_ELEMENT:

                    name = reader.getLocalName();

                    switch (ParserEnum.valueOf(name.toUpperCase())) {

                        case PRODUCTS:

                            writeTitle();

                            break;

                        case CATEGORY:

                            writeCategoryStart(reader.getAttributeValue(null,ParserEnum.NAME.name().toLowerCase()));

                            break;

                        case SUBCATEGORY:

                            writeSubcategoryStart(reader.getAttributeValue(null,

                                    ParserEnum.NAME.name().toLowerCase()));

                            break;

                        case PRODUCT:

                            writeProductStart();

                            break;

                        default:

                            writeProductFeatureStart(name);

                            break;

                    }

                    break;

                case XMLStreamConstants.END_ELEMENT:

                    name = reader.getLocalName();

                    switch (ParserEnum.valueOf(name.toUpperCase())) {

                        case CATEGORY:

                            writeCategoryEnd();

                            break;

                        case SUBCATEGORY:

                            writeSubcategoryEnd();

                            break;

                        case PRODUCT:

                            writeProductEnd();

                            break;

                        default:

                            writeProductFeatureEnd();

                            break;

                    }

                    break;

                case XMLStreamConstants.CHARACTERS:

                    writeText(reader.getText());

                    break;

                default:

                    break;

            }

        }

    }

}