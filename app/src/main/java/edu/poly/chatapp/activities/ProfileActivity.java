package edu.poly.chatapp.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import edu.poly.chatapp.R;
import edu.poly.chatapp.models.User;
import edu.poly.chatapp.utilities.Constants;
import edu.poly.chatapp.utilities.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {
    private ImageView imageViewProfile;
    private TextView textViewName, textViewEmail;

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewProfile = findViewById(R.id.imageProfile);
        textViewName = findViewById(R.id.textName);
        textViewEmail = findViewById(R.id.textEmail);

        preferenceManager = new PreferenceManager(getApplicationContext());

        loadUserProfile();
    }

    private void loadUserProfile() {
        String name = preferenceManager.getString(Constants.KEY_NAME);
        String email = preferenceManager.getString(Constants.KEY_EMAIL);
        String imageUrl = preferenceManager.getString(Constants.KEY_IMAGE);

        textViewName.setText(name);
        textViewEmail.setText(email);
        Glide.with(this).load(imageUrl).into(imageViewProfile);
    }
}
