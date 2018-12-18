package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
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

    @Query("SELECT * FROM Reserva WHERE ID_RESERVA = :ReservaID")
    List<Reserva> buscarPorIDLista(Integer ReservaID);

    @Query("SELECT * FROM Reserva WHERE depto_ID_DEPTO = :DeptoID")
    List<Reserva> buscarPorDepto(Integer DeptoID);

    @Insert
    long insert(Reserva reserva);

    @Update
    void update(Reserva reserva);
}
