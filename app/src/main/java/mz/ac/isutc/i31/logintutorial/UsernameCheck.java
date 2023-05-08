package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityUsernameCheckBinding;

public class UsernameCheck extends AppCompatActivity {

    private ActivityUsernameCheckBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsernameCheckBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        UserDao userDao = new UserDao();
        EditText username = binding.editUsername;

       binding.btRedirectVerify.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(username.getText().toString().isEmpty()){
                           username.setError("Insira o nome de usuario");
                       }else{
                           User user = userDao.getUserByUserName(username.getText().toString());
                           if(user == null){
                               Toast.makeText(UsernameCheck.this, "Nome de usuario não existe", Toast.LENGTH_LONG).show();
                           }else{
                               user.setTempToken( userDao.generateToken());
                               Intent intent = new Intent(UsernameCheck.this,TokenVerification.class);
                               intent.putExtra("operation","resetPass");
                               intent.putExtra("user",user);
                               startActivity(intent);
                           }
                       }
                   }
               }
       );

        binding.btBackLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(UsernameCheck.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}