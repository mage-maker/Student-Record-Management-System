package util;

import model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for file operations - reading from and writing to CSV file.
 * This class demonstrates file I/O operations in Java.
 */
public class FileUtils {
    // Path to the CSV file where student data will be stored
    private static final String FILE_PATH = "data/students.csv";

    // Static initializer block to ensure the data directory exists
    static {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    /**
     * Saves a list of students to the CSV file.
     * @param students List of Student objects to save
     */
    public static void saveStudents(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write each student as a line in the CSV file
            for (Student student : students) {
                writer.write(student.toCsvString());
                writer.newLine();
            }
            System.out.println("Students data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving students data: " + e.getMessage());
        }
    }

    /**
     * Loads students from the CSV file.
     * @return List of Student objects loaded from the file
     */
    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            // Read each line and convert to Student object
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Student student = new Student(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            Integer.parseInt(parts[2]),
                            parts[3]
                    );
                    students.add(student);
                }
            }
            System.out.println("Students data loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading students data: " + e.getMessage());
        }
        return students;
    }
}