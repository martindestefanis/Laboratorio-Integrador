package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Room;
import android.content.Context;

public class MyDatabase {
    private static MyDatabase _INSTANCIA_UNICA=null;

    public static MyDatabase getInstance(Context ctx){
        if(_INSTANCIA_UNICA==null) _INSTANCIA_UNICA = new MyDatabase(ctx);
        return _INSTANCIA_UNICA;
    }

    private AppDatabase db;
    private CiudadDAO ciudadDAO;
    private DepartamentoDAO departamentoDAO;
    private ReservaDAO reservaDAO;
    private UsuarioDAO usuarioDAO;

    private MyDatabase(Context ctx){
        db = Room.databaseBuilder(ctx,
                AppDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .build();
        ciudadDAO = db.ciudadDAO();
        departamentoDAO = db.departamentoDAO();
        reservaDAO = db.reservaDAO();
        usuarioDAO = db.usuarioDAO();
    }

    public CiudadDAO getCiudadDAO() {
        return ciudadDAO;
    }

    public void setCiudadDAO(CiudadDAO ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }

    public DepartamentoDAO getDepartamentoDAO() {
        return departamentoDAO;
    }

    public void setDepartamentoDAO(DepartamentoDAO departamentoDAO) {
        this.departamentoDAO = departamentoDAO;
    }

    public ReservaDAO getReservaDAO() {
        return reservaDAO;
    }

    public void setReservaDAO(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
}