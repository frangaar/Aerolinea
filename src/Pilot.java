import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Pilot {

    private int id;
    private String name;
    private String surname;
    private String dni;
    private String phone;
    private String pilotCode;
    private String birthDate;

    static ArrayList<Pilot> pilots = new ArrayList<>();

    String[] headers;

    public Pilot(){

    }

    public Pilot(int id, String name, String surname, String dni, String phone, String pilotCode, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.phone = phone;
        this.pilotCode = pilotCode;
        this.birthDate = birthDate;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPilotCode() {
        return pilotCode;
    }

    public void setPilotCode(String pilotCode) {
        this.pilotCode = pilotCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String displayPilots() {

        return this.getName() + " " + this.getSurname() + " " + this.getPilotCode();
    }

    public void createPilots() {

        pilots.clear();

        pilots.add(new Pilot(0,"Fran","Garcia", "12345678A","666666666","A-01","18/02/1985"));
        pilots.add(new Pilot(0,"Sara","Gomez", "12345678B","777777777","A-02","18/02/1983"));
    }

    public ArrayList<Pilot> getPilotsList(){

        emptyList(pilots);

        String sql = "select * from pilots";

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {

            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);

//            recorrer resultset
            while(myResulset.next()){
                pilots.add(new Pilot(myResulset.getInt("pilotId"),
                myResulset.getString("pilotName"),
                myResulset.getString("pilotSurname"),
                myResulset.getString("dni"),
                myResulset.getString("phone"),
                myResulset.getString("pilotCode"),
                myResulset.getString("birthDate")));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return pilots;
    }

    private void emptyList(ArrayList<?> list) {

        list.clear();
    }

    public String[] getPilotsHeaders() {


        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Plane planeObj = new Plane();
        Pilot pilotObj = new Pilot();
        pilots.clear();


        String sql = "select * from pilots";


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
        return "aerolinea.Pilot:" +
                "name='" + this.name + '\'' +
                ", surname='" + this.surname + '\'' +
                ", dni='" + this.dni + '\'' +
                ", phone='" + this.phone + '\'' +
                ", pilotCode='" + this.pilotCode + '\'' +
                ", birthDate='" + this.birthDate + '\'' +
                '\n';
    }

    public Pilot getPilotsInfo(int id) {

        return pilots.get(id);
    }
}
