package mining.user_scenarios;

/**
 * 
 * Allow to change the database where the mining will happen. is relational database or no sql database
 * 
 * @author jadson
 *
 */
public class UserScenariosMiningFactory {

	public enum DATABASE{POSTGRES, MONGODB};
	
	public static UserScenariosMining getMining(DATABASE database){
		if(database == DATABASE.MONGODB)
			return new UserScenariosMiningMongoDB();
		else 
			throw new UnsupportedOperationException("Mining in postgres not implemented");
	}
}
