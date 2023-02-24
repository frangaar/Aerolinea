import aerolinea.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PilotManager {
    private JTable pilotsTable;
    private JButton cancelButton;
    private JPanel PilotPanel = new JPanel();
    private JPanel PilotForm;
    private JPanel PilotButtons;

    String[] headers;

    private JFrame frame = new JFrame();

    public PilotManager(){

        PilotPanel.add(PilotForm);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(PilotPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        //frame.setSize(1411,491);

        createPilotsTable();
        PilotPanel.add(PilotButtons);
        events();
    }

    public void createPilotsTable() {

        //emptyTable();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<Pilot> pilots = new ArrayList<Pilot>();
        Pilot objPilotsList = new Pilot();
        headers = objPilotsList.getPilotsHeaders();
        pilots = objPilotsList.getPilotsList();

        int counter = 0;

        Object[] dataTable = new Object[headers.length];

        DefaultTableModel pilotsTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };


        pilotsTableModel.setColumnIdentifiers(headers);

        pilotsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pilotsTable.getTableHeader().setResizingAllowed(false);
        pilotsTable.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,400));
        pilotsTable.setSize(new Dimension(1000,400));
        //planesTable.setPreferredScrollableViewportSize(planesTable.getPreferredSize());
        pilotsTable.setPreferredScrollableViewportSize(pilotsTable.getPreferredSize());


        for(Pilot item : pilots){
            dataTable[0] = item.getId();
            dataTable[1] = item.getName();
            dataTable[2] = item.getSurname();
            dataTable[3] = item.getDni();
            dataTable[4] = item.getPhone();
            dataTable[5] = item.getPilotCode();
            dataTable[6] = item.getBirthDate();

            pilotsTableModel.addRow(dataTable);

            counter++;
        }

        pilotsTable.setModel(pilotsTableModel);
        pilotsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = pilotsTable.getColumnModel();
        columns.getColumn(0).setMinWidth(80);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);
        columns.getColumn(3).setMinWidth(50);
        columns.getColumn(4).setMinWidth(50);
        columns.getColumn(5).setMinWidth(50);
        columns.getColumn(6).setMinWidth(50);

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();

        centerRender.setHorizontalAlignment(JLabel.CENTER);
        columns.getColumn(0).setCellRenderer(centerRender);
        columns.getColumn(1).setCellRenderer(centerRender);
        columns.getColumn(2).setCellRenderer(centerRender);
        columns.getColumn(3).setCellRenderer(centerRender);
        columns.getColumn(4).setCellRenderer(centerRender);
        columns.getColumn(5).setCellRenderer(centerRender);
        columns.getColumn(6).setCellRenderer(centerRender);

        JScrollPane jScrollPane = new JScrollPane(pilotsTable);
        PilotPanel.add(jScrollPane);
    }

    public void events(){

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new AdminMenu();
            }
        });
    }
}
