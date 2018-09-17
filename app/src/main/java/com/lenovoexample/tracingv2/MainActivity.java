package com.lenovoexample.tracingv2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;


public class MainActivity extends AppCompatActivity {
    String correo, contraseña, repContraseña;
    private android.support.v4.app.FragmentManager fm;
    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            Bundle data = new Bundle();
            data.putString("correo", correo);

            switch (item.getItemId()) {
                case R.id.mHome:
                    PrimaryFragment primaryFragment = new PrimaryFragment();
                    ft.replace(R.id.frame, primaryFragment).commit();
                    return true;
                case R.id.mMapas:
                    SecundaryFragment secundaryFragment = new SecundaryFragment();
                    ft.replace(R.id.frame, secundaryFragment).commit();
                    return true;
                case R.id.mPerfil:
                    TertiaryFragment tertiaryFragment = new TertiaryFragment();
                    ft.replace(R.id.frame, tertiaryFragment).commit();
                    tertiaryFragment.setArguments(data);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle args = getIntent().getExtras();
        if(args!=null){                 //validacion
            correo = args.getString("correo");
            contraseña = args.getString("contraseña");
            repContraseña = args.getString("repContraseña");
        }
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        PrimaryFragment primaryFragment = new PrimaryFragment();
        ft.replace(R.id.frame, primaryFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == 1234 && resultCode == RESULT_OK){
            correo = data.getExtras().getString("correo");
            contraseña = data.getExtras().getString("contraseña");
            repContraseña = data.getExtras().getString("repContraseña");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //coloca menu
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch (id){
            case R.id.mSesion:
                intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("contraseña",contraseña);
                intent.putExtra("correo",correo);
                intent.putExtra("repContraseña",repContraseña);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                break;
        }
        //startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
