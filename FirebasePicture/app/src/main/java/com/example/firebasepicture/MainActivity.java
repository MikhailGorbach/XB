package com.example.firebasepicture;

import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasepicture.Policy.PolicyFragment;
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

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GetDataFromFragment
{
    ChipNavigationBar bottomNav;
    FragmentManager fragmentManager;

    private ModelRenderable renderable; //Переменная для работы с моделями
    private StorageReference modelRef;  //Директория в БД
    private ArFragment arFragment;      //Фрагмент с изображением
    private Fragment fragment;          //Фрагмент

    SharedPreferences mySharedPreferences;  //Объект для работы с внутренней бд
    Boolean boolTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    //Инициализация компонентов
    private void initComponents(){



        boolTmp = false;
        mySharedPreferences = getSharedPreferences("FS", this.MODE_PRIVATE);

        initBottomNavigation();

        fragmentManager = getSupportFragmentManager();
        modelRef = FirebaseStorage.getInstance().getReference();

        //Подключаем фрагменты для работы с моделями
        initFragment();

        //Инициализируем FireBase
        FirebaseApp.initializeApp(this);

        //temp

        PolicyFragment policyFragment = new PolicyFragment(bottomNav);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, policyFragment)
                .commit();
        //endtemp
    }

    //Инициализируем BottomNavigation
    private void initBottomNavigation(){
        bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                fragment = null;
                switch (id) {
                    case R.id.bottom_nav_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.bottom_nav_main:
                        fragment = new MenuFragment();
                        break;
                    case R.id.bottom_nav_ideas:
                        fragment = new IdeasFragment();
                        break;
                }
                if (fragment != null) {
                    findViewById(R.id.fragment_container).setBackgroundColor(Color.WHITE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                } else
                    Toast.makeText(MainActivity.this, "Error.",Toast.LENGTH_SHORT).show();

            }
        });
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

    //Работа с фрагментами
    private void initFragment(){
        //Инициализицая фрагмента для камеры
        arFragment = (ArFragment) getSupportFragmentManager()
                .findFragmentById(R.id.arFragment);

        //modelRef = modelRef.child("blasterH"+".glb");
        //newModel("blasterH"); //Создать по нажатию на экран

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
        modelRef = modelRef.child(data+".glb");
        newModel(data); //Создать по нажатию на экран
        modelRef = FirebaseStorage.getInstance().getReference();

        findViewById(R.id.fragment_container).setBackgroundColor(Color.TRANSPARENT);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, false);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, true);
    }

}