package org.eclipse.youdaofanyi.ui;

import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class DialogUtil {
	
	public static String getSelecedTextFromEditor() {
		String selectedText = "";
		try {               
		    IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		    if (part instanceof ITextEditor) {
		        final ITextEditor editor = (ITextEditor)part;
		        ISelection sel = editor.getSelectionProvider().getSelection();
		        if (sel instanceof TextSelection ) {
		            TextSelection textSel = (TextSelection)sel;
		            selectedText = textSel.getText();
		        }
		    } else if(part instanceof MultiPageEditorPart){
		    	final MultiPageEditorPart editor = (MultiPageEditorPart)part;
		        Object selectedPage = editor.getSelectedPage();
		        if (selectedPage instanceof ITextEditor) {
			        final ITextEditor textEditor = (ITextEditor)selectedPage;
			        ISelection sel = textEditor.getSelectionProvider().getSelection();
			        if (sel instanceof TextSelection ) {
			            TextSelection textSel = (TextSelection)sel;
			            selectedText = textSel.getText();
			        }
			    }
		    }
		} catch ( Exception ex ) {
		    ex.printStackTrace();
		}
		return selectedText;
	}
	
	public static void showTipMessageDialog(String tipMessage) {
		MessageBox mb = new MessageBox(Display.getDefault().getActiveShell());
		mb.setMessage(tipMessage);
		mb.setText("查询出错!");
		mb.open();
	}

}
