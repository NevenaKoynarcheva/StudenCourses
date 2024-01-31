import model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.*;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/coursera";
    private static final String username = "postgres";
    private static final String password = "1234";

    public static void main(String[] args) {
        String sql = "SELECT s.first_name, s.last_name, c.name, c.total_time, c.credit, c.instructor_id " +
                "FROM students s " +
                "JOIN students_courses_xref e ON s.pin = e.student_pin " +
                "JOIN courses c ON e.course_id = c.id " +
                "GROUP BY s.pin, s.first_name, s.last_name, c.name, c.total_time, c.credit, c.instructor_id " +
                "HAVING SUM(c.credit) > 50";


        try {
            Class.forName("org.postgresql.Driver");
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Student> students = new ArrayList<>();

                    while (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String courseName = resultSet.getString("name");
                        int courseTime = resultSet.getInt("total_time");
                        int courseCredit = resultSet.getInt("credit");
                        String instructor = resultSet.getString("instructor");

                        students.add(new Student(firstName, lastName, courseName, courseCredit, courseTime, instructor));
                    }

                    writeReport(students);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void writeReport(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src//csv//report.csv"))) {
            for (Student student : students) {
                if (getTotalCredit(students).containsKey(student.getFirstName() + " " + student.getLastName())) {
                    writer.write(student.getFirstName() + " " + student.getLastName() + ", " + getTotalCredit(students).get(student.getFirstName() + " " + student.getLastName()) + ")\n");
                }

                writer.write("\t" + student.getCourseName() + ", " +
                        student.getCredit() + ", " + student.getInstructor() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String , Integer> getTotalCredit(List<Student> students){
        Map<String , Integer> total = new HashMap<>();
        for (Student student : students){
            if (!total.containsKey(student.getFirstName() + " " + student.getLastName())){
                total.put(student.getFirstName() + " " + student.getLastName(),0);
            }
           total.put(student.getFirstName() + " " + student.getLastName(),total.get(student.getFirstName() + " " + student.getLastName())+student.getCredit());
        }
        return total;
    }
}
