package ir.rahmanism.general;

/**
 * Created by Rahmani on 08/01/2016.
 */
public class MyGeneral {

    /// Converts string to int if exception happend returns -1
    public static int ParseInt(String str) {
        int result = -1;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = -1;
        }
        return result;
    }

}
