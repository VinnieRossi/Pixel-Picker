package com.example.vinnie.pixelpicker;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button galleryButton;
    private Button cameraButton;
    private ImageView imageView;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryButton = (Button) findViewById(R.id.galleryButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        imageView = (ImageView) findViewById(R.id.imageView);

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
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
               super.onActivityResult(requestCode, resultCode, data);

                      switch(requestCode) {
                      case 0:
                               if (resultCode == RESULT_OK) {
                                       Uri selectedImage = data.getData();
                                       String[] filePathColumn = {MediaStore.Images.Media.DATA};

                                               Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                      cursor.moveToFirst();

                                               int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                       String filePath = cursor.getString(columnIndex);
                                       cursor.close();


                                                       Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                                       imageView.setImageBitmap(yourSelectedImage);

                                          }
                           break;

                               case 1:
                               if (resultCode == RESULT_OK) {
                                       Toast.makeText(this, "Image saved to: " + data.getData(), Toast.LENGTH_LONG).show();
                                   }
                            break;
                   }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleStartColorDropper(View v){
        Intent intent = new Intent(this,ColorDropperActivity.class);
        // Bundle bundle = new Bundle();
        // bundle.putStringArrayList("studentList", courses);

        //  intent.putExtras(bundle);
        startActivity(intent);
    }
}
