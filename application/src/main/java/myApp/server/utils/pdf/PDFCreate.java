package myApp.server.utils.pdf;
 
import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.itextpdf.text.DocumentException;

import myApp.client.service.ServiceRequest;
import myApp.client.service.ServiceResult;
import myApp.server.apr.Apr10_StampPDF;

public class PDFCreate {
	
	public void createPDF(SqlSession sqlSession, ServiceRequest request, ServiceResult result) throws IOException, DocumentException {
		Apr10_StampPDF pdf = new Apr10_StampPDF();
		pdf.getDocument(request);
	}
}