package code.bo;

public class BarCode {

	private String barCodeId;
	private Object[] data;
	
	public BarCode(String barCodeId, Object[] data) {
		this.barCodeId = barCodeId;
		this.data = data;
	}

	public String getBarCodeId() {
		return barCodeId;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}
	
}
