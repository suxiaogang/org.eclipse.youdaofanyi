package org.eclipse.youdaofanyi.preference;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.eclipse.youdaofanyi.Activator;
import org.eclipse.youdaofanyi.ui.GlobalConstants;

public class PreferencePageConfig extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	private StringFieldEditor fe_api_id;
	private StringFieldEditor fe_api_key;

	private IPreferenceStore store;

	public PreferencePageConfig() {
		super(FieldEditorPreferencePage.GRID);
		store = Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
		this.setPreferenceStore(this.store);
	}

	@Override
	protected void createFieldEditors() {
		
		Composite composite = getFieldEditorParent();
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		fe_api_id = new StringFieldEditor(GlobalConstants.KEY_ID, "API用户名:", composite);
		addField(fe_api_id);
		fe_api_key = new StringFieldEditor(GlobalConstants.KEY_VALUE, "API Key:", composite);
		
		addField(fe_api_key);
		if(store!=null){
			fe_api_id.setPreferenceStore(store);
			fe_api_id.load();
			fe_api_key.setPreferenceStore(store);
			fe_api_key.load();
		}
		
		// ***GROUP START***//
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		Composite infoComposite = new Composite(composite, SWT.NONE);
		infoComposite.setLayout(new GridLayout(1, true));
		infoComposite.setLayoutData(gridData);
		Group infoGroup = new Group(infoComposite, SWT.NONE);
		infoGroup.setText("提示");
		GridLayout groupLayout = new GridLayout(1, false);
		groupLayout.marginBottom = 5;
		groupLayout.marginTop = 5;
		groupLayout.marginLeft = 10;
		groupLayout.marginRight = 10;
		infoGroup.setLayout(groupLayout);
		infoGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		infoGroup.pack();
		
		Link openBroswerLink = new Link(infoGroup, SWT.NONE);
		openBroswerLink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		openBroswerLink.setText("1. <a>API key申请地址</a>\n\n2. 使用API key时，请求频率限制为每小时1000次，超过限制会被封禁");
		openBroswerLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					open(new URL("http://fanyi.youdao.com/openapi?path=data-mode"));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	@Override
	public boolean performOk() {
		if (fe_api_id.getStringValue().isEmpty()) {
			MessageDialog.openError(null, "提示", "API用户名不能为空!");
			return false;
		} else if(fe_api_key.getStringValue().isEmpty()){
			MessageDialog.openError(null, "提示", "API Key不能为空!");
			return false;
		} else{
			fe_api_id.store();
			fe_api_key.store();
			store.setValue(GlobalConstants.KEY_ID, fe_api_id.getStringValue());
			store.setValue(GlobalConstants.KEY_VALUE, fe_api_key.getStringValue());
			if (store instanceof ScopedPreferenceStore) {
				ScopedPreferenceStore scopedStore = (ScopedPreferenceStore) store;
				try {
					scopedStore.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return super.performOk();
	}
	@Override
	protected void performApply() {
		performOk();
		super.performApply();
	}

	public void open(URL url) {
		IWorkbenchBrowserSupport browserSupport= PlatformUI.getWorkbench().getBrowserSupport();
		IWebBrowser browser;
		try {
			browser = browserSupport.getExternalBrowser();
			browser.openURL(url);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
}
