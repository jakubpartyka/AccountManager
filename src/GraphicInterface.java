import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.File;

public class GraphicInterface {
    private static File openedFile;
    private static JTable table;
    private static AccountTableModel model;

    public static void main(String args[]) throws InterruptedException {
        //Creating the Frame
        JFrame frame = new JFrame("Facebook Account Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocation(300,200);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu managerTab = new JMenu("Manager");
        JMenu fileTab = new JMenu("File");
        mb.add(managerTab);
        mb.add(fileTab);

        //defining items for Manager tab
        JMenuItem m11 = new JMenuItem("Test accounts");
        JMenuItem m12 = new JMenuItem("Change Passwords");
        JMenuItem m13 = new JMenuItem("Discover names");
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
        model = new AccountTableModel();
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, pane);
        frame.setVisible(true);

        Thread.sleep(3000);
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

            model.setAccounts(DataManager.getAccounts());
            model.fireTableDataChanged();
            //showing message to user
            if(counter == 0)
                JOptionPane.showMessageDialog(null, "No accounts could be loaded from file");
            else if(counter == -1)
                JOptionPane.showMessageDialog(null, "Error loading file");
            else {
                Thread thread = new Thread(() -> JOptionPane.showMessageDialog(null, counter + " accounts loaded successfully"));
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread.start();
            }
        }
        else
            System.out.println("something went wrong");
    }

    private static void saveFile(){
    }

    private static void saveFileAs() {

    }

    private static void updateTabe(){
        //todo update JTable
    }
}
