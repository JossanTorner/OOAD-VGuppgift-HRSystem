package HR.UserSystem;

public abstract class AppUser {

    private final String name;
    private final String password;

    public AppUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
