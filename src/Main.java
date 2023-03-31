import javax.swing.*;

public class Main {

    /**
     * :)
     * @param args
     */
    public static void main(String[] args){

        User objCreateUsers = new User();
        objCreateUsers.createUsers();

        Plane objCreatePlanes = new Plane();
        objCreatePlanes.createPlanes();

        Pilot objCreatePilots = new Pilot();
        objCreatePilots.createPilots();

        new Login();
    }

    public static void messages(String type) {

        if(type.equals("welcome")){
            JOptionPane.showMessageDialog(null,"Bienvenidos a la aerolínea POLI-VOLS");
        }else if(type.equals("noData")){
            JOptionPane.showMessageDialog(null,"No data to display");
        }else if(type.equals("invalidNumber")){
            JOptionPane.showMessageDialog(null,"Number not valid");
        }else if(type.equals("operCompleted")){
            JOptionPane.showMessageDialog(null,"Operation completed");
        }else if(type.equals("operCancelled")){
            JOptionPane.showMessageDialog(null,"Operation cancelled");
        }else if(type.equals("noSeats")){
            JOptionPane.showMessageDialog(null,"No seats available.");
        }else if(type.equals("dateIncorrectFormat")){
            JOptionPane.showMessageDialog(null,"Date not valid");
        }else if(type.equals("dateNotValid")){
            JOptionPane.showMessageDialog(null,"Date must be higher than current date");
        }else if(type.equals("mustBeNumber")){
            JOptionPane.showMessageDialog(null,"Value must be a number");
        }else if(type.equals("higher45")){
            JOptionPane.showMessageDialog(null,"Value must be higher or equal than 45");
        }


    }
}