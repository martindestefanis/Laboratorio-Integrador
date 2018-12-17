package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DepartamentoDAO {
    @Query("SELECT * FROM Departamento")
    List<Departamento> getAll();

    @Insert
    long insert(Departamento departamento);

    @Query("SELECT * FROM Departamento WHERE ID_DEPTO = :DeptoID")
    Departamento buscarPorID(Integer DeptoID);

    @Query("SELECT * FROM Departamento WHERE ciudad_ID_CIUDAD = :ID_CIUDAD")
    List<Departamento> buscarPorCiudad(Integer ID_CIUDAD);
}
