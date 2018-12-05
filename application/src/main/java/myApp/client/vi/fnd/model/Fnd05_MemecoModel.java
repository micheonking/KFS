package myApp.client.vi.fnd.model;

import java.util.Date;
import myApp.client.utils.GridDataModel;

public class Fnd05_MemecoModel implements GridDataModel {

	private	Long	memecoId;
	private	String	memecoCode;
	private	String	memecoName;
	private	Boolean	useYn;
	private	String	note;

	@Override
	public void setKeyId(Long id) {
		this.setMemecoId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMemecoId();
	}

	public	Long getMemecoId() {
		return	memecoId;
	}

	public void setMemecoId(Long memecoId) {
		this.memecoId = memecoId;
	}

	public String getMemecoCode() {
		return memecoCode;
	}

	public void setMemecoCode(String memecoCode) {
		this.memecoCode = memecoCode;
	}

	public String getMemecoName() {
		return memecoName;
	}

	public void setMemecoName(String memecoName) {
		this.memecoName = memecoName;
	}

	public Boolean getUseYn() {
		return useYn;
	}

	public void setUseYn(Boolean useYn) {
		this.useYn = useYn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
