import org.jdatepicker.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.Date;

public class FlightManager{

    private JLabel lblflightId;
    private JTextField txtflightCode;
    private JTextField txtflightOrig;
    private JTextField txtflightDest;
    private JTextField txtflightDuration;
    private JDatePicker txtflightDepartureTime;
    private JComboBox cbPlane;
    private JComboBox cbPilot;
    private JTextField txtflightPrice;
    private JTextField txtflightReturningPercentage;

    private JPanel flightPanel = new JPanel();
    private JButton acceptButton;
    private JButton cancelButton;
    private JLabel lblflightDepartureTime;
    private JLabel lblFlightAvailableSeats;
    private JPanel flightForm;
    private JPanel flightDetail;
    private JTable flightsTable;

    private String screenOrigin;
    private String[] headers;
    private JFrame frame = new JFrame();

    public FlightManager(String operation, String screenOrig) throws IOException {

        screenOrigin = screenOrig;

        flightPanel.add(flightForm);
        flightPanel.add(flightDetail);

        if(operation.equals("Create")){
            disableFields(operation);
        }else if(operation.equals("Detail")){
            disableFields(operation);
        }else if(operation.equals("Delete")){
            disableFields(operation);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(flightPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        //frame.setSize(1411,491);

        fillComboPlanes();
        fillComboPilots();
        updateFlightsTable(false);
        events(operation);
        listeners();
        validations();
    }

    private void disableFields(String operation) {

        if(operation.equals("Create")){
            acceptButton.setEnabled(false);
        }else if(operation.equals("Detail")){
            acceptButton.setVisible(false);
            lblflightId.setEnabled(false);
            txtflightCode.setEnabled(false);
            txtflightOrig.setEnabled(false);
            txtflightDest.setEnabled(false);
            txtflightDuration.setEnabled(false);
            txtflightDepartureTime.setEnabled(false);
            lblflightDepartureTime.setEnabled(false);
            cbPlane.setEnabled(false);
            cbPilot.setEnabled(false);
            lblFlightAvailableSeats.setEnabled(false);
            txtflightPrice.setEnabled(false);
            txtflightReturningPercentage.setEnabled(false);
        }else if(operation.equals("Delete")){
            lblflightId.setEnabled(false);
            txtflightCode.setEnabled(false);
            txtflightOrig.setEnabled(false);
            txtflightDest.setEnabled(false);
            txtflightDuration.setEnabled(false);
            txtflightDepartureTime.setEnabled(false);
            lblflightDepartureTime.setEnabled(false);
            cbPlane.setEnabled(false);
            cbPilot.setEnabled(false);
            lblFlightAvailableSeats.setEnabled(false);
            txtflightPrice.setEnabled(false);
            txtflightReturningPercentage.setEnabled(false);
            acceptButton.setVisible(true);
            acceptButton.setText("Delete");
        }
    }

    private void fillComboPilots() {

        Pilot pilot = new Pilot();
        ArrayList<Pilot> pilotsList = new ArrayList<Pilot>();
        pilotsList.clear();
        cbPilot.removeAllItems();
        pilotsList = pilot.getPilotsList();

        cbPilot.addItem("Select option...");

        for (Pilot pilotName : pilotsList) {
            cbPilot.addItem(pilotName.getName() + " " + pilotName.getSurname());
        }
    }

    private void fillComboPlanes() {

        Plane plane = new Plane();
        ArrayList<Plane> planesList = new ArrayList<Plane>();
        planesList.clear();
        planesList = plane.getPlanesList();
        cbPlane.removeAllItems();

        cbPlane.addItem("Select option...");

        for (Plane planeName : planesList) {
            cbPlane.addItem(planeName.getName());
        }
    }

    public void updateFlightsTable(boolean update) {

        emptyTable();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<Flight> flights = new ArrayList<Flight>();
        Flight objFlightList = new Flight();
        headers = objFlightList.retrieveDBFlights();
        flights = objFlightList.getFlightsList();

        int counter = 0;

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

            counter++;
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
            flightPanel.add(jScrollPane);
        }
    }

    private void emptyTable() {

        DefaultTableModel dtm = (DefaultTableModel) flightsTable.getModel();
        dtm.setRowCount(0);
    }

    private void clearFields(){
        lblflightId.setText("");
        txtflightCode.setText("");
        txtflightOrig.setText("");
        txtflightDest.setText("");
        txtflightDuration.setText("");

        // Vacia JDatePicker
        Calendar flightDeptTime = null;
        DateModel<Calendar> dateModel = (DateModel<Calendar>) txtflightDepartureTime.getModel();
        dateModel.setValue(flightDeptTime);

        lblflightDepartureTime.setText("");
        cbPlane.setSelectedIndex(0);
        cbPilot.setSelectedIndex(0);
        lblFlightAvailableSeats.setText("");
        txtflightPrice.setText("");
        txtflightReturningPercentage.setText("");
    }

    private void events(String operation){

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean valid = false;

                if(operation.equals("Create")){
                    valid = createFlight(valid);
                }else if(operation.equals("Modify")){
                    try {
                        valid = modifyFlight(valid);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }else if(operation.equals("Delete")){
                    try {
                        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete flight?", "Delete flight confirmation",
                                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

                        if(option == 0){
                            valid = deleteFlight(valid);
                        }else{
                            JOptionPane.showMessageDialog(null,"Operation canceled");
                            valid = true;
                        }
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(valid){
                    updateFlightsTable(true);
                    clearFields();
                }else{
                    if(operation.equals("Create")){
                        JOptionPane.showMessageDialog(null,"Insert error.");
                    }else if(operation.equals("Modify")){
                        JOptionPane.showMessageDialog(null,"Update error.");
                    }else if(operation.equals("Delete")){
                        JOptionPane.showMessageDialog(null,"Delete error.");
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

        txtflightDepartureTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Calendar now = Calendar.getInstance();
                Font label = lblflightDepartureTime.getFont();
                lblflightDepartureTime.setFont(label.deriveFont(label.getStyle() & ~Font.BOLD));
                String curTime = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
                lblflightDepartureTime.setText(curTime);
                validations();
            }
        });

        cbPlane.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Plane objPlane = new Plane();
                Plane planeInfo = new Plane();
                int planeId = 0;
                if(cbPlane.getSelectedIndex() > 0){
                    planeId = cbPlane.getSelectedIndex()-1;
                }

                objPlane.getPlanesList();

                planeInfo = objPlane.getPlanesList().get(planeId);
                lblFlightAvailableSeats.setText(String.valueOf(planeInfo.getTOTALSEATS()));
            }
        });

        flightsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {

                if(operation.equals("Modify") || operation.equals("Detail") || operation.equals("Delete")){
                    fillFlightFields(me);
                    disableFields("Create");
                }
                if(operation.equals("Delete")){
                    validations();
                }
                }
        });
    }

    private void listeners() {

        java.awt.event.FocusListener buttonAcceptListener = new java.awt.event.FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            public void focusLost(java.awt.event.FocusEvent focusEvent) {
                JTextField src = (JTextField)focusEvent.getSource();
                try {
                    validations();
                } catch (ClassCastException ignored) {
                    /* I only listen to JTextFields */
                }
            }
        };

        java.awt.event.FocusListener formatFieldListener = new java.awt.event.FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            public void focusLost(java.awt.event.FocusEvent focusEvent) {
                JTextField src = (JTextField)focusEvent.getSource();
                try {

                    if(!src.getText().equals("")){
                        Integer.parseInt(src.getText());
                    }

                    validations();
                } catch (ClassCastException ignored) {
                    /* I only listen to JTextFields */
                }catch (NumberFormatException nfe){
                    Main.messages("mustBeNumber");
                    src.setText("");
                }
            }
        };

        java.awt.event.FocusListener fieldValueListener = new java.awt.event.FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            public void focusLost(java.awt.event.FocusEvent focusEvent) {
                JTextField src = (JTextField)focusEvent.getSource();
                if(Integer.parseInt(src.getText()) < 45){
                    Main.messages("higher45");
                }
                validations();
            }
        };

        txtflightCode.addFocusListener(buttonAcceptListener);
        txtflightOrig.addFocusListener(buttonAcceptListener);
        txtflightDest.addFocusListener(buttonAcceptListener);
        txtflightDuration.addFocusListener(buttonAcceptListener);
        txtflightDuration.addFocusListener(formatFieldListener);
        txtflightDuration.addFocusListener(fieldValueListener);
        txtflightDepartureTime.addFocusListener(buttonAcceptListener);
        cbPlane.addFocusListener(buttonAcceptListener);
        cbPilot.addFocusListener(buttonAcceptListener);
        txtflightPrice.addFocusListener(buttonAcceptListener);
        txtflightPrice.addFocusListener(formatFieldListener);
        txtflightReturningPercentage.addFocusListener(buttonAcceptListener);
        txtflightReturningPercentage.addFocusListener(formatFieldListener);
    }

    private void validations(){

        boolean enable = true;

        Calendar flightDeptTime = (Calendar) txtflightDepartureTime.getModel().getValue();

        if( txtflightCode.getText().equals("") ||
            txtflightOrig.getText().equals("") ||
            txtflightDest.getText().equals("") ||
            txtflightDuration.getText().equals("") ||
            flightDeptTime == null ||
            lblflightDepartureTime.getText().equals("") ||
            cbPlane.getSelectedIndex() == 0 ||
            cbPilot.getSelectedIndex() == 0 ||
            lblFlightAvailableSeats.getText().equals("") ||
            txtflightPrice.getText().equals("") ||
            txtflightReturningPercentage.getText().equals("")){
            enable = false;
        }

        acceptButton.setEnabled(enable);
    }

    private void fillFlightFields(MouseEvent me){

        if (me.getClickCount() == 2) {     // to detect doble click events
            JTable target = (JTable)me.getSource();
            int row = target.getSelectedRow(); // select a row
//                    int column = target.getSelectedColumn(); // select a column

            lblflightId.setText(flightsTable.getValueAt(row, 0).toString());
            txtflightCode.setText(flightsTable.getValueAt(row, 1).toString());
            txtflightOrig.setText(flightsTable.getValueAt(row, 2).toString());
            txtflightDest.setText(flightsTable.getValueAt(row, 3).toString());
            txtflightDuration.setText(flightsTable.getValueAt(row, 4).toString());

            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(flightsTable.getValueAt(row, 5).toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Display data from table to JDatepicker
            Calendar flightDeptTime = Calendar.getInstance();
            flightDeptTime.setTime(date);
            DateModel<Calendar> dateModel = (DateModel<Calendar>) txtflightDepartureTime.getModel();
            dateModel.setValue(flightDeptTime);


            Font label = lblflightDepartureTime.getFont();
            lblflightDepartureTime.setFont(label.deriveFont(label.getStyle() & ~Font.BOLD));
            String curTime = String.format("%02d:%02d", flightDeptTime.get(Calendar.HOUR_OF_DAY), flightDeptTime.get(Calendar.MINUTE));
            lblflightDepartureTime.setText(curTime);

            cbPlane.setSelectedItem(flightsTable.getValueAt(row, 6).toString());
            cbPilot.setSelectedItem(flightsTable.getValueAt(row, 7).toString());
            lblFlightAvailableSeats.setText(flightsTable.getValueAt(row, 8).toString());
            txtflightPrice.setText(flightsTable.getValueAt(row, 9).toString());
            txtflightReturningPercentage.setText(flightsTable.getValueAt(row, 10).toString());
        }
    }

    private boolean createFlight(boolean valid){

        Calendar flightDeptTime = (Calendar) txtflightDepartureTime.getModel().getValue();

        Date flightDateTime = flightDeptTime.getTime();

        flightDateTime = flightDeptTime.getTime();
        lblflightId.setText("0");

        int planeId = cbPlane.getSelectedIndex() - 1;

        Plane plane = new Plane();
        Plane planeItem = new Plane();
        plane.getPlanesList();
        planeItem = plane.getPlanesList().get(planeId);

        int pilotId = cbPilot.getSelectedIndex() - 1;

        Pilot pilot = new Pilot();
        Pilot pilotItem = new Pilot();
        pilot.getPilotsList();
        pilotItem = pilot.getPilotsList().get(pilotId);


        Flight objCreateFlight = new Flight();

        valid = objCreateFlight.createFlight(
                Integer.parseInt(lblflightId.getText()),
                txtflightCode.getText(),
                txtflightOrig.getText(),
                txtflightDest.getText(),
                Integer.parseInt(txtflightDuration.getText()),
                flightDateTime,
                planeItem,
                pilotItem,
                Double.parseDouble(txtflightPrice.getText()),
                Double.parseDouble(txtflightReturningPercentage.getText()));

        return valid;
    }

    private boolean modifyFlight(boolean valid) throws ParseException {

//        Date date = new Date();
//        Date time = new Date();
//        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        String startingTime = new SimpleDateFormat("HH:mm").format(time);

        Calendar flightDeptDate = (Calendar) txtflightDepartureTime.getModel().getValue();
        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(flightDeptDate.getTime());
        String startingTime = lblflightDepartureTime.getText();

        LocalDate datePart = LocalDate.parse(startingDate);
        LocalTime timePart = LocalTime.parse(startingTime);

        LocalDateTime dateTime = LocalDateTime.of(datePart, timePart);

        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date modifiedDate = Date.from(instant);

        int planeId = cbPlane.getSelectedIndex();

        Plane plane = new Plane();
        Plane planeItem = new Plane();
        plane.getPlanesList();
        planeItem = plane.getPlanesList().get(planeId);

        int pilotId = cbPilot.getSelectedIndex();

        Pilot pilot = new Pilot();
        Pilot pilotItem = new Pilot();
        pilot.getPilotsList();
        pilotItem = pilot.getPilotsList().get(pilotId);


        Flight objCreateFlight = new Flight();


        valid = objCreateFlight.modifyFlight(
                Integer.parseInt(lblflightId.getText()),
                txtflightCode.getText(),
                txtflightOrig.getText(),
                txtflightDest.getText(),
                Integer.parseInt(txtflightDuration.getText()),
                modifiedDate,
                planeItem,
                pilotItem,
                Integer.parseInt(lblFlightAvailableSeats.getText()),
                Double.parseDouble(txtflightPrice.getText()),
                Double.parseDouble(txtflightReturningPercentage.getText()));

        return valid;
    }

    private boolean deleteFlight(boolean valid) throws ParseException {

        Flight objCreateFlight = new Flight();


        valid = objCreateFlight.deleteFlight(Integer.parseInt(lblflightId.getText()));

        return valid;
    }
}