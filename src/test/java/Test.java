import org.apache.commons.lang3.time.DateUtils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {

        Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
        String now = new SimpleDateFormat("yyyy年MM月dd日").format(dat);

        System.out.println(now);
        String deliverTime="2021 02-25 16:58:58";
        //Date date= new SimpleDateFormat("yyyy MM-dd hh:mm:ss").parse("2021 02-25 16:58");
        Date date=DateUtils.parseDate("2021-02-25 16:58:22","yyyy-MM-dd hh:mm:ss");
        String res=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        System.out.println(res);
    }
}
