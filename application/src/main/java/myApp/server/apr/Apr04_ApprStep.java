package myApp.server.apr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.apr.model.Apr07_AltApprModel;
import myApp.client.vi.apr.model.Apr11_AdvertBookModel;
import myApp.client.vi.emp.model.Emp00_InfoModel;

public class Apr04_ApprStep {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String mapperName = "apr04_appr_step";
	
	public void selectByApprId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", request.getLongParam("apprId"));
		param.put("empId", request.getLongParam("empId"));
		
		String sqlId = mapperName + ".selectByApprId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		// 대결자 처리 추가 필요함. 
		//Apr07_AltApprModel altApprModel = sqlSession.selectOne("apr07_alt_appr" + ".selectOneByEmpId", param);
		
		
		List<GridDataModel> resultList = new ArrayList<GridDataModel>();
		for(int i = 0; i < list.size(); i++) {
			Apr04_ApprStepModel apprStepModel = (Apr04_ApprStepModel) list.get(i);
			Long tempEmpId = apprStepModel.getStepEmpId();
			param.put("empId", tempEmpId);
			Apr07_AltApprModel altApprModel = sqlSession.selectOne("apr07_alt_appr" + ".selectOneByEmpId", param);
			Long apprId = 0L;
			if(apprStepModel.getApprEmpId() != null) {
				apprId = apprStepModel.getApprEmpId();
			}
			//결재완료된 경우
			if("20".equals(apprStepModel.getStepStatusCode())){
				//결재라인과 실 결재자가 다를 경우
				if(!String.valueOf(apprStepModel.getStepEmpId()).equals(String.valueOf(apprId))) {
					if(apprStepModel.getApprEmpId() != null) {
						param.put("baseDate", apprStepModel.getApprDate());
						param.put("empId", apprId);
						
						Emp00_InfoModel empModel = sqlSession.selectOne("emp00_info.selectOneEmpInfo", param);
						String str = "";
						str = apprStepModel.getEmpInfoModel().getTitleName();
						str += " " + apprStepModel.getEmpInfoModel().getKorName() + "(";
						str += empModel.getTitleName() + " ";
						str += empModel.getKorName() + ")";
						apprStepModel.setStepApprName(str);
					}
				} else {
					String str = "";
					str = apprStepModel.getEmpInfoModel().getTitleName() + " ";
					str += apprStepModel.getEmpInfoModel().getKorName();
					apprStepModel.setStepApprName(str);
					apprStepModel.setApprEmpId(apprStepModel.getStepEmpId());
				}
			} else {
				//대결자가 있을 경우
				if(altApprModel != null) {
					if(apprStepModel.getApprEmpId() == null || apprStepModel.getApprEmpId() != altApprModel.getAltEmpId()) {
						String str = "";
						str = apprStepModel.getEmpInfoModel().getTitleName();
						str += " " + apprStepModel.getEmpInfoModel().getKorName() + "(";
						str += altApprModel.getAltTitleName() + " ";
						str += altApprModel.getAltEmpName() + ")";
						apprStepModel.setStepApprName(str);
						apprStepModel.setApprEmpId(altApprModel.getAltEmpId());
					} else {
						String str = "";
						str = apprStepModel.getEmpInfoModel().getTitleName() + " ";
						str += apprStepModel.getEmpInfoModel().getKorName();
						apprStepModel.setStepApprName(str);
						apprStepModel.setApprEmpId(apprStepModel.getStepEmpId());	
					}
					//대결자 없음
				} else {
					String str = "";
					str = apprStepModel.getEmpInfoModel().getTitleName() + " ";
					str += apprStepModel.getEmpInfoModel().getKorName();
					apprStepModel.setStepApprName(str);
					apprStepModel.setApprEmpId(apprStepModel.getStepEmpId());
				}
			}
			
			resultList.add(apprStepModel);
		}
		
		//result.setRetrieveResult(1, sqlId, list);
		result.setRetrieveResult(1, sqlId, resultList);
	}

	public void changeByApproval(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("empId", request.getLongParam("empId"));
		
		Apr07_AltApprModel altApprModel = sqlSession.selectOne("apr07_alt_appr.selectOneByEmpId", param);
		List<GridDataModel> resultList = new ArrayList<GridDataModel>();
		if(altApprModel != null) {
			resultList.add(altApprModel);
		}
		
		result.setRetrieveResult(1, "apr07_alt_appr.selectOneByEmpId", resultList);
		
	}
	
	public void selectByApprovalTypeCd(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		createApprovalTypeCd(sqlSession, request.getParam(), result);
	}

	public void confirmUpdate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", request.getLongParam("apprId"));//결재문서번호
		param.put("apprEmpId", request.getLongParam("apprEmpId"));//승인자
		param.put("rejectMessage", request.getStringParam("rejectMessage"));//반려사유
		param.put("apprStatusCode", request.getStringParam("apprStatusCode"));//결재상태
		param.put("stepEmpId", request.getStringParam("stepEmpId"));//실결재자
		param.put("advertYn", request.getStringParam("advertYn"));//광고유무
		param.put("companyId", request.getStringParam("companyId"));//회사코드
		param.put("autoApprId", request.getLongParam("autoApprId"));//자동결재할 원결재자
		
		sqlSession.update(mapperName + ".confirmUpdate", param);//결재선 업데이트

		//결재 한번더
		if("20".equals(request.getStringParam("apprStatusCode")) && request.getLongParam("autoApprId") != 0L) {//다음결재자가 자기일 경우 또 승인
			param.put("stepEmpId", request.getLongParam("autoApprId"));//실결재자
			sqlSession.update(mapperName + ".confirmUpdate", param);//결재선 업데이트
		}
		
		String sqlId = mapperName + ".selectByApprId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);//결재선 조회
		
		String docStatusCode = "30"; // 아래 IF문에 안걸리면 완료처리 한다.   
		
		for(GridDataModel data : list) {
			Apr04_ApprStepModel stepModel = (Apr04_ApprStepModel)data; 
			if("10".equals(stepModel.getStepTypeCode()) || "20".equals(stepModel.getStepTypeCode())) { //결재나 합의 타입일 경우
				if("10".equals(stepModel.getStepStatusCode()) ) {
					// 대기중이 있다. 
					docStatusCode = "20" ; // 결재중
				}
				else if("30".equals(stepModel.getStepStatusCode()) ) { 
					// 반려가 있다. 
					docStatusCode = "40" ; // 반려 
					break ; 
				}
			}
		}
		param.put("apprStatusCode", docStatusCode);
		if("30".equals(docStatusCode)) {//결재완료이면서 인감사용할 경우 인감대장번호 생성
			if(request.getLongParam("stampId") != null && request.getLongParam("stampId") != 0) {
				Apr01_ApprModel tempModel = sqlSession.selectOne("apr01_appr.getStampSeq");
				Long stampSeq = tempModel.getStampSeq();
				param.put("stampSeq", stampSeq);//인감대장번호
			}
		}
		
		sqlSession.update("apr01_appr.confirmUpdate", param);
		if("30".equals(docStatusCode)) {
			if("true".equals(param.get("advertYn"))) {//광고대장 insert
				Apr11_AdvertBookModel advertBookModel = sqlSession.selectOne("apr11_advert_book.getAdvertSeq");
				param.put("advertSeq", advertBookModel.getAdvertSeq());
				sqlSession.insert("apr11_advert_book.insertAdvertHistory", param);
			} else {
			}
			if(request.getLongParam("stampId") != null && request.getLongParam("stampId") != 0) {
				result.setRetrieveResult(1,  "makepdf", list);
			} else {
				result.setRetrieveResult(1,  "update confirm", list);	
			}
		} else {
			result.setRetrieveResult(1,  "update confirm", list);
		}
	}
	
	public void cancelUpdate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", request.getLongParam("apprId"));
		param.put("apprEmpId", request.getLongParam("apprEmpId"));
		param.put("rejectMessage", "");
		param.put("apprDate", "");
		param.put("apprStatusCode", "10");
		param.put("stepEmpId", request.getLongParam("apprEmpId"));
		System.out.println(""  + request.getLongParam("apprEmpId"));
		System.out.println(""  + request.getLongParam("apprId"));
		sqlSession.update(mapperName + ".cancelUpdate", param);
		
		String sqlId = mapperName + ".selectByApprId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		
		sqlSession.update("apr01_appr.cancelUpdate", param);
		result.setRetrieveResult(1,  "update confirm", list);
	}
	
	public void createApprovalTypeCd(SqlSession sqlSession, Map<String, Object> request, ServiceResult result) {
		
		Long empId  			= (Long)request.get("empId");
		Long apprId 			= (Long)request.get("apprId");
		Long companyId 			= (Long)request.get("companyId");
		Long orgId              = (Long)request.get("orgId");
		String approvalTypeCode = (String)request.get("approvalTypeCode");
		
		System.out.println("emp id is " + empId);
		System.out.println("org id is " + orgId);
		System.out.println("appr id is " + apprId);
		System.out.println("company id is " + companyId);
		System.out.println("approval Type Code is " + approvalTypeCode);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId", companyId);
		param.put("empId", empId);
		param.put("orgId", orgId);
		param.put("approvalTypeCode", approvalTypeCode);

		List<String> stepEmpIdList = new ArrayList<String>();
		List<Long>   stepOrgIdList = new ArrayList<Long>();
		
		/*
		 * 
		// 단계별 결재라인 조회 
		Apr06_ApprStepRuleModel apprStepRuleList = sqlSession.selectOne("apr06_appr_step_rule" + ".selectByApprovalTypeCode", param);
		
		//상신자 세팅
		String empIdStr = String.valueOf(empId);
		stepEmpIdList.add(empIdStr);
		
		if(apprStepRuleList != null) {
			// 칼럼정보를 행으로 변환한다. 
			if(apprStepRuleList.getApprEmpId01() != null && !empIdStr.equals(apprStepRuleList.getApprEmpId01())) {
				stepEmpIdList.add(apprStepRuleList.getApprEmpId01());
			}
			if(apprStepRuleList.getApprEmpId02() != null && !empIdStr.equals(apprStepRuleList.getApprEmpId02())) {
				stepEmpIdList.add(apprStepRuleList.getApprEmpId02());
			}
			if(apprStepRuleList.getApprEmpId03() != null && !empIdStr.equals(apprStepRuleList.getApprEmpId03())) {
				stepEmpIdList.add(apprStepRuleList.getApprEmpId03());
			}
			if(apprStepRuleList.getApprEmpId04() != null && !empIdStr.equals(apprStepRuleList.getApprEmpId04())) {
				stepEmpIdList.add(apprStepRuleList.getApprEmpId04());
			}
			if(apprStepRuleList.getApprEmpId05() != null && !empIdStr.equals(apprStepRuleList.getApprEmpId05())) {
				stepEmpIdList.add(apprStepRuleList.getApprEmpId05());
			}
		}
		*/

		//상신자 세팅
		String empIdStr = String.valueOf(empId);
		stepEmpIdList.add(empIdStr);
		stepOrgIdList.add(orgId);

		//타입에 따른 결재라인 조회
		List<Apr04_ApprStepModel> apprStepRuleList = sqlSession.selectList(mapperName + ".selectApprStepByType", param);
		if(apprStepRuleList != null) {
			
			if(apprStepRuleList.size() > 0) {
				
				System.out.println("########################## 결재라인이 있습니다. ");
				
				for(int i = 0; i < apprStepRuleList.size(); i++) {
					int k = 0;
					System.out.println("empIdStr : " + empIdStr);
					System.out.println("stepIdStr : " + apprStepRuleList.get(i).getEmpInfoModel().getEmpId());
					System.out.println("stepOrgId : " + apprStepRuleList.get(i).getEmpInfoModel().getOrgCodeId());
					if(!empIdStr.equals(String.valueOf(apprStepRuleList.get(i).getEmpInfoModel().getEmpId()))) {
						for(int j = 0; j < stepEmpIdList.size(); j++) {
							if(stepEmpIdList.get(j).equals(String.valueOf(apprStepRuleList.get(i).getEmpInfoModel().getEmpId()))) {
								k++;
							}
						}
						if(k <= 0) {
							stepEmpIdList.add(String.valueOf(apprStepRuleList.get(i).getEmpInfoModel().getEmpId()));
							stepOrgIdList.add(apprStepRuleList.get(i).getEmpInfoModel().getOrgCodeId());
						}
					}
				}
			}
		}
		else {
			System.out.println("########################## 결재라인이 없습니다."); 
		}

		List<GridDataModel> apprStepList = new ArrayList<GridDataModel>();
		List<GridDataModel> resultList = new ArrayList<GridDataModel>();
		for(int i = 0; i < stepEmpIdList.size(); i++) {
			Long stepEmpId = Long.parseLong(stepEmpIdList.get(i));
			Long stepOrgId = stepOrgIdList.get(i);

			param.put("empId", stepEmpId);

			Map<String, Object> empParam = new HashMap<String, Object>();
			empParam.put("empId", stepEmpId);
			empParam.put("orgId", stepOrgId);

			// 사원정보 찾기. 
			Emp00_InfoModel empModel = sqlSession.selectOne("emp00_info.selectByEmpId", empParam);

			Apr07_AltApprModel altApprModel = sqlSession.selectOne("apr07_alt_appr" + ".selectOneByEmpId", param);
			if(empModel != null) {
				
				Apr04_ApprStepModel apprStepModel = new Apr04_ApprStepModel();
				Long seq = sqlSession.selectOne("getSeq");
				apprStepModel.setApprStepId(seq);
				apprStepModel.setApprId(apprId);
				apprStepModel.setSeq(String.valueOf((i + 1) * 10));
				apprStepModel.setStepStatusCode("10");
				apprStepModel.setStepStatusName("대기");
				apprStepModel.setStepEmpId(stepEmpId);
				apprStepModel.setEmpInfoModel(empModel);
				if(i == 0) {
					apprStepModel.setStepTypeCode("00");
					apprStepModel.setStepTypeName("상신");
				} else {
					apprStepModel.setStepTypeCode("10");
					apprStepModel.setStepTypeName("결재");
				}
				
				// 대결자 정보 추가넣기 
				
				if(altApprModel != null) {
					String str = "";
//					str = empModel.getPositionName();
					str = empModel.getTitleName();
					str += " " + empModel.getKorName() + "(";
					str += altApprModel.getAltTitleName() + " ";
					str += altApprModel.getAltEmpName() + ")";
					apprStepModel.setStepApprName(str);
					apprStepModel.setApprEmpId(altApprModel.getAltEmpId());
				} else {
					String str = "";
					str = empModel.getTitleName() + " ";
					str += empModel.getKorName();
					apprStepModel.setStepApprName(str);
				}
				
				apprStepList.add(apprStepModel);
				GridDataModel dataModel = apprStepModel;
				dataModel.setKeyId(seq);
				resultList.add(dataModel);
			}
			else {
				// 결재자 정보를 찾을 수 없습니다.  
			}
		}

		//result.setRetrieveResult(apprStepList.size(), "결재라인 생성 및 조회", apprStepList);
		result.setRetrieveResult(resultList.size(), "결재라인 생성 및 조회", resultList);
	}

	private void setEmpInfo() {
		
	}
	
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

	}
}
