import org.apache.tomcat.jni.Time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {


    public static void  Main(String[] arg){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse("23/04/2021 19:40:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime();

        System.out.println(new Date().getTime());
        System.out.println(time);
    }
}
