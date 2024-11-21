package org.parsers;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.tariff.*;

public class TariffDOMParser {

    public Tariff parseTariff(File xmlFile) {
        Tariff tariff = new Tariff();
        List<Tariff.Plan> plans = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList planList = doc.getElementsByTagName("Plan");

            for (int i = 0; i < planList.getLength(); i++) {
                Element planElement = (Element) planList.item(i);
                Tariff.Plan plan = new Tariff.Plan();

                plan.setName(getTagValue("Name", planElement));
                plan.setOperatorName(getTagValue("OperatorName", planElement));
                plan.setPayroll(new BigDecimal(getTagValue("Payroll", planElement)));
                plan.setSMSPrice(new BigDecimal(getTagValue("SMSPrice", planElement)));

                // Parse CallPrices
                Element callPricesElement = (Element) planElement.getElementsByTagName("CallPrices").item(0);
                Tariff.Plan.CallPrices callPrices = new Tariff.Plan.CallPrices();
                callPrices.setInsideNetwork(new BigDecimal(getTagValue("InsideNetwork", callPricesElement)));
                callPrices.setOutsideNetwork(new BigDecimal(getTagValue("OutsideNetwork", callPricesElement)));
                callPrices.setLandline(new BigDecimal(getTagValue("Landline", callPricesElement)));
                plan.setCallPrices(callPrices);

                // Parse Parameters
                Element parametersElement = (Element) planElement.getElementsByTagName("Parameters").item(0);
                Tariff.Plan.Parameters parameters = new Tariff.Plan.Parameters();
                parameters.setFavoriteNumber(new BigInteger(getTagValue("FavoriteNumber", parametersElement)));
                parameters.setBilling(getTagValue("Billing", parametersElement));
                parameters.setConnectionFee(new BigDecimal(getTagValue("ConnectionFee", parametersElement)));
                plan.setParameters(parameters);

                plans.add(plan);
            }

            tariff.getPlan().addAll(plans);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tariff;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
