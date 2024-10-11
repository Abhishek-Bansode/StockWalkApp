package com.abhishekbansode.stockwalkapp.model;

public class StockResponse {
    public Body body;

    public static class Body {
        public String symbol;
        public String companyName;
        public PrimaryData primaryData;

        public static class PrimaryData {
            public String lastSalePrice;
            public String percentageChange;
        }
    }
}
