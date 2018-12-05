package myApp.server.opr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.client.utils.GridDataModel;
import myApp.client.vi.apr.model.Apr01_ApprModel;
import myApp.client.vi.apr.model.Apr04_ApprStepModel;
import myApp.client.vi.dcr.model.Dcr01_ClassTreeModel;
import myApp.client.vi.opr.model.Opr01_CreateModel;
import myApp.client.vi.opr.model.Opr03_RegModel;
import myApp.client.vi.sys.model.Sys10_FileModel;
import myApp.server.apr.Apr04_ApprStep;
import myApp.server.utils.db.UpdateDataModel;
import myApp.server.utils.file.MyAppProperties;

public class Opr01_Create {

	private String mapperName = "opr01_create";

	//(관리자용)
	public void selectByTitle(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String sqlId = mapperName + ".selectByTitle"; 

		Long   companyId  = request.getLongParam("companyId");
		String year 	  = request.getStringParam("year") + "%" ;
		String termCd 	  = request.getStringParam("termCd") ;
		if (termCd == null) {
			termCd = "%";
		}
		String title  	  = "%" + request.getStringParam("title") + "%";
		String progressYn = request.getStringParam("progressYn");
		String closeYn    = request.getStringParam("closeYn");
		String aprYn	  = request.getStringParam("aprYn");

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId"	, companyId	);
		param.put("year"		, year		);
		param.put("termCd"		, termCd	);
		param.put("title"		, title		);
		param.put("progressYn"	, progressYn);
		param.put("closeYn"		, closeYn	);
		param.put("aprYn"		, aprYn		);

		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		System.out.println("count is "+list.size());
		result.setRetrieveResult(1, sqlId, list);
	}

	//(담당자용)
	public void selectByTitle1(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String sqlId = mapperName + ".selectByTitle1"; 

		Long   companyId  = request.getLongParam("companyId");
		Long   empId  	  = request.getLongParam("empId");
		String year 	  = request.getStringParam("year") + "%" ;
		String termCd 	  = request.getStringParam("termCd") ;
		if (termCd == null) {
			termCd = "%";
		}
		String title  	  = "%" + request.getStringParam("title") + "%";
		String progressYn = request.getStringParam("progressYn");
		String closeYn    = request.getStringParam("closeYn"); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId"	, companyId	);
		param.put("empId"		, empId		);
		param.put("year"		, year		);
		param.put("termCd"		, termCd	);
		param.put("title"		, title		);
		param.put("progressYn"	, progressYn);
		param.put("closeYn"		, closeYn	);

		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		System.out.println("count is "+list.size());
		result.setRetrieveResult(1, sqlId, list);
	}

	public void selectByLookupTitle(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		String sqlId = mapperName + ".selectByLookupTitle"; 

		Long   companyId  = request.getLongParam("companyId");
		String title  	  = "%" + request.getStringParam("title") + "%";
		String year  	  = request.getStringParam("year") + "%";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyId"	, companyId	);
		param.put("title"		, title		);
		param.put("year"		, year		);

		List<GridDataModel> list = sqlSession.selectList(sqlId, param);
		System.out.println("count is "+list.size());
		result.setRetrieveResult(1, sqlId, list);
	}

	public void chkPreDelete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long count = sqlSession.selectOne(mapperName + ".chkPreDelete", request.getStringParam("createId"));
		if (count == 0) {
			result.setStatus(1);
		} else {
			result.setStatus(0);
		}
	}

	public void insertAppr(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long createId	= request.getLongParam("createId");
		Long empId		= request.getLongParam("empId");
		Long orgId		= request.getLongParam("orgId");
		Long companyId	= request.getLongParam("companyId");

		//opr01_create data Get
		Opr01_CreateModel createModel = new Opr01_CreateModel();
		createModel = sqlSession.selectOne("opr01_create.selectById", createId);
		
		System.out.println("운용보고 ======================> getApprId : " + createModel.getApprId());
		
		if (createModel.getApprId() == null) {
			//opr03_reg data Get
			Opr03_RegModel regModel = new Opr03_RegModel();
			regModel = sqlSession.selectOne("opr03_reg.selectById", createModel.getRegId());
			
			System.out.println("======================> regModel.getRegId() : " + regModel.getRegId());

			//sys10_file data Get
			Sys10_FileModel fileModel = new Sys10_FileModel();
			fileModel = sqlSession.selectOne("sys10_file.selectOneByParentId", regModel.getRegId());

			// (1) 상신문서 생성 (apr01_appr insert)
			Apr01_ApprModel aprModel = new Apr01_ApprModel();
			aprModel.setApprId(sqlSession.selectOne("dbConfig.getSeq"));
			aprModel.setTitle(createModel.getTitle());
			aprModel.setStatusCode("99");	//99:임시저장
			aprModel.setRegEmpId(createModel.getCreateEmpId());
			Long classTreeId = (long)2003845;	//2003845:자산운용보고서
			aprModel.setClassTreeId(classTreeId);
			aprModel.setInOutTypeCode("20");	//20:사내문서
			sqlSession.update("apr01_appr.insert", aprModel);

			System.out.println("aprModel.getApprId()     : " + aprModel.getApprId());
			System.out.println("1. apr01_appr insert~!! ok");
			
			// (2) 파일 생성 (sys10_file insert)
			Sys10_FileModel newFileModel = new Sys10_FileModel();
			Long fileSeq = sqlSession.selectOne("dbConfig.getSeq");
			newFileModel.setFileId(fileSeq);
			newFileModel.setParentId(aprModel.getApprId());
			newFileModel.setFileName(fileModel.getFileName());
			newFileModel.setServerPath(fileModel.getServerPath());
			newFileModel.setDelDate(fileModel.getDelDate());
			newFileModel.setNote(fileModel.getNote());
			newFileModel.setSize(fileModel.getSize());
			newFileModel.setExt(fileModel.getExt());
			sqlSession.update("sys10_file.insert", newFileModel);

			System.out.println("2. sys10_file insert~!! ok");

			String inFileName = this.getUploadPath(fileModel.getFileId()) + getFolderPath() + fileModel.getFileId();
			String outFileName = this.getUploadPath(fileSeq) + getFolderPath() + fileSeq;
			
			File subDir  = new File(this.getUploadPath(fileSeq));
	        if(!subDir.exists()) {
	        	subDir.mkdir(); // 해당 폴더가 없으면 신규로 만든다.   
	        }
			fileCopy(inFileName, outFileName);//파일복사
			
			System.out.println("2-1. sys10_file file copy~!! ok");

			// (3) 연관펀드 생성 (apr03_relate_fund insert)
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("createId", createId);
			param.put("apprId"  , aprModel.getApprId());
			sqlSession.update("apr03_relate_fund.insertOprFund", param);
			System.out.println("3. apr03_relate_fund insert~!! ok");

			// (4) 결재선 생성 (Apr04_ApprStep insert)
			Dcr01_ClassTreeModel classTreeModel = sqlSession.selectOne("dcr01_class_tree.selectById", classTreeId);
			Map<String, Object> apprStepParam = new HashMap<String, Object>();
			apprStepParam.put("companyId" 		, companyId);
			apprStepParam.put("empId"	  		, empId);
			apprStepParam.put("orgId"	  		, orgId);
			apprStepParam.put("apprId"	  		, aprModel.getApprId());
			apprStepParam.put("approvalTypeCode", classTreeModel.getApprovalTypeCode());
			Apr04_ApprStep apprStep = new Apr04_ApprStep();
			apprStep.createApprovalTypeCd(sqlSession, apprStepParam, result);

			List<GridDataModel> apprStepList = (List<GridDataModel>) result.getResult();
			UpdateDataModel<Apr04_ApprStepModel> updateModel = new UpdateDataModel<Apr04_ApprStepModel>(); 
			updateModel.updateModel(sqlSession, apprStepList, "apr04_appr_step", result);
			System.out.println("4. Apr04_ApprStep insert~!! ok");

			// (5) 운용보고서 ApprId Update (opr01_create update)
			createModel.setApprId(aprModel.getApprId());
			sqlSession.update("opr01_create.updateApprId", createModel);
			System.out.println("5. opr01_create update~!! ok");
		}
		
		Apr01_ApprModel apprModel = sqlSession.selectOne("apr01_appr.selectById", createModel.getApprId());
		result.setModel(1, "aprModel", apprModel);
	}

	public void deleteAppr(SqlSession sqlSession, Long apprId) {

		// (1) apr03_relate_fund delete
		sqlSession.delete("apr03_relate_fund.deleteByApprId", apprId);
		System.out.println("1. apr03_relate_fund delete~!! ok");

		// (2) sys10_file delete
		sqlSession.delete("sys10_file.deleteByParentId", apprId);
		System.out.println("2. sys10_file delete~!! ok");
		
		// (3) apr04_appr_step delete
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apprId", apprId);
		sqlSession.delete("apr04_appr_step.deleteByApprId", param);
		System.out.println("3. apr04_appr_step delete~!! ok");

		// (4) apr01_appr delete
		sqlSession.delete("apr01_appr.deleteAppr", apprId);
		System.out.println("4. apr01_appr delete~!! ok");
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<Opr01_CreateModel> updateModel = new UpdateDataModel<Opr01_CreateModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		for (GridDataModel data : request.getList()) {
			Opr01_CreateModel deleteModel = (Opr01_CreateModel) data;
			if (deleteModel.getApprId() != null) {
				deleteAppr(sqlSession, deleteModel.getApprId());
			}
//			sqlSession.delete(mapperName + "deleteAppr", deleteModel.getApprId());
		}
		UpdateDataModel<Opr01_CreateModel> updateModel = new UpdateDataModel<Opr01_CreateModel>();
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
	
	public void fileCopy(String inFileName, String outFileName) {
		try {
			
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);
   
			int data = 0;
			while((data=fis.read())!=-1) {
				fos.write(data);
			}
			fis.close();
			fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String getUploadPath(Long fileId){
		MyAppProperties a = new MyAppProperties("filePath");
		return a.getProperty() + (fileId/100); 
	}
	
	private String getFolderPath() {
		MyAppProperties a = new MyAppProperties("folderPath");
		return a.getProperty();
	}
}
