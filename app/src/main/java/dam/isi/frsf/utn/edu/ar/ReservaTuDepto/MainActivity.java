package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AltaDepartamentoFragment.OnNuevoLugarListener, MapaFragment.OnMapaListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FormularioBusquedaFragment fragmentInicio = new FormularioBusquedaFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragmentInicio)
                .commit();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        String tag = "";
        int id = item.getItemId();
        switch (id){
            case R.id.nav_deptos:
                tag = "listaDepartamentos";
                fragment =  getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment==null) {
                    fragment = new ListaDepartamentosFragment();
                }
                fragmentTransaction = true;
                break;
            case R.id.nav_alta_deptos:
                tag = "altaDepartamentoFragment";
                fragment =  getSupportFragmentManager().findFragmentByTag(tag);
                if(fragment==null) {
                    fragment = new AltaDepartamentoFragment();
                    ((AltaDepartamentoFragment) fragment).setListener(MainActivity.this);
                }
                fragmentTransaction = true;
                break;
            case R.id.nav_perfil:
                break;
            case R.id.nav_reservas:
                break;
        }

        if(fragmentTransaction) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenido, fragment,tag)
                    .addToBackStack(null)
                    .commit();

            item.setChecked(true);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void coordenadasSeleccionadas(LatLng c) {
        String tag = "altaDepartamentoFragment";
        Fragment fragment =  getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment==null) {
            fragment = new AltaDepartamentoFragment();
            ((AltaDepartamentoFragment) fragment).setListener(MainActivity.this);
        }
        Bundle bundle = new Bundle();
        bundle.putString("latLng",c.latitude+";"+c.longitude);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragment,tag)
                .commit();
    }

    @Override
    public void obtenerCoordenadas() {
        String tag="mapaDepartamentos";
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment==null) {
            fragment = new MapaFragment();
            ((MapaFragment) fragment).setListener(MainActivity.this);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("tipo_mapa", 1);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, fragment,tag)
                .addToBackStack(null)
                .commit();
    }
}
