package carpinteroseverino.model.alert;

import carpinteroseverino.model.CowBCS;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlertResponse {

    CowBCS bcs;
    List<String> alerts;

    public AlertResponse() {
    }

    public CowBCS getBcs() {
        return bcs;
    }

    public void setBcs(CowBCS bcs) {
        this.bcs = bcs;
    }

    public boolean addAlert(String alert){
        if(alerts == null) {
            alerts = new ArrayList<>();
        }

        return alerts.add(alert);
    }


    public List<String> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<String> alerts) {
        this.alerts = alerts;
    }
}
