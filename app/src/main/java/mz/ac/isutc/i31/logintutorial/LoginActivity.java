package mz.ac.isutc.i31.logintutorial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.recaptcha.Recaptcha;
import com.google.android.recaptcha.RecaptchaAction;
import com.google.android.recaptcha.RecaptchaTasksClient;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private  TextView txtMessage;
    private static  boolean isVerifyed;
    @Nullable
    private RecaptchaTasksClient recaptchaTasksClient = null;

    @Override
    public void onBackPressed() {
        System.exit(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initializeRecaptchaClient();
        setContentView(view);

        UserDao userDao = new UserDao();
        binding.capchaCheckBox.setEnabled(false);
        txtMessage = binding.txtMessage;

        isVerifyed=false;

        binding.btLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText username = binding.editUsername;
                        EditText password = binding.editPassword;

                        if (username.getText().toString().isEmpty()) {
                            username.setError("Insira o nome de usuario");
                        } else if (username.getText().toString().isEmpty()) {
                            password.setError("Insira a senha");
                        }else if(!binding.capchaCheckBox.isChecked() || !isVerifyed) {
                            binding.capchaCheckBox.setError("Marque a caixa");
                        } else {
                            User user = userDao.authenticate(password.getText().toString().trim(), username.getText().toString().trim());
                            if (user != null) {
                                username.setText(null);
                                password.setText(null);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                            } else {
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

        binding.capchaCheckBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (binding.capchaCheckBox.isChecked()){
                            executeLoginAction(view);
                        }
                    }
                }
        );
    }

    private void initializeRecaptchaClient() {
        Recaptcha.getTasksClient(getApplication(), "6LdcAvAlAAAAAA9jQJOoEMeycjnxZXlJYnJg1S9M")
            .addOnSuccessListener(this, new OnSuccessListener<RecaptchaTasksClient>() {
                @Override
                public void onSuccess(RecaptchaTasksClient client) {
                    LoginActivity.this.recaptchaTasksClient = client;
                    binding.capchaCheckBox.setEnabled(true);
                    txtMessage.setText(null);
                    Toast.makeText(LoginActivity.this, "Initialization succeed", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    isVerifyed=false;
                    txtMessage.setText("Erro ao inicializar o captcha!\nVerifique a sua conexão com a internet.");
                }
            });
    }

    private void executeLoginAction(View v) {
        assert recaptchaTasksClient != null;
        binding.capchaCheckBox.setEnabled(false);
        recaptchaTasksClient.executeTask(RecaptchaAction.LOGIN)
            .addOnSuccessListener(this, new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String token) {
                    binding.capchaCheckBox.setEnabled(true);
                    txtMessage.setText(null);
                    isVerifyed=true;
                    Toast.makeText(LoginActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    binding.capchaCheckBox.setEnabled(true);
                    binding.capchaCheckBox.setChecked(false);
                    isVerifyed=false;
                    txtMessage.setText("Erro ao realizar o captcha!\nVerifique a sua conexão com a internet.");
                }
            });
    }

}