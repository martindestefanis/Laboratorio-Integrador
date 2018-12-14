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

    @Query("SELECT * FROM USUARIO WHERE correo = :correo AND nombre = :nombre")
    Usuario buscarPorCorreoNombre(String correo, String nombre);

    @Query("SELECT * FROM Usuario WHERE ID_USUARIO = :UsuarioID")
    UsuarioConReservas buscarPorIDConReserva(Integer UsuarioID);

    @Query("SELECT * FROM Usuario")
    List<UsuarioConReservas> buscarUsuarioConReservas();

    @Insert
    long insert(Usuario r);

    @Insert
    void update(Usuario r);

    @Delete
    void delete(Usuario r);
}
