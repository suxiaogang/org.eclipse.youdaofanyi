package org.eclipse.youdaofanyi.preference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.youdaofanyi.dict.DictionaryUtil;
import org.eclipse.youdaofanyi.model.WordModel;

public class PreferencePageDict extends PreferencePage implements IWorkbenchPreferencePage {

	protected Text text;
	protected TableViewer tv;
	protected Table table;

	protected Button deleteButton;
	protected Button editButton;
	protected Button importButton;
	protected Button exportButton;
	protected Button texportButton;
	
	private List<WordModel> words = new ArrayList<WordModel>();
	
	protected void setData() {
		words = DictionaryUtil.convertPropFileToList();
	}

	@Override
	public void init(IWorkbench workbench) {
		noDefaultAndApplyButton();
		setData();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite com = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.verticalSpacing = 10;
		com.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		com.setLayoutData(gridData);

		Label desLabel = new Label(com, SWT.NONE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		desLabel.setText("单词本管理");
		desLabel.setLayoutData(gridData);

		Composite tableComposite = new Composite(com, SWT.NONE);
		tableComposite.setLayout(layout);
		tableComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		this.tv = new TableViewer(tableComposite, SWT.FULL_SELECTION | SWT.BORDER);
	    this.table = this.tv.getTable();
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.widthHint = 200;
		gridData.heightHint = 100;
		this.table.setLayoutData(gridData);

		createTable();

		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);

		Composite buttonComposite = new Composite(com, SWT.NONE);
		buttonComposite.setLayout(new GridLayout());
		buttonComposite.setLayoutData(new GridData(GridData.FILL_VERTICAL));

		this.deleteButton = new Button(buttonComposite, SWT.PUSH);
		this.deleteButton.setText("删除");
		gridData = new GridData();
		gridData.widthHint = 70;
		this.deleteButton.setLayoutData(gridData);
		this.deleteButton.setData("DELETE");

		this.editButton = new Button(buttonComposite, SWT.PUSH);
		this.editButton.setText("编辑");
		gridData = new GridData();
		gridData.widthHint = 70;
		this.editButton.setLayoutData(gridData);
		this.editButton.setData("EDIT");

		this.exportButton = new Button(buttonComposite, SWT.PUSH);
		this.exportButton.setText("导出");
		gridData = new GridData();
		gridData.widthHint = 70;
		this.exportButton.setLayoutData(gridData);
		this.exportButton.setData("EXPORT");
		
		initTVData();
		return com;
	}

    private void initTVData() {
    	tv.setContentProvider(new ArrayContentProvider());
		tv.setLabelProvider(new TableLabelProvider());
		tv.setInput(this.words);
	}

	protected void createTable() {
    	TableColumn columnName = new TableColumn(this.table, SWT.LEFT);
		columnName.setText("英文");
		columnName.setWidth(80);
		columnName.setAlignment(SWT.LEFT);
		
		TableColumn columnDes = new TableColumn(this.table, SWT.LEFT);
		columnDes.setText("中文释义");
		columnDes.setWidth(240);
		columnDes.setAlignment(SWT.LEFT);
    }
	
	private class TableLabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object element, int index) {
			WordModel word = (WordModel) element;
			switch (index) {
			case 0:
				return word.getChinese();
			case 1:
				return word.getExplains();
			default:
				return null;
			}
		}
		@Override
		public void addListener(ILabelProviderListener listener) {
		}
		@Override
		public void dispose() {
		}
		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}
		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
	}
}
