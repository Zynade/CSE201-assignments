import java.util.ArrayList;

public class Category {
    private final int categoryID;
    private final String name;
    private final ArrayList<Integer> productIDs = new ArrayList<Integer>();

    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }
    public int getCategoryID() {
        return categoryID;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Integer> getProductIDs() {
        return productIDs;
    }
    public void addProductID(int productID) {
        productIDs.add(productID);
    }
    public void removeProductID(int productID) {
        productIDs.remove(productID);
        if (productIDs.isEmpty()) {
            FlipZon.getCategories().remove(categoryID);
            System.out.println("Category " + name + " has been removed from the application.");
        }
    }
}
