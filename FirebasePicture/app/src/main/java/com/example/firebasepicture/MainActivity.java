package com.example.firebasepicture;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnFailureListener;

import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GetDataFromFragment
{
    private StorageReference mStorageReference; // НЕ ТРОГАТЬ!!!!!!!!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // !!!!!!!! КНОПКА ФРАГМЕНТА !!!!!!!!!!!!

        FragmentForButton fragmentForButton = new FragmentForButton();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentForButton).commit();

        //!!!!!!!!!!!!!!   НЕ ТРОГРАТЬ         !!!!!!!!!!!! МАГИЯ   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! НАЧАЛО

        //Подключаем Firebase для картинки

        initFirebase();

        //Подключаем Firebase для модельки

        FirebaseApp.initializeApp(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child("blasterH.glb");

        ArFragment arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        GetDataFromFragment listener = (GetDataFromFragment) arFragment;
        listener.GetData("  ");


        findViewById(R.id.downloadBtn)
                .setOnClickListener(v -> {

                    try {
                        File file = File.createTempFile("blasterH", "glb");

                        modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                buildModel(file);

                            }
                        });

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                });

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);

        });
    }
    private ModelRenderable renderable;

    private void initFirebase(){
        mStorageReference = FirebaseStorage.getInstance().getReference().child("1597154303_00018.jpg");
        try {
            final File localFile = File.createTempFile("1597154303_00018", "jpg");
            mStorageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity.this, "Picture Retrieved", Toast.LENGTH_SHORT).show();
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void buildModel(File file) {
        try {
            RenderableSource renderableSource = RenderableSource
                    .builder()
                    .setSource(this, Uri.parse(file.getPath()), RenderableSource.SourceType.GLB)
                    .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                    .build();

            ModelRenderable
                    .builder()
                    .setSource(this, renderableSource)
                    .setRegistryId(file.getPath())
                    .build()
                    .thenAccept(modelRenderable -> {
                        Toast.makeText(this, "Model built", Toast.LENGTH_SHORT).show();
                        renderable = modelRenderable;
                    });
        }
        catch (@NonNull Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void GetData(String data) {
        //data - имя модели
        Toast.makeText(this, ("Data from fragment: "+data), Toast.LENGTH_SHORT).show();
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!  ТУТ МАГИЯ ЗАКОЧИЛАСЬ  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}
