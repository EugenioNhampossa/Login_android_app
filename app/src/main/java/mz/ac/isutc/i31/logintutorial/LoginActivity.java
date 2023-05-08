package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        UserDao userDao = new UserDao();
        binding.btLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText username = binding.editUsername;
                        EditText password = binding.editPassword;

                        if(username.getText().toString().isEmpty()){
                            username.setError("Insira o nome de usuario");
                        }else if(username.getText().toString().isEmpty()){
                            password.setError("Insira a senha");
                        }else{
                            User user = userDao.authenticate(password.getText().toString().trim(),username.getText().toString().trim());
                            if(user != null) {
                                username.setText(null);
                                password.setText(null);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Credenciais inválidas!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        binding.usernameCheckRedirect.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, UsernameCheck.class);
                        startActivity(intent);
                    }
                }
        );

        binding.registerRedirectText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}