import java.util.LinkedList;
import java.util.List;
import org.ainslec.picocog.PicoWriter;
import org.bson.Document;
import org.ainslec.picocog.PicoWriter;
import org.bson.RawBsonDocument;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
public class Code implements DoStuff{
MongoDataBase db =MongoDataBase.getInstance();
public String doStuff(){
if(!db.is_user_achived_right("סיוע משפטי")){
try{if ( db.get_ivar("var2") < 10)
{
	System.out.println(db.getApp_user());
db.getApp_user().add_right("סיוע משפטי");
}
}
catch(NumberFormatException e){}

}String max_var = db.get_max_var();
Bson filter = new Document("id",db.getApp_user().getId());
		db.getApp_users().replaceOne(filter,db.getApp_user());
if(!max_var.equals("finish")){
return max_var.substring(3);
}
return max_var;
}

}
