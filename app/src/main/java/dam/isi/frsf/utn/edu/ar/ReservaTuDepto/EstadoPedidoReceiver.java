package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.ReservaDAO;

public class EstadoPedidoReceiver extends BroadcastReceiver {

    public static final String ESTADO_CONFIRMADO="dam.isi.frsf.utn.edu.ar.ReservaTuDepto.ESTADO_CONFIRMADO";
    public static final String ESTADO_PENDIENTE="dam.isi.frsf.utn.edu.ar.ReservaTuDepto.PENDIENTE";

        private ReservaDAO reservaDAO;

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");

            reservaDAO = MyDatabase.getInstance(context).getReservaDAO();

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Reserva reserva = reservaDAO.buscarPorID(intent.getExtras().getInt("idReserva"));
                    if (intent.getAction().equals(ESTADO_CONFIRMADO)) {
                        //Toast.makeText(context,"Reserva para " + reserva.getUsuario + " ha cambiado de estado a " + reserva.getEstado() ,Toast.LENGTH_LONG).show();
                        Intent destino = new Intent(context, AltaReservaFragment.class);
                        destino.putExtra("idReservaSeleccionado", reserva.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, destino, PendingIntent.FLAG_UPDATE_CURRENT);

                        Notification notification = new NotificationCompat.Builder(context, "CANAL01")
                                //Ver icono
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Tu reserva nro: " + reserva.getId() + " fue confirmada")
                                .setStyle(new NotificationCompat.InboxStyle()
                                        .addLine("Desde: " + dt.format(reserva.getFechaInicio()))
                                        .addLine("Hasta: " + dt.format(reserva.getFechaFin())))
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build();

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(reserva.getId(), notification);
                    }

                    if(intent.getAction().equals(ESTADO_PENDIENTE)){
                        Intent destino= new Intent(context,AltaReservaFragment.class);
                        destino.putExtra("idReservaSeleccionada",reserva.getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,destino,PendingIntent.FLAG_UPDATE_CURRENT);

                        Notification notification = new NotificationCompat.Builder(context,"CANAL01")
                                //Ver icono
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("Tu reserva esta pendiente")
                                .setStyle(new NotificationCompat.InboxStyle()
                                        .addLine("Espere un momento hasta que se confirme,")
                                        .addLine("su numero de reserva es " + reserva.getId() + "."))
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build();

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(reserva.getId(), notification);
                    }
                }
            };
                    Thread t = new Thread(r);
                    t.start();
    }
}
