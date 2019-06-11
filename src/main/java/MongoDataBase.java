import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.json.*;
import org.bson.Document;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDataBase {



//	Right[] rights;
//	Question[] questions;
//	User_App[] app_users;
//	Boolean[] is_right_achived;///TODO: gooo to mongo with all rights allready achived


    private Gson gson;
    private String uri;
    private MongoClient mongo_client;
    private MongoDatabase mongo_data_base;
    private User_App app_user;

    private Integer max_rate;
    private String max_var;

    private MongoCollection<Question> questions;
    private MongoCollection<Right> rights;
    private MongoCollection<User_App> app_users;

    private HashMap<String , Integer> rates;
    private HashMap<String, String> web_var_TO_var;
    private HashMap<String, String> signs;

    private static class MongoDataBaseHolder {
        private final static MongoDataBase INSTANCE = new MongoDataBase();
    }

    public static MongoDataBase getInstance() {
        return MongoDataBaseHolder.INSTANCE;
    }

    private MongoDataBase(){
        CodecRegistry pojoCodecRegistries = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        gson = new Gson();

        max_rate=0;
        max_var="";
        rates = new HashMap<String,Integer>();
        signs = new HashMap<String, String>();
        web_var_TO_var =new HashMap<String, String>();
        uri ="mongodb+srv://ron:nuE9ew__@cluster0-bkou6.gcp.mongodb.net/test?retryWrites=true";
        mongo_client= MongoClients.create(uri);
        mongo_data_base= mongo_client.getDatabase("RightNow");
        rights = mongo_data_base.getCollection("Rights", Right.class).withCodecRegistry(pojoCodecRegistries);

        questions = mongo_data_base.getCollection("Questions", Question.class).withCodecRegistry(pojoCodecRegistries);
        app_users = mongo_data_base.getCollection("AppUsers", User_App.class).withCodecRegistry(pojoCodecRegistries);



//		FindIterable<Right> findIterable_r = rights.find(new User_App());
//		MongoCursor<Right> iter_r = findIterable_r.iterator();
//		while(iter_r.hasNext())
//		{
//			System.out.println(iter_r.next().getRight_name());
//		}
//





        Bson d2 = new Document();
        FindIterable<Question> findIterable_q = questions.find(d2);
        MongoCursor<Question> iter_q = findIterable_q.iterator();
        while(iter_q.hasNext())
        {
            Question q=iter_q.next();
            web_var_TO_var.put(	q.getWeb_var(), make_algo_var(q));
            rates.put("var"+q.getId(), 0);
        }

        signs = new HashMap<String,String>();
        signs.put("=", ".equals(");
        signs.put("או", "||");
        signs.put("וגם", "&&");
        signs.put("=<", "<=");
        signs.put("=>", ">=");
        signs.put("=>", ">=");
        signs.put("+", "+");
        signs.put(">", ">");
        signs.put("<", "<");
        signs.put("-", "-");
        signs.put("/", "/");
        signs.put("*", "*");
        signs.put("^", "^");
        signs.put("%", "%");

    }


    public MongoCollection<Question> getQuestions() {
        return questions;
    }
    public MongoCollection<Right> getRights() {
        return rights;
    }
    public MongoCollection<User_App> getApp_users() {
        return app_users;
    }

    public String make_algo_var(Question q)
    {
        String type = "";
        if(q.getIs_text_ans())
            type = "get_svar";
        else
            type = "get_ivar";
        return "db."+type+"(\"var"+q.getId()+"\")";

    }

    public void setApp_user(User_App app_user)
    {
        this.app_user = app_user;
    }

    public String get_max_var()
    {
        if(max_rate.equals(0))
            return "finish";
        return max_var;
    }
    public User_App getApp_user()
    {
        return this.app_user;
    }

    public  String get_var(String name)
    {
        String output=this.web_var_TO_var.get(name);
        if (output!=null)
            return output;
        output =this.signs.get(name);
        if(output!=null)
            return output;
        if(isNumeric(name))
            return name;
        return "\""+name+"\"";
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public String get_svar(String var_name)
    {
        Integer var_id = Integer.valueOf(var_name.substring(3));
        String output= app_user.getAnswers().get(var_id);
        Integer curr_rate;
        if(output.equals(""))
        {
            curr_rate = rates.get(var_name)+1;
            rates.put(var_name,curr_rate);
            if(curr_rate>max_rate)
            {
                max_rate=curr_rate;
                max_var=var_name;
            }
        }
        return output;
    }

    public Integer get_ivar(String var_name)
    {
        return Integer.valueOf(get_svar(var_name));
    }
    public <C> MongoCollection<C>  get(Class<C> C,String collection_name){
        CodecRegistry pojoCodecRegistries = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return mongo_data_base.getCollection(collection_name,C).withCodecRegistry(pojoCodecRegistries);
////		User_App[] arr =(User_App[]) app_users.toArray(new User_App[app_users.size()]);
////		FindIterable<User_App> findIterable = app_users.find(new User_App());
////		MongoCursor<User_App> iter = findIterable.iterator();
//		while(iter.hasNext())
//		{
//			User_App user =iter.next();
//			System.out.println(user.getId());
//		}
////

//		String uri ="mongodb+srv://ron:nuE9ew__@cluster0-bkou6.gcp.mongodb.net/test?retryWrites=true";
//		MongoClient mongo_client= MongoClients.create(uri);
//		MongoDatabase mongo_data_base= mongo_client.getDatabase("RightNow");
//		MongoCollection<User_App> app_users = mongo_data_base.getCollection("AppUsers",User_App.class);
//		FindIterable<User_App> findIterable = app_users.find(new User_App());
//		MongoCursor<User_App> iter = findIterable.iterator();
//		while(iter.hasNext())
//		{
//			User_App user =iter.next();
//			System.out.println(user.getId());
//		}
    }

    public Question question_from_document(Document doc)
    {
        Question output = new Question();
        output.set_id(doc.getObjectId("_id"));
        output.setId(doc.getInteger("id", 0));
        output.setWeb_var(doc.getString("web_var"));
        output.setType(doc.getBoolean("type"));
        output.setIs_text_ans(doc.getBoolean("is_text_ans"));
        output.setQuestion(doc.getString("question"));
        output.setNum_of_options(doc.getInteger("num_of_options", 0));
        List<String> l= doc.getList("options", String.class);
        //	output.setOptions((String[]) l.toArray(new String[l.size()]));
        output.setNote(doc.getString("notes"));
        output.setAnswer_s(doc.getString("answer_s"));
        output.setAnswer_i(doc.getInteger("answer_i"));
        return output;


    }

    public Boolean is_user_achived_right(String right)
    {
        return this.app_user.getRights().contains(right);
    }



}
