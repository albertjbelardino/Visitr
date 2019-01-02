package jackal.visitr.visitr;

public class FullInfoTour {

    public String name;
    public String city;
    public int ID;
    public String genre;
    public int totaltime;
    public int numstops;
    public String[] keywords;
    public String tourdescription;
    public enum type{
        WALKING, DRIVING, HYBRID
    }
    public type thisType;


    public FullInfoTour(int ID, String name, String city, String genre, int totaltime, int numstops, String[] keywords, String tourdescription, type tourtype) {
        this.name = name;
        this.ID = ID;
        this.city = city;
        this.genre = genre;
        this.totaltime = totaltime;
        this.numstops = numstops;
        this.keywords = keywords;
        this.tourdescription = tourdescription;
        this.thisType = tourtype;

    }
}
