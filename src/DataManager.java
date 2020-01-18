//this class handles reading/writing data from/to external .txt file

import javax.swing.*;
import java.io.*;
import java.util.*;

class DataManager {
    private static List<Account> accountsToAdd = new ArrayList<>();                      //list for keeping accountsToAdd. Identified by unique profile id
    private static List<Account> initialAccounts = new ArrayList<>();                     //list of accounts that were loaded - for exit without saving check
    private static List<Account> lastSavedAccounts = new ArrayList<>();                       //list of saved accounts - for exit without saving check

    static int load(File selectedFile){
        //load data from file
        accountsToAdd.clear();          //clearing temporary arrayList
        try {
            //reading lines from specified text file
            BufferedReader fileReader = new BufferedReader(new FileReader(selectedFile));
            String line;
            line = fileReader.readLine();
            List<String> loaded;

            while (line != null) {
                loaded = Arrays.asList(line.split(":"));
                if(loaded.size() == 5) {
                    //name not given
                    Account account = new Account(loaded.get(0), loaded.get(1), loaded.get(2), loaded.get(3), loaded.get(4));
                    accountsToAdd.add(account);
                }
                else if (loaded.size() == 6){
                    //name given in file, but status and status date not given
                    Account account = new Account(loaded.get(0), loaded.get(1), loaded.get(2), loaded.get(3), loaded.get(4), loaded.get(5));
                    accountsToAdd.add(account);
                }
                else if (loaded.size() == 8){
                    //all data given in file (including name, status and status date)
                    Account account = new Account(loaded.get(0), loaded.get(1), loaded.get(2), loaded.get(3), loaded.get(4), loaded.get(5), loaded.get(6), loaded.get(7));
                    accountsToAdd.add(account);
                }
                line = fileReader.readLine();       //proceeding to next line
            }
            int counter;

            if(!GraphicInterface.getModel().getAccounts().isEmpty()){
                //if table has any rows, duplicates check will be performed
                return handleDuplicates();  //return amount of affected rows
            }
            counter = accountsToAdd.size();
            initialAccounts.addAll(accountsToAdd);            //saving loaded data for further comparing
            return counter;                               //if no duplicates were found, amount of added rows will be returned
        }
        catch (IOException e){
            return -1;                      //error return code
        }
    }

    private static int handleDuplicates() {
        //check if duplicates exist
        //-1 is returned if no duplicates are present
        //otherwise the number rows affected in table is returned
        boolean duplicatesPresent = false;
        for (Account accountToAdd : accountsToAdd) {
            if(GraphicInterface.getModel().getAccounts().contains(accountToAdd)){
                duplicatesPresent = true;
            }
        }
        if(!duplicatesPresent)
            return -1;                  //no duplicates present

        Object[] choices = {"Update records", "Skip duplicates","Cancel"};
        Object defaultChoice = choices[1];
        int i = JOptionPane.showOptionDialog(null,
                "Duplicates were found in loaded file. What would you like to do?",
                "Title message",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);

        if(i == 0){
            //update data
            int counter = 0;
            GraphicInterface.getModel().getAccounts().removeAll(accountsToAdd);
            return accountsToAdd.size();
        }
        else if(i == 1){
            //skip duplicates (keep old records)
            List<Account> tmp = GraphicInterface.getModel().getAccounts();
            accountsToAdd.removeAll(tmp);
            return accountsToAdd.size();
        }
        else if (i == 2){
            //cancel button pressed - cancel operation
            accountsToAdd.clear();
            return -2;
        }

        else {
            return -1;
        }
    }

    static List<Account> getAccountsToAdd() {
        return accountsToAdd;
    }

    static boolean save(File fileToSave, List<Account> accountsToSave) {
        //save data to file

        GraphicInterface.getModel().clearSearch();          //resetting main model so all data are saved to file
        try {
            FileWriter fw = new FileWriter(fileToSave);
            for (Account account : accountsToSave) {
                fw.write(account.toString());
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(accountsToSave.size() == GraphicInterface.getModel().getTotalCount()){
            lastSavedAccounts.clear();
            lastSavedAccounts.addAll(accountsToSave);                               //noting that changes were saved only if all accounts were exported
        }
        return true;
    }

    @SuppressWarnings("UnnecessaryContinue")
    static boolean changesSaved(){
        //this method checks if changes were saved
        AccountTableModel model = GraphicInterface.getModel();
        model.clearSearch();
        List<Account> accounts = model.getAccounts();

        if(lastSavedAccounts.isEmpty())
            lastSavedAccounts.addAll(initialAccounts);

        if(accounts.size() != lastSavedAccounts.size())
            return false;

        for (int i = 0; i < accounts.size() ; i++) {
            Account account1 = accounts.get(i);
            Account account2 = lastSavedAccounts.get(i);
                if(
                        account1.getId().equals(account2.getId())
                                && (account1.getName().equals(account2.getName()))

                                && (account1.getLogin().equals(account2.getLogin()))
                                && (account1.getPassword().equals(account2.getPassword()))

                                && (account1.getEmail().equals(account2.getEmail()))
                                && (account1.getEmailPassword().equals(account2.getEmailPassword()))

                                && (account1.getStatus().equals(account2.getStatus()))
                                && (account1.getStatusDate().equals(account2.getStatusDate()))

                                && (account1.getDateOfBirth().equals(account2.getDateOfBirth())))
                    continue;
                else
                    return false;
            }
        return true;
    }
}
