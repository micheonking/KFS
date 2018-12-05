package myApp.client.vi.dbm.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface Dbm01_TabCommentsModelProperties extends PropertyAccess<Dbm01_TabCommentsModel> {
	
	ModelKeyProvider<Dbm01_TabCommentsModel> keyId();
	
	ValueProvider<Dbm01_TabCommentsModel, String>	tableName();
	ValueProvider<Dbm01_TabCommentsModel, String>	tableComments();
	ValueProvider<Dbm01_TabCommentsModel, String>	tablespaceName();
	ValueProvider<Dbm01_TabCommentsModel, String>	tableType();
//	ValueProvider<User_TabCommentsModel, Long>	pctFree();
//	ValueProvider<User_TabCommentsModel, Long>	pctUsed();
//	ValueProvider<User_TabCommentsModel, Long>	iniTrans();
//	ValueProvider<User_TabCommentsModel, Long>	maxTrans();
//	ValueProvider<User_TabCommentsModel, Long>	pctIncrease();
//	ValueProvider<User_TabCommentsModel, Long>	freeLists();
//	ValueProvider<User_TabCommentsModel, Long>	numRows();
//	ValueProvider<User_TabCommentsModel, Long>	blocks();
//	ValueProvider<User_TabCommentsModel, Long>	emptyBlocks();
//	ValueProvider<User_TabCommentsModel, Long>	chainCnt();
//	ValueProvider<User_TabCommentsModel, Long>	avgRowLen();
//	ValueProvider<User_TabCommentsModel, String>	initialExtent();
//	ValueProvider<User_TabCommentsModel, String>	nextExtent();
//	ValueProvider<User_TabCommentsModel, String>	cache();
//	ValueProvider<User_TabCommentsModel, String>	tableLock();	
}
