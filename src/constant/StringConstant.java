package constant;

public class StringConstant {
	
	public static final String BASE_URL="https://www.metoffice.gov.uk/pub/data/weather/uk/climate/datasets/";
	public static final String[] CATEGORY= {"Tmax","Tmin","Tmean","Sunshine","Rainfall"};
	public static final String[] weatherParam= {"Max temp","Min temp","Mean temp","Sunshine","Rainfall"};
	public static final String[] key= {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC","WIN","SPR","SUM","AUT","ANN"};
	public static final String[] REGIONS= {"UK","England","Wales","Scotland"};
	public static final String genrateFilename(String category,String region)
	{
		return category+"_"+region+".txt";
	}
	 

}
