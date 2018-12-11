package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CiudadDAO {
    @Query("SELECT * FROM Ciudad")
    List<Ciudad> getAll();

    @Insert
    long insert(Ciudad r);

    @Insert
    void update(Ciudad r);

    @Delete
    void delete(Ciudad r);
}
