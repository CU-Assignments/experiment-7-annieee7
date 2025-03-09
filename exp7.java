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


