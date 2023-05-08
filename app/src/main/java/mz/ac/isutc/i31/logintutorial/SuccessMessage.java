package mz.ac.isutc.i31.logintutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import mz.ac.isutc.i31.logintutorial.databinding.ActivitySuccessMessageBinding;

public class SuccessMessage extends AppCompatActivity {

    private ActivitySuccessMessageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessMessageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Bundle extras = getIntent().getExtras();
        binding.message.setText(extras.getString("message"));
        setContentView(view);

        binding.btRedirectLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SuccessMessage.this,LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }
}