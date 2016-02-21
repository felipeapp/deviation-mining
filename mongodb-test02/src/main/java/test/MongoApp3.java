package test;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class MongoApp3 {

	public static void main(String[] args) throws Exception {

		MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "db-test");

		mongoOps.dropCollection(Person.class);

		List<Person> list = Arrays.asList(new Person("Thor", 30, new Address("A Street", Arrays.asList(123, 456))),
				new Person(null, 18, new Address("C Street", Arrays.asList(125, 1245, 102))),
				new Person("", 30, new Address("B Street", Arrays.asList(789, 1012))), new Person("Batman", 40, null));

		for (Person p : list) {
			mongoOps.save(p);
			System.out.println("Saved: " + p);
		}

		List<Person> people = mongoOps.find(query(where("name").exists(true)), Person.class);
		System.out.println("# Number of people: " + people.size());
		for (Person p : people) {
			System.out.println("# " + p);
		}

	}
}