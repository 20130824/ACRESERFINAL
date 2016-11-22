/**
 * Created by Dany on 21/11/2016.
 */
public enum TipoCiclo {
    JOVENES(1), ADULTOS(2);
    private final int value;

    TipoCiclo(int value){
        this. value = value;
    }
    public int getTipoCicloValue(){
        return  this.value;
    }
}
