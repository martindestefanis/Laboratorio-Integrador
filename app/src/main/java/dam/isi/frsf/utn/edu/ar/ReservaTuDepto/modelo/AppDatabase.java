package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Ciudad.class, Departamento.class, Reserva.class, Usuario.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CiudadDAO ciudadDAO();
    public abstract DepartamentoDAO departamentoDAO();
    public abstract ReservaDAO reservaDAO();
    public abstract UsuarioDAO usuarioDAO();
}
