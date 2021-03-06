package mac.ada.capstone;

import org.bson.Document;

import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Controller class for our template controller
@RestController
public class TemplateController {

	// Private collection variable for storing contents of our template collection
	private MongoCollection<Document> templateCollection;

	// Constructor for the template controller class
	public TemplateController() {
		// Here we pass in our connection string to our mongo db cluster
		final MongoClient mongoClient = MongoClients.create(
				"ConnectionString");
		final MongoDatabase database = mongoClient.getDatabase("test");

		this.templateCollection = database.getCollection("templateCollection");
	}

	// Method for handling the get request to "/" route
	@RequestMapping("/")
	public String index() {
		StringBuilder builder = new StringBuilder();

		// This method gets all entries from our "Template Collection"
	
		return StreamSupport.stream(this.templateCollection.find().spliterator(), false).map(Document::toJson).collect(Collectors.joining(", ", "[", "]"));
		
		// // Iterate over each entry retrieved, and add it to our return string
		// myDoc.forEach((Block<Document>) t -> {
		// 	builder.append("<div class=\"templateEntry\">");
		// 	builder.append("</br><b>Template Name</b>  </br></br>");
		// 	builder.append(t.getString("Name"));
		// 	builder.append("</br> </br><b>Template</b> </br> </br>");
		// 	builder.append(t.getString("Contents"));
		// 	builder.append("</br> </br><b>Template Creator</b> </br> </br>");
		// 	builder.append(t.getString("Creator"));
		// 	builder.append("</div></br></br></br>");
		// });

		// return myDoc;
	}

	// Method for handling post calls to add templates to our mongo db
	@PostMapping("/")
	public ResponseEntity postController(@RequestBody TemplateRequest templateRequest) {

		// Call the ToDocument() method on the template request object to turn it into a
		// Document object
		Document requestDoc = templateRequest.ToDocument();
		if (requestDoc != null) {
			// If the result is non-null, insert into collection
			this.templateCollection.insertOne(requestDoc);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return null;
		}
	}
}
