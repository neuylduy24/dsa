import java.util.ArrayList;
import java.util.Collections;
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
    public void setStudentId(String studentId){
        this.studentId = studentId;
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
        }
        else{
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

    public void bubbleSortStudentsByScore(boolean descending) {
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (descending) { 
                    if (students.get(j).getScore() < students.get(j + 1).getScore()) {
                        Collections.swap(students, j, j + 1);
                    }
                } else {
                    if (students.get(j).getScore() > students.get(j + 1).getScore()) {
                        Collections.swap(students, j, j + 1);
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return students.isEmpty();
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

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        try {
                            String studentId;
                            while (true) {
                                System.out.print("Enter Student ID: ");
                                studentId = scanner.nextLine().trim();
                                if (!studentId.matches("^[a-zA-Z0-9]+$")) {
                                    System.out.println("Invalid ID. Please use only letters and numbers.");
                                } else if(studentId.isEmpty()){
                                    System.out.println("Id cannot be empty.");
                                } else if (management.searchStudent(studentId) != null) {
                                    System.out.println("Student ID already exists. Please use a different ID."); 
                                } else {
                                    break;
                                }
                            }
                            System.out.print("Enter Student Name: ");
                            String fullName = scanner.nextLine();
                            System.out.print("Enter Student Score: ");
                            double score;
                            while (true) {
                                System.out.print("Enter Student Score (0-10): ");
                                score = scanner.nextDouble();
                                if (score >= 0 && score <= 10) {
                                    break;
                                } else {
                                    System.out.println("Invalid score. Please enter a value between 0 and 10.");
                                }
                            }
                            management.addStudent(studentId, fullName, score);
                            System.out.println("Add student successfully.");
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please enter a valid score.");
                            scanner.nextLine(); // Clear invalid input
                        }
                        break;

                        case 2:
                        try {
                            String studentId;
                            while (true) {
                                System.out.print("Enter Student ID to Edit: ");
                                studentId = scanner.nextLine().trim();
                                if (studentId.matches("^[a-zA-Z0-9]+$")) {
                                    break;
                                } else {
                                    System.out.println("Invalid ID. Please use only letters and numbers.");
                                }
                            }
                            Student student = management.searchStudent(studentId);
                            if (student == null) {
                                System.out.println("Student with ID " + studentId + " not found. Exiting edit.");
                                break; 
                            }
                            System.out.print("Enter New Name (leave blank to keep current): ");
                            String newName = scanner.nextLine();
                            String newScoreInput;
                            Double newScore = null;                  
                            while (true) {
                                System.out.print("Enter New Score (0-10, leave blank to keep current): ");
                                newScoreInput = scanner.nextLine();
                                if (newScoreInput.isEmpty()) {
                                    break;
                                }
                                try {
                                    newScore = Double.parseDouble(newScoreInput);
                                    if (newScore >= 0 && newScore <= 10) {
                                        break;
                                    } else {
                                        System.out.println("Invalid score. Please enter a value between 0 and 10.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid score format. Please enter a valid number.");
                                }
                            }
                            management.editStudent(studentId, newName.isEmpty() ? null : newName, newScore);
                            System.out.println("Student updated successfully.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid score format. Please enter a valid number.");
                        }
                        break;                    

                    case 3:
                        try {
                            String studentId;
                            while (true) {
                                System.out.print("Enter Student ID to Delete: ");
                                studentId = scanner.nextLine();
                                if (studentId.matches("^[a-zA-Z0-9]+$")) {
                                    break;
                                } else {
                                    System.out.println("Invalid ID. Please use only letters and numbers.");
                                }
                            }
                            Student student = management.searchStudent(studentId);
                            if (student == null) {
                                System.out.println("Student with ID " + studentId + " not found. Exiting delete.");
                                break;
                            }
                            management.deleteStudent(studentId);
                            System.out.println("Student deleted successfully.");
                        } catch (Exception e) {
                            System.out.println("An error occurred while deleting the student: " + e.getMessage());
                        }
                        break;

                    case 4:
                        try {
                            String studentId;
                            while (true) {
                                System.out.print("Enter Student ID to Search: ");
                                studentId = scanner.nextLine();
                                if (studentId.matches("^[a-zA-Z0-9]+$")) {
                                    break;
                                } else {
                                    System.out.println("Invalid ID. Please use only letters and numbers.");
                                }
                            }
                            Student student = management.searchStudent(studentId);
                            if (student != null) {
                                System.out.println(student);
                            } else {
                                System.out.println("Student not found.");
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred while searching for the student: " + e.getMessage());
                        }
                        break;

                    case 5:
                        try {
                            System.out.println("1. Sort in ascending order");
                            System.out.println("2. Sort in descending order");
                            System.out.print("Choose an option: ");
                            int sortChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            if (sortChoice == 1) {
                                management.bubbleSortStudentsByScore(false);
                                System.out.println("Students sorted in ascending order:");
                            } else if (sortChoice == 2) {
                                management.bubbleSortStudentsByScore(true);
                                System.out.println("Students sorted in descending order:");
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                            }

                            management.displayStudents();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please choose 1 or 2.");
                            scanner.nextLine(); // Clear invalid input
                        }
                        break;

                    case 6:
                        try {
                            if (management.isEmpty()) {
                                System.out.println("No students to display.");
                            } else {
                                management.displayStudents();
                            }
                        } catch (Exception e) {
                            System.out.println("An error occurred while displaying students: " + e.getMessage());
                        }
                        break;

                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}


