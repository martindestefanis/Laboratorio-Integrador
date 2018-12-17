package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class ConfiguracionActivity extends AppCompatActivity {

    public static class ConfiguracionFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            setPreferencesFromResource(R.xml.configuracion_ui, s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Configuración");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new ConfiguracionFragment())
                .commit();
    }
}