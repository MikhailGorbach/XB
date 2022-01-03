package com.example.firebasepicture.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.firebasepicture.Menu.IdeasFragment;
import com.example.firebasepicture.Menu.MenuFragment;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForButton;
import com.example.firebasepicture.Menu.MenuFragmentSource.FragmentForCard;
import com.example.firebasepicture.Menu.MenuFragmentSource.GetDataFromFragment;
import com.example.firebasepicture.Menu.SettingsFragment;
import com.example.firebasepicture.Model;
import com.example.firebasepicture.Policy.PolicyFragment;
import com.example.firebasepicture.R;
import com.example.firebasepicture.Utility.NetworkChangeListener;
import com.example.firebasepicture.Utility.OnBackPressedListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity implements GetDataFromFragment
{
    public static final String APP_PREFERENCES = "PrivatePolicy";
    public static final String PrivatePolicyKey = "unique";
    public static SharedPreferences mySharedPreferences;  //Объект для работы с внутренней бд
    public static ChipNavigationBar bottomNav;
    private static FrameLayout fragmentScreens;

    private FragmentManager fragmentManager;
    private ModelRenderable renderable; //Переменная для работы с моделями
    private StorageReference modelRef;  //Директория в БД
    private ArFragment arFragment;      //Фрагмент с изображением
    private Fragment fragment;          //Фрагмент
    private ProgressBar progressBar;

    private NetworkChangeListener networkChangeListener;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentScreens = findViewById(R.id.fragment_container);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = this;
        initComponents();

        if (Intent.ACTION_VIEW.equals(getIntent().getAction()) && getIntent().getDataString() != null) {
            String jobId = getIntent().getDataString().substring(getIntent().getDataString().lastIndexOf("/") + 1);
            Log.d("debug", "onCreate: "+jobId);

            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            Query query = firebaseFirestore.collection("models").whereEqualTo("article", jobId);

            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (queryDocumentSnapshots.isEmpty()) {
                        if (context != null)
                            Toast.makeText( context, "No data found in Database", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new FragmentForCard(list.get(0).toObject(Model.class),context)).commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(fragment.getContext(), "Fail get data from Database.", Toast.LENGTH_SHORT).show();
                }
            });

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

            ViewGroup layout = (ViewGroup) findViewById(R.id.parentLayout);

            progressBar.setVisibility(View.VISIBLE);

            File file = File.createTempFile(name, "glb");
            StorageTask<FileDownloadTask.TaskSnapshot> taskSnapshotStorageTask = modelRef.getFile(file)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Log.d("debug", "Модель найдена");
                            buildModel(file);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Модель не найдена.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        }
        catch (IOException e)
        {
            Log.d("debug", "error");
            progressBar.setVisibility(View.INVISIBLE);
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

            renderable = null;
        });

    }

    //Создаём модель
    @SuppressLint("ResourceType")
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
                        Toast.makeText(this, "Модель загружена", Toast.LENGTH_SHORT).show();
                        renderable = modelRenderable;
                        Log.d("debug", "Модель загружена");

                        progressBar.setVisibility(View.INVISIBLE);
                    }).exceptionally(new Function<Throwable, Void>() {
                @Override
                public Void apply(Throwable throwable) {
                    Toast.makeText(context, "Ошибка 246->main = " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("debug", "Error = " + throwable.getMessage());

                    progressBar.setVisibility(View.INVISIBLE);
                    return null;
                }
            });
        }
        catch (@NonNull Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            Log.d("debug", "Ошибка загрузки");
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //Данные из фрагментов
    @Override
    public void GetData(String data) {
        Log.d("debug", data);
        modelRef = modelRef.child("models Android/" + data+".glb");
        newModel(data); //Создать по нажатию на экран
        modelRef = FirebaseStorage.getInstance().getReference();

        findViewById(R.id.fragment_container).setBackgroundColor(Color.TRANSPARENT);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, false);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, true);
    }

    //Запуск проверки интернета
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }

    //Остановка проверки интернета
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }

    static public void clear(){
        fragmentScreens.setBackgroundColor(Color.TRANSPARENT);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, false);
        bottomNav.setItemEnabled(R.id.bottom_nav_main, true);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment: fm.getFragments()) {
            if (fragment instanceof  OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}