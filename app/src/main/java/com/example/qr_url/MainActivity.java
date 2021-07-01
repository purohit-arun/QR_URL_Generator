package com.example.qr_url;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_url.ui.main.SectionsPagerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Saves the image as PNG to the app's private external storage folder.
     *
     * @param @image Bitmap to save.
     * @return Uri of the saved file or null
     */
//    private Uri saveImageExternal(Bitmap image) {
//        //TODO - Should be processed in another thread
//        Uri uri = null;
//        try {
//            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "to-share.png");
//            FileOutputStream stream = new FileOutputStream(file);
//            image.compress(Bitmap.CompressFormat.PNG, 90, stream);
//            stream.close();
////            uri = Uri.fromFile(file);
//            String suri = "content:///storage/emulated/0/Android/data/com.example.qrurl/files/Pictures/to-share.png";
//            uri = Uri.parse(suri);
//        } catch (IOException e) {
//            Log.d("Tag", "IOException while trying to write file for sharing: " + e.getMessage());
//        }
//        return uri;
//    }

    //Checking storage accessibility
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }

    //actual sharing of image code
//    private void shareImageUri(Uri uri) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.putExtra(Intent.EXTRA_STREAM, uri);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setType("image/png");
//        startActivity(intent);
//    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bitmap icon = Fragment_1.getterbitmapqr();

        if (icon != null) {
            if (id == R.id.share) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Qrcode to share",
                    Toast.LENGTH_SHORT).show();
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }
}





