import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;

//import org.bson.codecs.pojo.annotations.BsonProperty;

public class Right_Info {

    @BsonProperty("right_name")
    private String right_name;
    @BsonProperty("county")
    private String county;
    @BsonProperty("unit")
    private String unit;
    @BsonProperty("address")
    private String address;
    @BsonProperty("post_office_box")
    private String post_office_box;
    @BsonProperty("zip_code")
    private String zip_code;
    @BsonProperty("activity_hours")
    private String activity_hours;
    @BsonProperty("phone")
    private String phone;
    @BsonProperty("extension")
    private String extension;
    @BsonProperty("fax")
    private String fax;
    @BsonProperty("email")
    private String email;
    @BsonProperty("accessibility")
    private String accessibility;
    @BsonProperty("num_of_clinics")
    private int num_of_clinics;
    @BsonProperty("clinics")
    private ArrayList<Clinic> clinics;
    @BsonProperty("documents")
    private String documents;
    @BsonProperty("web")
    private String web;

    public Right_Info(){}

    public Right_Info(String right_name, String county, String unit, String address, String post_office_box, String zip_code, String activity_hours, String phone, String extension, String fax, String email, String accessibility, int num_of_clinics, ArrayList<Clinic> clinics) {
        this.right_name=right_name;
        this.county = county;
        this.unit = unit;
        this.address = address;
        this.post_office_box = post_office_box;
        this.zip_code = zip_code;
        this.activity_hours = activity_hours;
        this.phone = phone;
        this.extension = extension;
        this.fax = fax;
        this.email = email;
        this.accessibility = accessibility;
        this.num_of_clinics = num_of_clinics;
        this.clinics = clinics;
    }

    public String getRight_name() {
        return right_name;
    }

    public void setRight_name(String right_name) {
        this.right_name = right_name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost_office_box() {
        return post_office_box;
    }

    public void setPost_office_box(String post_office_box) {
        this.post_office_box = post_office_box;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getAcitvity_hours() {
        return activity_hours;
    }

    public void setAcitvity_hours(String acitvity_hours) {
        this.activity_hours = acitvity_hours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public int getNum_of_clinics() {
        return num_of_clinics;
    }

    public void setNum_of_clinics(int num_of_clinics) {
        this.num_of_clinics = num_of_clinics;
    }

    public ArrayList<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(ArrayList<Clinic> clinics) {
        this.clinics = clinics;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getDocuments() {
        return documents;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getWeb() {
        return web;
    }

}
