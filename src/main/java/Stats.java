public class Stats {
    String name;
    String parcel;
    int permit;
    String address;
    String[] types;

    String getName() {
        return name;
    }

    String getParcel() {
        return parcel;
    }

    int getPermitNum() {
        return permit;
    }

    String getAddress() {
        return address;
    }

    String[] getTypes() {
        return types;
    }

    Stats (String name, String parcel, int permit, String address, String[] types) {
        this.name = name;
        this.parcel= parcel;
        this.permit = permit;
        this.address = address;
        this.types = types;
    }
}
