package mainclass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import constant.FilePaths;
import constant.StringConstant;
import utility.HttpDownloadUtility;


public class WeatherInfo {
	
	
	
	public static void main(String[] args)
	{
		for(int i=0;i<StringConstant.CATEGORY.length;i++)
		{
			for(int j=0;j<StringConstant.REGIONS.length;j++)
			{
				String downloadUrl=StringConstant.BASE_URL+StringConstant.CATEGORY[i]+"/ranked/"+StringConstant.REGIONS[j]+".txt";
				  final int indexi = i;
				  final int indexj=j;
				  WeatherInfo obj= new WeatherInfo();
					new Thread()
					{
					    public void run() {
					    	try {
					    		int cateIndex=indexi;
					    		int regionIndex=indexj;
								HttpDownloadUtility.downloadFile(downloadUrl, FilePaths.inputDirPath(),StringConstant.genrateFilename(StringConstant.CATEGORY[cateIndex], StringConstant.REGIONS[regionIndex]));
								System.out.println(FilePaths.inputDirPath()+"\\"+StringConstant.genrateFilename(StringConstant.CATEGORY[cateIndex], StringConstant.REGIONS[regionIndex]));
								
								if(new File(FilePaths.inputDirPath()+"\\"+StringConstant.genrateFilename(StringConstant.CATEGORY[cateIndex], StringConstant.REGIONS[regionIndex])).exists()) {
									obj.readtxtFile(FilePaths.inputDirPath()+"\\"+StringConstant.genrateFilename(StringConstant.CATEGORY[cateIndex], StringConstant.REGIONS[regionIndex]),cateIndex,StringConstant.REGIONS[regionIndex]);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    }
					}.start();
					
			}
		}
	}
	
	public void readtxtFile(String filepath,int weatherparam,String region)
	{
		System.out.println("start readgin file");
		BufferedReader br = null;
		String everything=null;
		try {
			br = new BufferedReader(new FileReader(filepath));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		   everything = sb.toString();
		   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String data[]=everything.split("\n");
		int[] headercount=headerOpsitionCount(data[7]);
		for(int i=8;i<(data.length);i++)
		{
			int beginIndex=0;
			for(int j=0;j<(headercount.length);j++)
			{
				    if(headercount[j]<data[i].length())
				    {
				    if(!data[i].substring(beginIndex, headercount[j]).trim().isEmpty())	{
				    	//System.out.println(region+","+StringConstant.weatherParam[weatherparam]+","+data[i].substring(beginIndex, headercount[j]).trim().split("\\s+")[1]+","+StringConstant.key[j]+","+data[i].substring(beginIndex, headercount[j]).trim().split("\\s+")[0]);
				    	wrtieToCsvFile(region, StringConstant.weatherParam[weatherparam], data[i].substring(beginIndex, headercount[j]).trim().split("\\s+")[1], StringConstant.key[j], data[i].substring(beginIndex, headercount[j]).trim().split("\\s+")[0]);
				    }
				     else
				     {
				    	//System.out.println(region+","+StringConstant.weatherParam[weatherparam]+","+"N/A"+","+StringConstant.key[j]+","+"N/A");
				    	wrtieToCsvFile(region, StringConstant.weatherParam[weatherparam], "N/A", StringConstant.key[j], "N/A");
				     }
				    }
				    
					beginIndex=headercount[j];
			}
		}
		
	}
	
	public int[] headerOpsitionCount(String headerStr)
	{
		String header=headerStr ;	
	    int i=0;
	    int[] headercount = new int[17] ;
	    int startindex=0;
		while(i<17)
		{
			if(i==0)
			{
				headercount[i]=(header.indexOf("Year"))+4;
			}
			else
			{
				headercount[i]=((header.indexOf("Year"))+4)+headercount[i-1];
			}
			
			startindex=(header.indexOf("Year"))+4;
			i++;
			header=header.substring(startindex, (header.length()));
		}
		
		return headercount;
	}
	
	public void wrtieToCsvFile(String regionCode, String weatherParam, String year, String key, String value)
	{
		final String COMMA_DELIMITER =",";
		final String NEW_LINE_SEPARATOR = "\n";
		final String FILE_NAME="weather.csv";
		//CSV file header
		final String FILE_HEADER="region_code,weather_param,year,key,value";
		FileWriter fileWriter = null;
		String  filePath=FilePaths.outputDirPath()+"\\"+FILE_NAME;
		Boolean alreadyExist=new File(filePath).exists();
		File filename= new File(filePath);
		try {
			fileWriter = new FileWriter(filename,true);
			if(!alreadyExist)
			{
				fileWriter.append(FILE_HEADER.toString());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			    fileWriter.append(regionCode);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(weatherParam);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(year);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(key);
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(value);
				fileWriter.append(NEW_LINE_SEPARATOR);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
		
	}

}
