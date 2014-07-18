package org.eclipse.youdaofanyi.util;
 
public class CommonUtil {
	
	public static boolean isMacOSX() {
		String osName = System.getProperty("os.name");
		return osName.startsWith("Mac");
	}
	
}