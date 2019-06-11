import java.util.ArrayList;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.conversions.Bson;

public class Right implements Bson{

    @BsonProperty("id")
    private Integer id;
    @BsonProperty("name")
    private String right_name;
    @BsonProperty("description")
    private String description;
    @BsonProperty("formulas")
    private ArrayList<String> formulas;
//	@BsonProperty("counties")
//	private ArrayList<Right_Info> counties;


    public Right(Integer id,String right_name, String description, ArrayList<String> formulas) {
        this.id =id;
        this.right_name = right_name;
        this.description = description;
        this.formulas = formulas;
//		this.counties= counties;
        // TODO Auto-generated constructor stub
    }

    public Right() {
        // TODO Auto-generated constructor stub
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getRight_name() {
        return right_name;
    }

    public void setRight_name(String right_name) {
        this.right_name = right_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getFormulas() {
        return formulas;
    }

    public void setFormulas(ArrayList<String> formulas) {
        this.formulas = formulas;
    }

    /*	public ArrayList<Right_Info> getCounties() {
            return counties;
        }

        public void setCounties(ArrayList<Right_Info> counties) {
            this.counties = counties;
        }
    */
    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<Right>(this, codecRegistry.get(Right.class));
    }


}
