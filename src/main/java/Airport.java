import exceptions.NonMilitaryPlainException;
import models.MilitaryType;
import planes.ExperimentalPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.*;


public class Airport {
    private List<? extends Plane> planes;


    public List<PassengerPlane> getPassengerPlanes() {
        List<PassengerPlane> passengersPlaneList = new ArrayList<>();
        planes.forEach(plane -> {
            if (isPassengerPlane(plane)) {
                passengersPlaneList.add((PassengerPlane) plane);
            }
        });
        return passengersPlaneList;
    }


    private boolean isPassengerPlane(Plane plane) {
        return (plane instanceof PassengerPlane) ? true : false;
    }

    public List<MilitaryPlane> getMilitaryPlanes()  {
        List<MilitaryPlane> militaryPlanes = new ArrayList<>();
        for (Plane plane : planes) {
            if (plane instanceof MilitaryPlane) {
                militaryPlanes.add((MilitaryPlane) plane);
            }
        }
        return militaryPlanes;
    }


    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        List<PassengerPlane> passengerPlanes = getPassengerPlanes();
        return Collections.max(passengerPlanes, Comparator.comparing(PassengerPlane::getPassengersCapacity));

    }

    public List<MilitaryPlane> getMilitaryPlanesByType(MilitaryType type) {
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();
        List militaryPlanesByType = new ArrayList<>();
        for (int i = 0; i < militaryPlanes.size(); i++) {
            MilitaryPlane militaryPlain = militaryPlanes.get(i);
            if (isExpectedMilitaryPlaneType(militaryPlain, type)) {
                militaryPlanesByType.add(militaryPlain);
            }
        }
        return militaryPlanesByType;
    }


    public boolean isExpectedMilitaryPlaneType(MilitaryPlane plane, MilitaryType militaryType) {
        return plane.getType() == militaryType ? true : false;
    }


    public List<ExperimentalPlane> getExperimentalPlanes() {
        List<ExperimentalPlane> experimentalPlanes = new ArrayList<>();
        return findInCycleExperimentalPlanes(experimentalPlanes);
    }


    public List<ExperimentalPlane> findInCycleExperimentalPlanes(List<ExperimentalPlane> experimentalPlanes) {
        planes.forEach(plane -> {
            if (plane instanceof ExperimentalPlane) {
                experimentalPlanes.add((ExperimentalPlane) plane);
            }
        });
        return experimentalPlanes;
    }


    public List<? extends Plane> sortByMaxDistance(List<? extends Plane> planes) {
        planes.forEach(plane -> {
            Collections.sort(planes, new Comparator<Plane>() {
                @Override
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxFlightDistance() - o2.getMaxFlightDistance();
                }
            });
        });
        return planes;
    }


    public List<? extends Plane> sortByMaxSpeed(List<? extends Plane> planes) {
        planes.forEach(plane -> {
            Collections.sort(planes, new Comparator<Plane>() {
                @Override
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxSpeed() - o2.getMaxSpeed();
                }
            });
        });
        return planes;
    }

    public List<? extends Plane> sortByMaxLoadCapacity(List<? extends Plane> planes) {
        planes.forEach(plane -> {
            Collections.sort(planes, new Comparator<Plane>() {
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxLoadCapacity() - o2.getMaxLoadCapacity();
                }
            });
        });
        return planes;
    }

    public List<? extends Plane> getPlanes() {
        return planes;
    }


    @Override
    public String toString() {
        return "Airport{" +
                "planes=" + planes.toString() +
                '}';
    }


    public Airport(List<? extends Plane> planes) {
        this.planes = planes;
    }
}
