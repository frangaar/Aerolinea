import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Flight {

    private int flightId;
    private String flightCode;
    private String flightOrigin;
    private String flightDestination;
    private int flightDuration;
    private Date flightDepartureTime;
    private Plane plane;
    private Pilot pilot;
    private int availableSeats;
    private double price;
    private double returnPercentage;

    static ArrayList<Flight> flights = new ArrayList<Flight>();

    String[] headers;

    public Flight(){

    }

    public Flight(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, double price, double returnPercentage) {
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.flightOrigin = flightOrigin;
        this.flightDestination = flightDestination;
        this.flightDuration = flightDuration;
        this.flightDepartureTime = flightDepartureTime;
        this.plane = plane;
        this.pilot = pilot;
        this.availableSeats = plane.getTOTALSEATS();
        this.price = price;
        this.returnPercentage = returnPercentage;
    }

    public Flight(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, int availableSeats, double price, double returnPercentage) {
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.flightOrigin = flightOrigin;
        this.flightDestination = flightDestination;
        this.flightDuration = flightDuration;
        this.flightDepartureTime = flightDepartureTime;
        this.plane = plane;
        this.pilot = pilot;
        this.availableSeats = availableSeats;
        this.price = price;
        this.returnPercentage = returnPercentage;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightCode() {
        return this.flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getFlightOrigin() {
        return this.flightOrigin;
    }

    public void setFlightOrigin(String flightOrigin) {
        this.flightOrigin = flightOrigin;
    }

    public String getFlightDestination() {
        return this.flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }

    public int getFlightDuration() {
        return this.flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public Date getFlightDepartureTime() {
        return this.flightDepartureTime;
    }

    public void setFlightDepartureTime(Date flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public Plane getPlane() {
        return this.plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Pilot getPilot() {
        return this.pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public int getAvailableSeats() {
        return this.availableSeats;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getReturnPercentage() {
        return this.returnPercentage;
    }

    public void setReturnPercentage(double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }


    public boolean createFlight(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, double price, double returnPercentage) {

        String sql = "";
        boolean valid = false;

        try{
            flights.add(new Flight(flights.size()+1,flightCode, flightOrigin, flightDestination, flightDuration, flightDepartureTime, plane, pilot, price, returnPercentage));

            sql = createInsertSql(flights.size(),flightCode, flightOrigin, flightDestination, flightDuration, flightDepartureTime, plane, pilot, price, returnPercentage);
            executeData(sql);

            valid = true;
        }catch(Exception e){
            e.getMessage();
        }
        return valid;
    }

    public boolean modifyFlight(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, int availableSeats, double price, double returnPercentage) {

        String sql = "";
        boolean valid = false;

        try{
            sql = createUpdateSql(flightId,flightCode, flightOrigin, flightDestination, flightDuration, flightDepartureTime, plane, pilot, availableSeats, price, returnPercentage);
            executeData(sql);

            valid = true;
        }catch(Exception e){
            e.getMessage();
        }
        return valid;
    }

    public boolean deleteFlight(int flightId) {

        String sql = "";
        boolean valid = false;

        try{
            sql = createDeleteSql(flightId);
            executeData(sql);

            valid = true;
        }catch(Exception e){
            e.getMessage();
        }
        return valid;

    }

    private String createInsertSql(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, double price, double returnPercentage) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sql = "";

        sql = "insert into flights values(" + flightId +
                ",'" + flightCode +
                "','" + flightOrigin +
                "','" + flightDestination +
                "'," + flightDuration +
                ",'" + dateFormat.format(flightDepartureTime) +
                "'," + plane.getId() +
                "," + pilot.getId() +
                "," + plane.getTOTALSEATS() +
                "," + price +
                "," + returnPercentage + ");";

        return sql;
    }

    private String createUpdateSql(int flightId, String flightCode, String flightOrigin, String flightDestination, int flightDuration, Date flightDepartureTime, Plane plane, Pilot pilot, int availableSeats, double price, double returnPercentage) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sql = "";

        int planeId = plane.getId();
        int pilotId = pilot.getId();

        sql = "update flights set flightId=" + flightId +
                ",flightCode='" + flightCode +
                "',flightOrigin='" + flightOrigin +
                "',flightDestination='" + flightDestination +
                "',flightDuration=" + flightDuration +
                ",flightDepartureTime='" + dateFormat.format(flightDepartureTime) +
                "',plane=" + planeId +
                ",pilot=" + pilotId +
                ",availableSeats=" + availableSeats +
                ",price=" + price +
                ",returnPercentage=" + returnPercentage + " where flightId=" + flightId + ";";

        return sql;
    }

    private String createDeleteSql(int flightId) {

        String sql = "";

        sql = "delete from flights where flightId=" + flightId;

        return sql;
    }

    private void executeData(String sql) {

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {
//            System.out.println("Database connected!");
            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            miStatement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public String updateFlightsTableDB(Flight flight,int seatsPurchased, String operation){

        String sql = "";
        int availableSeats = 0;

        if(operation.equals("Buy")){
            availableSeats = (flight.getAvailableSeats() - seatsPurchased);
        }else if(operation.equals("Cancel")){
            availableSeats = (flight.getAvailableSeats() + seatsPurchased);
        }

        sql = "update flights set availableSeats=" + availableSeats + " where flightId=" + flight.getFlightId();

        return sql;
    }

    public String[] retrieveDBFlights() {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Plane planeObj = new Plane();
        Pilot pilotObj = new Pilot();
        flights.clear();

        String sql = "select * from flights";

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {

            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);

//            recorrer resultset
            while(myResulset.next()){

                flights.add(new Flight(
                        myResulset.getInt("flightId"),
                        myResulset.getString("flightCode"),
                        myResulset.getString("flightOrigin"),
                        myResulset.getString("flightDestination"),
                        myResulset.getInt("flightDuration"),
                        dateFormat.parse(myResulset.getString("flightDepartureTime")),
                        planeObj.getPlanesInfo(myResulset.getInt("plane")-1),
                        pilotObj.getPilotsInfo(myResulset.getInt("pilot")-1),
                        myResulset.getInt("availableSeats"),
                        myResulset.getDouble("price"),
                        myResulset.getDouble("returnPercentage")));
            }

            ResultSetMetaData rsmd = myResulset.getMetaData();
            headers = new String[rsmd.getColumnCount()];

            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                headers[i] = rsmd.getColumnName(i+1);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return headers;
    }

    public ArrayList<Flight> getFlightsList(){

        return flights;
    }

    public Flight getFlightItem(int id){

        return flights.get(id);
    }

    @Override
    public String toString() {

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date arrivalTime = new Date();

        arrivalTime.setTime(this.flightDepartureTime.getTime());
        arrivalTime.setMinutes(this.flightDepartureTime.getMinutes() + this.flightDuration);

        return  "\u001B[32m" + "aerolinea.Flight Info: " + "\u001B[0m" + '\n' +
                "aerolinea.Flight Code: " + this.flightCode + '\n' +
                "aerolinea.Flight Origin: " + this.flightOrigin + '\n' +
                "aerolinea.Flight Destination: " + this.flightDestination + '\n' +
                "aerolinea.Flight Duration: " + this.flightDuration + '\n' +
                "aerolinea.Flight Departure Time: " + outputFormat.format(this.flightDepartureTime) + '\n' +
                "aerolinea.Flight Arrival Time: " + outputFormat.format(arrivalTime) + '\n' +
                "Available Seats: " + this.availableSeats + '\n' +
                "Price: " + this.price + " euros" + '\n' +
                "Return Percentage: " + this.returnPercentage + "%" + '\n' +
                "\u001B[32m" + "aerolinea.Plane Info: " + "\u001B[0m" + '\n' +
                "aerolinea.Plane Name: " + this.plane.getName() + '\n' +
                "aerolinea.Plane Creation Date: " + this.plane.getCreationDate() + '\n' +
                "aerolinea.Plane Fuel Capacity: " + this.plane.getFuelCapacity() + '\n' +
                "\u001B[32m" + "aerolinea.Pilot Info: " + "\u001B[0m" + '\n' +
                "aerolinea.Pilot Name: " + this.pilot.getName() + '\n' +
                "aerolinea.Pilot Surname: " + this.pilot.getSurname() + '\n' +
                "aerolinea.Pilot Code: " + this.pilot.getPilotCode() +
                '\n';
    }

    public boolean checkNoSeats() {

        return this.getAvailableSeats() == 0;
    }
}
