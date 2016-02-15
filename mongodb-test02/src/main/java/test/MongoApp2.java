package test;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

public class MongoApp2 {

	public static void main(String[] args) throws Exception {

		MongoOperations mongoOps = new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "db-test"));

		List<Person> list = Arrays.asList(new Person("Thor", 30, new Address("A Street", Arrays.asList(123, 456))),
				new Person("Spider-man", 18, new Address("C Street", Arrays.asList(125, 1245, 102))),
				new Person("Deadpool", 30, new Address("B Street", Arrays.asList(789, 1012))),
				new Person("Batman", 40, new Address("D Street", Arrays.asList(212))));

		for (Person p : list) {
			mongoOps.save(p);
			System.out.println("Saved: " + p);
		}

		System.out.println("---------------------------------");

		for (Person p : list) {
			p.setAge(p.getAge() + 1);
			p.getAddress().setStreet("New " + p.getAddress().getStreet());
			mongoOps.save(p);
			System.out.println("Updated: " + p);
		}

		System.out.println("---------------------------------");

		List<Person> people = mongoOps.findAll(Person.class);
		System.out.println("# Number of people: " + people.size());
		for (Person p : people) {
			System.out.println("# " + p);
		}

		mongoOps.dropCollection(Person.class);

	}
}