package org.tariff;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Tariff {

    private List<Plan> plan;

    public List<Plan> getPlan() {
        if (plan == null) {
            plan = new ArrayList<>();
        }
        return plan;
    }

    public static class Plan {
        private String name;
        private String operatorName;
        private BigDecimal payroll;
        private CallPrices callPrices;
        private BigDecimal smsPrice;
        private Parameters parameters;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public BigDecimal getPayroll() {
            return payroll;
        }

        public void setPayroll(BigDecimal payroll) {
            this.payroll = payroll;
        }

        public CallPrices getCallPrices() {
            return callPrices;
        }

        public void setCallPrices(CallPrices callPrices) {
            this.callPrices = callPrices;
        }

        public BigDecimal getSmsPrice() {
            return smsPrice;
        }

        public void setSmsPrice(BigDecimal smsPrice) {
            this.smsPrice = smsPrice;
        }

        public Parameters getParameters() {
            return parameters;
        }

        public void setParameters(Parameters parameters) {
            this.parameters = parameters;
        }

        public static class CallPrices {
            private BigDecimal insideNetwork;
            private BigDecimal outsideNetwork;
            private BigDecimal landline;

            public BigDecimal getInsideNetwork() {
                return insideNetwork;
            }

            public void setInsideNetwork(BigDecimal insideNetwork) {
                this.insideNetwork = insideNetwork;
            }

            public BigDecimal getOutsideNetwork() {
                return outsideNetwork;
            }

            public void setOutsideNetwork(BigDecimal outsideNetwork) {
                this.outsideNetwork = outsideNetwork;
            }

            public BigDecimal getLandline() {
                return landline;
            }

            public void setLandline(BigDecimal landline) {
                this.landline = landline;
            }
        }

        public static class Parameters {
            private BigInteger favoriteNumber;
            private String billing;
            private BigDecimal connectionFee;

            public BigInteger getFavoriteNumber() {
                return favoriteNumber;
            }

            public void setFavoriteNumber(BigInteger favoriteNumber) {
                this.favoriteNumber = favoriteNumber;
            }

            public String getBilling() {
                return billing;
            }

            public void setBilling(String billing) {
                this.billing = billing;
            }

            public BigDecimal getConnectionFee() {
                return connectionFee;
            }

            public void setConnectionFee(BigDecimal connectionFee) {
                this.connectionFee = connectionFee;
            }
        }
    }
}
