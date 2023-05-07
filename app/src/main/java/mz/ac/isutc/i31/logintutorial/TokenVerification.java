package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        Notify notify = new Notify(TokenVerification.this);
        notify.createNotificationChannel();
        notify.createNotification("Verificação","CODIGO: "+user.getTempToken());

        setContentView(view);

        binding.btVerify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String operation = extras.getString("operation");
                        if(operation.equals("register")){
                            String token = binding.editToken.getText().toString();
                            if(token.equals(user.getTempToken())) {
                                if (userDao.addUser(user)) {
                                    Toast.makeText(TokenVerification.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(TokenVerification.this, "Código inválido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }
}