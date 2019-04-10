package capstone.moviewalk.moviewalk;

public class Data {
    String member_id;
    String member_name;
    String member_title;
    String member_latitude;
    String member_longitude;
    String member_address;
    String member_image1;
    String member_image2;
    String member_information;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_title() {
        return member_title;
    }

    public void setMember_title(String member_title) {
        this.member_title = member_title;
    }

    public String getMember_latitude() {
        return member_latitude;
    }

    public void setMember_latitude(String member_latitude) {
        this.member_latitude = member_latitude;
    }

    public String getMember_longitude() {
        return member_longitude;
    }

    public void setMember_longitude(String member_longitude) {
        this.member_longitude = member_longitude;
    }

    public String getMember_address() {
        return member_address;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

    public String getMember_image1() {
        return member_image1;
    }

    public void setMember_image1(String member_image1) {
        this.member_image1 = member_image1;
    }

    public String getMember_image2() {
        return member_image2;
    }

    public void setMember_image2(String member_image2) {
        this.member_image2 = member_image2;
    }

    public String getMember_information() {
        return member_information;
    }

    public void setMember_information(String member_information) {
        this.member_information = member_information;
    }

    public Data(String member_id, String member_name, String member_title, String member_latitude, String member_longitude, String member_address, String member_image1, String member_image2, String member_information) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_title = member_title;
        this.member_latitude = member_latitude;
        this.member_longitude = member_longitude;
        this.member_address = member_address;
        this.member_image1 = member_image1;
        this.member_image2 = member_image2;
        this.member_information = member_information;
    }


}
