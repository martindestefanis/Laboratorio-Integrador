package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM Usuario")
    List<Usuario> getAll();

    @Query("SELECT * FROM USUARIO WHERE ID_USUARIO = :id")
    Usuario buscarPorID(long id);

    @Query("SELECT * FROM USUARIO WHERE correo = :correo AND nombre = :nombre")
    Usuario buscarPorCorreoNombre(String correo, String nombre);

    @Query("SELECT * FROM Usuario WHERE correo = :correo AND nombre = :nombre")
    UsuarioConReservas buscarUsuarioConReservas(String correo, String nombre);

    @Insert
    long insert(Usuario usuario);
}
