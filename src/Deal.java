public class Deal {
    private final int dealID;
    private final int productID_1, productID_2;
    private final double dealPriceNormal, dealPricePrime, dealPriceElite;
    private final Product product_1, product_2;

    public Deal(int dealID, int productID_1, int productID_2, double dealPriceNormal, double dealPricePrime, double dealPriceElite) {
        this.dealID = dealID;
        this.productID_1 = productID_1;
        this.productID_2 = productID_2;
        this.dealPriceNormal = dealPriceNormal;
        this.dealPricePrime = dealPricePrime;
        this.dealPriceElite = dealPriceElite;
        this.product_1 = FlipZon.getProductByID(productID_1);
        this.product_2 = FlipZon.getProductByID(productID_2);
    }
    public static int getNextDealID() {
        return FlipZon.getDeals().size() + 1;
    }
    public static boolean isValid(Product product1, Product product2, double dealPriceNormal, double dealPricePrime, double dealPriceElite) {
        if (product1.getQuantity() == 0 || product2.getQuantity() == 0) {
            return false;
        } else {
            if (product1.getPrice() * (1 - (product1.getDiscountRateNormal() / 100)) + product2.getPrice() * (1 - (product2.getDiscountRateNormal() / 100)) < dealPriceNormal) {
                return false;
            } else if (product1.getPrice() * (1 - (product1.getDiscountRatePrime() / 100)) + product2.getPrice() * (1 - (product2.getDiscountRatePrime() / 100)) < dealPricePrime) {
                return false;
            } else return !(product1.getPrice() * (1 - (product1.getDiscountRateElite() / 100)) + product2.getPrice() * (1 - (product2.getDiscountRateElite() / 100)) < dealPriceElite);
        }
    }

    public void printDeal() {
        System.out.println("Deal #" + this.getDealID());
        System.out.println("\tSPICY Deal! You can avail the following products for very cheap!");
        System.out.println("\t\tProduct 1: " + product_1.getName());
        System.out.println("\t\tProduct 2: " + product_2.getName());
        System.out.println("\t\tOriginal price: Rs. " + (product_1.getPrice() + product_2.getPrice()));
        System.out.println("\t\tDiscounted price: ");
        System.out.println("\t\t\tRs. " + this.dealPriceNormal + " for Normal customers");
        System.out.println("\t\t\tRs. " + this.dealPricePrime + " for Prime customers");
        System.out.println("\t\t\tRs. " + this.dealPriceElite + " for Elite customers");
//        System.out.println("\t\tYou save: " + (product_1.getPrice() + product_2.getPrice() - this.dealPrice) + "!");
        System.out.println("");
    }

    // Getters
    public double getDiscountedCost(Customer customer) {
        if (customer.getStatus().equals("normal")) return this.dealPriceNormal;
        if (customer.getStatus().equals("prime")) return this.dealPricePrime;
        if (customer.getStatus().equals("elite")) return this.dealPriceElite;
        return 0;
    }
    public int getDealID() {
        return this.dealID;
    }
    public int getProductID_1() {
        return this.productID_1;
    }
    public int getProductID_2() {
        return this.productID_2;
    }
    public Product getProduct_1() {
        return this.product_1;
    }
    public Product getProduct_2() {
        return this.product_2;
    }
}
