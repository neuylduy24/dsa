import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String studentId;
    private String fullName;
    private double score;

    public Student(String studentId, String fullName, double score) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getRanking() {
        if (score >= 0 && score < 5.0) {
            return "Fail";
        } else if (score >= 5.0 && score < 6.5) {
            return "Medium";
        } else if (score >= 6.5 && score < 7.5) {
            return "Good";
        } else if (score >= 7.5 && score < 9.0) {
            return "Very Good";
        } else if (score >= 9.0 && score <= 10.0) {
            return "Excellent";
        } else {
            return "Invalid Score";
        }
    }

    @Override
    public String toString() {
        return "ID: " + studentId + ", Name: " + fullName + ", Score: " + score + ", Rank: " + getRanking();
    }
}
class StudentManagement {
    private List<Student> students;

    public StudentManagement() {
        students = new ArrayList<>();
    }

    public void addStudent(String studentId, String fullName, double score) {
        Student student = new Student(studentId, fullName, score);
        students.add(student);
    }

    public void editStudent(String studentId, String newFullName, Double newScore) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                if (newFullName != null) {
                    student.setFullName(newFullName);
                }
                if (newScore != null) {
                    student.setScore(newScore);
                }
                break;
            }
        }
    }

    public void deleteStudent(String studentId) {
        students.removeIf(student -> student.getStudentId().equals(studentId));
    }

    public Student searchStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public void sortStudentsByScore(boolean descending) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Compare adjacent elements based on the sorting order
                boolean compare = descending ? 
                    students.get(j).getScore() < students.get(j + 1).getScore() : 
                    students.get(j).getScore() > students.get(j + 1).getScore();
    
                if (compare) {
                    // Swap if elements are in the wrong order
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

    public void displayStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManagement management = new StudentManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Sort Students");
            System.out.println("6. Display All Students");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Enter Student Score: ");
                    double score = scanner.nextDouble();
                    management.addStudent(studentId, fullName, score);
                    break;
                case 2:
                    System.out.print("Enter Student ID to Edit: ");
                    studentId = scanner.nextLine();
                    System.out.print("Enter New Name (leave blank to keep current): ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Score (leave blank to keep current): ");
                    String newScoreInput = scanner.nextLine();
                    Double newScore = newScoreInput.isEmpty() ? null : Double.parseDouble(newScoreInput);
                    management.editStudent(studentId, newName.isEmpty() ? null : newName, newScore);
                    break;
                case 3:
                    System.out.print("Enter Student ID to Delete: ");
                    studentId = scanner.nextLine();
                    management.deleteStudent(studentId);
                    break;
                case 4:
                    System.out.print("Enter Student ID to Search: ");
                    studentId = scanner.nextLine();
                    Student student = management.searchStudent(studentId);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    System.out.print("Sort in descending order? (yes/no): ");
                    boolean descending = scanner.nextLine().equalsIgnoreCase("yes");
                    management.sortStudentsByScore(descending);
                    break;
                case 6:
                    management.displayStudents();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
