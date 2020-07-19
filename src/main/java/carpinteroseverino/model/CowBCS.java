package carpinteroseverino.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CowBCS {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonBackReference
    private Cow cow;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private int bcs;


    public CowBCS() {
    }

    public CowBCS(Cow cow, Date date, int bcs) {
        this.cow = cow;
        this.date = date;
        this.bcs = bcs;
        this.cow.addBCS(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cow getCow() {
        return cow;
    }

    public void setCow(Cow cow) {
        this.cow = cow;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBcs() {
        return bcs;
    }

    public void setBcs(int bcs) {
        this.bcs = bcs;
    }
}
