import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class PlacementCell {
    private static ArrayList<Student> allStudents = new ArrayList<Student>();
    private static ArrayList<Company> allCompanies = new ArrayList<Company>();
    private static ArrayList<Student> registeredStudents = new ArrayList<Student>();
    private static ArrayList<Company> registeredCompanies = new ArrayList<Company>();

    static LocalDateTime studentRegStartTime = null;
    static LocalDateTime companyRegStartTime = null;
    static LocalDateTime studentRegDeadline = null;
    static LocalDateTime companyRegDeadline = null;

    public static ArrayList<Student> getAllStudents() {
        return allStudents;
    }
    public static ArrayList<Company> getAllCompanies() {
        return allCompanies;
    }
    public static ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }
    public static ArrayList<Company> getRegisteredCompanies() {
        return registeredCompanies;
    }
    public static void addStudentToAllStudents(Student student) {
        allStudents.add(student);
    }
    public static void addCompanyToAllCompanies(Company company) {
        allCompanies.add(company);
    }
    public static void startStudentRegistrations(LocalDateTime startTime, LocalDateTime deadline) {
        studentRegStartTime = startTime;
        studentRegDeadline = deadline;
        System.out.println("The institute is now welcoming student registration!");
        System.out.println("You have until" + studentRegDeadline + " to register.");
    }

    public static void startCompanyRegistrations(LocalDateTime startTime, LocalDateTime deadline) {
        companyRegStartTime = startTime;
        companyRegDeadline = deadline;
        System.out.println("The institute is now welcoming company registration!");
        System.out.println("Deadline for company registrations is " + companyRegDeadline + ".");
    }

    public static void printNumberOfRegisteredStudents() {
        System.out.println("Number of registered students: " + registeredStudents.size());
    }

    public static void printNumberOfRegisteredCompanies() {
        System.out.println("Number of registered companies: " + registeredCompanies.size());
    }

    public static void printStudentStatistics() {
        int numPlaced = 0;
        int numNotPlaced = 0;
        int numBlocked = 0;
        for (Student student : registeredStudents) {
            int _status = student.getCurrentStatus();
            if (_status == 0) {
                numPlaced++;
            } else if (_status == 1) {
                numNotPlaced++;
            } else if (_status == 2) {
                numBlocked++;
            }
        }
        System.out.println("\nNumber of students placed: " + numPlaced);
        for (Student student : registeredStudents) {
            if (student.getCurrentStatus() == 0) {
                System.out.println(student.getName() + " placed at " + student.getAcceptedCompany().getName() + " for " + student.getAcceptedCompany().getRole() + " with a salary of " + student.getCurrentSalary());
            }
        }
        System.out.println("\nNumber of students not yet placed: " + numNotPlaced);
        for (Student student : registeredStudents) {
            if (student.getCurrentStatus() == 1) {
                System.out.println("Name: " + student.getName() + ", Roll: " + student.getRollNum() + ", CGPA: " + student.getCgpa() + ", Branch: " + student.getBranch());
            }
        }
        System.out.println("\nStudents blocked out of the placement process: " + numBlocked);
        for (Student student : registeredStudents) {
            if (student.getCurrentStatus() == 2) {
                System.out.println("Name: " + student.getName() + ", Roll: " + student.getRollNum() + ", CGPA: " + student.getCgpa() + ", Branch: " + student.getBranch());
            }
        }
    }

    public static void printStudentPlacementStatus(String studentName, int studentRollNum) {
        for (Student student : registeredStudents) {
            if (student.getName().equals(studentName) && student.getRollNum() == studentRollNum) {

                // Generate the list of companies the student hasn't applied to
                ArrayList<Company> companiesNotAppliedTo = new ArrayList<>();
                for (Company company : registeredCompanies) {
                    boolean flag = false;
                    for (Company companyAppliedTo : student.getCompaniesAppliedTo()) {
                        if (company.getName().equals(companyAppliedTo.getName())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        companiesNotAppliedTo.add(company);
                    }
                }

                // Generate the list of companies the student did not receive offers from
                ArrayList<Company> companiesNotOfferedBy = new ArrayList<>();
                for (Company company : student.getCompaniesAppliedTo()) {
                    boolean flag = false;
                    for (Company companyOfferedBy : student.getCompaniesOfferedBy()) {
                        if (company.getName().equals(companyOfferedBy.getName())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        companiesNotOfferedBy.add(company);
                    }
                }

                System.out.println(student.getName() + "has applied to and received offers from:");
                for (Company company : student.getCompaniesOfferedBy()) {
                    System.out.print(company.getName() + " worth " + company.getSalaryOffered());
                }

                System.out.println(student.getName() + "has applied to but not received offers from:");
                for (Company company : companiesNotOfferedBy) {
                    System.out.print(company.getName());
                }
                System.out.println(student.getName() + "has not applied to:");
                for (Company company : companiesNotAppliedTo) {
                    System.out.print(company.getName());
                }
                return;
            }
            System.out.println("Student not found!");
        }
    }

    public static void printCompanyPlacementStatus(String companyName) {
        for (Company company : registeredCompanies) {
            if (company.getName().equals(companyName)) {
                System.out.println(company.getName() + " has offered placements to:");
                for (Student student : company.getStudentsOffered()) {
                    System.out.println(student.getName() + ", " + student.getRollNum() + " for " + student.getAcceptedCompany().getRole() + " with a salary of " + student.getCurrentSalary());
                }
                System.out.println(company.getName() + " has not offered placements to:");
                for (Student student : company.getStudentsNotOffered()) {
                    System.out.println(student.getName() + ", " + student.getRollNum());
                }
            }
        }
    }

    public static void printAverageSalary() {
        int totalSalary = 0;
        int numPlaced = 0;
        for (Student student : registeredStudents) {
            if (student.getCurrentStatus() == 1) {
                totalSalary += student.getCurrentStatus();
                numPlaced++;
            }
        }
        System.out.println("Number of placed students: " + numPlaced);
        System.out.println("Average salary of placed students: " + totalSalary / numPlaced);
    }

    public static void printCompanyProcessResults(Company company) {
        ArrayList<Student> selectedStudents = company.getStudentsOffered();
        for (Company company1 : registeredCompanies) {
            if (company1.getName().equals(company.getName())) {
                System.out.println("The following students have been selected for the company " + company1.getName() + ":");
                for (Student student : selectedStudents) {
                    System.out.println(student.getName() + ", " + student.getRollNum());
                }
            }
        }
    }

    public static void registerCompany(Company company) {
        registeredCompanies.add(company);
    }
    public static void registerStudent(Student student) {
        registeredStudents.add(student);
    }
    public static boolean requestCgpaUpdate(Student student, double newCgpa) {
        return true;
    }
//    private boolean isStudentRegistrationOpen() {
//        return studentRegDeadline != null && LocalDateTime.now().isBefore(studentRegDeadline);
//    }
//    private boolean isCompanyRegistrationOpen() {
//        return companyRegDeadline != null && LocalDateTime.now().isBefore(companyRegDeadline);
//    }
}
