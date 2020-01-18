import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")

public class AccountTableModel extends AbstractTableModel {
    private static final String [] columnNames = {"login","password","email","email pass","id","b. date","name","status","status timestamp"};
    private final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    private List<Account> accounts = new ArrayList<>();
    private List<Account> temporaryAccountList = new ArrayList<>();

    AccountTableModel(){
        //for some reason table model cannot be created with null data object. This one is created and deleted right away in main GUI class. It's not visible to the user
        accounts.add(new Account(" ", " ", " ", " ", " ", " ", " ", "data not loaded"));
    }

    void delete(int[] selectedRows) {
        //method that handles user deletion with "delete" button
        if(selectedRows.length == 0)
            return;
        String message = selectedRows.length > 1 ? "Are you sure you want to delete " + selectedRows.length + " account?" : "Are you sure you want to delete this account?";
        int n = JOptionPane.showConfirmDialog(null, message,"Confirm deletion", JOptionPane.YES_NO_OPTION);
        if(n == 0) {
            List<Account> toDelete = new ArrayList<>();
            for (int selectedRow : selectedRows) {
                toDelete.add(accounts.get(selectedRow));
            }
            clearSearch();
            accounts.removeAll(toDelete);
            fireTableDataChanged();
            String deletedMessage = selectedRows.length > 1 ? toDelete.size() + " accounts deleted" : "1 account deleted";
            JOptionPane.showMessageDialog(null, deletedMessage);
        }
    }

    void addAccounts(List<Account> accounts){
        this.accounts.addAll(accounts);
        fireTableDataChanged();
    }

    void addAccount(Account account){
        this.accounts.add(account);
        fireTableDataChanged();
    }

    List<Account> getAccountsByIndexes(int [] indexes){
        List<Account> toReturn = new ArrayList<>();
        for (int index:indexes) {
            toReturn.add(accounts.get(index));
        }
        return toReturn;
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
        if(!temporaryAccountList.isEmpty()) {
            accounts.clear();
            accounts.addAll(temporaryAccountList);
            temporaryAccountList.clear();
            fireTableDataChanged();
        }
    }

    void searchResultsFor(String text) {
        //apply search filter

        //resetting accounts if some search was done before without clearing
        clearSearch();

        //applying new search filter
        temporaryAccountList.addAll(accounts);        //saving main list to backup
        List<Account> matching = new ArrayList<>();
        for (Account account : accounts){
            String[] fields = account.toString().split(":");
            for (String field : fields){
                if (field.toLowerCase().contains(text.toLowerCase()))
                    if(!matching.contains(account))
                        matching.add(account);
            }
        }
        accounts.clear();
        accounts.addAll(matching);
        fireTableDataChanged();
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
        GraphicInterface.updateSelectionLabel();
    }

    int getTotalCount(){
        //returns the number of all accounts (not only those included in search results)
        if (temporaryAccountList.size() > accounts.size())
            return temporaryAccountList.size();
        else
            return accounts.size();
    }




    //overriding methods
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
}
