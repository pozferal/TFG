package layout;

/**
 * Created by Alvaro on 10/05/2017.
 */

public class ObjetoMenu {
    private String titulo;
    private Integer puntos;


    public ObjetoMenu(String titulo,Integer puntos ) {
        this.titulo = titulo;
        this.puntos=puntos;
    }



    public String getTitulo(){
        return titulo;
    }
    public Integer getPuntos(){
        return puntos;
    }
}
