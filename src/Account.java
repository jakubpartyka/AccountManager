import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class Account {
    //personal data
    private String name;
    private String lastName;
    private Date dateOfBirth;

    //login data
    private String id;
    private String login;
    private String password;
    private String email;
    private String emailPassword;

    Account(String login, String password){
        this.login = login;
        this.password = password;
    }

    Account(String login, String password,String emailPassword, String date, String id){
        this.login = login;
        this.password = password;
        this.email = login;
        this.email = emailPassword;
        this.id = id;

        SimpleDateFormat sdf = new SimpleDateFormat("DD.MM.YYYY");
        try {
            this.dateOfBirth = sdf.parse(date);
        } catch (ParseException e) {
            this.dateOfBirth = null;
        }
    }

    String getId() {
        return id;
    }
}
