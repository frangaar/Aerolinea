import aerolinea.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PlaneManager {
    private JPanel PlanePanel = new JPanel();
    private JTable planesTable;
    private JPanel PlaneForm;
    private JButton cancelButton;
    private JPanel PlaneButtons;

    String[] headers;

    private JFrame frame = new JFrame();

    public PlaneManager(){

        PlanePanel.add(PlaneForm);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(PlanePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        //frame.setSize(1411,491);

        createPlanesTable();
        PlanePanel.add(PlaneButtons);
        events();
    }

    public void createPlanesTable() {

        //emptyTable();

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<Plane> planes = new ArrayList<Plane>();
        Plane objPlanesList = new Plane();
        headers = objPlanesList.getPlanesHeaders();
        planes = objPlanesList.getPlanesList();

        int counter = 0;

        Object[] dataTable = new Object[headers.length];

        DefaultTableModel planesTableModel = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };


        planesTableModel.setColumnIdentifiers(headers);

        planesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        planesTable.getTableHeader().setResizingAllowed(false);
        planesTable.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,400));
        planesTable.setSize(new Dimension(1000,400));
        //planesTable.setPreferredScrollableViewportSize(planesTable.getPreferredSize());
        planesTable.setPreferredScrollableViewportSize(planesTable.getPreferredSize());


        for(Plane item : planes){
            dataTable[0] = item.getId();
            dataTable[1] = item.getName();
            dataTable[2] = item.getCreationDate();
            dataTable[3] = item.getFuelCapacity();
            dataTable[4] = item.getTOTALSEATS();

            planesTableModel.addRow(dataTable);

            counter++;
        }

        planesTable.setModel(planesTableModel);
        planesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = planesTable.getColumnModel();
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

        JScrollPane jScrollPane = new JScrollPane(planesTable);
        PlanePanel.add(jScrollPane);
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
