package HR.UserSystem;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private static List<AppUser> users = new ArrayList<>();

    private UserDatabase() {
        users = loadUsers();
    }

    private List<AppUser> loadUsers(){
        List<AppUser> users = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/HR/UserSystem/administrators.txt"))) {
            String line;
            while((line = br.readLine()) != null){
                String[] info = line.split(" ");
                AppUser user = UserFactory.createAppUser(info[0],info[1], info[2].trim());
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public static AppUser findUserInDatabase(String name, String password){
        for(AppUser appUser : users){
            if (appUser.getName().equals(name) && appUser.getPassword().equals(password)){
                return appUser;
            }
        }
        return null;
    }

    public static List<AppUser> getUsers() {
        return users;
    }
}