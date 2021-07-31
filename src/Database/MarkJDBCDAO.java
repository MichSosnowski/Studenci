package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkJDBCDAO {
    private Connection connection = null;
    private Statement stmt = null;
    private PreparedStatement ptmt = null;
    private ResultSet resultSet = null;
    public static MarkJDBCDAO dbConn = new MarkJDBCDAO();

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstance().getConnection();
    }

    public boolean checkTables() {
        try {
            String queryString = "SELECT COUNT(DISTINCT table_name) FROM information_schema.columns WHERE table_schema = 'oceny'";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            String result = "";
            while (resultSet.next()) {
                result += resultSet.getInt("COUNT(DISTINCT table_name)");
            }
            if (!result.equals("0")) return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void createTables() {
        try {
            String queryString = "CREATE TABLE grupy(" +
                    "id_grupy int AUTO_INCREMENT PRIMARY KEY," +
                    "termin varchar(30) NOT NULL" +
                    ")";
            connection = getConnection();
            stmt = connection.prepareStatement(queryString);
            stmt.executeUpdate(queryString);
            queryString = "CREATE TABLE studenci(" +
                    "id_studenta int AUTO_INCREMENT PRIMARY KEY," +
                    "imie varchar(30) NOT NULL," +
                    "nazwisko varchar(30) NOT NULL," +
                    "numer_USOS int(6) NOT NULL," +
                    "punktyI int(2) NOT NULL," +
                    "punktyII int(2) NOT NULL," +
                    "suma int(3) NOT NULL," +
                    "ocena varchar(3) NOT NULL," +
                    "id_grupy int NOT NULL," +
                    "FOREIGN KEY (id_grupy)" +
                    "REFERENCES grupy(id_grupy)" +
                    "ON DELETE CASCADE)";
            stmt = connection.prepareStatement(queryString);
            stmt.executeUpdate(queryString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addGroup(Groups group) {
        try {
            String queryString = "INSERT INTO grupy(termin) VALUES(?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            ptmt.setString(1, group.getTerm());
            ptmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeGroup(int numberSelection) {
        try {
            String queryString = "DELETE FROM grupy WHERE id_grupy = " + numberSelection;
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            ptmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String findAllGroups() {
        try {
            String queryString = "SELECT * FROM grupy";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            String result = "";
            while (resultSet.next()) {
                result += "Grupa " + resultSet.getInt("id_grupy") + ": " + resultSet.getString("termin") + "\n";
            }
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getIdStudent(Students student) {
        try {
            String queryString = "SELECT id_studenta FROM studenci WHERE imie = \'" + student.getFirstName() + "\' AND nazwisko = \'" +
                    student.getSurname() + "\' AND numer_USOS = " + student.getUSOSNumber();
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id_studenta");
            }
            return id;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void giveMark(int idStudent, String ocena) {
        try {
            String queryString = "UPDATE studenci SET ocena = " + ocena + " WHERE id_studenta = " + idStudent;
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            ptmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addStudent(Students student, int numberSelection) {
        try {
            String queryString = "INSERT INTO studenci(imie, nazwisko, numer_USOS, punktyI, punktyII, suma, ocena, id_grupy) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            ptmt.setString(1, student.getFirstName());
            ptmt.setString(2, student.getSurname());
            ptmt.setInt(3, student.getUSOSNumber());
            ptmt.setInt(4, student.getPointsI());
            ptmt.setInt(5, student.getPointsII());
            ptmt.setInt(6, student.getSum());
            ptmt.setString(7, student.getMark());
            ptmt.setInt(8, numberSelection);
            ptmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void editStudent(Students student, int idStudent) {
        try {
            String queryString = "UPDATE studenci SET imie = \'" + student.getFirstName() + "\', nazwisko = \'" + student.getSurname() +
                    "\', numer_USOS = " + student.getUSOSNumber() + ", punktyI = " + student.getPointsI() + ", punktyII = " + student.getPointsII() +
                    ", suma = " + student.getSum() + " WHERE id_studenta = " + idStudent;
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            ptmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeStudent(Students student) {
        try {
            String queryString = "DELETE FROM studenci WHERE id_studenta = " + student.getIdStudent();
            connection = getConnection();
            stmt = connection.prepareStatement(queryString);
            stmt.executeUpdate(queryString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Students> findAllStudents(int numberSelection) {
        try {
            String queryString = "SELECT * FROM studenci WHERE id_grupy = " + numberSelection;
            connection = getConnection();
            ptmt = connection.prepareStatement(queryString);
            resultSet = ptmt.executeQuery();
            List<Students> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(new Students(resultSet.getInt("id_studenta"), resultSet.getString("imie"), resultSet.getString("nazwisko"), resultSet.getInt("numer_USOS"), resultSet.getInt("punktyI"), resultSet.getInt("punktyII"), resultSet.getInt("suma"), resultSet.getString("ocena")));
            }
            return students;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
                if (ptmt != null) ptmt.close();
                if (connection != null) connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
