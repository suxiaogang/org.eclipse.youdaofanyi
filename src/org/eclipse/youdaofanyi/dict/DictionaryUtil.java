package org.eclipse.youdaofanyi.dict;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.youdaofanyi.Activator;
import org.eclipse.youdaofanyi.document.DocumentHelper;
import org.eclipse.youdaofanyi.model.WordModel;
import org.jdom.Document;
import org.jdom.Element;

public class DictionaryUtil {
	
	@SuppressWarnings("unchecked")
	public static List<WordModel> convertPropFileToList() {
		List<WordModel> words = new ArrayList<WordModel>();
		File dictFile = getUserDictFile();
		try {
			Document doc = DocumentHelper.getDocument(new FileInputStream(dictFile));
			Element elementWordbook = doc.getRootElement();
			List<Element> children = elementWordbook.getChildren();
			for (Element element : children) {
				Element word = element.getChild("word");
				String wordValue = word.getValue();
				Element trans = element.getChild("trans");
				String transValue = trans.getValue();
				Element phonetic = element.getChild("phonetic");
				String phoneticValue = phonetic.getValue();
				WordModel wm = new WordModel(wordValue, transValue, phoneticValue);
				words.add(wm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return words;
	}
	
	public static void writeToDictXML(String word,String trans,String phonetic) {
		File dictFile = getUserDictFile();
		try {
			Document doc = DocumentHelper.getDocument(new FileInputStream(dictFile));
			Element elementWordbook = doc.getRootElement();
			Element itemElement = new Element("item");
				Element wordElement = new Element("word");
				wordElement.setText(word);
				Element transElement = new Element("trans");
				transElement.setText(trans);
				Element phoneticmElement = new Element("phonetic");
				phoneticmElement.setText(phonetic);
				itemElement.addContent(wordElement);
				itemElement.addContent(transElement);
				itemElement.addContent(phoneticmElement);
			elementWordbook.addContent(itemElement);
			DocumentHelper.saveDocToFile(doc, dictFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static File getUserDictFile() {
		IPath iPath = Platform.getStateLocation(Activator.getDefault().getBundle());
		File parentfile = iPath.toFile();
		File propFile = new File(parentfile, "dict.xml");
		if(!propFile.exists()){
			try {
				propFile.createNewFile();
				initDefaultFromTemplate();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return propFile;
	}
	
	public static void initDefaultFromTemplate() {
		InputStream inputStream = null;
		try {
			inputStream = (InputStream) Activator.getDefault().getBundle().getEntry("/template/dict.xml").openStream();
			copyFile(getUserDictFile(), inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void copyFile(File dirFile, File sourceFile) {
		try {
			copyFile(dirFile, new FileInputStream(sourceFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFile(File dirFile, InputStream is) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(dirFile);
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = is.read(bytes)) != -1) {
				os.write(bytes, 0, length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
