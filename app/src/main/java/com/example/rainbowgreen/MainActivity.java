package com.example.rainbowgreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    ImageView imageview;
    EditText datatext;
    Button b1, b2, b3;
    ScrollView scroll;
    int image;
    private String text;
    private FirebaseStorage firebaseStorage;
    private StorageReference mStorageRef;
    public Uri imageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!= null && data.getData()!= null)
        {     
            imageUri = data.getData();
            imageview.setImageURI(imageUri);
        }
    }

    private void UploadImage() {
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = mStorageRef.child("images/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                text = datatext.getText().toString().trim();
                                Model model = new Model(uri.toString(), text);
                                myRef.push().setValue(model);
                                Snackbar.make(findViewById(R.id.main_activity), "Image Uploaded", BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview = findViewById(R.id.load_image);
        firebaseStorage = FirebaseStorage.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        scroll = findViewById(R.id.main_activity);
        b1 = findViewById(R.id.select_button);
        b2 = findViewById(R.id.upload_button);
        b3 = findViewById(R.id.next_button);
        datatext = findViewById(R.id.write);
        myRef = FirebaseDatabase.getInstance("https://rainbowgreen-c0893-default-rtdb.firebaseio.com/").getReference().child("Model");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }

            private void choosePicture() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        b2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, show.class);
                startActivity(intent);
            }
        });
    }
}