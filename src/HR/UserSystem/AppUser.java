package HR.UserSystem;

public abstract class AppUser {

    private final String name;
    private final String password;
    private final UserRole role;

    public AppUser(String name, String password, UserRole role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}
