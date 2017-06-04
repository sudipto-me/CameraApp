package app.realm.gnt.com.imagecaptuting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String userChoosenTask,SELECT_FILE;

    private Button btn_takePictureButton;
    private ImageView iv_takeImage;
    private Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_takePictureButton = (Button)findViewById(R.id.btn_Select_Photo);
        iv_takeImage = (ImageView)findViewById(R.id.iv_Simple_ImageView);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){

            btn_takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},0);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      if(requestCode==0){
          if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                  && grantResults[1]==PackageManager.PERMISSION_DENIED){

              btn_takePictureButton.setEnabled(true);
          }
      }
    }

    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT,file);

        startActivityForResult(intent,100);

    }

    private  static File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100){
            if(requestCode == RESULT_OK){
                iv_takeImage.setImageURI(file);
            }
        }
    }

    /*
    This function is for the dialog box.when the user click on Button"Select a Photo" this time a pop will come.
    This pop will show which option the user wants such as:use camera
    or take a image from gallery or cancel the process.
     */

    /*



    private void selectImage(){

        final CharSequence[] items = {"Take a photo","Choose from gallery","Cancel"};//this is the sequence of choices user wants

        //alertdailog for the pop up
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);

        mbuilder.setTitle("Add Photo");

      mbuilder.setItems(items, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int item) {
              boolean result = Utility.checkPermission(MainActivity.this);
              if(items[item].equals("Take Photo")){
                  userChoosenTask = "Take Photo";
                  if(result){
                      cameraIntent();
                  }
              }
              else if(items[item].equals("Select From Gallery")){
                  userChoosenTask = "Select From Gallery";
                  if (result){
                      galleryIntent();
                  }
              }
              else if(items[item].equals("Cancel")){
                  dialog.dismiss();
              }
          }
      });
        mbuilder.show();



    }

    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select File"), Integer.parseInt(SELECT_FILE));
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Integer.parseInt(CAMERA_SERVICE));//check this line
    }
    */




}
