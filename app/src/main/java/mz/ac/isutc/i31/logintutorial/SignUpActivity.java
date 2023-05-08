package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        UserDao userDao = new UserDao();
        EditText username = binding.editUsername;
        EditText cellNumber = binding.editCellNumber;
        EditText password = binding.editPassword;
        EditText passwordConfirm = binding.editPasswordConfirm;

        binding.btRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(username.getText().toString().isEmpty()){
                            username.setError("Insira o nome de usuário");
                        }else if(cellNumber.getText().toString().isEmpty()){
                            cellNumber.setError("Insira o nr de celular");
                        }else if(password.getText().toString().isEmpty()){
                            password.setError("Insira a senha");
                        }else if(passwordConfirm.getText().toString().isEmpty()){
                            passwordConfirm.setError("Confirme a senha");
                        }else if(!passwordConfirm.getText().toString().equals(password.getText().toString())){
                            passwordConfirm.setError("As senhas são diferentes");
                        } else {
                            Intent intent = new Intent(SignUpActivity.this,TokenVerification.class);
                            User user = new User(
                                    userDao.generateId(),
                                    username.getText().toString(),
                                    cellNumber.getText().toString(),
                                    password.getText().toString(),
                                    userDao.generateToken()
                            );
                            username.setText(null);
                            password.setText(null);
                            cellNumber.setText(null);
                            passwordConfirm.setText(null);
                            intent.putExtra("operation","register");
                            intent.putExtra("user",user);
                            startActivity(intent);
                        }
                    }
                }
        );

        binding.loginRedirectText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

}