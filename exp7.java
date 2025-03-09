//EASY
import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/yourDatabase";
        String user = "yourUsername";
        String password = "yourPassword";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {

            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") +
                                   ", Name: " + rs.getString("Name") +
                                   ", Salary: " + rs.getDouble("Salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//MEDIUM
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/yourDatabase";
    static final String USER = "yourUsername";
    static final String PASSWORD = "yourPassword";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("1. Create Product\n2. Read Products\n3. Update Product\n4. Delete Product\n5. Exit");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.print("Enter ProductName, Price, Quantity: ");
                    String name = scanner.next();
                    double price = scanner.nextDouble();
                    int quantity = scanner.nextInt();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)");
                    stmt.setString(1, name);
                    stmt.setDouble(2, price);
                    stmt.setInt(3, quantity);
                    stmt.executeUpdate();
                } else if (choice == 2) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getInt("ProductID") + ", Name: " + rs.getString("ProductName") + ", Price: " + rs.getDouble("Price") + ", Quantity: " + rs.getInt("Quantity"));
                    }
                } else if (choice == 3) {
                    System.out.print("Enter ProductID to update: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter new Price and Quantity: ");
                    double price = scanner.nextDouble();
                    int quantity = scanner.nextInt();
                    PreparedStatement stmt = conn.prepareStatement("UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?");
                    stmt.setDouble(1, price);
                    stmt.setInt(2, quantity);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                } else if (choice == 4) {
                    System.out.print("Enter ProductID to delete: ");
                    int id = scanner.nextInt();
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?");
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                } else break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//HARD
//Model: Student.java
public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

//Controller: StudentDAO.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private final String URL = "jdbc:mysql://localhost:3306/yourDatabase";
    private final String USER = "yourUsername";
    private final String PASSWORD = "yourPassword";

    public void addStudent(Student student) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, student.getStudentID());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getDepartment());
            stmt.setDouble(4, student.getMarks());
            stmt.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student")) {
            while (rs.next()) {
                students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getDouble("Marks")));
            }
        }
        return students;
    }
}

//View: StudentManagement.java
import java.sql.SQLException;
import java.util.Scanner;

public class StudentManagement {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student\n2. View Students\n3. Exit");
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter ID, Name, Department, Marks: ");
                int id = scanner.nextInt();
                String name = scanner.next();
                String department = scanner.next();
                double marks = scanner.nextDouble();
                try {
                    dao.addStudent(new Student(id, name, department, marks));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) {
                try {
                    for (Student s : dao.getAllStudents()) {
                        System.out.println("ID: " + s.getStudentID() + ", Name: " + s.getName() + ", Department: " + s.getDepartment() + ", Marks: " + s.getMarks());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else break;
        }
        scanner.close();
    }
}


