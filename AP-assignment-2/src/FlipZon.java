import java.util.ArrayList;
import java.util.HashMap;

public class FlipZon {
    private static final HashMap<Integer, Product> products = new HashMap<Integer, Product>();
    private static final HashMap<Integer, Category> categories = new HashMap<Integer, Category>();
    private static final HashMap<Integer, Deal> deals = new HashMap<Integer, Deal>();
    private static final Admin[] admins = {
        new Admin("Beff Jezos", "admin"),
        new Admin("Gill Bates", "admin")
    };
    private static final ArrayList<Customer> customers = new ArrayList<Customer>();
    private static Customer loggedInUser = null;
    public static Admin loggedInAdmin = null;

    public static void exploreProductCatalog() {
        for (Category category : FlipZon.getCategories().values()) {
            System.out.println("Category: " + category.getName());
            for (Integer productID : category.getProductIDs()) {
                Product product = FlipZon.getProductByID(productID);
                System.out.println("\tProduct: " + product.getName());
                System.out.println("\t\tPrice: " + product.getPrice());
                System.out.println("\t\tQuantity: " + product.getQuantity());
                System.out.println("\t\tDescription: " + product.getDescription());
                System.out.println("\t\tDiscounts:");
                System.out.println("\t\t\tNormal customers: " + product.getDiscountRateNormal() + "%");
                System.out.println("\t\t\tPrime customers: " + product.getDiscountRatePrime() + "%");
                System.out.println("\t\t\tElite customers: " + product.getDiscountRateElite() + "%");
                System.out.println("");
            }
        }
    }
    public static void exploreDeals() {
        if (deals.size() == 0) {
            System.out.println("There are no deals available at the moment. Please check back later!");
            return;
        }
        for (Deal deal : FlipZon.getDeals().values()) {
            deal.printDeal();
        }
    }
    public static boolean signInAsAdmin(String username, String password) {
        for (Admin admin : admins) {
            if (admin.authenticateCredentials(username, password)) {
                setLoggedInAdmin(admin);
                return true;
            }
        }
        return false;
    }
    public static boolean signInAsCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.authenticateCredentials(username, password)) {
                setLoggedInUser(customer);
                return true;
            }
        }
        return false;
    }
    public static boolean signUpCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return false;
            }
        }
        FlipZon.addCustomer(new NormalCustomer(username, password));
        return true;
    }
    public static Product getProductByID(int productID) {
        Product product = FlipZon.getProducts().get(productID);
        if (product == null) {
            throw new IllegalArgumentException("Product with ID " + productID + " does not exist!");
        }
        return product;
    }
    public static Category getCategoryByID(int categoryID) {
        Category category = FlipZon.getCategories().get(categoryID);
        if (category == null) {
            throw new IllegalArgumentException("Category with ID " + categoryID + " does not exist!");
        }
        return category;
    }
    public static Deal getDealByID(int dealID) {
        Deal deal = FlipZon.getDeals().get(dealID);
        if (deal == null) {
            throw new IllegalArgumentException("Deal with ID " + dealID + " does not exist!");
        }
        return deal;
    }

    // Getters
    public static HashMap<Integer, Product> getProducts() {
        return FlipZon.products;
    }
    public static HashMap<Integer, Category> getCategories() {
        return FlipZon.categories;
    }
    public static HashMap<Integer, Deal> getDeals() {
        return FlipZon.deals;
    }
    public static Admin[] getAdmins() {
        return FlipZon.admins;
    }
    public static ArrayList<Customer> getCustomers() {
        return FlipZon.customers;
    }
    public static void addCustomer(Customer customer) {
        FlipZon.getCustomers().add(customer);
    }
    public static void removeCustomer(Customer customer) {
        for (int i = 0; i < FlipZon.getCustomers().size(); i++) {
            if (FlipZon.getCustomers().get(i).getUsername().equals(customer.getUsername())) {
                FlipZon.getCustomers().remove(i);
                break;
            }
        }
    }
//
    public static Customer getLoggedInUser() {
        return FlipZon.loggedInUser;
    }
    public static void setLoggedInUser(Customer user) {
        FlipZon.loggedInUser = user;
    }
    public static Admin getLoggedInAdmin() {
        return FlipZon.loggedInAdmin;
    }
    public static void setLoggedInAdmin(Admin admin) {
        FlipZon.loggedInAdmin = admin;
    }
}
