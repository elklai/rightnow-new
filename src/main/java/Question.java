import java.util.ArrayList;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;


public class Question{

    @BsonProperty("_id")
    private ObjectId _id;
    @BsonProperty("id")
    private Integer id;
    @BsonProperty("web_var")
    private String web_var;
    @BsonProperty("type")
    private Boolean type;//true for close
    @BsonProperty("is_text_ans")
    private Boolean is_text_ans;
    @BsonProperty("question")
    private String question;
    @BsonProperty("num_of_options")
    private Integer num_of_options;
    @BsonProperty("options")
    private ArrayList<String> options;
    @BsonProperty("notes")
    private String notes;
    @BsonProperty("answer_s")
    private String answer_s;
    @BsonProperty("answer_i")
    private Integer answer_i;

    public Question() {}

    public Question(Integer id, String web_var,Boolean type,Boolean is_text_ans, String question,ArrayList<String> options,String s_answer,Integer i_answer,String notes){
        this.id=id;
        this.web_var= web_var;
        this.type=type;
        this.is_text_ans=is_text_ans;
        this.question=question;
        this.num_of_options=options.size();
        this.options=options;
        this.notes=notes;
        this.answer_i=null;
        this.answer_s=null;

    }

    public ObjectId get_id() {
        return _id;
    }
    public void set_id(ObjectId _id) {
        this._id = _id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWeb_var() {
        return web_var;
    }
    public void setWeb_var(String web_var) {
        this.web_var = web_var;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public Boolean getType() {
        return type;
    }
    public void setType(Boolean type) {
        this.type = type;
    }
    public Boolean getIs_text_ans() {
        return is_text_ans;
    }
    public void setIs_text_ans(Boolean is_text_ans) {
        this.is_text_ans = is_text_ans;
    }
    public Integer getNum_of_options() {
        return num_of_options;
    }
    public void setNum_of_options(Integer num_of_options) {
        this.num_of_options = num_of_options;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
    public String getNote() {
        return notes;
    }
    public void setNote(String note) {
        this.notes = note;
    }

    public Integer getAnswer_i() {
        return answer_i;
    }

    public void setAnswer_i(Integer answer_i) {
        this.answer_i = answer_i;
    }

    public String getAnswer_s() {
        return answer_s;
    }

    public void setAnswer_s(String answer_s) {
        this.answer_s = answer_s;
    }
}
