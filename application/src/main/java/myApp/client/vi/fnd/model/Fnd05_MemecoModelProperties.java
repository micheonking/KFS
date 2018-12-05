package myApp.client.vi.fnd.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface Fnd05_MemecoModelProperties extends PropertyAccess<Fnd05_MemecoModel> {

	ModelKeyProvider<Fnd05_MemecoModel> keyId();

	ValueProvider<Fnd05_MemecoModel,	Long	>	memecoId();
	ValueProvider<Fnd05_MemecoModel,	String	>	memecoCode();
	ValueProvider<Fnd05_MemecoModel,	String	>	memecoName();
	ValueProvider<Fnd05_MemecoModel,	Boolean	>	useYn();
	ValueProvider<Fnd05_MemecoModel,	String	>	note();

}
