package org.main;

import org.tariff.Tariff;
import org.tariff.Tariff.Plan;
import org.parsers.TariffSAXParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("SAX parser");
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            TariffSAXParser handler = new TariffSAXParser();
            saxParser.parse(new File("src/main/resources/tariff.xml"), handler);

            Tariff tariff = handler.getTariff();
            // Output result to verify
            for (Plan plan : tariff.getPlan()) {
                System.out.println("Plan Name: " + plan.getName());
                System.out.println("Operator: " + plan.getOperatorName());
                System.out.println("Payroll: " + plan.getPayroll());
                System.out.println("Inside Network Call Price: " + plan.getCallPrices().getInsideNetwork());
                System.out.println("Outside Network Call Price: " + plan.getCallPrices().getOutsideNetwork());
                System.out.println("Landline Call Price: " + plan.getCallPrices().getLandline());
                System.out.println("SMS Price: " + plan.getSmsPrice());
                System.out.println("Favorite Number: " + plan.getParameters().getFavoriteNumber());
                System.out.println("Billing: " + plan.getParameters().getBilling());
                System.out.println("Connection Fee: " + plan.getParameters().getConnectionFee());
                System.out.println("---------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
