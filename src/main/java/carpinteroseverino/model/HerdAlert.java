package carpinteroseverino.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.ScopeMetadata;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class HerdAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JsonBackReference
    @NotNull
    private Herd herd;

    private String compOp;

    private int bcsThreshold;


    public HerdAlert() {
    }

    public HerdAlert(Herd herd, int bcsThreshold, String compOp) {
        this.herd = herd;
        this.bcsThreshold = bcsThreshold;
        this.compOp = compOp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Herd getHerd() {
        return herd;
    }

    public void setHerd(Herd herd) {
        this.herd = herd;
    }

    public int getBcsThreshold() {
        return bcsThreshold;
    }

    public void setBcsThreshold(int bcsThreshold) {
        this.bcsThreshold = bcsThreshold;
    }

    public String getCompOp() {
        return compOp;
    }

    public void setCompOp(String compOp) {
        this.compOp = compOp;
    }

    public boolean checkThreshold(double bcs) {
        if (compOp.equals("GT"))
            return bcs > bcsThreshold;
        else if (compOp.equals("LT"))
            return bcs < bcsThreshold;

        return false;
    }

    @JsonIgnore
    public String getCompOpText() {
        if (compOp.equals("GT"))
            return "greater than";
        else
            return "lesser than";
    }

    @JsonProperty
    public Integer getHerdId() {
        return herd == null ? null : herd.getId();
    }
}
