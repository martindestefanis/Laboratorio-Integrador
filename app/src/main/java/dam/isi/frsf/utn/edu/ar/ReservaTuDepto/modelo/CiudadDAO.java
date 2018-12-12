package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CiudadDAO {
    @Query("SELECT * FROM Ciudad")
    List<Ciudad> getAll();

    @Query("SELECT * FROM Ciudad WHERE ID_CIUDAD = :CiudadID")
    Ciudad buscarPorID(long CiudadID);

    @Insert
    long insert(Ciudad ciudad);

    @Update
    void update(Ciudad ciudad);

    @Delete
    void delete(Ciudad ciudad);
}
