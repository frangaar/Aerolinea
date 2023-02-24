import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Plane {

    private int id;
    private String name;
    private String creationDate;
    private int fuelCapacity;
    private int TOTALSEATS;

    static ArrayList<Plane> planes = new ArrayList<Plane>();

    String[] headers;

    public Plane(){

    }

    public Plane(int id, String name, String creationDate, int fuelCapacity, int TOTALSEATS) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.fuelCapacity = fuelCapacity;
        this.TOTALSEATS = TOTALSEATS;
    }

    public int getId() {
        return id;
    }

    public void setName(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public int getTOTALSEATS() {
        return this.TOTALSEATS;
    }

    public void createPlanes() {

        planes.clear();

        planes.add(new Plane(0,"Boeing-747","25/07/2018", 229980,250));
        planes.add(new Plane(0,"Boeing-737","25/07/2017", 117340,150));
    }

    public ArrayList<Plane> getPlanesList(){

        emptyList(planes);

        String sql = "select * from planes";

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {

            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);

//            recorrer resultset
            while(myResulset.next()){

                planes.add(new Plane(myResulset.getInt("planeId"),
                myResulset.getString("planeName"),
                myResulset.getString("planeName"),
                myResulset.getInt("fuelCapacity"),
                myResulset.getInt("totalSeats")));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return planes;
    }

    private void emptyList(ArrayList<?> list) {

        list.clear();
    }

    public String[] getPlanesHeaders() {


        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        planes.clear();


        String sql = "select * from planes";


        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {

            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);


//            recorrer resultset

            ResultSetMetaData rsmd = myResulset.getMetaData();
            headers = new String[rsmd.getColumnCount()];

            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                headers[i] = rsmd.getColumnName(i+1);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        return headers;
    }




    @Override
    public String toString() {
        return "aerolinea.Plane:" +
                "name='" + this.name + '\'' +
                ", creationDate='" + this.creationDate + '\'' +
                ", fuelCapacity=" + this.fuelCapacity +
                '\n';
    }

    public Plane getPlanesInfo(int id) {

        return planes.get(id);
    }
}
