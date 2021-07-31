package Database;

public class Students {
    private int idStudent, USOSNumber, pointsI, pointsII, sum;
    private String firstName, surname, mark;

    public Students(int idStudent, String firstName, String surname, int USOSNumber, int pointsI, int pointsII, int sum, String mark) {
        this.idStudent = idStudent;
        this.firstName = firstName;
        this.surname = surname;
        this.USOSNumber = USOSNumber;
        this.pointsI = pointsI;
        this.pointsII = pointsII;
        this.sum = sum;
        this.mark = mark;
    }

    public Students(String firstName, String surname, int USOSNumber, int pointsI, int pointsII, int sum, String mark) {
        this.firstName = firstName;
        this.surname = surname;
        this.USOSNumber = USOSNumber;
        this.pointsI = pointsI;
        this.pointsII = pointsII;
        this.sum = sum;
        this.mark = mark;
    }

    public int getUSOSNumber() {
        return USOSNumber;
    }

    public void setUSOSNumber(int USOSNumber) {
        this.USOSNumber = USOSNumber;
    }

    public int getPointsI() {
        return pointsI;
    }

    public void setPointsI(int pointsI) {
        this.pointsI = pointsI;
    }

    public int getPointsII() {
        return pointsII;
    }

    public void setPointsII(int pointsII) {
        this.pointsII = pointsII;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) { this.surname = surname; }

    public int getIdStudent() { return idStudent; }

    public void setIdStudent(int idStudent) { this.idStudent = idStudent; }
}
