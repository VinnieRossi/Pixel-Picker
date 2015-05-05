package com.example.vinnie.pixelpicker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private Button galleryButton;
    private Button cameraButton;
    private Button dropperButton;
    private ImageView imageView;
    private Bitmap yourSelectedImage;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize button variables
        galleryButton = (Button) findViewById(R.id.galleryButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        dropperButton = (Button) findViewById(R.id.dropperButton);

        // Get reference to the layout's imageview
        imageView = (ImageView) findViewById(R.id.imageView);

        // Initialize an onClick method for each button
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        dropperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri != null) {
                    Intent intent = new Intent(getBaseContext(), PageViewActivity.class);
                    intent.putExtra("selectedImage", fileUri.toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(getBaseContext(), "Please select an image", Toast.LENGTH_SHORT).show();
                }
                }
            });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            fileUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            yourSelectedImage = BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(yourSelectedImage);
        }
    }
}
