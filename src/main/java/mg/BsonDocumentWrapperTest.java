package mg;

import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import com.mongodb.DBObjectCodecProvider;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.Document;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class BsonDocumentWrapperTest {

  private static final CodecRegistry DEFAULT_REGISTRY =
      fromProviders(asList(new ValueCodecProvider(), new BsonValueCodecProvider(),
          new DBObjectCodecProvider(), new DocumentCodecProvider()));

//  private static final ProvidersCodecRegistry DEFAULT_REGISTRY =
//      new ProvidersCodecRegistry(asList(new ValueCodecProvider(), new BsonValueCodecProvider(),
//          new DBObjectCodecProvider()));

  public static void main(String[] args) {

    Document document = new Document();
    document.put("name", "why");
    document.put("gender", "male");
    BsonDocument bsonDocument = BsonDocumentWrapper.asBsonDocument(document, DEFAULT_REGISTRY);
    System.out.println("bsonDocument.toJson() = " + bsonDocument.toJson());
    System.out.println("bsonDocument.values() = " + bsonDocument.values());

  }

}
