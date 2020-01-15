import java.io.*;
import java.util.*;

class DataManager {
    private static Map<String,Account> accounts = new HashMap<>();                      //list for keeping accounts. Identified by unique profile id

    static int load(File selectedFile){
        try {

            BufferedReader fileReader = new BufferedReader(new FileReader(selectedFile));

            String line;
            line = fileReader.readLine();
            List<String> loaded;
            int counter = 0;

            while (line != null) {
                loaded = Arrays.asList(line.split(":"));
                Account account = new Account(loaded.get(0), loaded.get(1), loaded.get(2), loaded.get(3), loaded.get(4));
                accounts.put(account.getId(), account);
                counter++;
                line = fileReader.readLine();
            }
            return counter;
        }
        catch (IOException e){
            return -1;
        }
    }

    public static Map<String, Account> getAccounts() {
        return accounts;
    }
}
