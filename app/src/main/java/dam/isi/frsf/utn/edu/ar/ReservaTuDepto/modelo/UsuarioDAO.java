package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Insert
    long insert(Usuario r);

    @Insert
    void update(Usuario r);

    @Delete
    void delete(Usuario r);
}
