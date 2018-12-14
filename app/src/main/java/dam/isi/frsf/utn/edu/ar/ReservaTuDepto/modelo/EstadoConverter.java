package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.TypeConverter;

public class EstadoConverter {

        @TypeConverter
        public static Reserva.Estado toEstado(String status) {
            return Reserva.Estado.valueOf(status);
        }
        @TypeConverter
        public static String toString(Reserva.Estado status) {
            return status.toString();
        }

}
