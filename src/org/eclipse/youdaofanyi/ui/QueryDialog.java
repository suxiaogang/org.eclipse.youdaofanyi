package org.eclipse.youdaofanyi.ui;
 
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.youdaofanyi.Activator;
import org.eclipse.youdaofanyi.dict.DictionaryUtil;
import org.eclipse.youdaofanyi.httpclient.HttpClientUtil;
import org.eclipse.youdaofanyi.model.ResultModel;
import org.eclipse.youdaofanyi.preference.PreferenceUtil;
import org.eclipse.youdaofanyi.util.CommonUtil;
import org.jdom.Document;
 
public class QueryDialog extends Dialog{
	
	private Text queryText;
	private Text resultTextText;
	private Button queryButton;
	private Button addToDictButton;
	private Button dictManageButton;
	
    private Group infoGroup;
    
    private String word = "";
    private String trans = "";
    private String phonetic = "";
 
	protected QueryDialog(Shell parentShell) {
		super(parentShell);
	}
 
	/**
	 * init for OS Windows
	 */
	private void initForWindows(Composite parent){
 
		Shell shell = this.getShell();
		shell.setSize(450, 300);
		Monitor primary = shell.getMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2 - 50;
		shell.setText("有道翻译");
		shell.setLocation(x, y);
		shell.setImage(Activator.getImageDescriptor("/icon/icon_16x16.png").createImage());
		/* 布局开始 */
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginBottom = 10;
		layout.marginTop = 10;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		/* headerComposite... */
		Composite headerComposite = new Composite(composite, SWT.NONE);
		headerComposite.setLayout(new GridLayout(3, false));
		headerComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(headerComposite, SWT.NONE).setText("请输入:");
		
		GridData quertTextGD = new GridData(GridData.FILL_HORIZONTAL);
		quertTextGD.horizontalSpan = 1;
		quertTextGD.heightHint = 14;
		quertTextGD.widthHint = 300;
		queryText = new Text(headerComposite, SWT.BORDER | SWT.MULTI);
		queryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		queryText.setLayoutData(quertTextGD);
		String getSelecedText = DialogUtil.getSelecedTextFromEditor();
		if (!getSelecedText.isEmpty()) {
			queryText.setText(getSelecedText);// 设置选中的文字
			query();
		} else {
			queryText.setText("");// 设置选中的文字
		}
		queryButton = new Button(headerComposite, SWT.NONE);
		queryButton.setText("查询");
		// 给Button添加事件
		addListenerToButton();
		// ******************************//
		// ***GROUP START***//
		Composite infoComposite = new Composite(parent, SWT.NONE);
		infoComposite.setLayout(new GridLayout(1, true));
		infoComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		infoGroup = new Group(infoComposite, SWT.NONE);
		infoGroup.setText("查询结果");
		GridLayout groupLayout = new GridLayout(2, false);
		groupLayout.marginBottom = 5;
		groupLayout.marginTop = 5;
		groupLayout.marginLeft = 10;
		groupLayout.marginRight = 10;
		infoGroup.setLayout(groupLayout);
		infoGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		infoGroup.pack();
																			
		GridData gridData = new GridData(GridData.CENTER);
		gridData.horizontalSpan = 2;
		gridData.heightHint = 100;
		gridData.widthHint = 378;
		resultTextText = new Text(infoGroup,SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);//   SWT.BORDER | 
		resultTextText.setLayoutData(gridData);
		resultTextText.setFont(new Font(getShell().getDisplay(), "Arial", 8, SWT.NORMAL));
		resultTextText.setBackground(new Color(Display.getDefault(), new RGB(255, 255, 240)));
		resultTextText.setEditable(false);
		
		GridData btn1GD = new GridData(GridData.FILL_VERTICAL);
		btn1GD.horizontalSpan = 1;
		btn1GD.heightHint = 24;
		btn1GD.widthHint = 305;
		addToDictButton = new Button(infoGroup, SWT.NONE);
		addToDictButton.setText("添加到单词本");
		addToDictButton.setEnabled(false);
		addToDictButton.setLayoutData(btn1GD);
		addToDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				DictionaryUtil.writeToDictXML(word, trans, phonetic);
				addToDictButton.setEnabled(false);
			}
		});
		
		GridData btn2GD = new GridData(GridData.FILL_VERTICAL);
		btn2GD.horizontalSpan = 1;
		btn2GD.heightHint = 24;
		btn2GD.widthHint = 80;
		dictManageButton = new Button(infoGroup, SWT.NONE);
		dictManageButton.setText("单词本管理");
		dictManageButton.setLayoutData(btn2GD);
		dictManageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				close();
				PreferenceUtil.openPreferenceDialog("org.eclipse.youdaofanyi.preferencePageOfDict");
			}
		});
	
	}
	
	/**
	 * init for Mac OS X
	 */
	private void initForMac(Composite parent) {
 
		Shell shell = this.getShell();
		shell.setSize(450, 300);
		Monitor primary = shell.getMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2 - 50;
		shell.setText("有道翻译");
		shell.setLocation(x, y);
		shell.setImage(Activator.getImageDescriptor("/icon/icon_16x16.png").createImage());
		/* 布局开始 */
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.marginBottom = 10;
		layout.marginTop = 10;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		/* headerComposite... */
		Composite headerComposite = new Composite(composite, SWT.NONE);
		headerComposite.setLayout(new GridLayout(3, false));
		headerComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(headerComposite, SWT.NONE).setText("请输入:");
		
		GridData quertTextGD = new GridData(GridData.FILL);
		quertTextGD.horizontalSpan = 1;
		quertTextGD.heightHint = 16;
		quertTextGD.widthHint = 315;
		queryText = new Text(headerComposite, SWT.BORDER);
		queryText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		queryText.setLayoutData(quertTextGD);
		String getSelecedText = DialogUtil.getSelecedTextFromEditor();
		if (!getSelecedText.isEmpty()) {
			queryText.setText(getSelecedText);// 设置选中的文字
			query();
		} else {
			queryText.setText("");// 设置选中的文字
		}
		queryButton = new Button(headerComposite, SWT.NONE);
		queryButton.setText("查询");
		// 给Button添加事件
		addListenerToButton();
		// ******************************//
		// ***GROUP START***//
		Composite infoComposite = new Composite(parent, SWT.NONE);
		infoComposite.setLayout(new GridLayout(1, true));
		infoComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		infoGroup = new Group(infoComposite, SWT.NONE);
		//infoGroup.setText("查询结果");
		GridLayout groupLayout = new GridLayout(2, false);
		groupLayout.marginBottom = 5;
		groupLayout.marginTop = 0;
		groupLayout.marginLeft = 0;
		groupLayout.marginRight = 0;
		infoGroup.setLayout(groupLayout);
		infoGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		//infoGroup.pack();
																			
		GridData gridData = new GridData(GridData.BEGINNING);
		gridData.horizontalSpan = 2;
		gridData.heightHint = 120;
		gridData.widthHint = 402;
		resultTextText = new Text(infoGroup,SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);//   SWT.BORDER | 
		resultTextText.setLayoutData(gridData);
		resultTextText.setFont(new Font(getShell().getDisplay(), "Arial", 11, SWT.NORMAL));
		resultTextText.setBackground(new Color(Display.getDefault(), new RGB(255, 255, 240)));
		resultTextText.setEditable(false);
		
		GridData btn1GD = new GridData(GridData.FILL_VERTICAL);
		btn1GD.horizontalSpan = 1;
		btn1GD.heightHint = 24;
		btn1GD.widthHint = 315;
		addToDictButton = new Button(infoGroup, SWT.NONE);
		addToDictButton.setText("添加到单词本");
		addToDictButton.setEnabled(false);
		addToDictButton.setLayoutData(btn1GD);
		addToDictButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				DictionaryUtil.writeToDictXML(word, trans, phonetic);
				addToDictButton.setEnabled(false);
			}
		});
		
		GridData btn2GD = new GridData(GridData.FILL_VERTICAL);
		btn2GD.horizontalSpan = 1;
		btn2GD.heightHint = 24;
		btn2GD.widthHint = 100;
		dictManageButton = new Button(infoGroup, SWT.NONE);
		dictManageButton.setText("单词本管理");
		dictManageButton.setLayoutData(btn2GD);
		dictManageButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				close();
				PreferenceUtil.openPreferenceDialog("org.eclipse.youdaofanyi.preferencePageOfDict");
			}
		});
	
	}
	
	@Override
	protected Control createContents(Composite parent) {
		if (CommonUtil.isMacOSX()) {
			initForMac(parent);
		} else {
			initForWindows(parent);
		}
		
		return super.createContents(parent);
	}
 
	@Override
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
	    if (id == IDialogConstants.CANCEL_ID || id == IDialogConstants.OK_ID) {
	    	return null;
	    }
	    return super.createButton(parent, id, label, defaultButton);
	}
	
	public void addListenerToButton(){
		queryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				query();
				super.mouseDown(e);
			}
		});
	}
	
	public void query() {
		String qtext = queryText.getText();
		if(qtext.trim().isEmpty()){
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "提示", "请输入或选择查询");
		} else {
			Document doc = HttpClientUtil.getDocumentByQuery(qtext);
			if(doc != null){
				ResultModel rm = HttpClientUtil.convertDocToModel(doc);
				resultTextText.setText(ResultModel.getFormattedDisplatString(rm));
				addToDictButton.setEnabled(!rm.isError());
				word = rm.getQuery();
			    trans = rm.getExplains().toString().replace("[", "").replace("]", "");
			    phonetic = rm.getPhonetic();
			}
		}
	}
}