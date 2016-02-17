package test;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.MongoClient;

public class MongoApp1 {

	public static void main(String[] args) throws Exception {

		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "db-test"));

		List<Person> list = Arrays.asList(new Person("Thor", 30, new Address("A Street", Arrays.asList(123, 456))),
				new Person("Spider-man", 18, new Address("C Street", Arrays.asList(125, 1245, 102))),
				new Person("Deadpool", 30, new Address("B Street", Arrays.asList(789, 1012))),
				new Person("Batman", 40, new Address("D Street", Arrays.asList(212))));

		for (Person p : list) {
			mongoOps.insert(p);
			System.out.println("Inserted: " + p);
		}

		System.out.println("---------------------------------");

		for (Person p : list) {
			Person result = mongoOps.findById(p.getId(), Person.class);
			System.out.println("Found: " + result);
		}

		mongoOps.updateFirst(query(where("name").is("Deadpool")),
				new Update().set("address.street", "X Street").set("age", 31), Person.class);
		Person r1 = mongoOps.findOne(query(where("name").is("Deadpool")), Person.class);
		System.out.println("Updated: " + r1);

		Person r2 = mongoOps.findOne(query(where("name").is("Batman").and("age").is(40)), Person.class);
		System.out.println("Found: " + r2);

		List<Person> people = mongoOps.findAll(Person.class);
		System.out.println("# Number of people: " + people.size());
		for (Person p : people) {
			System.out.println("# " + p);
		}

		mongoOps.dropCollection(Person.class);
	}
}