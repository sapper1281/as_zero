/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.vivc.aszero.beans.pagebean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author apopovkin
 */
@ManagedBean
@RequestScoped
public class test {

    
    public String getDateFRIDAY() {
        
    TimeZone ts = TimeZone.getDefault();
		DateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm");
		Date now = new Date();
		System.out.println(ts.getDisplayName());
		System.out.println(format.format(now));
        
        
    
    Calendar cal = new GregorianCalendar();
    System.out.printf("Local time: %04d-%02d-%02d %02d:%02d:%02d\n", cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));

    cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Moscow"));
    System.out.printf("Moscow time: %04d-%02d-%02d %02d:%02d:%02d\n", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));

    cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    System.out.printf("UTC time: %04d-%02d-%02d %02d:%02d:%03d\n", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));    


        
        
    Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
 c.setTime(new java.util.Date());
 int get = c.get(java.util.Calendar.YEAR);
      SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssz");
       // System.out.println(dateFormatter2.format(c.getTime()));
    return dateFormatter2.format(c.getTime());
            }
    /**
     * Creates a new instance of test
     */
    public test() {
    }
}
