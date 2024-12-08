package HR.UserSystem;

public class UserFactory {

    public static AppUser createAppUser(String username, String password, String role){
        switch(role){
            case "SENIOR_MANAGER" -> {
                return new SeniorHRManager(username, password);
            }
            case "JUNIOR_MANAGER" -> {
                return new JuniorHRManager(username, password);
            }
            default -> {
                throw new IllegalArgumentException("Unrecognized role");
            }
        }
    }
}
