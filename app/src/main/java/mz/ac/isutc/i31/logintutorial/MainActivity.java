package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.Utils.GenerateToken;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Bundle extra = getIntent().getExtras();
        User user = (User)extra.getSerializable("user");
        UserDao userDao = new UserDao();

        binding.txtToken.setText("Token: "+userDao.generateToken());
        binding.txtUsername.setText("User: " + user.getUsername());

        //Generates a token in every 5 seconds
        GenerateToken generateToken = new GenerateToken(binding,userDao);
        Timer timer = new Timer();
        timer.schedule(generateToken, 0, 5000);

        binding.btLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        setContentView(view);
    }
}