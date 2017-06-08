package layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.tfg.gymkanaturistica.MainActivity;
import com.alvaro.tfg.gymkanaturistica.R;
import com.alvaro.tfg.gymkanaturistica.db.DatosRuta;

import java.util.List;

/**
 * Created by Alvaro on 29/04/2017.
 */

public class AdaptadorLista  extends ArrayAdapter{

    Activity context;
    List datos;
    DatosRuta objRuta;

    public AdaptadorLista(Activity context, List datos) {
        super(context, R.layout.list_item_rutas, datos);
        this.context=context;
        this.datos=datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View row= inflater.inflate(R.layout.list_item_rutas, parent, false);
        this.setNotifyOnChange(true);

        objRuta=(DatosRuta)datos.get(position);
        TextView lblTitulo=(TextView)row.findViewById(R.id.titulo);
        lblTitulo.setText(objRuta.getDescripcion());

        ImageView imgCheck=(ImageView)row.findViewById(R.id.imgCheck);
        if (objRuta.isActiva()){
            imgCheck.setVisibility(View.VISIBLE);
        }else{
            imgCheck.setVisibility(View.INVISIBLE);
        }


        ImageView imgTipoRuta=(ImageView) row.findViewById(R.id.imgTipoRuta);
        Integer tipoRuta=objRuta.getTipo_Ruta_id();
        if (tipoRuta !=null) {
            if (tipoRuta == 1) {
                imgTipoRuta.setImageResource(R.drawable.ic_coche);
            } else if (tipoRuta == 2) {
                imgTipoRuta.setImageResource(R.drawable.ic_andando);
            } else if (tipoRuta == 3) {
                imgTipoRuta.setImageResource(R.drawable.ic_bici);
            }
        }

        TextView txtDuracion=(TextView)row.findViewById(R.id.txtDuracion);
        txtDuracion.setText(objRuta.getDuracion());

        TextView txtDistancia=(TextView)row.findViewById(R.id.txtDistancia);
        txtDistancia.setText(" - " + objRuta.getDistancia());


        /*
        TextView txtPuntos=(TextView)row.findViewById(R.id.txtDistancia);
        if (txtPuntos!=null) txtPuntos.setText(objRuta.getPuntuacion()+" Ptos.");*/

        return(row);
    }
}
