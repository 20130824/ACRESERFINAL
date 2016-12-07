/**
 * Created by Dany on 07/12/2016.
 */
public class OtherVolunteer {
    private TipoVoluntario tipoVoluntario;
    private Participante participante;
    private Voluntario voluntario;



    public OtherVolunteer(TipoVoluntario tipoVoluntario, Participante participante, Voluntario voluntario) {
        this.tipoVoluntario = tipoVoluntario;
        this.participante = participante;
        this.voluntario = voluntario;
    }

    public TipoVoluntario getTipoVoluntario() {
        return tipoVoluntario;
    }

    public void setTipoVoluntario(TipoVoluntario tipoVoluntario) {
        this.tipoVoluntario = tipoVoluntario;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(Voluntario voluntario) {
        this.voluntario = voluntario;
    }
}
