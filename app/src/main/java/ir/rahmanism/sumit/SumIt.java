package ir.rahmanism.sumit;

/**
 * Created by Rahmani on 29/12/2015.
 */
public class SumIt {

    public int mark;
    public int correct;
    public int wrong;
    public int answeredCount;

    /// Calculates the percent of mark
    public int markPercent() {
        int p = 0;

        try {
            p = this.correct * 100 / this.answeredCount;
            p = ((int)(p * 100)) / 100;
        }
        catch (Exception x) {
            p = 0;
        }

        return p;
    }
}
