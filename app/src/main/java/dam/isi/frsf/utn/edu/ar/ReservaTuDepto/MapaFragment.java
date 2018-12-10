package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap miMapa;
    private int tipoMapa;

    public MapaFragment() {

    }
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        tipoMapa = 0;
        Bundle argumentos = getArguments();
        if(argumentos !=null) {
            tipoMapa = argumentos .getInt("tipo_mapa",0);
        }
        getMapAsync(this); return rootView;
    }
    @Override public void onMapReady(GoogleMap map) {
        miMapa = map;
    }

}
