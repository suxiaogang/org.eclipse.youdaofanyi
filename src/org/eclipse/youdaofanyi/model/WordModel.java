package org.eclipse.youdaofanyi.model;


public class WordModel {
	
	private String chinese;
	private String explains;
	private String phonetic;
	
	public WordModel(String chinese, String explains, String phonetic) {
		super();
		this.chinese = chinese;
		this.explains = explains;
		this.phonetic = phonetic;
	}
	
	/**
	 * @return the phonetic
	 */
	public String getPhonetic() {
		return phonetic;
	}
	/**
	 * @param phonetic the phonetic to set
	 */
	public void setPhonetic(String phonetic) {
		this.phonetic = phonetic;
	}
	/**
	 * @return the chinese
	 */
	public String getChinese() {
		return chinese;
	}
	/**
	 * @param chinese the chinese to set
	 */
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	/**
	 * @return the explains
	 */
	public String getExplains() {
		return explains;
	}
	/**
	 * @param explains the explains to set
	 */
	public void setExplains(String explains) {
		this.explains = explains;
	}

	@Override
	public String toString() {
		return "WordModel [chinese=" + chinese + ", explains=" + explains + "]";
	}

}
