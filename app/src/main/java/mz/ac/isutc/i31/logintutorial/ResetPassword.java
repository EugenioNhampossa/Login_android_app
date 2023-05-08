package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityResetPasswordBinding;

public class ResetPassword extends AppCompatActivity {
    private  ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        UserDao userDao = new UserDao();

        Bundle extras = getIntent().getExtras();
        User user = (User)extras.getSerializable("user");
        setContentView(view);

        binding.btReset.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText password = binding.editNewPassword;
                        EditText passwordConf = binding.editNewPasswordConf;
                        if(password.getText().toString().isEmpty()) {
                            password.setError("Introduza a nova senha");
                        } else if (passwordConf.getText().toString().isEmpty()) {
                            passwordConf.setError("Confirme a senha");
                        } else if (!passwordConf.getText().toString().equals(password.getText().toString())) {
                            passwordConf.setError("As senhas são diferentes");
                        }else {
                            if(userDao.resetPass(user,password.getText().toString())){
                                password.setText(null);
                                passwordConf.setText(null);
                                Intent intent = new Intent(ResetPassword.this,SuccessMessage.class);
                                intent.putExtra("message","Senha alterada! Pode fazer login");
                                startActivity(intent);
                            }
                        }
                    }
                }
        );
    }
}