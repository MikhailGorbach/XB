package com.example.firebasepicture.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.firebasepicture.Menu.IdeasFragment;
import com.example.firebasepicture.Menu.MenuFragment;
import com.example.firebasepicture.Menu.MenuFragmentSource.GetDataFromFragment;
import com.example.firebasepicture.Menu.SettingsFragment;
import com.example.firebasepicture.Policy.PolicyFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements GetDataFromFragment
{
    public static final String APP_PREFERENCES = "PrivatePolicy";
    public static final String PrivatePolicyKey = "unique";
    public static SharedPreferences mySharedPreferences;  //Объект для работы с внутренней бд
    public static ChipNavigationBar bottomNav;

    private FragmentManager fragmentManager;
    private ModelRenderable renderable; //Переменная для работы с моделями
    private StorageReference modelRef;  //Директория в БД
    private ArFragment arFragment;      //Фрагмент с изображением
    private Fragment fragment;          //Фрагмент

    private NetworkChangeListener networkChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        if (Intent.ACTION_VIEW.equals(getIntent().getAction()) && getIntent().getDataString() != null) {
            final String jobId = getIntent().getDataString().substring(getIntent().getDataString().lastIndexOf("/") + 1);
        }

    }

    //Инициализация компонентов
    private void initComponents(){
        networkChangeListener = new NetworkChangeListener();
        fragmentManager = getSupportFragmentManager();
        modelRef = FirebaseStorage.getInstance().getReference();
        mySharedPreferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        boolean privatePolicyAccept = mySharedPreferences.getBoolean(PrivatePolicyKey, false);

        initBottomNavigation();

        if(!privatePolicyAccept){
            PolicyFragment policyFragment = new PolicyFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, policyFragment)
                    .commit();

            return;
        }

        //Подключаем фрагменты для работы с моделями
        initFragment();

        //Инициализируем FireBase
        FirebaseApp.initializeApp(this);
    }

    //Инициализируем BottomNavigation
    @SuppressLint("ResourceAsColor")
    private void initBottomNavigation(){
        Context ctx = this;
        bottomNav = findViewById(R.id.bottom_nav_menu);
        bottomNav.setOnItemSelectedListener(id -> {
            fragment = null;
            switch (id) {
                case R.id.bottom_nav_ideas:
                    //findViewById(id).setBackgroundTintList(ctx.getResources().getColorStateList(R.color.white));
                    fragment = new IdeasFragment();
                    break;
                case R.id.bottom_nav_main:
                    //setBackgroundTintList(ctx.getResources().getColorStateList(R.color.white));
                    fragment = new MenuFragment();
                    break;
                case R.id.bottom_nav_settings:
                    //findViewById(id).setBackgroundColor(R.color.white);//ssetBackgroundTintList(ctx.getResources().getColorStateList(R.color.white));
                    fragment = new SettingsFragment();
                    break;
            }
            if (fragment != null) {
                findViewById(R.id.fragment_container).setBackgroundColor(Color.WHITE);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

    //Собрать новую модель
    private void newModel(String name){
        try {
            File file = File.createTempFile(name, "glb");
            StorageTask<FileDownloadTask.TaskSnapshot> taskSnapshotStorageTask = modelRef.getFile(file)
                    .addOnSuccessListener(taskSnapshot -> buildModel(file))
                    .addOnFailureListener(new OnFailureListener() {
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

        //При нажатии создать новую сцену с renderable обектом
        assert arFragment != null;
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            if (renderable == null){
                return;
            }

            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            TransformableNode lamp = new TransformableNode(arFragment.getTransformationSystem());
            lamp.setParent(anchorNode);
            lamp.setRenderable(renderable);
            lamp.getScaleController().setEnabled(false);
            lamp.select();

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
        catch (@NonNull Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void GetData(String data) {//data - имя модели например "blasterH"
        modelRef = modelRef.child("models Android/" + data+".glb");
        newModel(data); //Создать по нажатию на экран
        modelRef = FirebaseStorage.getInstance().getReference();

        findViewById(R.id.fragment_container).setBackgroundColor(Color.TRANSPARENT);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, false);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, true);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}