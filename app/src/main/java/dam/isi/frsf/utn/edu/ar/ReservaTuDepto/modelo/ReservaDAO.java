package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReservaDAO {
    @Query("SELECT * FROM Reserva")
    List<Reserva> getAll();

    @Query("SELECT * FROM Reserva WHERE ID_RESERVA = :ReservaID")
    Reserva buscarPorID(Integer ReservaID);

    @Insert
    long insert(Reserva r);

    @Update
    void update(Reserva r);

    @Delete
    void delete(Reserva r);
}
