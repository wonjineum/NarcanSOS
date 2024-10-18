package wonjin.eum.narcansos;

public class Counter {

 int cnt;

 public Counter (int c){
     cnt = c;
 }

    public Counter (){
    }

    public int getCnt() {
        return cnt;
    }

    public void updateCnt(){
     cnt += 1;

    }
}
