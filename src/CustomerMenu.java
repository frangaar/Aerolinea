import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CustomerMenu {
    private JPanel customerMenu;
    private JButton displayFlightDataButton;
    private JButton buyTicketsButton;
    private JButton cancelTicketsButton;
    private JButton exitButton;

    JFrame frame = new JFrame();

    public CustomerMenu() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(customerMenu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(395,192);
        effects();
        events();
    }

    private void effects() {

        displayFlightDataButton.setForeground(Color.BLUE.darker());
        displayFlightDataButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buyTicketsButton.setForeground(Color.BLUE.darker());
        buyTicketsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelTicketsButton.setForeground(Color.BLUE.darker());
        cancelTicketsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exitButton.setForeground(Color.BLUE.darker());
        exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void events() {

        displayFlightDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new FlightManager("Detail","customerMenu");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buyTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TicketManager("Buy","customerMenu");
            }
        });

        cancelTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new TicketManager("Cancel","customerMenu");
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
