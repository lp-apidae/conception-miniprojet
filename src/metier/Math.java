package metier;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class Math 
{

	public static String formatDouble(Double nombreDouble) 
	{
	    DecimalFormat decimalFormat = new DecimalFormat("0.00"); 
	    decimalFormat.setDecimalSeparatorAlwaysShown(true);
	    return decimalFormat.format(nombreDouble);
	}
	
	public static double arrondir(Double nombreDouble) 
	{
		 DecimalFormat decimalFormat = new DecimalFormat("0.00"); 
		 decimalFormat.setDecimalSeparatorAlwaysShown(false);
		 decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		 return Double.parseDouble(decimalFormat.format(nombreDouble).replaceAll(",", "."));
	}
}