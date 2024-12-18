package HR.UserSystem;

public class UserFactory {

    public static AppUser createAppUser(String username, String password, String role){
        switch(role){
            case "SENIOR_MANAGER" -> {
                return new SeniorManager(username, password);
            }
            case "JUNIOR_MANAGER" -> {
                return new JuniorManager(username, password);
            }
            default -> {
                throw new IllegalArgumentException("Unrecognized role");
            }
        }
    }
}
