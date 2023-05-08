package mz.ac.isutc.i31.logintutorial;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import mz.ac.isutc.i31.logintutorial.Dao.UserDao;
import mz.ac.isutc.i31.logintutorial.Model.User;
import mz.ac.isutc.i31.logintutorial.Utils.Notify;
import mz.ac.isutc.i31.logintutorial.databinding.ActivityTokenVerificationBinding;

public class TokenVerification extends AppCompatActivity {
    private ActivityTokenVerificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTokenVerificationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        UserDao userDao = new UserDao();

        Bundle extras = getIntent().getExtras();
        User user = (User)extras.getSerializable("user");
        binding.txtNumber.setText(user.getCellNumber());
        Log.d(TAG, "onCreate: "+user.toString());

        Notify notify = new Notify(TokenVerification.this);
        notify.createNotificationChannel();
        notify.createNotification("Verificação","CODIGO: "+user.getTempToken());

        setContentView(view);

        binding.btVerify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String operation = extras.getString("operation");
                        String token = binding.editToken.getText().toString();
                        if(operation.equals("register")){
                            if(token.equals(user.getTempToken())) {
                                if (userDao.addUser(user)) {
                                    Toast.makeText(TokenVerification.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                                    binding.editToken.setText(null);
                                    Intent intent = new Intent(TokenVerification.this,SuccessMessage.class);
                                    intent.putExtra("message","Usuário Registrado. Já pode entrar na aplicação");
                                    startActivity(intent);
                                }
                            }else{
                                Toast.makeText(TokenVerification.this, "Código inválido", Toast.LENGTH_SHORT).show();
                            }
                        } else if (operation.equals("resetPass")) {
                            if(token.equals(user.getTempToken())) {
                                Intent intent = new Intent(TokenVerification.this,ResetPassword.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }else{
                                Toast.makeText(TokenVerification.this, "Código inválido", Toast.LENGTH_SHORT).show();
                            }
                        } else if (operation.equals("login")) {
                            if(token.equals(user.getTempToken())) {
                                Intent intent = new Intent(TokenVerification.this,MainActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }else{
                                Toast.makeText(TokenVerification.this, "Código inválido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );

        binding.btBackLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(TokenVerification.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}