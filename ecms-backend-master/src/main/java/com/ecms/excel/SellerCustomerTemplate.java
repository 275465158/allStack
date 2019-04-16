package com.ecms.excel;

import com.ecms.annotation.ExcelField;
import com.ecms.annotation.ExcelSheet;
import org.apache.poi.hssf.util.HSSFColor;

@ExcelSheet(name = "客户列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
public class SellerCustomerTemplate extends BaseTemplate {

    @ExcelField(name = "公司名称")
    private String customerName;
    @ExcelField(name = "税号")
    private String taxNumber;
    @ExcelField(name = "省/直辖市")
    private String addressProvince;
    @ExcelField(name = "市")
    private String addressCity;
    @ExcelField(name = "区/县/县级市")
    private String addressCounty;
    @ExcelField(name = "是否含税")
    private String taxIncludeDisplay;
    @ExcelField(name = "付款方式")
    private String payType;
    @ExcelField(name = "付款规则")
    private String payRule;
    @ExcelField(name = "发票类型")
    private String invoice;
    @ExcelField(name = "送货方式")
    private String delivery;
    @ExcelField(name = "门牌号")
    private String addressDetail;
    @ExcelField(name = "联系人")
    private String contacterName;
    @ExcelField(name = "公司电话")
    private String customerPhone;

    public String getTaxIncludeDisplay() {
        return taxIncludeDisplay;
    }

    public void setTaxIncludeDisplay(String taxIncludeDisplay) {
        this.taxIncludeDisplay = taxIncludeDisplay;
    }

    public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayRule() {
		return payRule;
	}

	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getContacterName() {
        return contacterName;
    }

    public void setContacterName(String contacterName) {
        this.contacterName = contacterName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
