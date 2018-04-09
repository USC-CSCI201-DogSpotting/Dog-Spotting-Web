package csci201;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Util {
	public static long getCurrDate() {
		Date date = new Date(0);
		DateFormat year = new SimpleDateFormat("yyyy");
		int retVal = Integer.parseInt(year.format(date));
		retVal *= 10000;
		DateFormat month = new SimpleDateFormat("mm");
		retVal += Integer.parseInt(month.format(date)) * 100;
		DateFormat day = new SimpleDateFormat("dd");
		retVal += Integer.parseInt(day.format(date));
		System.out.println(retVal);
		return retVal;
	}
}
