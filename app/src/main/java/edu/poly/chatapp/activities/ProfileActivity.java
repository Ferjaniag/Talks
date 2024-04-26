package edu.poly.chatapp.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);

        DocumentReference userRef = db.collection(Constants.KEY_COLLECTION_USERS)
                .document(userId);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    String name = user.name;
                    String email = user.email;
                    String base64EncodedImage = user.image; // Assuming your User class has a field for base64 image data

                    textViewName.setText(name);
                    textViewEmail.setText(email);

                    if (base64EncodedImage != null) {
                        byte[] bytes = Base64.decode(base64EncodedImage, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageViewProfile.setImageBitmap(bitmap);
                        imageViewProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageViewProfile.setClipToOutline(true);
                    } else {
                        // Handle case where image data is missing (optional)
                    }
                }
            }
        }).addOnFailureListener(e -> {
            // Handle failure
        });
    }


}
