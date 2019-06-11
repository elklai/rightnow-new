import java.util.ArrayList;
import java.util.HashMap;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
public class User_App{
    @BsonProperty("id")
    private String id;
    @BsonProperty("user_name")
    private String user_name;
    @BsonProperty("password")
    private String password;
    @BsonProperty("answers")
    private ArrayList<String> answers;
    @BsonProperty("rights")
    private ArrayList<String> rights;
    @BsonProperty("running_quiz_id")
    private Integer running_quiz_id;
    @BsonProperty("county")
    private String county;

    public User_App(){}

    public User_App(String id, String user_name, String password ,ArrayList<String> answers,ArrayList<String> rights, Integer running_quiz , String county) {
        this.id =id;
        this.user_name=user_name;
        this.password=password;
        this.answers = answers;
        this.rights =rights;
        this. running_quiz_id=running_quiz;
        this.county=county;
        this.answers=new ArrayList<String>();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void add_right(String right_name)
    {
        if(!rights.contains(right_name))
            rights.add(right_name);
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getRights() {
        return rights;
    }

    public void setRights(ArrayList<String> rights) {
        this.rights = rights;
    }

    public Integer getRunning_quiz_id() {
        return running_quiz_id;
    }

    public void setRunning_quiz_id(Integer running_quiz_id) {
        this.running_quiz_id = running_quiz_id;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

}

