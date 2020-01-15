class Account {
    //personal data
    private String name;
    private String dateOfBirth;

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
        this.emailPassword = emailPassword;
        this.id = id;
        this.dateOfBirth = date;
    }

    String getId() {
        return id;
    }

    String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }

    String getName() {
        if(name == null)
            return "not found ";
        else
            return name;
    }

    String  getDateOfBirth() {
        return dateOfBirth;
    }

    String getEmail() {
        return email;
    }

    String getEmailPassword() {
        return emailPassword;
    }

    void setId(String id) {
        this.id = id;
    }


    void setName(String name) {
        this.name = name;
    }

    void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    void setLogin(String login) {
        this.login = login;
    }

    void setPassword(String password) {
        this.password = password;
    }
}
