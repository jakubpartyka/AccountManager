class Account {
    //final fields
    final String UNKNOWN = "unknown";
    final String NONE = "none";
    final String STATUS_OPERATIONAL = "operational";
    final String STATUS_INACTIVE = "operational";
    final String STATUS_ACTION_NEEDED = "action required";
    final String STATUS_NOT_FOUND = "not found";

    //personal data
    private String name;
    private String dateOfBirth;

    //login data
    private String id;
    private String login;
    private String password;
    private String email;
    private String emailPassword;
    private String status;
    private String statusDate;

    Account(String login, String password,String emailPassword, String dateOfBirth, String id){
        this.login = login;
        this.password = password;
        this.email = login;
        this.emailPassword = emailPassword;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.name = UNKNOWN;
        this.status = UNKNOWN;
        this.statusDate = NONE;
    }

    Account(String login, String password, String emailPassword, String dateOfBirth, String id, String name) {
        this.login = login;
        this.password = password;
        this.email = login;
        this.emailPassword = emailPassword;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.status = UNKNOWN;
        this.statusDate = NONE;
    }

    Account(String login, String password, String emailPassword, String dateOfBirth, String id, String name, String status, String statusTime) {
        this.login = login;
        this.password = password;
        this.email = login;
        this.emailPassword = emailPassword;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.status = status;
        this.statusDate = statusTime;
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

    @Override
    public String toString() {
        return login + ':' + password + ':' + emailPassword + ':' +dateOfBirth + ':' + id + ':' + name + ':' + status + ':' + statusDate;
    }

    boolean isEmpty() {
        return login.equals(" ") && id.equals(" ") && password.equals(" ");
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(this.getClass()))
            return false;
        return this.id.equals(((Account) obj).getId());
    }

    void setStatus(String status) {
        this.status = status;
    }

    void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDate() {
        return statusDate;
    }
}
