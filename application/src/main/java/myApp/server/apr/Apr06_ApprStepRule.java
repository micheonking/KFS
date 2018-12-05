package myApp.server.apr;

import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;

public class Apr06_ApprStepRule {
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private String mapperName = "apr06_appr_step_rule";

	public void selectByApprovalTypeCd(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void selectTree(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void insertRoot(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void insertChild(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

	}
}
