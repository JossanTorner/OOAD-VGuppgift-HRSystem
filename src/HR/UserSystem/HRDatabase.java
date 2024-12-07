package HR.UserSystem;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

public class HRDatabase {

    List<AppUser> users = new ArrayList<>();

    public HRDatabase() {
        users = loadUsersFromDatabase();
    }

    public List<AppUser> loadUsersFromDatabase(){
        List<AppUser> users = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/HR/UserSystem/administrators.txt"))) {
            String line;
            while((line = br.readLine()) != null){
                String[] info = line.split(" ");
                AppUser user = UserFactory.createAppUser(info[0],info[1], UserRole.valueOf(info[2].trim()));
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public AppUser findUserInDatabase(String name, String password){
        for(AppUser appUser : users){
            if (appUser.getName().equals(name) && appUser.getPassword().equals(password)){
                return appUser;
            }
        }
        return null;
    }

//    public void createNewUser(AppUser user) {
//        if(Files.exists(Path.of("src/HR/UserSystem/administrators.txt"))){
//            try(BufferedWriter writer = new BufferedWriter(new FileWriter("src/HR/UserSystem/administrators.txt", true))) {
//                writer.write(user.getName() + ", " + user.getPassword() + ", " + user.getRole());
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public List<AppUser> getUsersFromDatabase(){
        return users;
    }
}
