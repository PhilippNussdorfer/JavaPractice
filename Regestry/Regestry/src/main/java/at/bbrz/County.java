package at.bbrz;

public class County {
    private String isoCode;
    private String name;

    public County(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }

    @Override
    public String toString() {
        return "name: " + name + "\nisoCode: " +isoCode;
    }
}
