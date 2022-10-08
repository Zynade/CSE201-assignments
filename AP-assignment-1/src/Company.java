import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Company {
    private final String name;
    private String role;
    private float salaryOffered;
    private float cgpaCriteria;
    private LocalDateTime registrationDateTime;

    private ArrayList<Student> studentsSelected = new ArrayList<Student>();
    private ArrayList<Student> studentsApplied = new ArrayList<Student>();

    public Company(String name, String role, float salaryOffered, float cgpaCriteria) {
        this.name = name;
        this.role = role;
        this.salaryOffered = salaryOffered;
        this.cgpaCriteria = cgpaCriteria;
//        this.registrationDateTime = "Not registered";
    }
    public void registerForPlacementDrive(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
        PlacementCell.registerCompany(this);
        // .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    }
    public ArrayList<Student> getEligibleStudents(ArrayList<Student> students) {
        ArrayList<Student> eligibleStudents = new ArrayList<Student>();
        for (Student student : students) {
            if (student.getCgpa() >= this.cgpaCriteria && student.getCurrentSalary() * 3 <= this.salaryOffered) {
                eligibleStudents.add(student);
            }
        }
        return eligibleStudents;
    }
    public void selectStudents() {
        int studCount = 0;
        Random ran = new Random();
        ArrayList<Student> eligibleStudents = this.getEligibleStudents(PlacementCell.getRegisteredStudents());
        int numStudentsToSelect = ran.nextInt(eligibleStudents.size());
        if (numStudentsToSelect <= 0 && eligibleStudents.size() > 0) {
            numStudentsToSelect = 1;
        }
        for (Student student : eligibleStudents) {
            if (studCount < numStudentsToSelect) {
                student.setCurrentStatus(0);
                student.addCompanyOfferedBy(this);
                this.studentsSelected.add(student);
                studCount++;
            }
        }
    }
    public void applyStudent(Student student) {
        student.setCurrentStatus(0);
        this.studentsApplied.add(student);
    }
    public void acceptStudent(Student student) {
        student.setCurrentStatus(1);
        this.studentsSelected.add(student);
    }
    public void rejectStudent(Student student) {
        this.studentsApplied.remove(student);
        this.studentsSelected.remove(student);
    }
    public String getName() {
        return name;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setSalaryOffered(float salaryOffered) {
        this.salaryOffered = salaryOffered;
    }
    public float getSalaryOffered() {
        return salaryOffered;
    }
    public void setCgpaCriteria(float cgpaCriteria) {
        this.cgpaCriteria = cgpaCriteria;
    }
    public float getCgpaCriteria() {
        return cgpaCriteria;
    }
    public LocalDateTime getRegistrationDateTime() {
        return this.registrationDateTime;
    }

    public ArrayList<Student> getStudentsApplied() {
        return this.studentsApplied;
    }
    public ArrayList<Student> getStudentsSelected() {
        return this.studentsSelected;
    }
    public ArrayList<Student> getStudentsOffered() {
        return this.studentsSelected;
    }
    public ArrayList<Student> getStudentsNotOffered() {
        ArrayList<Student> studentsNotOffered = new ArrayList<>();
        for (Student student : studentsApplied) {
            boolean flag = false;
            for (Student studentOffered : studentsSelected) {
                if (student.getName().equals(studentOffered.getName())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                studentsNotOffered.add(student);
            }
        }
        return studentsNotOffered;
    }

    public void printDetails() {
        System.out.println("Company name: " + this.name);
        System.out.println("Role: " + this.role);
        System.out.println("Salary offered: " + this.salaryOffered);
        System.out.println("CGPA criteria: " + this.cgpaCriteria);
        System.out.println("Registration date and time: " + this.registrationDateTime);
//        System.out.println("Students offered: " + this.getStudentsOffered());
//        System.out.println("Students not offered: " + this.getStudentsNotOffered());
    }
}
