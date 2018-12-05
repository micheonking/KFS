package myApp.client.vi.apr.model;

import myApp.client.utils.GridDataModel;

public class Apr06_ApprStepRuleModel implements GridDataModel {
	
	private Long apprStepRuleId;
	private Long companyId;
	private String apprStepRuleCode;
	private String apprStepRuleNamm;
	private String apprLine01;
	private String apprEmpId01;
	private String apprLine02;
	private String apprEmpId02;
	private String apprLine03;
	private String apprEmpId03;
	private String apprLine04;
	private String apprEmpId04;
	private String apprLine05;
	private String apprEmpId05;
	private String apprSeq;
	private String apprLine;

	@Override
	public void setKeyId(Long id) {
		this.setApprStepRuleId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getApprStepRuleId();
	}

	public Long getApprStepRuleId() {
		return apprStepRuleId;
	}

	public void setApprStepRuleId(Long apprStepRuleId) {
		this.apprStepRuleId = apprStepRuleId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getApprStepRuleCode() {
		return apprStepRuleCode;
	}

	public void setApprStepRuleCode(String apprStepRuleCode) {
		this.apprStepRuleCode = apprStepRuleCode;
	}

	public String getApprStepRuleNamm() {
		return apprStepRuleNamm;
	}

	public void setApprStepRuleNamm(String apprStepRuleNamm) {
		this.apprStepRuleNamm = apprStepRuleNamm;
	}

	public String getApprLine01() {
		return apprLine01;
	}

	public void setApprLine01(String apprLine01) {
		this.apprLine01 = apprLine01;
	}

	public String getApprEmpId01() {
		return apprEmpId01;
	}

	public void setApprEmpId01(String apprEmpId01) {
		this.apprEmpId01 = apprEmpId01;
	}

	public String getApprLine02() {
		return apprLine02;
	}

	public void setApprLine02(String apprLine02) {
		this.apprLine02 = apprLine02;
	}

	public String getApprEmpId02() {
		return apprEmpId02;
	}

	public void setApprEmpId02(String apprEmpId02) {
		this.apprEmpId02 = apprEmpId02;
	}

	public String getApprLine03() {
		return apprLine03;
	}

	public void setApprLine03(String apprLine03) {
		this.apprLine03 = apprLine03;
	}

	public String getApprEmpId03() {
		return apprEmpId03;
	}

	public void setApprEmpId03(String apprEmpId03) {
		this.apprEmpId03 = apprEmpId03;
	}

	public String getApprLine04() {
		return apprLine04;
	}

	public void setApprLine04(String apprLine04) {
		this.apprLine04 = apprLine04;
	}

	public String getApprEmpId04() {
		return apprEmpId04;
	}

	public void setApprEmpId04(String apprEmpId04) {
		this.apprEmpId04 = apprEmpId04;
	}

	public String getApprLine05() {
		return apprLine05;
	}

	public void setApprLine05(String apprLine05) {
		this.apprLine05 = apprLine05;
	}

	public String getApprEmpId05() {
		return apprEmpId05;
	}

	public void setApprEmpId05(String apprEmpId05) {
		this.apprEmpId05 = apprEmpId05;
	}

	public String getApprSeq() {
		return apprSeq;
	}

	public void setApprSeq(String apprSeq) {
		this.apprSeq = apprSeq;
	}

	public String getApprLine() {
		return apprLine;
	}

	public void setApprLine(String apprLine) {
		this.apprLine = apprLine;
	}
	
	
}
