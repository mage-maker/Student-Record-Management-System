package service;

import exception.StudentNotFoundException;
import model.Student;
import util.FileUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that contains business logic for student management.
 * This class demonstrates Java 8 features like streams and lambdas.
 */
public class StudentService {
    // List to store student objects in memory
    private List<Student> students;

    /**
     * Constructor initializes the service by loading students from file
     */
    public StudentService() {
        // Load students from file when service is initialized
        this.students = FileUtils.loadStudents();
    }

    /**
     * Adds a new student to the list and saves to file
     * @param student The student to add
     */
    public void addStudent(Student student) {
        // Check if ID already exists using Java 8 Stream API
        boolean idExists = students.stream()
                .anyMatch(s -> s.getId() == student.getId());
        
        if (idExists) {
            System.out.println("Student with ID " + student.getId() + " already exists!");
            return;
        }
        
        students.add(student);
        FileUtils.saveStudents(students);
        System.out.println("Student added successfully!");
    }

    /**
     * Returns a copy of all students
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent external modification
    }

    /**
     * Finds a student by ID
     * @param id The ID to search for
     * @return The found student
     * @throws StudentNotFoundException if student is not found
     */
    public Student getStudentById(int id) throws StudentNotFoundException {
        // Using Java 8 Stream API to find student by ID
        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Student with ID " + id + " not found!"));
    }

    /**
     * Updates an existing student
     * @param updatedStudent The updated student object
     * @throws StudentNotFoundException if student is not found
     */
    public void updateStudent(Student updatedStudent) throws StudentNotFoundException {
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == updatedStudent.getId()) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new StudentNotFoundException("Student with ID " + updatedStudent.getId() + " not found!");
        }

        students.set(index, updatedStudent);
        FileUtils.saveStudents(students);
        System.out.println("Student updated successfully!");
    }

    /**
     * Deletes a student by ID
     * @param id The ID of the student to delete
     * @throws StudentNotFoundException if student is not found
     */
    public void deleteStudentById(int id) throws StudentNotFoundException {
        // Using Java 8 removeIf with lambda
        boolean removed = students.removeIf(student -> student.getId() == id);
        
        if (!removed) {
            throw new StudentNotFoundException("Student with ID " + id + " not found!");
        }
        
        FileUtils.saveStudents(students);
        System.out.println("Student deleted successfully!");
    }

    /**
     * Searches for students by name (case-insensitive partial match)
     * @param name The name to search for
     * @return List of matching students
     */
    public List<Student> searchStudentsByName(String name) {
        // Using Java 8 Stream API for filtering
        return students.stream()
                .filter(student -> student.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Returns students sorted by name
     * @return List of students sorted by name
     */
    public List<Student> getStudentsSortedByName() {
        // Using Java 8 Stream API with Comparator
        return students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    /**
     * Returns students sorted by age
     * @return List of students sorted by age
     */
    public List<Student> getStudentsSortedByAge() {
        // Using Java 8 Stream API with Comparator
        return students.stream()
                .sorted(Comparator.comparing(Student::getAge))
                .collect(Collectors.toList());
    }

    /**
     * Returns students filtered by course
     * @param course The course to filter by
     * @return List of students in the specified course
     */
    public List<Student> getStudentsByCourse(String course) {
        // Using Java 8 Stream API for filtering
        return students.stream()
                .filter(student -> student.getCourse().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the next available ID for a new student
     * @return The next available ID
     */
    public int getNextId() {
        // Using Java 8 Stream API to find the maximum ID
        return students.stream()
                .mapToInt(Student::getId)
                .max()
                .orElse(0) + 1;
    }
}