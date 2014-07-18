package org.eclipse.youdaofanyi.model;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

public class ResultModel {
	
	private final Element fanyiElement;
	private Element errorCodeElement;
	private Element basicElement;
	private Element phoneticElement;//音标
	private Element explainsElement;//释义
	
	private String errorCode;
	
	private String query;
	private String trans;
	private String phonetic;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getTrans() {
		return trans;
	}
	public void setTrans(String trans) {
		this.trans = trans;
	}
	private List<String> explainsList = new ArrayList<String>();;
	
	private boolean isError = false;
	
	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	@SuppressWarnings("unchecked")
	public ResultModel(Document doc) {
		Element rootElement = doc.getRootElement();
		fanyiElement = rootElement;
		errorCodeElement = fanyiElement.getChild("errorCode");
		
		//errorCode
		errorCode = errorCodeElement.getValue();
		if(!errorCode.equals("0")){
			setError(true);
		}
		basicElement = fanyiElement.getChild("basic");
		if(basicElement != null){
			phoneticElement = basicElement.getChild("phonetic");
			explainsElement = basicElement.getChild("explains");
		}
		List<Element> exElementList = null;
		if(explainsElement != null){
			exElementList = explainsElement.getChildren("ex");
		}
		if(exElementList != null){
			for (Element exElement : exElementList) {
				String exValue = exElement.getValue();
				explainsList.add(exValue);
			}
		}
		//phonetic
		if(phoneticElement != null){
			phonetic = phoneticElement.getValue();
		}
		//query
		Element queryElement = fanyiElement.getChild("query");
		query = queryElement.getValue();
		
		//trans
		if(exElementList != null){
			trans = exElementList.toString();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getPhonetic() {
		if(phonetic == null || phonetic.isEmpty()){
			return "";
		}
		return "[" + phonetic + "]";
	}

	public List<String> getExplains() {
		if(explainsList.size()==0){
			setError(true);
		}
		return explainsList;
	}
	
	public static String getFormattedDisplatString(ResultModel rm){
		String separator = System.getProperty("line.separator");
		if(rm.getExplains().size()==0){
			return transErrorCode(rm.getErrorCode()).isEmpty() ? "暂无结果" : transErrorCode(rm.getErrorCode());
		}else{
			StringBuilder sb = new StringBuilder();
			if(!rm.getPhonetic().isEmpty()){
				sb.append("音标: " + rm.getPhonetic()).append(separator);
			}
			sb.append("释义:").append(separator);
			for (int i = 0; i < rm.getExplains().size(); i++) {
				String str = rm.getExplains().get(i);
				sb.append(str).append(separator);
			}
			return sb.toString();
		}
	}
	/*
	　0 - 正常
	　20 - 要翻译的文本过长
	　30 - 无法进行有效的翻译
	　40 - 不支持的语言类型
	　50 - 无效的key
	　60 - 无词典结果，仅在获取词典结果生效
	 */
	public static String transErrorCode(String code){
		if(code.equals("20")){
			return "要翻译的文本过长";
		} else if(code.equals("30")) {
			return "无法进行有效的翻译";
		} else if(code.equals("40")) {
			return "不支持的语言类型";
		} else if(code.equals("50")) {
			return "无效的key";
		} else if(code.equals("60")) {
			return "无词典结果，仅在获取词典结果生效";
		}
		return "";
	}
	
}
