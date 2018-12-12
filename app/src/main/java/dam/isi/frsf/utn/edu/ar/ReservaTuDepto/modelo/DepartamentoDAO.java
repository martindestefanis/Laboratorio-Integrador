package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DepartamentoDAO {
    @Query("SELECT * FROM Departamento")
    List<Departamento> getAll();

    @Insert
    long insert(Departamento depto);

    @Update
    void update(Departamento depto);

    @Delete
    void delete(Departamento depto);
}
