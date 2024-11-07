package org.parsers;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.tariff.*;

public class TariffStAXParser {

    public Tariff parseTariff(String xmlFilePath) {
        Tariff tariff = new Tariff();
        List<Tariff.Plan> plans = new ArrayList<>();

        Tariff.Plan plan = null;
        Tariff.Plan.CallPrices callPrices = null;
        Tariff.Plan.Parameters parameters = null;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream(xmlFilePath));

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        String localName = reader.getLocalName();

                        switch (localName) {
                            case "Plan":
                                plan = new Tariff.Plan();
                                break;
                            case "Name":
                                if (plan != null) plan.setName(reader.getElementText());
                                break;
                            case "OperatorName":
                                if (plan != null) plan.setOperatorName(reader.getElementText());
                                break;
                            case "Payroll":
                                if (plan != null) plan.setPayroll(new BigDecimal(reader.getElementText()));
                                break;
                            case "SMSPrice":
                                if (plan != null) plan.setSMSPrice(new BigDecimal(reader.getElementText()));
                                break;
                            case "CallPrices":
                                callPrices = new Tariff.Plan.CallPrices();
                                break;
                            case "InsideNetwork":
                                if (callPrices != null) callPrices.setInsideNetwork(new BigDecimal(reader.getElementText()));
                                break;
                            case "OutsideNetwork":
                                if (callPrices != null) callPrices.setOutsideNetwork(new BigDecimal(reader.getElementText()));
                                break;
                            case "Landline":
                                if (callPrices != null) callPrices.setLandline(new BigDecimal(reader.getElementText()));
                                break;
                            case "Parameters":
                                parameters = new Tariff.Plan.Parameters();
                                break;
                            case "FavoriteNumber":
                                if (parameters != null) parameters.setFavoriteNumber(new BigInteger(reader.getElementText()));
                                break;
                            case "Billing":
                                if (parameters != null) parameters.setBilling(reader.getElementText());
                                break;
                            case "ConnectionFee":
                                if (parameters != null) parameters.setConnectionFee(new BigDecimal(reader.getElementText()));
                                break;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        localName = reader.getLocalName();
                        switch (localName) {
                            case "Plan":
                                if (plan != null) plans.add(plan);
                                plan = null;
                                break;
                            case "CallPrices":
                                if (plan != null) plan.setCallPrices(callPrices);
                                callPrices = null;
                                break;
                            case "Parameters":
                                if (plan != null) plan.setParameters(parameters);
                                parameters = null;
                                break;
                        }
                        break;
                }
            }

            reader.close();
            tariff.getPlan().addAll(plans);

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }

        return tariff;
    }
}
