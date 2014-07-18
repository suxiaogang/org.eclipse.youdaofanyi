package org.eclipse.youdaofanyi.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.youdaofanyi.Activator;
import org.eclipse.youdaofanyi.preference.PreferenceUtil;

public class APIHintDialog extends Dialog{

	protected APIHintDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Shell shell = this.getShell();
		shell.setSize(300, 80);
		Monitor primary = shell.getMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2 - 50;
		shell.setText("有道翻译");
		shell.setLocation(x, y);
		shell.setImage(Activator.getImageDescriptor("/icon/icon_16x16.png").createImage());
		//
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginBottom = 10;
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//
		Link weiboLink = new Link(composite, SWT.NONE);
		weiboLink.setText("请先去<a>首选项</a>中配置API key");
		weiboLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				close();
				PreferenceUtil.openPreferenceDialog("org.eclipse.youdaofanyi.preferencePageOfConfig");
			}
		});
		return super.createContents(parent);
	}
	
	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
	    if (id == IDialogConstants.CANCEL_ID || id == IDialogConstants.OK_ID) {
	    	return null;
	    }
	    return super.createButton(parent, id, label, defaultButton);
	}

}
