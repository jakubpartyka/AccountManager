import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

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
        m11.addActionListener(a -> loadFile());
        JMenuItem m12 = new JMenuItem("Change Passwords");
        m12.addActionListener(a -> saveFile());
        JMenuItem m13 = new JMenuItem("Option 3");
        m13.addActionListener(a -> saveFileAs());

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

    private static void saveFileAs() {
    }

    private static void loadFile(){
        JFileChooser jf = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jf.setFileFilter(filter);
        int returnValue = jf.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jf.getSelectedFile();
            openedFile = selectedFile;
            System.out.println(selectedFile.getAbsolutePath());
            DataManager.load(selectedFile);
        }
        else
            System.out.println("something went wrong");

    }

    static void saveFile(){}
}
