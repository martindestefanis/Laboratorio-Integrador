package dam.isi.frsf.utn.edu.ar.ReservaTuDepto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.MyDatabase;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Reserva;
import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.ReservaDAO;

public class DptoMessagingService extends FirebaseMessagingService {
    private static final String LOGTAG = "android-fcm";
    private Reserva r;
    private ReservaDAO reservaDAO;
    public DptoMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //repositorioPedido = new PedidoRepository();
        reservaDAO = MyDatabase.getInstance(this).getReservaDAO();
        r = new Reserva();
        if (remoteMessage.getNotification() != null) {
            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();
            int pedidoId = Integer.parseInt(titulo);
            r = reservaDAO.buscarPorID(pedidoId);
            if(texto.equals("listo")) {
                r.setEstado(Reserva.Estado.CONFIRMADO);
                reservaDAO.update(r);
                Intent i = new Intent(getApplicationContext(),EstadoPedidoReceiver.class);
                i.setAction(EstadoPedidoReceiver.ESTADO_CONFIRMADO);
                i.putExtra("idReserva",r.getId());
                getApplicationContext().sendBroadcast(i);
            }
        }
    }

    /*@Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
