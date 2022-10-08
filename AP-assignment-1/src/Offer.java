public class Offer {
    private String companyName;
    private String role;
    private float salaryOffered;
    private String studentName;
    private String status;

    public Offer(String companyName, String role, float salaryOffered) {
        this.companyName = companyName;
        this.role = role;
        this.salaryOffered = salaryOffered;
        this.status = "Pending"; // Three options: pending, accepted, or rejected.
    }
    public Offer() {
        this.companyName = "N/A";
        this.role = "N/A";
        this.salaryOffered = 0;
        this.status = "N/A";
    }
    public void printDetails() {
        System.out.println("Company name: " + this.companyName);
        System.out.println("Role: " + this.role);
        System.out.println("Salary offered: " + this.salaryOffered);
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public String getRole() {
        return this.role;
    }
    public float getSalaryOffered() {
        return this.salaryOffered;
    }
    public String getStudentName() {
        return this.studentName;
    }
    public String getStatus() {
        return this.status;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setSalaryOffered(float salaryOffered) {
        this.salaryOffered = salaryOffered;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
