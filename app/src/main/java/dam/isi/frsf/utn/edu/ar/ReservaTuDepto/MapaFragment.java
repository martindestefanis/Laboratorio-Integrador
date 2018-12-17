package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.AppDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Departamento;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.DepartamentoDAO;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap miMapa;
    private int tipoMapa = 0;
    private int idDepto = 0;
    private int idCiudad = 0;
    private OnMapaListener listener;
    private DepartamentoDAO departamentoDAO;
    private List<Departamento> listaDepartamento;

    public MapaFragment() {

    }

    public void setListener(OnMapaListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        Bundle argumentos = getArguments();
        if(argumentos != null) {
            tipoMapa = argumentos .getInt("tipo_mapa",0);
            idDepto = argumentos.getInt("idDepto",0);
            idCiudad = argumentos.getInt("idCiudad",0);
        }
        getMapAsync(this);
        return rootView;
    }

    @Override public void onMapReady(GoogleMap map) {
        miMapa = map;
        actualizarMapa();

        switch (tipoMapa){
            case 1:
                miMapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        listener.coordenadasSeleccionadas(latLng);
                    }
                });
                break;
            case 2:
                cargarMapaConUnDepto(idDepto);
                break;
            case 3:
                cargarMapaConCiudadDepartamentos(idCiudad);
                break;
        }
    }

    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        miMapa.setMyLocationEnabled(true);
    }

    public interface OnMapaListener {
        public void coordenadasSeleccionadas(LatLng c);
    }

    public void cargarMapaConUnDepto(final int idDepto){
        departamentoDAO = MyDatabase.getInstance(getContext()).getDepartamentoDAO();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final Departamento unDepto = departamentoDAO.buscarPorID(idDepto);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        miMapa.addMarker(new MarkerOptions()
                                .position(new LatLng(unDepto.getLatitud(),unDepto.getLongitud()))
                                .title("[" + unDepto.getDescripcion() + "]")
                                .snippet(unDepto.getDireccion()));
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(unDepto.getLatitud(),unDepto.getLongitud()))
                                .zoom(15)
                                .build();
                        miMapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void cargarMapaConCiudadDepartamentos(final int idCiudad){
        departamentoDAO = MyDatabase.getInstance(getContext()).getDepartamentoDAO();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                listaDepartamento = departamentoDAO.buscarPorCiudad(idCiudad);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for(Departamento d : listaDepartamento){
                            Marker marker = miMapa.addMarker(new MarkerOptions()
                                    .position(new LatLng(d.getLatitud(),d.getLongitud()))
                                    .title("[" + d.getDescripcion() + "]")
                                    .snippet(d.getDireccion()));
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds LIMITE = builder.build();
                        miMapa.moveCamera(CameraUpdateFactory.newLatLngBounds(LIMITE, 10));
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }


}
