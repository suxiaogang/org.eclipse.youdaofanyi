package org.eclipse.youdaofanyi.preference;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.youdaofanyi.Activator;
import org.eclipse.youdaofanyi.ui.GlobalConstants;

public class PreferenceUtil {
	
	public static String getAPIkey() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String key = store.getString(GlobalConstants.KEY_ID);
		String value = store.getString(GlobalConstants.KEY_VALUE);
		if (!key.trim().isEmpty() && !value.trim().isEmpty()){
			return key + "&key=" + value;//Sam-Su&key=1825873072
		} else {
			return "";
		}
	}

	public static void openURL(String url) {
		IWorkbenchBrowserSupport browserSupport= PlatformUI.getWorkbench().getBrowserSupport();
		IWebBrowser browser;
		try {
			browser = browserSupport.getExternalBrowser();
			browser.openURL(new URL(url));
		} catch (PartInitException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void openPreferenceDialog(String preferenceID) {
		//"org.eclipse.youdaofanyi.preferencePageOfConfig"
		//""
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, preferenceID, null, null);
		dialog.open();
	}
}
