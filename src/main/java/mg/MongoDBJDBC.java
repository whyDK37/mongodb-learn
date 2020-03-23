package mg;

import static mg.Config.HOST;
import static mg.Config.PORT;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MongoDBJDBC {

  public static void main(String[] args) {

    MongoCollection<Document> collection = get_collection("test", "test");

    Document document = new Document();
    document.put("name", "why");
    document.put("gender", "male");
    insert_one(collection, document);

    Document one = find_one(collection);
    System.out.println("one = " + one);

    List<Document> documents = new ArrayList<>();
    documents.add(buildDoc("abc", "male"));
    documents.add(buildDoc("mark", "male"));
    insert_many(collection, documents);
    List<Document> many = find_many(collection);
    System.out.println("many = " + many);
  }

  private static Document buildDoc(String mark, String male) {
    Document document = new Document();
    document.put("name", mark);
    document.put("gender", male);
    return document;
  }

  /***
   * 获取一个数据库连接，返回特有文档集合
   * @param dataBase
   * @param collecTion
   * @return
   */
  public static MongoCollection<Document> get_collection(String dataBase, String collecTion) {
    MongoCollection<Document> collection = null;
    try {
      @SuppressWarnings("resource")
      MongoClient mongoClient = new MongoClient(HOST, PORT);
      MongoDatabase mongoDatabase = mongoClient.getDatabase(dataBase);
      collection = mongoDatabase.getCollection(collecTion);//获取数据库dataBase下的集合collecTion，如果没有将自动创建
      System.out.println("成功获取" + dataBase + "数据库下的" + collecTion + "集合");
    } catch (Exception e) {
      System.out.println("请打开MongoDB服务");
    }
    return collection;
  }

  /***
   * 插入一条文档
   * @param collection
   * @param document
   */
  public static void insert_one(MongoCollection<Document> collection, Document document) {
    collection.insertOne(document);
    System.out.println("一条文档成功添加！");
  }

  /**
   * 插入多条文档
   */
  public static void insert_many(MongoCollection<Document> collection, List<Document> documents) {
    collection.insertMany(documents);
    System.out.println("多条文档成功添加！");
  }

  /***
   * 返回查到的第一条文档，无条件
   * @param collection
   * @return
   */
  public static Document find_one(MongoCollection<Document> collection) {

    return collection.find().first();
  }

  /***
   * 返回查询到的所有文档，无条件
   * @param collection
   * @return
   */
  public static List<Document> find_many(MongoCollection<Document> collection) {
    List<Document> documents = new ArrayList<Document>();
    MongoCursor<Document> cursor = collection.find().iterator();
    try {
      while (cursor.hasNext()) {
        documents.add(cursor.next());
      }
    } finally {
      cursor.close();
    }
    return documents;
  }


}