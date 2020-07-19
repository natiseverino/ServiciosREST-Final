package carpinteroseverino;

import carpinteroseverino.model.*;
import carpinteroseverino.model.alert.AlertResponse;
import carpinteroseverino.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    CowRepository cowRepository;

    @Autowired
    HerdRepository herdRepository;

    @Autowired
    BCSRepository bcsRepository;

    @Autowired
    AnimalAlertRepository animalAlertRepository;

    @Autowired
    HerdAlertRepository herdAlertRepository;

    @RequestMapping("/")
    public String index() {
        return "Congratulations from Controller.java";
    }

    @RequestMapping(value = "/cow", method = RequestMethod.POST)
    public ResponseEntity<Cow> createCow(@RequestBody Cow cow) {
        Cow newCow = cow;
        cowRepository.save(newCow);
        return new ResponseEntity<Cow>(newCow, HttpStatus.OK);
    }

    @RequestMapping(value = "/cow/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cow> getCow(@PathVariable("id") int id) {

        try {
            Cow cow = cowRepository.findOne(id);
            return new ResponseEntity<Cow>(cow, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/cows", method = RequestMethod.GET)
    public ResponseEntity<List<Cow>> getCows() {
        return ResponseEntity.ok(cowRepository.findAll());
    }


    @RequestMapping(value = "/herd", method = RequestMethod.POST)
    public ResponseEntity<Herd> createHerd(@RequestBody Herd herd) {
        Herd newHerd = herd;
        herdRepository.save(newHerd);
        return new ResponseEntity<Herd>(newHerd, HttpStatus.OK);

    }

    @RequestMapping(value = "/herd/{id}", method = RequestMethod.GET)
    public ResponseEntity<Herd> getHerd(@PathVariable("id") int id) {
        try {
            Herd herd = herdRepository.findOne(id);
            return new ResponseEntity<Herd>(herd, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/herds", method = RequestMethod.GET)
    public ResponseEntity<List<Herd>> getHerds() {
        return ResponseEntity.ok(herdRepository.findAll());
    }

    @RequestMapping(value = "/bindCowHerd", method = RequestMethod.PUT)
    public ResponseEntity<String> bindCowToHerd(@RequestBody Map<String, Integer> body) {
        int herdId = body.get("HerdId");
        int cowId = body.get("CowId");

        Herd herd = herdRepository.findOne(herdId);
        Cow cow = cowRepository.findOne(cowId);

        if (herd == null && cow == null)
            return new ResponseEntity<String>("Head and cow not found!", HttpStatus.NOT_FOUND);

        if (herd == null)
            return new ResponseEntity<String>("Head not found!", HttpStatus.NOT_FOUND);

        if (cow == null)
            return new ResponseEntity<String>("Cow not found!", HttpStatus.NOT_FOUND);

        if (herd != null && cow != null) {
            herd.addCow(cow);
            cow.setHerd(herd);
            cowRepository.save(cow);
        }

        return ResponseEntity.ok("Herd " + herdId + " and cow " + cowId + " were successfully bound!");
    }

    @RequestMapping(value = "/cow/{id}/bcs", method = RequestMethod.POST)
    public ResponseEntity<AlertResponse> addBCS(@PathVariable("id") int id, @RequestBody Map<String, Integer> body) {

        try {
            int bcs = body.get("bcs");
            Cow cow = cowRepository.findOne(id);
            CowBCS cowBCS = new CowBCS(cow, new Date(), bcs);
            bcsRepository.save(cowBCS);

            AlertResponse response = checkAlert(cow, bcs);
            response.setBcs(cowBCS);

            return new ResponseEntity<AlertResponse>(response, HttpStatus.OK);

        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private AlertResponse checkAlert(Cow cow, int bcs) {
        List<AnimalAlert> animalAlertList = animalAlertRepository.findByCow(cow);
        AlertResponse response = new AlertResponse();

        for (AnimalAlert animalAlert : animalAlertList) {
            if (animalAlert.checkThreshold(bcs)) {
                response.addAlert("ALERT! Cow " + animalAlert.getCowId() + " bcs is " + animalAlert.getCompOpText() + " " + animalAlert.getBcsThreshold());
            }
        }

        Herd herd = cow.getHerd();
        if (herd != null) { // The cow might not be in a herd!!
            List<HerdAlert> herdAlertList = herdAlertRepository.findByHerd(herd);
            for (HerdAlert herdAlert : herdAlertList) {
                if (herdAlert.checkThreshold(herd.getAvgBCS())) {
                    response.addAlert("ALERT! Herd " + herdAlert.getHerdId() + " avg bcs is " + herdAlert.getCompOpText() + " " + herdAlert.getBcsThreshold());
                }
            }
        }


        return response;
    }


    @RequestMapping(value = "/cow/{id}/bcs", method = RequestMethod.GET)
    public ResponseEntity<List<CowBCS>> getBCS(@PathVariable("id") int id) {

        try {
            Cow cow = cowRepository.findOne(id);
            return new ResponseEntity<List<CowBCS>>(cow.getBcs(), HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value = "/cow/{id}/alert", method = RequestMethod.POST)
    public ResponseEntity<AnimalAlert> addAnimalAlert(@PathVariable("id") int id, @RequestBody Map<String, Object> body) {
        try {
            Cow cow = cowRepository.findOne(id);
            int threshold = (Integer) body.get("bcsThreshold");
            String compOp = (String) body.get("compOp");
            AnimalAlert alert = new AnimalAlert(cow, threshold, compOp);

            animalAlertRepository.save(alert);

            return new ResponseEntity<AnimalAlert>(alert, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/cow/{id}/alert", method = RequestMethod.GET)
    public ResponseEntity<List<AnimalAlert>> getCowAlerts(@PathVariable("id") int id) {
        try {
            Cow cow = cowRepository.findOne(id);
            List<AnimalAlert> animalAlertList = animalAlertRepository.findByCow(cow);

            return new ResponseEntity<List<AnimalAlert>>(animalAlertList, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/cow/alerts", method = RequestMethod.GET)
    public ResponseEntity<List<AnimalAlert>> getCowAlerts() {
        try {
            List<AnimalAlert> animalAlertList = animalAlertRepository.findAll();
            return new ResponseEntity<List<AnimalAlert>>(animalAlertList, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/herd/{id}/alert", method = RequestMethod.POST)
    public ResponseEntity<HerdAlert> addHerdAlert(@PathVariable("id") int id, @RequestBody Map<String, Object> body) {
        try {
            Herd herd = herdRepository.findOne(id);
            int threshold = (Integer) body.get("bcsThreshold");
            String compOp = (String) body.get("compOp");
            HerdAlert alert = new HerdAlert(herd, threshold, compOp);

            herdAlertRepository.save(alert);

            return new ResponseEntity<HerdAlert>(alert, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/herd/{id}/alert", method = RequestMethod.GET)
    public ResponseEntity<List<HerdAlert>> getHerdAlerts(@PathVariable("id") int id) {
        try {
            Herd herd = herdRepository.findOne(id);
            List<HerdAlert> herdAlertList = herdAlertRepository.findByHerd(herd);

            return new ResponseEntity<List<HerdAlert>>(herdAlertList, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/herd/alerts", method = RequestMethod.GET)
    public ResponseEntity<List<HerdAlert>> getHerdAlerts() {
        try {
            List<HerdAlert> herdAlertList = herdAlertRepository.findAll();
            return new ResponseEntity<List<HerdAlert>>(herdAlertList, HttpStatus.OK);

        } catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}


