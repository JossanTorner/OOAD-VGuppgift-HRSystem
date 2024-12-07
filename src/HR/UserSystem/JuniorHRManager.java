package HR.UserSystem;

public class JuniorHRManager extends AppUser {

    public JuniorHRManager(String name, String password) {
        super(name, password, UserRole.JUNIOR_MANAGER);
    }

}
