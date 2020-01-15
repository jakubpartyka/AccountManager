import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.List;

public class AccountTableModel extends AbstractTableModel {
    private static final String [] columnNames = {"login","password","email","email pass","id","b. date","name"};
    private final Class[] columnClass = new Class[] {String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    private List<Account> accounts;

    AccountTableModel(){
        accounts = Collections.singletonList(new Account(" ", " ", " ", " ", " "));
    }

    AccountTableModel(List<Account> accounts){
        this.accounts = accounts;
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
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
