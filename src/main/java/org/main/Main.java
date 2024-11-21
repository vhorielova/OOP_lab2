package org.main;

import org.parsers.TariffDOMParser;
import org.parsers.TariffStAXParser;
import org.tariff.Tariff;
import org.tariff.Tariff.Plan;
import org.parsers.TariffSAXParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import static org.validator.XMLValidator.validateXMLSchema;

public class Main {
    public static void main(String[] args) {
        Tariff tariff;
        Comparator<Plan> comparator;
        File xmlFile = new File("src/main/resources/tariff.xml");

        System.out.println("SAX parser");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            TariffSAXParser handler = new TariffSAXParser();
            saxParser.parse(xmlFile, handler);

            tariff = handler.getTariff();
            comparator = Comparator.comparing(obj -> obj.getPayroll());
            Collections.sort(tariff.getPlan(), comparator);
            for (Plan plan : tariff.getPlan()) {
                System.out.println("Plan Name: " + plan.getName());
                System.out.println("Operator: " + plan.getOperatorName());
                System.out.println("Payroll: " + plan.getPayroll());
                System.out.println("Inside Network Call Price: " + plan.getCallPrices().getInsideNetwork());
                System.out.println("Outside Network Call Price: " + plan.getCallPrices().getOutsideNetwork());
                System.out.println("Landline Call Price: " + plan.getCallPrices().getLandline());
                System.out.println("SMS Price: " + plan.getSMSPrice());
                System.out.println("Favorite Number: " + plan.getParameters().getFavoriteNumber());
                System.out.println("Billing: " + plan.getParameters().getBilling());
                System.out.println("Connection Fee: " + plan.getParameters().getConnectionFee());
                System.out.println("-------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("DOM Parser");
        TariffDOMParser parser = new TariffDOMParser();
        tariff = parser.parseTariff(xmlFile);

        comparator = Comparator.comparing(obj -> obj.getPayroll());
        Collections.sort(tariff.getPlan(), comparator);

        for (Tariff.Plan plan : tariff.getPlan()) {
            System.out.println("Plan Name: " + plan.getName());
            System.out.println("Operator Name: " + plan.getOperatorName());
            System.out.println("Payroll: " + plan.getPayroll());
            System.out.println("SMS Price: " + plan.getSMSPrice());

            Tariff.Plan.CallPrices callPrices = plan.getCallPrices();
            System.out.println("Inside Network: " + callPrices.getInsideNetwork());
            System.out.println("Outside Network: " + callPrices.getOutsideNetwork());
            System.out.println("Landline: " + callPrices.getLandline());

            Tariff.Plan.Parameters parameters = plan.getParameters();
            System.out.println("Favorite Number: " + parameters.getFavoriteNumber());
            System.out.println("Billing: " + parameters.getBilling());
            System.out.println("Connection Fee: " + parameters.getConnectionFee());
            System.out.println("-------------------------------");
        }

        System.out.println("StAX Parser");
        TariffStAXParser StAXparser = new TariffStAXParser();
        tariff = StAXparser.parseTariff(String.valueOf(xmlFile));

        comparator = Comparator.comparing(obj -> obj.getPayroll());
        Collections.sort(tariff.getPlan(), comparator);

        for (Tariff.Plan plan : tariff.getPlan()) {
            System.out.println("Plan Name: " + plan.getName());
            System.out.println("Operator Name: " + plan.getOperatorName());
            System.out.println("Payroll: " + plan.getPayroll());
            System.out.println("SMS Price: " + plan.getSMSPrice());

            Tariff.Plan.CallPrices callPrices = plan.getCallPrices();
            System.out.println("Inside Network: " + callPrices.getInsideNetwork());
            System.out.println("Outside Network: " + callPrices.getOutsideNetwork());
            System.out.println("Landline: " + callPrices.getLandline());

            Tariff.Plan.Parameters parameters = plan.getParameters();
            System.out.println("Favorite Number: " + parameters.getFavoriteNumber());
            System.out.println("Billing: " + parameters.getBilling());
            System.out.println("Connection Fee: " + parameters.getConnectionFee());
            System.out.println("-------------------------------");
        }

        //Валідація xsd
        String xsdFilePath = "src/main/resources/tariff.xsd";

        boolean isValid = validateXMLSchema(xsdFilePath, String.valueOf(xmlFile));

        if (isValid) {
            System.out.println("XML документ відповідає схемі XSD.");
        } else {
            System.out.println("XML документ не відповідає схемі XSD.");
        }

        //Перетворення XSL
        try {

            File xsltFile = new File("src/main/resources/tariff.xsl");
            File outputXML = new File("src/main/resources/newTariff.xml");

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            transformer.transform(
                    new StreamSource(xmlFile),
                    new StreamResult(outputXML)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
