import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class Login {
    private JTextField textUser;
    private JLabel lblUser;
    private JLabel lblPassword;
    private JPanel login;
    private JButton acceptButton;
    private JButton cancelButton;
    private JPasswordField textPassw;
    private JComboBox cbProfile;

    JFrame frame = new JFrame();

    ArrayList<String> users = new ArrayList<String>();

    public Login(){

        openFrame();

    }

    public Login(ArrayList<Pilot> pilots, ArrayList<Plane> planes, ArrayList<Flight> flights) {

        openFrame();
    }

    private void openFrame() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(login);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(300,200);
        events();
        setCombo(cbProfile);
    }

    private void setCombo(JComboBox cbProfile) {

        String sql = "select userRole from users";


//        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {
//            System.out.println("Database connected!");
            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);
//            recorrer resultset

            while(myResulset.next()){
                users.add(myResulset.getString("userRole"));
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        for (String user : users) {
            cbProfile.addItem(user);
        }
    }

    private void events(){

        textUser.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    acceptButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        textPassw.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    acceptButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        cbProfile.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    acceptButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        acceptButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    acceptButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = false;
                valid = validateUser(textUser, textPassw);

                if (valid) {
                    JOptionPane.showMessageDialog(null, "Welcome " + textUser.getText());
                    frame.dispose();
                    if(cbProfile.getSelectedItem().equals("admin")){
                        new AdminMenu();
                    }else{
                        new CustomerMenu();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid user");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private boolean validateUser(JTextField textUser, JPasswordField textPassw) {

        int items = 0;

        String sql = "select * from users where userName='" + textUser.getText() + "' and userPass='" + String.valueOf(textPassw.getPassword()) + "'" + " and userRole='" + cbProfile.getSelectedItem() + "'";


//        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(Connect.url, Connect.username, Connect.password)) {
//            System.out.println("Database connected!");
            //crear objeto statement
            Statement miStatement = connection.createStatement();
            //ejecutar sql

            ResultSet myResulset = miStatement.executeQuery(sql);
//            recorrer resultset

            while(myResulset.next()){
                items++;
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        return items > 0;
    }
}
