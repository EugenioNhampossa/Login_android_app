package mz.ac.isutc.i31.logintutorial.Dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import mz.ac.isutc.i31.logintutorial.Model.User;

public class UserDao {
    private  static  ArrayList<User> userList = new ArrayList<>();

    public ArrayList<User> getUsers(){
        return userList;
    }

    public boolean addUser(User user){
       String hash = hashString(user.getPassword());
       if(hash!=null) {
           return userList.add(new User(user.getId(), user.getUsername(), user.getCellNumber(), hash));
       }else{
           return  false;
       }
    }

    public String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public User authenticate(String password, String username){
        for (User user:userList) {
            if(user.getUsername().equals(username)) {
                String hashedPassword = hashString(password);
                if(hashedPassword.equals(password)){
                    return user;
                }
                return null;
            }
        }
        return null;
    }

    public String generateToken(){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public int generateId(){
        if(userList.isEmpty()){
            return 1;
        }else{
            return userList.get(userList.size()-1).getId()+1;
        }
    }

    public User getUserByUserName(String username){
        for (User user: userList) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean resetPass(String token,String newPassword){
        for (User user: userList) {
            if(user.getTempToken().equals(token)){
                user.setPassword(hashString(newPassword));
                return true;
            }
        }
        return false;
    }

}
