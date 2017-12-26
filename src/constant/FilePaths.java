package constant;

import java.io.File;

public class FilePaths {
	
	public static String inputDirPath()
	{
		 String home = System.getProperty("user.home");
		 File files = new File(home+"\\KisanHub\\Inputfiles");
		    if (!files.exists()) {
		        if (files.mkdirs()) {
		            System.out.println("sub directories created successfully");
		        } else {
		            System.out.println("failed to create sub directories");
		        }
		    }
		return files.getAbsolutePath();
	}
	
	public static String outputDirPath()
	{
		String home = System.getProperty("user.home");
		File files = new File(home+"\\KisanHub\\Outputfiles");
	    if (!files.exists()) {
	        if (files.mkdirs()) {
	            System.out.println("sub directories created successfully");
	        } else {
	            System.out.println("failed to create sub directories");
	        }
	    }
	return files.getAbsolutePath();
	}

}
