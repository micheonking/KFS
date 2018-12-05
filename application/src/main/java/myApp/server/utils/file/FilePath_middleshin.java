package myApp.server.utils.file;

public class FilePath_middleshin {

	// folder는 반드시 직접 만들어 주어야 한다. 
	public String getFilePath(String fileId){
		return "E:\\WebFiles\\" + (Long.parseLong(fileId)/100) ;	
	}
	
}
