import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminMenu {
    private JPanel adminMenu;
    private JButton createFlightButton;
    private JButton displayFlightDataButton;
    private JButton modifyFlightDataButton;
    private JButton deleteFlightButton;
    private JButton displayPlanesButton;
    private JButton displayPilotsButton;
    private JButton exitButton;
    JFrame frame = new JFrame();
    public AdminMenu() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(adminMenu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(434,279);
        effects();
        events();
    }

    private void effects() {

        createFlightButton.setForeground(Color.BLUE.darker());
        createFlightButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        displayFlightDataButton.setForeground(Color.BLUE.darker());
        displayFlightDataButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        modifyFlightDataButton.setForeground(Color.BLUE.darker());
        modifyFlightDataButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteFlightButton.setForeground(Color.BLUE.darker());
        deleteFlightButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        displayPlanesButton.setForeground(Color.BLUE.darker());
        displayPlanesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        displayPilotsButton.setForeground(Color.BLUE.darker());
        displayPilotsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.setForeground(Color.BLUE.darker());
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void events() {

        createFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new FlightManager("Create","adminMenu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        displayFlightDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new FlightManager("Detail", "adminMenu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        modifyFlightDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new FlightManager("Modify","adminMenu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new FlightManager("Delete","adminMenu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        displayPlanesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new PlaneManager();
            }
        });

        displayPilotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new PilotManager();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Login();
            }
        });
    }
}
