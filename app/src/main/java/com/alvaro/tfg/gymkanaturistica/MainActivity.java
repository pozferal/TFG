package com.alvaro.tfg.gymkanaturistica;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alvaro.tfg.gymkanaturistica.db.DatosRuta;
import com.alvaro.tfg.gymkanaturistica.db.GymkanaDB;
import com.alvaro.tfg.gymkanaturistica.db.OperacionesBD;
import com.google.android.gms.maps.SupportMapFragment;

import layout.AcercaDe;
import layout.Descargar;
import layout.Puntuaciones;
import layout.Rumbo;
import layout.Seleccionar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Descargar.OnFragmentInteractionListener,
        Rumbo.OnFragmentInteractionListener,
        Seleccionar.OnFragmentInteractionListener,
        Puntuaciones.OnFragmentInteractionListener,
        AcercaDe.OnFragmentInteractionListener{



    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="GymkanaTuristica.db";
    public static final String TAG="GymkanaTuristica";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);


        View decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        OperacionesBD db = OperacionesBD.obtenerInstancia(this.getApplicationContext());

        if (db.hayRutas()){

            if (db.getRutaActiva()!=null){
                Rumbo fragmentoRumbo = new Rumbo();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoRumbo).commit();

            }else {

                Seleccionar fragmentoSeleccionar = new Seleccionar();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoSeleccionar).commit();
            }

        }else{

            Toast.makeText(this, "No existen datos de rutas. \n Importe datos desde el menú para comenzar ", Toast.LENGTH_LONG).show();
            Descargar fragmentoDescargar = new Descargar();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoDescargar).commit();

        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Descargar fragmentoDescargar=null;
        Rumbo fragmentoRumbo=null;
        Seleccionar fragmentoSeleccionar=null;
        Puntuaciones fragmentoPuntuaciones=null;
        AcercaDe fragmentoAcercaDe=null;
        GymkanaDB conexionBD=new GymkanaDB(this,DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase basedatos=conexionBD.getWritableDatabase();

        if (id == R.id.nav_importar) {
            fragmentoDescargar = new Descargar();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoDescargar).commit();


        } else if (id == R.id.nav_escoger) {
            fragmentoSeleccionar=new Seleccionar();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos,fragmentoSeleccionar).commit();

        } else if (id == R.id.nav_puntuaciones) {
            fragmentoPuntuaciones=new Puntuaciones();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos,fragmentoPuntuaciones).commit();


        } else if (id == R.id.nav_acercade) {
            fragmentoAcercaDe=new AcercaDe();
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos,fragmentoAcercaDe).commit();



        } else if (id == R.id.nav_rumbo) {

            String sentencia="SELECT * FROM RUTAS WHERE ACTIVA=1";
            Cursor result2=basedatos.rawQuery(sentencia,null);

            if (result2.moveToFirst()){
                 fragmentoRumbo = new Rumbo();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoRumbo).commit();

            }else {

                 fragmentoSeleccionar = new Seleccionar();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenidoFragmentos, fragmentoSeleccionar).commit();
            }





        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        basedatos.close();
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }else {


            if (keyCode == KeyEvent.KEYCODE_BACK) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Pulse aceptar si desea salir de la aplicación Gymkana Turística")
                        .setTitle("¿Desea salir?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.exit(0);
                                    }
                                }
                        );
                builder.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }




}
