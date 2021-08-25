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

    private ModelRenderable renderable; //Переменная для работы с моделями

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    //Инициализация компонентов
    private void initComponents(){

        //Инициализиция кнопки фрагмента
        FragmentForButton fragmentForButton = new FragmentForButton();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentForButton).commit();

        //Подключаем Firebase для модельки
        FirebaseApp.initializeApp(this);

        //Запуск фрагменты
        initFragment();

        //Переменные для работы с моделями
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference modelRef = storage.getReference().child("blasterH.glb");

        //Инициализация кнопки Скачать
        initBtnDownload(modelRef);

    }

    //Настройка кнопки Скачать
    private void initBtnDownload(StorageReference modelRef){
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
    }

    //Работа с фрагментом
    private void initFragment(){
        ArFragment arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);

        });
    }

    //Инициализируем модель
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

}
