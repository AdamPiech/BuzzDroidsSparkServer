package dataModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-08.
 */

public class BeaconList {

    private String command;
    private List<Beacon> beacons;
    private LocalDateTime time;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
