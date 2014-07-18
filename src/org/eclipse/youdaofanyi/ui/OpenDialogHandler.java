package org.eclipse.youdaofanyi.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.youdaofanyi.preference.PreferenceUtil;

public class OpenDialogHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String apiKey = PreferenceUtil.getAPIkey();
		if(apiKey.isEmpty()){
			APIHintDialog dialog = new APIHintDialog(Display.getCurrent().getActiveShell());
			dialog.open();
			return null;
		}
		QueryDialog dialog = new QueryDialog(Display.getCurrent().getActiveShell());
		dialog.open();
		return null;
	}
	
}