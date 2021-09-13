package com.example.firebasepicture;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GetDataFromFragment
{
    private static final String TAG = "FragmentDebag";

    private ModelRenderable renderable; //Переменная для работы с моделями
    private StorageReference modelRef;  //Директория в БД
    private ArFragment arFragment;      //Фрагмент с изображением
    private FragmentMenu menu;          //menu - фрагмент

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    //Инициализация компонентов
    private void initComponents(){
        modelRef = FirebaseStorage.getInstance().getReference();
        menu = null;

        //Подключаем фрагменты
        initFragment();

        //Инициализируем FireBase
        FirebaseApp.initializeApp(this);

        //Инициализация кнопки Скачать
        initBtnDownload();

        //Инициализация кнопки Каталога
        initBtnMenu();
    }

    //Собрать новую модель
    private void newModel(String name){
        try {
            File file = File.createTempFile(name, "glb");
            modelRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    buildModel(file);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error. Model hasn't founded.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Настройка кнопки Скачать
    private void initBtnDownload(){
        findViewById(R.id.downloadBtn)
        .setOnClickListener(v -> {
            newModel("blasterH");
        });
    }

    //Настройка кнопки Меню
    private void initBtnMenu(){
        findViewById(R.id.btnMenu)
                .setOnClickListener(v -> {
                    menu = new FragmentMenu();
                    getSupportFragmentManager().beginTransaction().add(R.id.arFragment, menu).commit();
                });
    }

    //Работа с фрагментами
    private void initFragment(){
        //Инициализиция фрагмента с кнопками (меню)
        FragmentForButton fragmentForButton = new FragmentForButton();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentForButton).commit();

        //Инициализицая фрагмента для камеры
        arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        //При нажатии создать новую сцену с renderable обектом
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(renderable);
            arFragment.getArSceneView().getScene().addChild(anchorNode);
        });

    }

    //Создаём модель
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
    public void GetData(String data) {//data - имя модели например "blasterH"
        Log.d(TAG, "GetData at MainActivity: "+data );

        if(menu != null)
            getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, menu).commit();

        modelRef = modelRef.child(data+".glb");
        newModel(data); //Создать по нажатию на экран
        modelRef = FirebaseStorage.getInstance().getReference();
    }

}

class DataBase{
    final private String USER_KEY = "blasterH.glb";
    private FirebaseStorage storage;                //Переменная для работы с БД
    private StorageReference rootRef;               //Переменная указывающая на корневую папку БД

    public DataBase(){
        storage = FirebaseStorage.getInstance();
        rootRef = storage.getReference();
    }

}