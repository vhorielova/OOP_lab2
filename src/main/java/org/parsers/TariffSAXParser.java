package org.parsers;

import org.tariff.Tariff;
import org.tariff.Tariff.Plan;
import org.tariff.Tariff.Plan.CallPrices;
import org.tariff.Tariff.Plan.Parameters;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TariffSAXParser extends DefaultHandler {

    private Tariff tariff;
    private Plan currentPlan;
    private CallPrices currentCallPrices;
    private Parameters currentParameters;
    private StringBuilder content;

    public Tariff getTariff() {
        return tariff;
    }

    @Override
    public void startDocument() throws SAXException {
        tariff = new Tariff();
        content = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        content.setLength(0);
        switch (qName) {
            case "Plan":
                currentPlan = new Plan();
                break;
            case "CallPrices":
                currentCallPrices = new CallPrices();
                break;
            case "Parameters":
                currentParameters = new Parameters();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "Name":
                currentPlan.setName(content.toString());
                break;
            case "OperatorName":
                currentPlan.setOperatorName(content.toString());
                break;
            case "Payroll":
                currentPlan.setPayroll(new BigDecimal(content.toString()));
                break;
            case "InsideNetwork":
                currentCallPrices.setInsideNetwork(new BigDecimal(content.toString()));
                break;
            case "OutsideNetwork":
                currentCallPrices.setOutsideNetwork(new BigDecimal(content.toString()));
                break;
            case "Landline":
                currentCallPrices.setLandline(new BigDecimal(content.toString()));
                break;
            case "SMSPrice":
                currentPlan.setSMSPrice(new BigDecimal(content.toString()));
                break;
            case "FavoriteNumber":
                currentParameters.setFavoriteNumber(new BigInteger(content.toString()));
                break;
            case "Billing":
                currentParameters.setBilling(content.toString());
                break;
            case "ConnectionFee":
                currentParameters.setConnectionFee(new BigDecimal(content.toString()));
                break;
            case "CallPrices":
                currentPlan.setCallPrices(currentCallPrices);
                break;
            case "Parameters":
                currentPlan.setParameters(currentParameters);
                break;
            case "Plan":
                tariff.getPlan().add(currentPlan);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content.append(ch, start, length);
    }
}
