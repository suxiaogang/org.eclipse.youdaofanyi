package org.eclipse.youdaofanyi.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.youdaofanyi.Activator;

public class PreferencePageMain extends PreferencePage implements
		IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
	}

	public Control createContents(Composite parent) {
		Composite mainPanel = new Composite(parent, 0);
		GridLayout mainLayout = new GridLayout();
		mainLayout.marginWidth = 0;
		mainLayout.marginHeight = 0;
		mainLayout.marginLeft = 5;
		mainLayout.marginRight = 10;
		mainPanel.setLayout(mainLayout);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.heightHint = 150;
		gridData.widthHint = 200;
		gridData.horizontalSpan = 1;

		Label spacer = new Label(mainPanel, SWT.NONE | SWT.CENTER);
		spacer.setLayoutData(gridData);
		spacer.setImage(Activator.getImageDescriptor("/icon/logo.png").createImage());
		
		Label traceOptionsLabel = new Label(mainPanel, SWT.NONE);
		traceOptionsLabel.setText("如有疑问,请通过以下方式进行反馈:");
		
		Composite group = new Composite(mainPanel, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginTop = 0;
		layout.marginBottom = 5;
		layout.marginLeft = 0;
		layout.marginRight = 15;
		layout.horizontalSpacing = 3;
		layout.verticalSpacing = 10;
		group.setLayout(layout);
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(groupGridData);
		
		createImgLabel(group, "icon-weibo.png");
		Link weiboLink = new Link(group, SWT.NONE);
		weiboLink.setText("<a>新浪微博</a>");
		weiboLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferenceUtil.openURL("http://weibo.com/47660666");
			}
		});
		
		createImgLabel(group, "icon_github.png");
		Link linkGithub = new Link(group, SWT.NONE);
		linkGithub.setText("<a>Github</a>");
		linkGithub.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferenceUtil.openURL("http://Github.com/suxiaogang");
			}
		});
		
		createImgLabel(group, "icon_email.png");
		Link emailLink = new Link(group, SWT.NONE);
		emailLink.setText("<a>电子邮件</a>");
		emailLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				PreferenceUtil.openURL("mailto:me@suxiaogang.com");
			}
		});
		
		noDefaultAndApplyButton();
		return mainPanel;
	}

	public Label createImgLabel (Composite com,String iconPath) {
		Label label = new Label(com, SWT.NONE);
		label.setImage(Activator.getImageDescriptor("/icon/"+iconPath).createImage());
		return label;
	}
	
}
