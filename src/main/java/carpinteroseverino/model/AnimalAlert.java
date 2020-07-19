package carpinteroseverino.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class AnimalAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonBackReference
    @NotNull
    private Cow cow;

    private String compOp;
    private int bcsThreshold;


    public AnimalAlert() {
    }

    public AnimalAlert(Cow cow, int bcsThreshold, String compOp) {
        this.cow = cow;
        this.bcsThreshold = bcsThreshold;
        this.compOp = compOp; //GT: GreaterThan, LT: LesserThan
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompOp() {
        return compOp;
    }

    @JsonIgnore
    public String getCompOpText() {
        if (compOp.equals("GT"))
            return "greater than";
        else
            return "lesser than";
    }

    public void setCompOp(String compOp) {
        this.compOp = compOp;
    }

    public Cow getCow() {
        return cow;
    }

    public void setCow(Cow cow) {
        this.cow = cow;
    }

    public int getBcsThreshold() {
        return bcsThreshold;
    }

    public void setBcsThreshold(int bcsThreshold) {
        this.bcsThreshold = bcsThreshold;
    }

    public boolean checkThreshold(int bcs) {
        if (compOp.equals("GT"))
            return bcs > bcsThreshold;
        else if (compOp.equals("LT"))
            return bcs < bcsThreshold;

        return false;
    }

    @Override
    public String toString() {
        return "AnimalAlert{" +
                "id=" + id +
                ", cow=" + cow +
                ", compOp='" + compOp + '\'' +
                ", bcsThreshold=" + bcsThreshold +
                '}';
    }

    @JsonProperty
    public Integer getCowId() {
        return cow == null ? null : cow.getId();
    }

}
