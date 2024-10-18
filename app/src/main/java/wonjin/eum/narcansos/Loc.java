package wonjin.eum.narcansos;

import com.google.errorprone.annotations.Keep;

@Keep  public class Loc {

    double lan;
    double log;
    boolean stat;

    public Loc(double lani, double logi){
       lan = lani;
       log = logi;
       stat = false;
    }

    public String print(){
        return String.valueOf(lan) + " , " + String.valueOf(log);
    }
}
