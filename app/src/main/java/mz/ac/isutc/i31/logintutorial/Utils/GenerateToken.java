package mz.ac.isutc.i31.logintutorial.Utils;

import android.annotation.SuppressLint;

import java.util.TimerTask;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityMainBinding;

public class GenerateToken extends TimerTask {
    private ActivityMainBinding binding;
    private UserDao userDao;

    public GenerateToken(ActivityMainBinding binding, UserDao userDao){
        this.binding = binding;
        this.userDao = userDao;
    }

    @SuppressLint("SetTextI18n")
    public void run() {
        binding.txtToken.setText("Token: " + userDao.generateToken());
    }
}
