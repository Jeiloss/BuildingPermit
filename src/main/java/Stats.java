public class Stats {
    private String name;
    private String parcel;
    private int permit;
    private String address;
    private Short price;
    private String[] types;

    private Short width;
    private Short length;
    private Short height;
    private Short stories;
    private Short area;

    String possMissingField(Short num) {
        if (num == null || num <=0) {
            return "___";
        } else {
            return price.toString();
        }

    }

    String getName() {
        return name;
    }

    String getParcel() {
        return parcel;
    }

    String getPermitNum() {
        return String.valueOf(permit);
    }

    String getAddress() {
        return address;
    }

    String[] getTypes() {
        return types;
    }

    String getPrice() { return possMissingField(price); }

    String getWidth() { return possMissingField(width); }

    String getLength() { return possMissingField(length); }

    String getHeight() { return possMissingField(height); }

    String getStories() { return possMissingField(stories); }

    String getArea() { return possMissingField(area); }

    Stats (String name, String parcel, int permit, String address, String[] types) {
        this.name = name;
        this.parcel= parcel;
        this.permit = permit;
        this.address = address;
        this.types = types;
    }
}