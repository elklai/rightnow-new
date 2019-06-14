import java.io.IOException;
import java.util.ArrayList;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;


public class  App
{
    public static void main(String[] args )
    {

        User_App ron = null;
        MongoDataBase db =MongoDataBase.getInstance();
        MongoCollection<User_App> app_users=db.getApp_users();
        Bson d1 = new Document("id",args[0]);
        FindIterable<User_App> findIterable_a = app_users.find(d1);
        MongoCursor<User_App> iter_a = findIterable_a.iterator();
        while(iter_a.hasNext())
        {
            ron = iter_a.next();
        }
        db.setApp_user(ron);
        Code_gen gen=new Code_gen(db);
        try{
            System.out.println(gen.generator());
        }
        catch(IOException e)
        {
            System.out.println("Error in get Code - throw IOException");
        }
    }
}
