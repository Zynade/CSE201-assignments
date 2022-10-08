import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printWelcomeScreen() {
        System.out.println("Welcome to FutureBuilder!");
        System.out.println("\t1) Enter the Application");
        System.out.println("\t2) Exit the Application");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            CLI.printModeMenu();
        } else if (choice == 2) {
            System.out.println("Thank you for using FutureBuilder!");
            System.exit(0);
        }
    }

    public static void printModeMenu() {
        System.out.println("Select the mode of operation:");
        System.out.println("\t1) Enter as Student Mode");
        System.out.println("\t2) Enter as Company Mode");
        System.out.println("\t3) Enter as Placement Cell Mode");
        System.out.println("\t4) Return to Main Application");
        int modeChoice = scanner.nextInt();
        scanner.nextLine();
        if (modeChoice == 1) {
            CLI.printStudentMode();
        } else if (modeChoice == 2) {
            CLI.printCompanyMode();
        } else if (modeChoice == 3) {
            CLI.printPlacementCellMode();
        } else if (modeChoice == 4) {
            CLI.printWelcomeScreen();
        }
    }

    public static void printStudentMode() {
        System.out.println("Welcome to the Student Mode!");
        System.out.println("\t1) Enter as a Student");
        System.out.println("\t2) Add students");
        System.out.println("\t3) Back");
        int studentModeChoice = scanner.nextInt();
        scanner.nextLine();
        if (studentModeChoice == 1) {
            System.out.println("Name: ");
            String name = scanner.next();
            System.out.println("Roll number: ");
            int rollNumber = scanner.nextInt();
            scanner.nextLine();
            for (Student student : PlacementCell.getAllStudents()) {
                if (student.getName().equals(name) && student.getRollNum() == rollNumber) {
                    CLI.printStudentMenu(student);
                    return;
                }
            }
            System.out.println("Student not found!");
            CLI.printStudentMode();
        } else if (studentModeChoice == 2) {
            CLI.printAddStudents();
        } else if (studentModeChoice == 3) {
            CLI.printModeMenu();
        }
    }

    public static void printAddStudents() {
        System.out.println("Enter the number of students to be added:");
        Scanner scanner = new Scanner(System.in);
        int numStudents = scanner.nextInt();
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Details for Student" + (i + 1) + ":");
            System.out.println("Name: ");
            String name = scanner.next();
            System.out.println("CGPA: ");
            float cgpa = scanner.nextFloat();
            System.out.println("Roll number: ");
            int rollNumber = scanner.nextInt();
            System.out.println("Branch: ");
            String branch = scanner.next();
            Student student = new Student(name, rollNumber, cgpa, branch);
            PlacementCell.addStudentToAllStudents(student);
            System.out.println();
        }
        CLI.printStudentMode();
    }

    public static void printStudentMenu(Student student) {
        System.out.println("Welcome " + student.getName() + "!");
        System.out.println("\t1) Register for Placement Drive");
        System.out.println("\t2) Register for Company ");
        System.out.println("\t3) Get All Available Companies");
        System.out.println("\t4) Get current status");
        System.out.println("\t5) Update CGPA");
        System.out.println("\t6) Accept offer");
        System.out.println("\t7) Reject offer");
        System.out.println("\t8) Back");

        int studentChoice = scanner.nextInt();
        scanner.nextLine();
        if (studentChoice == 1) {
            CLI.printRegisterForPlacementDrive(student);
        } else if (studentChoice == 2) {
            CLI.printRegisterForCompany(student);
        } else if (studentChoice == 3) {
            CLI.printAllAvailableCompanies(student);
        } else if (studentChoice == 4) {
            CLI.printGetCurrentStatus(student);
        } else if (studentChoice == 5) {
            CLI.printUpdateCGPA(student);
        } else if (studentChoice == 6) {
            CLI.printAcceptOffer(student);
        } else if (studentChoice == 7) {
            CLI.printRejectOffer(student);
        } else if (studentChoice == 8) {
            CLI.printStudentMode();
        }
    }

    public static void printRegisterForPlacementDrive(Student student) {
        if (PlacementCell.getRegisteredStudents().contains(student)) {
            System.out.println("You are already registered!");
        } else {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(PlacementCell.studentRegStartTime) || now.isAfter(PlacementCell.studentRegDeadline)) {
                System.out.println("Registration is closed!");
            } else {
                PlacementCell.registerStudent(student);
                System.out.println("Welcome! You have successfully registered for the placement drive at IIIT Delhi!");
            }
        }
        System.out.println("Your details are as follows:");
        student.printDetails();
        CLI.printStudentMenu(student);
    }

    public static void printRegisterForCompany(Student student) {
        System.out.println("Enter the name of the company you want to register for:");
        String companyName = scanner.next();
        for (Company company : PlacementCell.getRegisteredCompanies()) {
            if (company.getName().equals(companyName)) {
                if (company.getStudentsApplied().contains(student)) {
                    System.out.println("You are already registered for this company!");
                } else {
                    if (!student.checkEligibility(company)) {
                        System.out.println("You are not eligible for this company!");
                    } else {
                        company.applyStudent(student);
                        System.out.println("You have successfully applied for " + companyName + "!");
                    }
                }
                CLI.printStudentMenu(student);
                return;
            }
        }
        System.out.println("No such company exists!");
        CLI.printStudentMenu(student);
    }

    public static void printAllAvailableCompanies(Student student) {
        System.out.println("The following companies are available:");
        student.printAvailableCompanies();
        CLI.printStudentMenu(student);
    }

    public static void printGetCurrentStatus(Student student) {
        System.out.println("Your current status is:");
        int status = student.getCurrentStatus();
        if (status == -1) System.out.println("Not Applied");
        else if (status == 0) System.out.println("Applied, but not placed");
        else if (status == 1) System.out.println("Placed");
        else if (status == 2) System.out.println("Blocked");
        CLI.printStudentMenu(student);
    }

    public static void printUpdateCGPA(Student student) {
        System.out.println("Enter your new CGPA:");
        float cgpa = scanner.nextFloat();
        student.updateCgpa(cgpa);
        System.out.println("Your CGPA has been updated!");
        CLI.printStudentMenu(student);
    }

    public static void printAcceptOffer(Student student) {
        student.actOnOffer(true);
        CLI.printStudentMenu(student);
    }

    public static void printRejectOffer(Student student) {
        student.actOnOffer(false);
        CLI.printStudentMenu(student);
    }

    public static void printCompanyMode() {
        System.out.println("Welcome to the Company Mode!");
        System.out.println("\t1) Add Company and Details");
        System.out.println("\t2) Choose Company");
        System.out.println("\t3) Get Available Companies");
        System.out.println("\t4) Back");
        int companyModeChoice = scanner.nextInt();
        scanner.nextLine();
        if (companyModeChoice == 1) {
            CLI.printAddCompany();
        } else if (companyModeChoice == 2) {
            CLI.printChooseCompany();
        } else if (companyModeChoice == 3) {
            CLI.printGetAvailableCompanies();
        } else if (companyModeChoice == 4) {
            CLI.printModeMenu();
        }
    }

    public static void printAddCompany() {
        System.out.println("Company name: ");
        String name = scanner.next();
        System.out.println("Role: ");
        String role = scanner.next();
        System.out.println("Salary (in LPA): ");
        float salary = scanner.nextFloat();
        System.out.println("CGPA requirement: ");
        float cgpa = scanner.nextFloat();

        Company company = new Company(name, role, salary, cgpa);
        PlacementCell.addCompanyToAllCompanies(company);
        System.out.println("Company added successfully!");
        CLI.printCompanyMode();
    }

    public static void printChooseCompany() {
        System.out.println("Choose to sign in as any of the following companies: ");
        ArrayList<Company> allCompanies = PlacementCell.getAllCompanies();
        for (int i = 0; i < allCompanies.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + allCompanies.get(i).getName());
        }
        System.out.println("\t" + (allCompanies.size() + 1) + ". Back");
        int companyChoice = scanner.nextInt();
        scanner.nextLine();
        if (companyChoice == allCompanies.size() + 1) {
            CLI.printCompanyMode();
        } else {
            if (companyChoice > allCompanies.size() || companyChoice < 1) {
                System.out.println("Invalid choice!");
                CLI.printChooseCompany();
            } else {
                CLI.printCompanyMenu(allCompanies.get(companyChoice - 1));
            }
        }
    }

    private static void printGetAvailableCompanies() {
        for (Company company : PlacementCell.getAllCompanies()) {
            System.out.println("\t" + company.getName());
        }
        CLI.printCompanyMode();
    }

    public static void printCompanyMenu(Company company) {
        System.out.println("Welcome, " + company.getName() + "!");
        System.out.println("\t1) Update Role");
        System.out.println("\t2) Update Salary");
        System.out.println("\t3) Update CGPA Requirement");
        System.out.println("\t4) Register to Placement Drive");
        System.out.println("\t5) Back");
        int companyChoice = scanner.nextInt();
        scanner.nextLine();
        if (companyChoice == 1) {
            CLI.printUpdateRole(company);
        } else if (companyChoice == 2) {
            CLI.printUpdateSalary(company);
        } else if (companyChoice == 3) {
            CLI.printUpdateCGPARequirement(company);
        } else if (companyChoice == 4) {
            CLI.printRegCompanyForPlacementDrive(company);
        } else if (companyChoice == 5) {
            CLI.printCompanyMode();
        }
    }

    public static void printUpdateRole(Company company) {
        System.out.println("Enter the new role: ");
        String role = scanner.next();
        company.setRole(role);
        System.out.println("Role updated successfully!");
        CLI.printCompanyMenu(company);
    }

    public static void printUpdateSalary(Company company) {
        System.out.println("Enter the new salary (in LPA): ");
        float salary = scanner.nextFloat();
        company.setSalaryOffered(salary);
        System.out.println("Salary updated successfully!");
        CLI.printCompanyMenu(company);
    }

    public static void printUpdateCGPARequirement(Company company) {
        System.out.println("Enter the new CGPA requirement: ");
        float cgpa = scanner.nextFloat();
        company.setCgpaCriteria(cgpa);
        System.out.println("CGPA requirement updated successfully!");
        CLI.printCompanyMenu(company);
    }

    public static void printRegCompanyForPlacementDrive(Company company) {

        if (PlacementCell.getRegisteredCompanies().contains(company)) {
            System.out.println("The company is already registered!");
        } else {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String nowString = now.format(formatter);
            now = LocalDateTime.parse(nowString, formatter);

            if (now.isBefore(PlacementCell.companyRegStartTime) || now.isAfter(PlacementCell.companyRegDeadline)) {
                System.out.println("Registration is closed!");
            } else {
                company.registerForPlacementDrive(now);
                PlacementCell.registerCompany(company);
                System.out.println("Company registered successfully!");
            }
        }
        System.out.println("Your company details are as follows:");
        company.printDetails();
        CLI.printCompanyMenu(company);
    }

    public static void printPlacementCellMode() {
        System.out.println("Welcome to the IIIT Delhi Placement Cell!");
        System.out.println("\t1) Open Student Registrations");
        System.out.println("\t2) Open Company Registrations");
        System.out.println("\t3) Get Number of Student Registrations");
        System.out.println("\t4) Get Number of Company Registrations");
        System.out.println("\t5) Get Number of Offered/Unoffered/Blocked Students");
        System.out.println("\t6) Get Student Details");
        System.out.println("\t7) Get Company Details");
        System.out.println("\t8) Get Average Package");
        System.out.println("\t9) Get Company Process Results");
        System.out.println("\t10) Back");

        int placementCellModeChoice = scanner.nextInt();
        scanner.nextLine();
        if (placementCellModeChoice == 1) {
            CLI.printOpenStudentReg();
        } else if (placementCellModeChoice == 2) {
            CLI.printOpenCompanyReg();
        } else if (placementCellModeChoice == 3) {
            CLI.printGetNumStudentReg();
        } else if (placementCellModeChoice == 4) {
            CLI.printGetNumCompanyReg();
        } else if (placementCellModeChoice == 5) {
            CLI.printStudentStatistics();
        } else if (placementCellModeChoice == 6) {
            CLI.printGetStudentDetails();
        } else if (placementCellModeChoice == 7) {
            CLI.printGetCompanyDetails();
        } else if (placementCellModeChoice == 8) {
            CLI.printGetAveragePackage();
        } else if (placementCellModeChoice == 9) {
            CLI.printGetCompanyProcessResults();
        } else if (placementCellModeChoice == 10) {
            CLI.printModeMenu();
        }
    }

    public static void printOpenStudentReg() {
        if (PlacementCell.companyRegDeadline == null) {
            System.out.println("Please enter the time line for company registration before trying to register students.");
            CLI.printPlacementCellMode();
            return;
        }
        System.out.println("Enter the start time (in the format yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.println("Enter the deadline (in the format yyyy-MM-dd HH:mm): ");
        String deadline = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime regOpeningTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime regClosingTime = LocalDateTime.parse(deadline, formatter);

        if (regOpeningTime.isAfter(regClosingTime)) {
            System.out.println("Invalid dates!");
            CLI.printOpenStudentReg();
            return;
        }
        if (regOpeningTime.isBefore(PlacementCell.companyRegDeadline)) {
            System.out.println("The student registration time cannot start before the company registration deadline!");
            CLI.printOpenStudentReg();
            return;
        }
        PlacementCell.startStudentRegistrations(regOpeningTime, regClosingTime);
        System.out.println("Student registrations opened successfully!");
        CLI.printPlacementCellMode();
    }

    public static void printOpenCompanyReg() {
        System.out.println("Enter the start time (in the format yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.println("Enter the deadline (in the format yyyy-MM-dd HH:mm): ");
        String deadline = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime regOpeningTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime regClosingTime = LocalDateTime.parse(deadline, formatter);

        if (regOpeningTime.isAfter(regClosingTime)) {
            System.out.println("Invalid dates!");
            CLI.printOpenCompanyReg();
            return;
        }
        PlacementCell.startCompanyRegistrations(regOpeningTime, regClosingTime);
//        System.out.println("Company registrations opened successfully!");
        CLI.printPlacementCellMode();
    }

    public static void printGetNumStudentReg() {
        PlacementCell.printNumberOfRegisteredStudents();
        CLI.printPlacementCellMode();
    }

    public static void printGetNumCompanyReg() {
        PlacementCell.printNumberOfRegisteredCompanies();
        CLI.printPlacementCellMode();
    }

    public static void printStudentStatistics() {
        PlacementCell.printStudentStatistics();
        CLI.printPlacementCellMode();
    }

    public static void printGetStudentDetails() {
        System.out.println("Student name: ");
        String name = scanner.nextLine();
        System.out.println("Student roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();
        PlacementCell.printStudentPlacementStatus(name, rollNumber);
        CLI.printPlacementCellMode();
    }

    public static void printGetCompanyDetails() {
        System.out.println("Company name: ");
        String name = scanner.nextLine();
        PlacementCell.printCompanyPlacementStatus(name);
        CLI.printPlacementCellMode();
    }

    public static void printGetAveragePackage() {
        PlacementCell.printAverageSalary();
        CLI.printPlacementCellMode();
    }

    public static void printGetCompanyProcessResults() {
        System.out.println("Company name: ");
        String name = scanner.nextLine();
        Company company = null;
        for (Company company1 : PlacementCell.getRegisteredCompanies()) {
            if (company1.getName().equals(name)) {
                company = company1;
                break;
            }
        }
        if (company == null) {
            System.out.println("No such company exists!");
            CLI.printGetCompanyProcessResults();
            return;
        }
        company.selectStudents();
        PlacementCell.printCompanyProcessResults(company);
        CLI.printPlacementCellMode();
    }
}
