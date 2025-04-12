package model;

/**
 * Student class represents a student entity with basic attributes.
 * This is our data model class following OOP principles.
 */
public class Student {
    // Private fields for encapsulation
    private int id;
    private String name;
    private int age;
    private String course;

    // Default constructor
    public Student() {
    }

    // Parameterized constructor for creating a student with all attributes
    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // Getters and Setters for encapsulation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // toString method for easy printing of student information
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age=" + age +
                ", course='" + course + "'" +
                '}';
    }

    // Method to convert Student object to CSV format for file storage
    public String toCsvString() {
        return id + "," + name + "," + age + "," + course;
    }
}