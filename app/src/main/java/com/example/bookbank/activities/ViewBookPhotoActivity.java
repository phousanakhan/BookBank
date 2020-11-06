package com.example.bookbank.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.bookbank.R;
import com.example.bookbank.models.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewBookPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_photo);
    }

    /**
     * This is the class that gets an image and sets it to the image view provided.
     * @param bookId This is the book id used to reference an image.
     * @param bookImage This is the image view that the image should be set to.
     */
    static public void setImage(String bookId, final ImageView bookImage) {
        final StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/" + bookId);
        bookImage.setImageResource(R.drawable.default_book_image);

        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bookImage.setImageBitmap(bitmap);
                    }
                });
    }
}