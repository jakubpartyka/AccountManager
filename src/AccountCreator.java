//this class represents account manual adding form

import javax.swing.*;
import javax.swing.SpringLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

class AccountCreator {
    AccountCreator(){
        showGUI();
    }

    private void showGUI() {
        String[] labels = {"Login: ", "Password: ", "Email: ", "Email pass: ", "ID: ", "Birth date: ", "Name: ", "Status: ", "Status timestamp: "};
        int numPairs = labels.length + 1;

        //create frame
        JFrame frame = new JFrame("Add new account");


        //create and populate the panel
        JPanel panel = new JPanel(new SpringLayout());
        ArrayList<JTextField> fieldList = new ArrayList<>();
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            panel.add(l);
            JTextField textField = new JTextField(10);
            fieldList.add(textField);
            l.setLabelFor(textField);
            panel.add(textField);
        }

        //create submit button
        JButton submit = new JButton("add");
        submit.addActionListener(e -> {
            Account account = new Account(
                    fieldList.get(0).getText(),
                    fieldList.get(1).getText(),
                    fieldList.get(2).getText(),
                    fieldList.get(3).getText(),
                    fieldList.get(5).getText(),
                    fieldList.get(4).getText(),
                    fieldList.get(6).getText(),
                    fieldList.get(7).getText(),
                    fieldList.get(8).getText()
            );
            GraphicInterface.getModel().addAccount(account);
            frame.dispose();
        });

        //create set timestamp button
        JButton setTimestamp = new JButton("timestamp: now");
        setTimestamp.addActionListener(e -> fieldList.get(fieldList.size()-1)
                .setText(new SimpleDateFormat("DD-MM-YY HH.mm.ss").format(Calendar.getInstance().getTime())));

        //add buttons to panel
        panel.add(submit);
        panel.add(setTimestamp);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //create and set up the window
        frame.setLocation(300,250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //set up content panel
        panel.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(panel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
