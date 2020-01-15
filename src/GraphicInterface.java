import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphicInterface {
    private static File openedFile;

    public static void main(String args[]) {
        //Creating the Frame
        JFrame frame = new JFrame("Facebook Account Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocation(400,200);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu managerTab = new JMenu("Manager");
        JMenu fileTab = new JMenu("File");
        mb.add(managerTab);
        mb.add(fileTab);

        //defining items for Manager tab
        JMenuItem m11 = new JMenuItem("Test accounts");
        JMenuItem m12 = new JMenuItem("Change Passwords");
        JMenuItem m13 = new JMenuItem("Option 3");
        //todo add action listeners for this tab

        //defining items for file tab
        JMenuItem m21 = new JMenuItem("Load data");
        m21.addActionListener(a -> loadFile());
        JMenuItem m22 = new JMenuItem("Save data");
        m22.addActionListener(a -> saveFile());
        JMenuItem m23 = new JMenuItem("Save data as...");
        m23.addActionListener(a -> saveFileAs());


        //adding menu items to tabs
        managerTab.add(m11);
        managerTab.add(m12);
        managerTab.add(m13);

        fileTab.add(m21);
        fileTab.add(m22);
        fileTab.add(m23);


        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");

        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);

    }

    private static void loadFile(){
        //this method loads file chosen by user and then loads it with DataManager
        JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");          //accepting text files only
        jf.setFileFilter(filter);
        int returnValue = jf.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jf.getSelectedFile();
            openedFile = selectedFile;
            System.out.println(selectedFile.getAbsolutePath());
            int counter = DataManager.load(selectedFile);

            //showing message to user
            if(counter == 0)
                JOptionPane.showMessageDialog(null, "No accounts could be loaded from file");
            else if(counter == -1)
                JOptionPane.showMessageDialog(null, "Error loading file");
            else {
                JOptionPane.showMessageDialog(null, counter + " accounts loaded successfully");
            }
        }
        else
            System.out.println("something went wrong");
    }

    static void saveFile(){}

    private static void saveFileAs() {
    }
}
