public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public boolean addProduct(String productID, String name, String description, double price, int quantity, int categoryID) {
        // Add a product to the application
        Product product = new Product(productID, name, description, price, quantity, categoryID);
        if (FlipZon.getProducts().containsKey(product.parseProductIDToInt())) {
            System.out.println("Product ID already exists. Please try again.");
            return false;
        } else {
            if (FlipZon.getCategories().containsKey(categoryID)) {
                FlipZon.getProducts().put(product.parseProductIDToInt(), product);
                FlipZon.getCategories().get(categoryID).addProductID(product.parseProductIDToInt());
//                System.out.println("Product " + product.getName() + " has been added to the application.");
                return true;
            } else {
                System.out.println("Category with ID " + categoryID + " does not exist!");
                System.out.println("If you would like to create a new category, please use the addCategory() method.");
                return false;
            }
        }
    }

    public void decrementProductQuantity(String productID) {
        // Decrement the quantity of a product by 1
        int _productID = Product.parseProductIDToInt(productID);
        FlipZon.getProductByID(_productID).reduceQuantity(1);
        if (FlipZon.getProductByID(_productID).getQuantity() == 0) {
            removeProduct(productID);
        }
    }

    public void removeProduct(String productID) {
        // Completely eliminate all references to the product in the application.

        int _prodID = Product.parseProductIDToInt(productID);
        Product product = FlipZon.getProductByID(_prodID);

        // Remove from Categories
        for (Category category : FlipZon.getCategories().values()) {
            if (category.getProductIDs().contains(_prodID)) {
                category.removeProductID(_prodID);
            }
        }

        // Remove from Deals
        for (Deal deal : FlipZon.getDeals().values()) {
            if (deal.getProductID_1() == _prodID || deal.getProductID_2() == _prodID) {
                FlipZon.getDeals().remove(deal.getDealID());
                System.out.println("Deal #" + deal.getDealID() + " has been removed from the application.");
            }
        }

        // Remove from Products
        FlipZon.getProducts().remove(_prodID);
        System.out.println("Product has been removed from the application.");
    }
    public boolean addCategory(int categoryID, String name) {
        // Add a category to the application
        if (FlipZon.getCategories().containsKey(categoryID)) {
            return false;
        } else {
            Category category = new Category(categoryID, name);
            FlipZon.getCategories().put(categoryID, category);
            return true;
        }
    }
    public boolean removeCategory(int categoryID) {
        // Remove a category from the application
        if (FlipZon.getCategories().containsKey(categoryID)) {
            FlipZon.getCategories().remove(categoryID);
            System.out.println("Category has been removed from the application.");
            for (Product product : FlipZon.getProducts().values()) {
                if (product.getCategoryID() == categoryID) {
                    removeProduct(product.getProductID());
                    System.out.println("Product " + product.getName() + " has been removed from the application.");
                }
            }
            return true;
        } else {
            return false;
        }
    }
    public void addDeal(int dealID, String productID_1, String productID_2, double discountNormal, double discountPrime, double discountElite) {
        // Add a deal to the application
        Deal deal = new Deal(dealID, Product.parseProductIDToInt(productID_1), Product.parseProductIDToInt(productID_2), discountNormal, discountPrime, discountElite);
        if (FlipZon.getDeals().containsKey(dealID)) {
            System.out.println("Deal ID already exists. Please try again.");
        } else {
            if (FlipZon.getProducts().containsKey(Product.parseProductIDToInt(productID_1)) && FlipZon.getProducts().containsKey(Product.parseProductIDToInt(productID_2))) {
                FlipZon.getDeals().put(dealID, deal);
                System.out.println("Deal #" + dealID + " has been added to the application.");
            } else {
                System.out.println("One or more of the products in the deal does not exist!");
                System.out.println("If you would like to create a new product, please use the addProduct() method.");
            }
        }
    }
//    public void removeDeal() {
//        // Remove a deal from the application
//    }
}
