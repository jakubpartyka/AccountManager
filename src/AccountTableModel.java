import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountTableModel extends AbstractTableModel {
    private static final String [] columnNames = {"login","password","email","email pass","id","b. date","name","status","last status date"};
    private final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    private List<Account> accounts = new ArrayList<>();
    private List<Account> temporaryAccountList = new ArrayList<>();

    AccountTableModel(){
        accounts.add(new Account(" ", " ", " ", " ", " ", " ", " ", "data not loaded"));
    }

    void delete(int[] selectedRows) {
        //method that handles user deletion with "delete" button
        if(selectedRows.length == 0)
            return;
        int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedRows.length + " rows?","Confirm deletion", JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            List<Account> toDelete = new ArrayList<>();
            for (int selectedRow : selectedRows) {
                toDelete.add(accounts.get(selectedRow));
            }
            accounts.removeAll(toDelete);
            fireTableDataChanged();
            JOptionPane.showMessageDialog(null, toDelete.size() + " rows deleted");
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Account row = accounts.get(rowIndex);
        switch (columnIndex) {
            case 0 : return row.getLogin();
            case 1 : return row.getPassword();
            case 2 : return row.getEmail();
            case 3 : return row.getEmailPassword();
            case 4 : return row.getId();
            case 5 : return row.getDateOfBirth();
            case 6 : return row.getName();
            case 7 : return row.getStatus();
            case 8 : return row.getStatusDate();
        }
        return null;
    }

    @Override
    public void setValueAt(Object i, int rowIndex, int columnIndex) {
        Account row = accounts.get(rowIndex);
        String input = (String) i;
        switch (columnIndex) {
            case 0 : row.setLogin( input); return;
            case 1 : row.setPassword(input); return;
            case 2 : row.setEmail(input); return;
            case 3 : row.setEmailPassword(input); return;
            case 4 : row.setId(input); return;
            case 5 : row.setDateOfBirth(input); return;
            case 6 : row.setName(input); return;
            case 7 : row.setStatus(input); return;
            case 8 : row.setStatusDate(input);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 3 || columnIndex == 6 || columnIndex == 7 || columnIndex == 8);
    }

    void addAccounts(List<Account> accounts){
        this.accounts.addAll(accounts);
    }

    List<Account> getAccounts() {
        return accounts;
    }

    void removeRow(int i) {
        accounts.remove(i);
        this.fireTableDataChanged();
    }

    void removeRow(Account account) {
        accounts.remove(account);
        this.fireTableDataChanged();
    }

    void clearAccounts(){
        this.accounts.clear();
    }

    void clearSearch(){
        //clear search results
        List<Account> tmp = temporaryAccountList;
        temporaryAccountList = accounts;
        accounts = tmp;
        fireTableDataChanged();
    }

    void searchResultsFor(String text) {
        //apply search filter
        temporaryAccountList = accounts;        //saving main list to backup
        List<Account> tmp = new ArrayList<>();
        for (Account account : accounts){
            List<String> fields = Arrays.asList(account.toString().split(":"));
            for (String field : fields){
                if (field.contains(text))
                    if(!tmp.contains(account))
                        tmp.add(account);
            }
        }
        accounts = tmp;
        fireTableDataChanged();


    }
}
