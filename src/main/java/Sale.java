public class Sale {

    int id;
    String first_name;
    String last_name;

    double sales;
    String state;

    String department;

    public Sale(int id, String firstName, String lastName, double sales, String state, String department) {
        this.id = id;
        this.first_name = firstName;
        this.last_name = lastName;
        this.sales = sales;
        this.state = state;
        this.department = department;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", first_Name='" + first_name + '\'' +
                ", last_Name='" + last_name + '\'' +
                ", sales=" + sales +
                ", state='" + state + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
