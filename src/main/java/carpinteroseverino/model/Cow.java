package carpinteroseverino.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int electronicId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthdate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date lastDateOfBirth;
    private int birthCount;
    private double weight;


    @ManyToOne
    @JsonBackReference
    private Herd herd;

    @OneToMany
    @JsonManagedReference
    @JsonIgnore
    private List<CowBCS> bcs;


    public Cow() {
        this.bcs = new ArrayList<>();
    }

    public Cow(int electronicId, Date birthdate, Date lastDateOfBirth, int birthCount, double weight) {
        this.electronicId = electronicId;
        this.birthdate = birthdate;
        this.lastDateOfBirth = lastDateOfBirth;
        this.birthCount = birthCount;
        this.weight = weight;
        this.bcs = new ArrayList<>();
    }

    public void addBCS(CowBCS cowBCS){
        this.bcs.add(cowBCS);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getElectronicId() {
        return electronicId;
    }

    public void setElectronicId(int electronicId) {
        this.electronicId = electronicId;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getLastDateOfBirth() {
        return lastDateOfBirth;
    }

    public void setLastDateOfBirth(Date lastDateOfBirth) {
        this.lastDateOfBirth = lastDateOfBirth;
    }

    public int getBirthCount() {
        return birthCount;
    }

    public void setBirthCount(int birthCount) {
        this.birthCount = birthCount;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Herd getHerd() {
        return herd;
    }

    public void setHerd(Herd herd) {
        this.herd = herd;
    }

    @JsonProperty
    public Integer getHerdId() {
        return herd == null ? null : herd.getId();
    }

    @JsonProperty
    public Integer getLastBCS() {
        return bcs.size() == 0 ? null : bcs.get(bcs.size()-1).getBcs();
    }


    @JsonIgnore
    public List<CowBCS> getBcs() {
        return bcs;
    }

    @Override
    public String toString() {
        return "Cow ="+id;
    }
}
