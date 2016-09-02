package com.example.fede.entendeme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by fede on 2/9/2016.
 */
public class CheckPhoto extends ActionBarActivity {

    private ImageView imgPhoto;
    private ImageButton btnCut, btnTakeOtherPhoto, btnOk;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_photo);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCut = (ImageButton) findViewById(R.id.btnCut);
        btnTakeOtherPhoto = (ImageButton) findViewById(R.id.btnTakeOtherPhoto);
        btnOk = (ImageButton) findViewById(R.id.btnOk);

        Intent intent = getIntent();
        Bitmap imageBitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        imgPhoto.setImageBitmap(imageBitmap);
    }

    public void onClickTakeOtherPhoto(View view)
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, CheckPhoto.class);
            intent.putExtra("BitmapImage", imageBitmap);
            startActivity(intent);
        }
    }
}
