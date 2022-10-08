import java.util.ArrayList;
import java.util.Objects;

public class Student {
    private final String name;
    private final String email;
    private final int rollNum;
    private float cgpa;
    private final String branch;

    private int placementStatus; // -1 = not applied, 0 = not placed, 1 = placed, 2 = blocked

    private Company acceptedCompany;
    private ArrayList<Company> companiesAppliedTo = new ArrayList<>();
    private ArrayList<Company> companiesOfferedBy = new ArrayList<>();

    //    private Offer currentOffer = new Offer();
//    private String currentRole;
//    private String currentCompany = "Not placed";
//    private float currentSalary = 0;

    public Student(String name, int rollNum, float cgpa, String branch) {
        this.name = name;
        this.email = name + rollNum + "@university.edu";
        this.rollNum = rollNum;
        this.cgpa = cgpa;
        this.branch = branch;
        this.placementStatus = -1;
    }

    public void printDetails() {
        System.out.println("Name: " + this.name);
        System.out.println("Email: " + this.email);
        System.out.println("Roll number: " + this.rollNum);
        System.out.println("CGPA: " + this.cgpa);
        System.out.println("Branch: " + this.branch);
    }

    public void registerForPlacementDrive() {
        PlacementCell.registerStudent(this);
    }

    public void registerForCompany(Company company) {
        for (Company company_iterator : PlacementCell.getRegisteredCompanies()) {
            if (company_iterator.getName().equals(company.getName())) {
                if (this.checkEligibility(company)) {
                    company.applyStudent(this);
                    this.companiesAppliedTo.add(company);
                    this.placementStatus = 0;
                    System.out.println("Student " + this.name + " applied to " + company.getName());
                    return;
                }
            }
        }
    }

    public boolean checkEligibility(Company company) {
        if (this.cgpa >= company.getCgpaCriteria()) {
            return company.getSalaryOffered() >= 3 * this.getCurrentSalary();
        }
        return false;
    }

    public void printAvailableCompanies() {
        if (this.getCurrentStatus() == 2) {
            System.out.println("Unavailable. You have been blocked from the placement drive.");
        }
        for (Company company : PlacementCell.getRegisteredCompanies()) {
            if (this.checkEligibility(company)) {
                company.printDetails();
            }
        }
    }

    public void printCurrentStatus() {
        int _status = this.placementStatus;
        if (_status == -1) {
            System.out.println("Not applied for placement drive.");
        } else if (_status == 0) {
            System.out.println("Not placed");
        } else if (_status == 1) {
            System.out.println("Placed at " + this.acceptedCompany.getName() + " with an offer of " + this.getCurrentSalary());
            System.out.println("Also received offers from: ");
            for (Company company : this.companiesAppliedTo) {
                if (!Objects.equals(company.getName(), this.acceptedCompany.getName())) {
                    System.out.println(company.getName() + "with a salary of" + company.getSalaryOffered());
                }
            }
        } else if (_status == 2) {
            System.out.println("Blocked");
        }
    }

    public void updateCgpa(float newCgpa) {
        boolean permission = PlacementCell.requestCgpaUpdate(this, newCgpa);
        if (permission) {
            // if the placement cell approves of the CGPA update, set it to the new value.
            this.cgpa = newCgpa;
        }
    }

    public void actOnOffer(boolean acceptOffer) {
        if (this.placementStatus == 2) {
            System.out.println("You are blocked from placement drive.");
            return;
        }
        Company currentCompany = this.getNextBestOffer();
        if (currentCompany == null) {
            System.out.println("No more offers available.");
            return;
        }
        if (acceptOffer) {
            this.placementStatus = 1;
            currentCompany.acceptStudent(this);
            this.acceptedCompany = currentCompany;
            System.out.println("Congratulations! You have been placed at " + currentCompany.getName() + " with a salary of " + this.getCurrentSalary());
        } else {
            if (this.getCompaniesOfferedBy().size() == 1) {
                // get blocked
                this.placementStatus = 2;
            }
            currentCompany.rejectStudent(this);
            System.out.println("You have rejected " + currentCompany.getName());
        }
        this.companiesAppliedTo.remove(currentCompany);
        this.companiesOfferedBy.remove(currentCompany);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRollNum() {
        return rollNum;
    }

    public float getCgpa() {
        return cgpa;
    }

    public String getBranch() {
        return branch;
    }

    public int getCurrentStatus() {
        return this.placementStatus;
    }
    public void setCurrentStatus(int status) {
        if (status >= -1 && status <= 2) {
            this.placementStatus = status;
        }
    }
//
//    public String getCompanyName() {
//        return currentOffer.getCompanyName();
//    }
//
//    public String getRole() {
//        return currentOffer.getRole();
//    }
//
//    public float getSalary() {
//        return currentOffer.getSalaryOffered();
//    }

    public ArrayList<Company> getCompaniesAppliedTo() {
        return companiesAppliedTo;
    }

    public void addCompanyAppliedTo(Company company) {
        this.companiesAppliedTo.add(company);
    }

    public void removeCompanyAppliedTo(Company company) {
        this.companiesAppliedTo.remove(company);
    }

    public ArrayList<Company> getCompaniesOfferedBy() {
        return companiesOfferedBy;
    }

    public void addCompanyOfferedBy(Company company) {
        this.companiesOfferedBy.add(company);
    }

    public Company getNextBestOffer() {
        float maxSalary = 0;
        Company maxCompany = null;
        for (Company company : this.companiesOfferedBy) {
            if (company.getSalaryOffered() > maxSalary) {
                maxSalary = company.getSalaryOffered();
                maxCompany = company;
            }
        }
        return maxCompany;
    }

    public float getCurrentSalary() {
        if (this.acceptedCompany == null) {
            return 0;
        }
        return this.acceptedCompany.getSalaryOffered();
    }
    public Company getAcceptedCompany() {
        return acceptedCompany;
    }
}
