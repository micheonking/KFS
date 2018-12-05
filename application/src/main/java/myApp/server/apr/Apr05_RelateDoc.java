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
import myApp.client.vi.apr.model.Apr05_RelateDocModel;
import myApp.server.utils.db.UpdateDataModel;

public class Apr05_RelateDoc {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String mapperName = "apr05_relate_doc";
	private Long companyId;

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void selectByApprId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", request.getLongParam("apprId"));
		param.put("companyId", request.getLongParam("companyId"));
		
		String sqlId = mapperName + ".selectByApprId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
	public void selectByApprovalTypeCd(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void insert(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> tempMap = request.getParam();
        List<GridDataModel> docGridList = new ArrayList<GridDataModel>();
        docGridList.add((GridDataModel) tempMap.get("docGrid"));
        List<GridDataModel> insertList = new ArrayList<GridDataModel>();
        
        Long apprId = request.getLongParam("paramId");
        
        for(GridDataModel data : docGridList) {
//        	Dcr04_DocModel  docModel = (Dcr04_DocModel) data ;
//			Apr05_RelateDocModel relateDocModel = new Apr05_RelateDocModel();
//			
//			Long seq = sqlSession.selectOne("dbConfig.getSeq"); 
//			
//			relateDocModel.setRelateDocId(seq);
//			relateDocModel.setApprId(apprId);
//			relateDocModel.setDocId(docModel.getDocId());
//		    relateDocModel.setGubun((String)tempMap.get("gubun"));
//			
//			insertList.add((GridDataModel)relateDocModel);
		}
        
		UpdateDataModel<Apr05_RelateDocModel> updateStepModel = new UpdateDataModel<Apr05_RelateDocModel>(); 
		updateStepModel.updateModel(sqlSession, insertList, mapperName, result);
	}

	public void selectByClassTreeId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("classTreeId", request.getLongParam("classTreeId"));
		
		String sqlId = mapperName + ".selectByClassTreeId"; 
		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		result.setRetrieveResult(1, sqlId, list);
	}
	
}
