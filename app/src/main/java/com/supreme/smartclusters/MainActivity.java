package com.supreme.smartclusters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.supreme.smartclusters.Fragments.Applied;
import com.supreme.smartclusters.Fragments.Home;
import com.supreme.smartclusters.Fragments.Recommeded;
import com.supreme.smartclusters.Fragments.Universities;
import com.supreme.smartclusters.Fragments.profile;
import com.supreme.smartclusters.Login.login;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private AppBarConfiguration mAppBarConfiguration;
    private final Uri mainImageURI = null;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userId;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(R.color.colorPrimary);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.username);
        final TextView email = (TextView) headerView.findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String profemail = user.getEmail();
        email.setText(profemail);





        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mActionBarDrawerToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mActionBarDrawerToggle.syncState();

            }
        };

        mActionBarDrawerToggle.syncState();
        drawer.addDrawerListener(mActionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Home home = new Home();
                        goToFragment(home);
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_applied:
                        Applied applied = new Applied();
                        goToFragment(applied);
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_Recomeded:
                        Recommeded recommeded = new Recommeded();
                        goToFragment(recommeded);
                        drawer.closeDrawers();
                        break;

                    case R.id.nav_unis:
                        Universities uni = new Universities();
                        goToFragment(uni);
                        drawer.closeDrawers();

                        break;

                    case R.id.nav_profile:
                        profile profile = new profile();
                        goToFragment(profile);
                        drawer.closeDrawers();
                        break;



                }

                return false;
            }
        });


    }
    private void goToFragment(Fragment selectedFragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, selectedFragment)
                .commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

           

            case R.id.action_logout:


                mAuth.signOut();
                navigateToLogin();
                finish();
                break;



            case R.id.action_profile:

                  startActivity(new Intent(getApplicationContext(),  MarksEntry.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void navigateToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, login.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
