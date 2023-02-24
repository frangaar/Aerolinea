import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TicketManager {
    private JPanel ticketPanel = new JPanel();
    private JPanel ticketForm;
    private JTextField txtTicketsPurchase;
    private JLabel lblflightId;
    private JButton acceptButton;
    private JButton cancelButton;
    private JTable flightsTable;
    private JLabel lblTickets;
    private JPanel flightDetail;
    private JFrame frame = new JFrame();

    private String screenOrigin;
    private String[] headers;

    public TicketManager(String operation, String screenOrig){

        screenOrigin = screenOrig;

        ticketPanel.add(ticketForm);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(ticketPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        //frame.setSize(1411,491);

        if(operation.equals("Buy")){
            updateFlightsList(false);
        }else if(operation.equals("Cancel")){
            updateTicketsList(false);
            disableFields(operation);
        }

        events(operation);
    }

    private void disableFields(String operation) {

        if(operation.equals("Cancel")){
            lblTickets.setText("Tickets to cancel");
        }
    }

    public void updateFlightsList(boolean update) {

        emptyTable();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<Flight> flights = new ArrayList<Flight>();
        Flight objFlightList = new Flight();
        headers = objFlightList.retrieveDBFlights();
        flights = objFlightList.getFlightsList();

        Object[] dataTable = new Object[headers.length];

        DefaultTableModel flightsTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        flightsTableModel.setColumnIdentifiers(headers);

        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightsTable.getTableHeader().setResizingAllowed(false);
        flightsTable.setPreferredSize(new Dimension(800,400));
        flightsTable.setSize(new Dimension(800,400));
        flightsTable.setPreferredScrollableViewportSize(flightsTable.getPreferredSize());


        for(Flight item : flights){
            dataTable[0] = item.getFlightId();
            dataTable[1] = item.getFlightCode();
            dataTable[2] = item.getFlightOrigin();
            dataTable[3] = item.getFlightDestination();
            dataTable[4] = item.getFlightDuration();
            dataTable[5] = dateFormat.format(item.getFlightDepartureTime());
            dataTable[6] = item.getPlane().getName();
            dataTable[7] = item.getPilot().getName() + " " + item.getPilot().getSurname();
            dataTable[8] = item.getAvailableSeats();
            dataTable[9] = item.getPrice();
            dataTable[10] = item.getReturnPercentage();

            flightsTableModel.addRow(dataTable);
        }

        flightsTable.setModel(flightsTableModel);
        flightsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = flightsTable.getColumnModel();
        columns.getColumn(0).setMinWidth(80);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);
        columns.getColumn(3).setMinWidth(50);
        columns.getColumn(4).setMinWidth(50);
        columns.getColumn(5).setMinWidth(120);
        columns.getColumn(6).setMinWidth(80);
        columns.getColumn(7).setMinWidth(100);
        columns.getColumn(8).setMinWidth(50);
        columns.getColumn(9).setMinWidth(80);
        columns.getColumn(10).setMinWidth(50);

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();

        centerRender.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRender);
        columns.getColumn(1).setCellRenderer(centerRender);
        columns.getColumn(2).setCellRenderer(centerRender);
        columns.getColumn(3).setCellRenderer(centerRender);
        columns.getColumn(4).setCellRenderer(centerRender);
        columns.getColumn(5).setCellRenderer(centerRender);
        columns.getColumn(6).setCellRenderer(centerRender);
        columns.getColumn(7).setCellRenderer(centerRender);
        columns.getColumn(8).setCellRenderer(centerRender);
        columns.getColumn(9).setCellRenderer(centerRender);
        columns.getColumn(10).setCellRenderer(centerRender);

        if(!update){
            //Add table only if first time
            JScrollPane jScrollPane = new JScrollPane(flightsTable);
            ticketPanel.add(jScrollPane);
        }
    }

    public void updateTicketsList(boolean update) {

        emptyTable();

        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        Flight objFlight = new Flight();
        Ticket objTicketList = new Ticket();
        headers = objTicketList.retrieveDBTickets();
        tickets = objTicketList.getTicketsList();
        objFlight.retrieveDBFlights();

        Object[] dataTable = new Object[headers.length];

        DefaultTableModel flightsTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        flightsTableModel.setColumnIdentifiers(headers);

        flightsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flightsTable.getTableHeader().setResizingAllowed(false);
        flightsTable.setPreferredSize(new Dimension(800,400));
        flightsTable.setSize(new Dimension(800,400));
        flightsTable.setPreferredScrollableViewportSize(flightsTable.getPreferredSize());


        for(Ticket item : tickets){
            if(item.getSeatsPurchased() > 0){
                dataTable[0] = item.getTicketId();
                dataTable[1] = item.getFlightId();
                dataTable[2] = objFlight.getFlightsList().get(item.getFlightId()-1).getFlightCode();
                dataTable[3] = item.getSeatsPurchased();
                dataTable[4] = item.getPrice();

                flightsTableModel.addRow(dataTable);
            }
        }

        flightsTable.setModel(flightsTableModel);
        flightsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = flightsTable.getColumnModel();
        columns.getColumn(0).setMinWidth(80);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);
        columns.getColumn(3).setMinWidth(50);
        columns.getColumn(4).setMinWidth(50);

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();

        centerRender.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRender);
        columns.getColumn(1).setCellRenderer(centerRender);
        columns.getColumn(2).setCellRenderer(centerRender);
        columns.getColumn(3).setCellRenderer(centerRender);
        columns.getColumn(4).setCellRenderer(centerRender);

        if(!update){
            //Add table only if first time
            JScrollPane jScrollPane = new JScrollPane(flightsTable);
            ticketPanel.add(jScrollPane);
        }
    }

    private void emptyTable() {

        DefaultTableModel dtm = (DefaultTableModel) flightsTable.getModel();
        dtm.setRowCount(0);
    }

    private void events(String operation){

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean valid = false;
                int availableSeats = 0;

                if(lblflightId.getText().equals("") || txtTicketsPurchase.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"You must select a flight and the number of seats.");
                }else {
                    int row = flightsTable.getSelectedRow();
                    if(operation.equals("Buy")){
                        availableSeats= Integer.parseInt(flightsTable.getValueAt(row, 8).toString());
                    }else if(operation.equals("Cancel")){
                        availableSeats = Integer.parseInt(flightsTable.getValueAt(row, 3).toString());
                    }
                    int seatsPurchased = Integer.parseInt(txtTicketsPurchase.getText());

                    if (availableSeats == 0) {
                        Main.messages("noSeats");
                    } else if (seatsPurchased < 1) {
                        Main.messages("invalidNumber");
                    } else if (seatsPurchased > availableSeats) {
                        JOptionPane.showMessageDialog(null, "There is only " + availableSeats + " seats to select.");
                    } else {
                        if (operation.equals("Buy")) {
                            valid = buyTickets(seatsPurchased,operation);
                        } else if (operation.equals("Cancel")) {
                            valid = cancelTickets(operation,seatsPurchased);
                        }
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(screenOrigin.equals("adminMenu")){
                    new AdminMenu();
                }else if(screenOrigin.equals("customerMenu")){
                    new CustomerMenu();
                }
            }
        });

        flightsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {

                fillFlightFields(me);
            }
        });
    }

    private void validations(){

    }

    private boolean buyTickets(int seatsPurchased,String operation){

        boolean valid = false;

        Ticket objTicket = new Ticket();
        Flight objFlight = new Flight();
        int index = Integer.parseInt(lblflightId.getText())-1;
        objFlight = objFlight.getFlightItem(index);
        valid = objTicket.buyTickets(objFlight,seatsPurchased,operation);

        if(valid){
            updateFlightsList(true);
            clearFields();
        }else{
            JOptionPane.showMessageDialog(null,"Insert error.");
        }

        return valid;
    }

    private boolean cancelTickets(String operation, int seatsPurchased){

        boolean valid = false;
        int ticketId = 0;
        int flightId = 0;
        int row = flightsTable.getSelectedRow(); // select a row

        Ticket objTicket = new Ticket();
        Flight objFlight = new Flight();


        flightId = Integer.parseInt(flightsTable.getValueAt(row, 1).toString())-1;
        objFlight = objFlight.getFlightItem(flightId);
        ticketId = row;
        objTicket = objTicket.getTicketsList().get(ticketId);
        valid = objTicket.cancelTickets(objFlight, seatsPurchased,operation);

        if(valid){
            if(operation.equals("Buy")){
                updateFlightsList(true);
            }else if(operation.equals("Cancel")){
                updateTicketsList(true);
            }
            clearFields();
        }else{
            JOptionPane.showMessageDialog(null,"Insert error.");
        }

        return valid;
    }

    private void fillFlightFields(MouseEvent me){

        if (me.getClickCount() == 2) {     // to detect doble click events
            JTable target = (JTable)me.getSource();
            int row = target.getSelectedRow(); // select a row

            lblflightId.setText(flightsTable.getValueAt(row, 0).toString());
            txtTicketsPurchase.setText("0");
        }
    }

    private void clearFields(){
        lblflightId.setText("");
        txtTicketsPurchase.setText("");
    }
}
