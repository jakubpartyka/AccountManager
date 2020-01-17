//this class represents main GUI - it implements all methods that are being used by main gui elements.
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphicInterface {
    private static File saveFile;               //this is the file to which data will be saved on cntr + s / save
    private static AccountTableModel model;
    private static JLabel selectionLabel;
    private static JTable table;
    //todo boolean changes saved

    public static void main(String args[]) {

        //Creating the Frame
        JFrame frame = new JFrame("Facebook Account Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocation(250,150);

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
        JMenuItem m14 = new JMenuItem("Exit");


        //listeners for Manager Tab
        m13.addActionListener(e -> runWebTask(table.getSelectedRows(),"DiscoverName"));

        //quit listener and shortcut
        m14.addActionListener(l -> exit());
        m14.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        //defining items for file tab
        JMenuItem m21 = new JMenuItem("Load data");
        m21.addActionListener(a -> loadFile());
        m21.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem m22 = new JMenuItem("Save data");
        m22.addActionListener(a -> saveFile());
        m22.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem m23 = new JMenuItem("Save data as...");
        m23.addActionListener(a -> saveFileAs());

        //adding menu items to tabs
        managerTab.add(m11);
        managerTab.add(m12);
        managerTab.add(m13);
        managerTab.addSeparator();
        managerTab.add(m14);

        fileTab.add(m21);
        fileTab.add(m22);
        fileTab.add(m23);


        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Search for");
        JTextField searchBox = new JTextField(10); // accepts upto 10 characters
        JButton search = new JButton("Search");
        search.setPreferredSize(new Dimension(80,30));
        JButton delete = new JButton("Delete");
        JButton selectAll = new JButton("Select All");
        JButton selectNone = new JButton("Select None");

        //status label
        selectionLabel = new JLabel("none selected");
        selectionLabel.setHorizontalAlignment(SwingConstants.RIGHT);


        //search boolean
        AtomicBoolean searchFilterOn = new AtomicBoolean(false);

        panel.add(label); // Components Added using Flow Layout
        panel.add(searchBox);
        searchBox.addKeyListener(new KeyListener() {
            //simple key listener that allows to apply search filter with enter and remove it with esc
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getExtendedKeyCode() == 10 && searchBox.getText().length() > 0) {
                    model.searchResultsFor(searchBox.getText());
                    search.setText("reset");
                    searchFilterOn.set(true);
                }
                if(e.getExtendedKeyCode() == 27 && searchFilterOn.get()){
                    model.clearSearch();
                    searchBox.setText("");
                    search.setText("Search");
                    searchFilterOn.set(false);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        panel.add(search);
        panel.add(delete);
        panel.add(selectAll);
        panel.add(selectNone);
        panel.add(selectionLabel);

        //JTable at the Center
        model = new AccountTableModel();
        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        table.addMouseListener(new MouseAdapter() {
            //mouse adapter to update "selection label" on every new selection
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                updateSelectionLabel();
            }
        });
        table.addKeyListener(new KeyListener() {
            //KeyListener that updates selection bar when ctrl+a or cmmd+a combination is pressed.
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getExtendedKeyCode() == 65)
                    updateSelectionLabel();
            }
        });



        //action listener for buttons
        delete.addActionListener(l -> {
            model.delete(table.getSelectedRows());
            updateSelectionLabel();
        });

        selectNone.addActionListener(l -> {
            table.clearSelection();
            updateSelectionLabel();
        });

        selectAll.addActionListener(l -> {
            table.selectAll();
            updateSelectionLabel();
        });

        search.addActionListener(l -> {
            //action listener that applies search filter to result list. See search() in AccountTableModel.
            if(!searchFilterOn.get() && searchBox.getText().length() > 0) {
                //apply search filter
                model.searchResultsFor(searchBox.getText());
                search.setText("reset");
                searchFilterOn.set(true);
            }
            else {
                //clear search
                model.clearSearch();
                searchBox.setText("");
                search.setText("Search");
                searchFilterOn.set(false);
            }
        });

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, pane);
        frame.setVisible(true);
    }

    static void updateSelectionLabel() {
        selectionLabel.setText(table.getSelectedRows().length + " out of " + model.getTotalCount() + " selected");
    }

    private static void exit() {
        //this method checks if current file was saved before exiting
        //todo file save check
        System.exit(0);
    }

    private static void loadFile(){
        //this method loads file chosen by user and then loads it with DataManager
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files only", "txt", "text");          //accepting text files only
        fileChooser.setDialogTitle("Choose a file to open");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            int counter = DataManager.load(selectedFile);           //loading data to DataManager and getting status integer
            model.addAccounts(DataManager.getAccountsToAdd());      //updating table data
            model.fireTableDataChanged();                           //re-rendering table

            //showing message to user
            if(counter == 0)
                JOptionPane.showMessageDialog(null, "No accounts were loaded from file");
            else if(counter == -1)
                JOptionPane.showMessageDialog(null, "No records were loaded from file");
            else if(counter == -2)
                JOptionPane.showMessageDialog(null, "Loading data from file canceled.");
            else {
                Thread thread = new Thread(() -> JOptionPane.showMessageDialog(null, counter + " accounts loaded successfully"));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                thread.start();
            }
        }
        else
            System.out.println("file choosing cancelled");          //if no file was chosen (eg. fileChooser was closed) operation is cancelled.
    }

    private static void saveFile(){
        //this method saves current model contents to specified text file. If save file is not specified saveFileAs() method will be invoked
        try {
            if(DataManager.save(saveFile))
                JOptionPane.showMessageDialog(null, "Data saved to " + saveFile);
        }
        catch (NullPointerException e) {
            saveFileAs();
        }
    }

    private static void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");          //accepting text files only
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Specify a file to save");
        int selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if(!fileToSave.getAbsolutePath().endsWith(".txt"))
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");   //making sure .txt file will be used
            saveFile = fileToSave;                                                        //setting default save file
            if(DataManager.save(fileToSave))
                JOptionPane.showMessageDialog(null, "successfully saved data to " + saveFile.getAbsolutePath());
            else
                JOptionPane.showMessageDialog(null, "Save failed...");
        }
        else {
            System.out.println("file saving was cancelled");        //if file saving fails (eg. file chooser was closed) operation is cancelled
        }
    }

    static AccountTableModel getModel() {
        return model;
    }

    private static void runWebTask(int [] indexList, String taskName){
        List<Account> accountsForTask = model.getAccountsByIndexes(indexList);

        switch (taskName) {
            case "DiscoverName" :
                WebTask discoverName = new DiscoverName(accountsForTask);
                Thread thread = new Thread(discoverName);
                thread.start();
                break;
            default :
                try {
                    throw new Exception("No such operation defined");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
