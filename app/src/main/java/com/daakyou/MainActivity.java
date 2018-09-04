package com.daakyou;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.daakyou.fragments.account;
import com.daakyou.fragments.home;
import com.daakyou.fragments.opendrawer;
import com.daakyou.fragments.wallet;
import com.daakyou.singup.signup;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements home.OnFragmentInteractionListener, wallet.OnFragmentInteractionListener, account.OnFragmentInteractionListener {

    private FirebaseAuth ath;
    private NavigationView navigationView;
    DrawerLayout layout;
    private ImageView toolbarbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ath=FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);
        toolbarbutton=(ImageView)findViewById(R.id.imbttoolbar);
        layout=(DrawerLayout)findViewById(R.id.drawer_layout);
       opendrawer ob=new opendrawer();
       navlisner(navigationView);
       ob.opendw(toolbarbutton,layout);


        if(ath.getCurrentUser()==null)
        {

            Intent send=new Intent(getApplicationContext(),signup.class);
            startActivity(send);


        }




    }

    private void navlisner(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                layout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void switchfrag(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragcontainer,fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
