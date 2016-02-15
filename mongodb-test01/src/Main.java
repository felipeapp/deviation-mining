
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Main {

	public static void main(String[] args) throws ParseException {

		MongoClient mc = new MongoClient();

		MongoDatabase db = mc.getDatabase("test");

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		db.getCollection("restaurants").insertOne(
		        new Document("address", new Document()
		        							.append("street", "2 Avenue")
		        							.append("zipcode", "10075")
		        							.append("building", "1480")
		        							.append("coord", Arrays.asList(-73.9557413, 40.7720266)))
		        	.append("borough", "Manhattan")
		        	.append("cuisine", "Italian")
		        	.append("grades", Arrays.asList(
		        						new Document()
		        							.append("date", format.parse("2014-10-01T00:00:00Z"))
		        							.append("grade", "A")
		        							.append("score", 11),
		        						new Document()
		        							.append("date", format.parse("2014-01-16T00:00:00Z"))
		        							.append("grade", "B")
		        							.append("score", 17)))
		        	.append("name", "Vella")
		        	.append("restaurant_id", "41704620"));
		
//		FindIterable<Document> iterable = db.getCollection("restaurants").find();
//		FindIterable<Document> iterable = db.getCollection("restaurants").find(new Document("borough", "Manhattan"));
		FindIterable<Document> iterable = db.getCollection("restaurants").find(Filters.eq("address.zipcode", "10075"));
//		FindIterable<Document> iterable = db.getCollection("restaurants").find().sort(Sorts.ascending("borough", "address.zipcode"));
		
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
		
		mc.close();
		
	}

}
