import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Ticket {

    private int ticketId;
    private int flightId;
    private int seatsPurchased;
    private double price;

    static ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    String[] headers;
    public Ticket(){

    }

    public Ticket(int ticketId, int flightId, int seatsPurchased, double price) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.seatsPurchased = seatsPurchased;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getFlightId() {
        return this.flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getSeatsPurchased() {
        return this.seatsPurchased;
    }

    public void setSeatsPurchased(int seatsPurchased) {
        this.seatsPurchased = seatsPurchased;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean buyTickets(Flight flight,int seatsPurchased, String operation){

        double totalPrice = 0;
        String sql = "";
        boolean valid = false;

        totalPrice = flight.getPrice() * seatsPurchased;


        int proceed = JOptionPane.showConfirmDialog(null, "The total amount of the tickets is " + totalPrice + " euros. Do you want to proceed?", "Purchase tickets",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if(proceed == 0){
            tickets.add(new Ticket(tickets.size()+1,flight.getFlightId(),seatsPurchased,totalPrice));
            sql = createInsertSql(tickets.size(),flight.getFlightId(), seatsPurchased, totalPrice);
            executeData(sql);
            Flight objFlight = new Flight();
            sql = objFlight.updateFlightsTableDB(flight,seatsPurchased,operation);
            executeData(sql);
            flight.setAvailableSeats(flight.getAvailableSeats()-seatsPurchased);
            Main.messages("operCompleted");
            valid = true;
        }else{
            Main.messages("operCancelled");
            valid = true;
        }

        return valid;
    }

    public boolean cancelTickets(Flight flight,int seatsPurchased,String operation){

        double totalReturn = 0;
        String sql = "";
        boolean valid = false;

        totalReturn = (flight.getPrice() * seatsPurchased) * (flight.getReturnPercentage()/100);

        int proceed = JOptionPane.showConfirmDialog(null, "The total amount to return for the tickets is " + totalReturn + " euros. Do you want to proceed?", "Cancel tickets",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if(proceed == 0){
            sql = createUpdateSql(seatsPurchased);
            executeData(sql);

            Flight objFlight = new Flight();
            sql = objFlight.updateFlightsTableDB(flight,seatsPurchased,operation);
            executeData(sql);

            flight.setAvailableSeats(flight.getAvailableSeats() + seatsPurchased);
            this.setSeatsPurchased(this.getSeatsPurchased() - seatsPurchased);
            this.setPrice(this.getPrice() - totalReturn);
            Main.messages("operCompleted");
            valid = true;
        }else{
            Main.messages("operCancelled");
            valid = true;
        }

        return valid;
    }

    private String createInsertSql(int ticketId, int flighId, int seatsPurchased, double totalPrice) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sql = "";

        sql = "insert into tickets values(null,"+ flighId +
                "," + seatsPurchased +
                "," + totalPrice + ");";

        return sql;
    }

    private String createUpdateSql(int seatsPurchased) {

        String sql = "";


        sql = "update tickets set seatsPurchased=" + (this.getSeatsPurchased()-seatsPurchased) +
                " where ticketId=" + this.ticketId + ";";

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

    public String[] retrieveDBTickets() {

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Plane planeObj = new Plane();
        Pilot pilotObj = new Pilot();
        tickets.clear();

        String sql = "select tickets.ticketId, tickets.flightId, flights.flightCode, tickets.seatsPurchased, tickets.price from tickets,flights where flights.flightId = tickets.flightId";

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {

            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);

//            recorrer resultset
            while(myResulset.next()){

                tickets.add(new Ticket(
                        myResulset.getInt("ticketId"),
                        myResulset.getInt("flightId"),
                        myResulset.getInt("seatsPurchased"),
                        myResulset.getDouble("price")));
            }

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

    public ArrayList<Ticket> getTicketsList(){

        return tickets;
    }

    public void removeTicket(ArrayList<Ticket> tickets, int index) {

        tickets.remove(index);
    }

    @Override
    public String toString() {
        return "aerolinea.Ticket:" +
                "flightId='" + this.ticketId + '\'' +
                "flightCode='" + this.flightId + '\'' +
                ", seatsPurchased=" + this.seatsPurchased +
                ", price=" + this.price +
                '\n';
    }
}
