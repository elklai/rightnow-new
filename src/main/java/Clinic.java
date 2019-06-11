import org.bson.codecs.pojo.annotations.BsonProperty;

public class Clinic {

    @BsonProperty("name")
    private String name;
    @BsonProperty("phone")
    private String phone;
    @BsonProperty("address")
    private String address;
    @BsonProperty("openning_hours")
    private String openning_hours;

    public Clinic() {

    }

    public Clinic(String name, String phone, String address, String openning_hours)
    {
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.openning_hours=openning_hours;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenning_hours() {
        return openning_hours;
    }

    public void setOpenning_hours(String openning_hours) {
        this.openning_hours = openning_hours;
    }

}
