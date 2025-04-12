import exception.StudentNotFoundException;
import model.Student;
import service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Main class with the entry point and console menu system.
 * This class demonstrates user interaction via Scanner.
 */
public class Main {
    // Static instances for service and scanner
    private static final StudentService studentService = new StudentService();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Welcome to Student Management System!");
        
        // Main application loop
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            // Process user choice
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    searchStudentsByName();
                    break;
                case 7:
                    viewStudentsSortedByName();
                    break;
                case 8:
                    viewStudentsSortedByAge();
                    break;
                case 9:
                    viewStudentsByCourse();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Student Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Displays the main menu options
     */
    private static void displayMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Search Students by Name");
        System.out.println("7. View Students Sorted by Name");
        System.out.println("8. View Students Sorted by Age");
        System.out.println("9. View Students by Course");
        System.out.println("0. Exit");
        System.out.println("======================================");
    }
    /**
     * Handles adding a new student
     */
    private static void addStudent() {
        System.out.println("\n----- Add New Student -----");
        
        // Get next available ID
        int id = studentService.getNextId();
        System.out.println("Assigned ID: " + id);
        
        String name = getStringInput("Enter student name: ");
        int age = getIntInput("Enter student age: ");
        String course = getStringInput("Enter student course: ");

        Student student = new Student(id, name, age, course);
        studentService.addStudent(student);
    }

    /**
     * Displays all students
     */
    private static void viewAllStudents() {
        System.out.println("\n----- All Students -----");
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        displayStudents(students);
    }

    /**
     * Finds and displays a student by ID
     */
    private static void viewStudentById() {
        System.out.println("\n----- View Student by ID -----");
        int id = getIntInput("Enter student ID: ");
        
        try {
            Student student = studentService.getStudentById(id);
            System.out.println(student);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates an existing student
     */
    private static void updateStudent() {
        System.out.println("\n----- Update Student -----");
        int id = getIntInput("Enter student ID to update: ");
        
        try {
            Student student = studentService.getStudentById(id);
            System.out.println("Current student details: " + student);
            
            // Allow user to update fields or keep current values
            String name = getStringInput("Enter new name (or press Enter to keep current): ");
            if (!name.isEmpty()) {
                student.setName(name);
            }
            
            String ageStr = getStringInput("Enter new age (or press Enter to keep current): ");
            if (!ageStr.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageStr);
                    student.setAge(age);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age format. Age not updated.");
                }
            }
            
            String course = getStringInput("Enter new course (or press Enter to keep current): ");
            if (!course.isEmpty()) {
                student.setCourse(course);
            }
            
            studentService.updateStudent(student);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes a student by ID
     */
    private static void deleteStudent() {
        System.out.println("\n----- Delete Student -----");
        int id = getIntInput("Enter student ID to delete: ");
        
        try {
            studentService.deleteStudentById(id);
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Searches for students by name
     */
    private static void searchStudentsByName() {
        System.out.println("\n----- Search Students by Name -----");
        String name = getStringInput("Enter name to search: ");
        
        List<Student> students = studentService.searchStudentsByName(name);
        
        if (students.isEmpty()) {
            System.out.println("No students found with name containing '" + name + "'.");
            return;
        }
        
        System.out.println("Found " + students.size() + " student(s):");
        displayStudents(students);
    }

    /**
     * Displays students sorted by name
     */
    private static void viewStudentsSortedByName() {
        System.out.println("\n----- Students Sorted by Name -----");
        List<Student> students = studentService.getStudentsSortedByName();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        displayStudents(students);
    }

    /**
     * Displays students sorted by age
     */
    private static void viewStudentsSortedByAge() {
        System.out.println("\n----- Students Sorted by Age -----");
        List<Student> students = studentService.getStudentsSortedByAge();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        displayStudents(students);
    }

    /**
     * Displays students filtered by course
     */
    private static void viewStudentsByCourse() {
        System.out.println("\n----- View Students by Course -----");
        String course = getStringInput("Enter course name: ");
        
        List<Student> students = studentService.getStudentsByCourse(course);
        
        if (students.isEmpty()) {
            System.out.println("No students found in course '" + course + "'.");
            return;
        }
        
        System.out.println("Students in course '" + course + "':");
        displayStudents(students);
    }

    /**
     * Helper method to display a list of students in a formatted table
     */
    private static void displayStudents(List<Student> students) {
        System.out.println("ID\tName\t\tAge\tCourse");
        System.out.println("------------------------------------------");
        for (Student student : students) {
            System.out.printf("%-5d\t%-15s\t%-5d\t%s%n", 
                    student.getId(), 
                    student.getName(), 
                    student.getAge(), 
                    student.getCourse());
        }
    }

    /**
     * Helper method to get string input from user
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Helper method to get integer input from user with validation
     */
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}