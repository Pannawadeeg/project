package com.example.demo1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Camera extends AppCompatActivity {

    Button selectBtn, cameraBtn, predictBtn;
    TextView result;
    Bitmap bitmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //ขอใช้งานกล้อง
        getPermission();

        String[] labels = new String[1001];
        int cnt=0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line=bufferedReader.readLine();
            while (line!=null){
                labels[cnt]=line;
                cnt++;
                line=bufferedReader.readLine();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        selectBtn = findViewById(R.id.selectBtn);
        predictBtn = findViewById(R.id.predicBtn);
        cameraBtn = findViewById(R.id.cameratBtn);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,12);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {




            }
        });

    }

    int getMax(float[] arr){
        int max=0;
        for(int i=0; i< arr.length; i++){
            if(arr[i] > arr[max]) max=i;
        }
        return max;
    }

    void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Camera.this, new String[]{
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 11);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean storagePermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (cameraPermissionGranted && storagePermissionGranted) {
                    // ทั้งการอนุญาตให้ใช้งานกล้องและการอ่านไฟล์รูปภาพได้รับการอนุญาต
                    // ทำงานที่ต้องการที่นี่
                    openCamera(); // เปิดกล้อง
                    // หรือ
                    selectImage(); // เลือกรูปภาพจากแกลลอรี่
                } else {
                    // ผู้ใช้ไม่ได้รับอนุญาตให้ใช้งานกล้องและ/หรืออ่านไฟล์รูปภาพ
                    // ทำการแจ้งผู้ใช้หรือจัดการตามที่คุณต้องการ
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    void openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // คุณได้รับอนุญาตให้ใช้งานกล้องแล้ว
            // ทำการเรียกใช้งานกล้องที่นี่
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CONTEXT_INCLUDE_CODE);
        } else {
            // คุณไม่ได้รับอนุญาตให้ใช้งานกล้อง
            // คุณควรขอสิทธิ์อนุญาตให้ใช้งานกล้องผ่านการร้องขอสิทธิ์
            ActivityCompat.requestPermissions(Camera.this, new String[]{Manifest.permission.CAMERA}, 11);
        }
    }
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*"); // เลือกเฉพาะไฟล์รูปภาพ
        startActivityForResult(intent, RECEIVER_EXPORTED);
    }

    @Override
    protected
    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    //    private static final int REQUEST_CODE = 22;
//    Button btnpicture;
//    ImageView imageView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera);
//        btnpicture = findViewById(R.id.btcamera_id);
//        imageView = findViewById(R.id.image);
//
//        btnpicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public
//            void onClick(View v) {
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, REQUEST_CODE);
//            }
//
//        });
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            assert data != null;
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//        } else {
//            Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show();
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//    // โหลดโมเดลจากไฟล์ใน assets หรือ res/raw
//    private MappedByteBuffer loadModelFile() throws IOException {
//        AssetFileDescriptor fileDescriptor = getAssets().openFd("model.tflite");
//        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel fileChannel = inputStream.getChannel();
//        long startOffset = fileDescriptor.getStartOffset();
//        long declaredLength = fileDescriptor.getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
//    }
//
//    // ใช้โมเดลตรวจจับพันธุ์สุนัขบนภาพที่ถ่ายจากกล้อง
//    private void detectDogBreed(Bitmap photo) {
//        try {
//            // โหลดโมเดล
//            MappedByteBuffer tfliteModel = loadModelFile();
//            Interpreter.Options tfliteOptions = new Interpreter.Options();
//            tfliteOptions.setNumThreads(4); // จำนวนเทรดที่ใช้ในการทำนาย
//
//            // สร้างโมเดลอินเทอร์เพร็ตเตอร์
//            Interpreter tflite = new Interpreter(tfliteModel, tfliteOptions);
//
//            // ประมวลผลภาพ
//            // นี่คือตอนที่คุณจะใช้โมเดลเพื่อตรวจจับพันธุ์สุนัขบนรูปภาพ
//            // และแสดงผลลัพธ์ในแอปของคุณ
//            // ...
//
//            // ปิดโมเดลอินเทอร์เพร็ตเตอร์เมื่อไม่ได้ใช้งาน
//            tflite.close();
//        } catch (IOException e) {
//            // จัดการข้อผิดพลาดที่เกิดขึ้นในการโหลดโมเดล
//            e.printStackTrace();
//        }
//    }


}