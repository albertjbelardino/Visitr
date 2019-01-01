package Objects;

public class BaseTour {

    public String name;
    public String city;
    public int ID;

    public BaseTour(int ID, String name, String city) {
        this.name = name;
        this.city = city;
        this.ID = ID;
    }

    public BaseTour() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
