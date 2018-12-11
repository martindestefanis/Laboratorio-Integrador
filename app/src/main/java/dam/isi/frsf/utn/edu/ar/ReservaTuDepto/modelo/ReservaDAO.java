package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ReservaDAO {
    @Query("SELECT * FROM Reserva")
    List<Reserva> getAll();

    @Insert
    long insert(Reserva r);

    @Insert
    void update(Reserva r);

    @Delete
    void delete(Reserva r);
}
