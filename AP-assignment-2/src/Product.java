public class Product {
    private final String productID, name, description;
    private final int categoryID;
    private final double price;
    private int quantity;
    private double discountRateNormal, discountRatePrime, discountRateElite;
    public Product(String pid, String name, String description, double price, int quantity, int categoryID) {
        this.productID = pid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.discountRateNormal = 0;
        this.discountRatePrime = 5;
        this.discountRateElite = 10;
    }

    public void printProduct() {
        System.out.println("Product ID: " + this.productID);
        System.out.println("Name: " + this.name);
        System.out.println("Description: " + this.description);
        System.out.println("Price: " + this.price);
        System.out.println("Quantity: " + this.quantity);
        System.out.println("Category ID: " + this.categoryID);
        System.out.println("Discount Rate (Normal): " + this.discountRateNormal + "%");
        System.out.println("Discount Rate (Prime): " + this.discountRatePrime + "%");
        System.out.println("Discount Rate (Elite): " + this.discountRateElite + "%");
    }

    public void reduceQuantity(int quantity) {
        if (this.getQuantity() > 0) {
            this.quantity -= quantity;
        }

//        if (this.getQuantity() == 0) {
//            this.removeProduct();
//        }
    }

//    public void removeProduct() {
//        // Completely eliminate all references to the product in the application.
//
//        int _prodID = parseProductIDToInt();
//        // Remove from Products
//        FlipZon.getProducts().remove(_prodID);
//        System.out.println("Product " + this.getName() + " has been removed from the application.");
//
//        // Remove from Categories
//        for (Category category : FlipZon.getCategories().values()) {
//            if (category.getProductIDs().contains(_prodID)) {
//                category.removeProductID(_prodID);
//            }
//        }
//
//        // Remove from Deals
//        for (Deal deal : FlipZon.getDeals().values()) {
//            if (deal.getProductID_1() == _prodID || deal.getProductID_2() == _prodID) {
//                FlipZon.getDeals().remove(deal.getDealID());
//                System.out.println("Deal #" + deal.getDealID() + " has been removed from the application.");
//            }
//        }
//    }

    public void setDiscountRate(double normal, double prime, double elite) {
        this.discountRateNormal = normal;
        this.discountRatePrime = prime;
        this.discountRateElite = elite;
    }

    public static int parseProductIDToInt(String productID) {
        return Integer.parseInt(productID.split("\\.", 0)[1]);
    }
    public int parseProductIDToInt() {
//        String _id = this.getProductID().split("\\.", 0)[1];
        return parseProductIDToInt(this.getProductID());
    }

    // Getters
    public String getProductID() {
        return this.productID;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public double getPrice() {
        return this.price;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public int getCategoryID() {
        return this.categoryID;
    }
    public double getDiscountRateNormal() {
        return this.discountRateNormal;
    }
    public double getDiscountRatePrime() {
        return this.discountRatePrime;
    }
    public double getDiscountRateElite() {
        return this.discountRateElite;
    }
    public double getDiscountRate(String status) {
        return switch (status) {
            case "normal" -> this.getDiscountRateNormal();
            case "prime" -> this.getDiscountRatePrime();
            case "elite" -> this.getDiscountRateElite();
            default -> 0;
        };
    }
    public static int getNextProductID() {
        return FlipZon.getProducts().size() + 1;
    }
}
