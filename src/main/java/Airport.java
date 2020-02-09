import exceptions.NonMilitaryPlainException;
import models.MilitaryType;
import planes.ExperimentalPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.*;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


public class Airport {
    private List<? extends Plane> planes;


    public List<PassengerPlane> getPassengersPlane() {
        List<PassengerPlane> passengersPlaneList = new ArrayList<>();
        for (Plane plane : planes) {
            addPlaneIfItIsPassenger(plane, passengersPlaneList);
        }
        return passengersPlaneList;
    }

    public List<PassengerPlane> addPlaneIfItIsPassenger(Plane plane, List<PassengerPlane> passengerPlaneList) {
        if (plane instanceof PassengerPlane) {
            passengerPlaneList.add((PassengerPlane) plane);
        } else {
            LOGGER.info("Plane is not passenger");
        }
        return passengerPlaneList;
    }


    public List<MilitaryPlane> getMilitaryPlanes() throws NonMilitaryPlainException {
        List<MilitaryPlane> militaryPlanes = new ArrayList<MilitaryPlane>();
        for (Plane plane : planes) {
            addPlaneIfItIsMilitary(plane, militaryPlanes);
        }
        return militaryPlanes;
    }

    public List<MilitaryPlane> addPlaneIfItIsMilitary(Plane plane, List<MilitaryPlane> militaryPlanes) {
        if (plane instanceof MilitaryPlane) {
            militaryPlanes.add((MilitaryPlane) plane);
        } else {
            LOGGER.info("Plane is not military");
        }
        return militaryPlanes;
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() throws NonMilitaryPlainException {
        List<PassengerPlane> passengerPlanes = getPassengersPlane();
        PassengerPlane planeWithMaxCapacity = passengerPlanes.get(0);
        return findInCyclePlaneWithMaxCapacity(passengerPlanes, planeWithMaxCapacity);
    }

    public PassengerPlane findInCyclePlaneWithMaxCapacity(List<PassengerPlane> passengerPlanes, PassengerPlane planeWithMaxCapacity) throws NonMilitaryPlainException {
        for (int i = 0; i < getPassengersPlane().size(); i++) {
            if (passengerPlanes.get(i).getPassengersCapacity() > planeWithMaxCapacity.getPassengersCapacity()) {
                planeWithMaxCapacity = passengerPlanes.get(i);
            }
        }
        return planeWithMaxCapacity;
    }


    public List<MilitaryPlane> getTransportMilitaryPlanes() throws NonMilitaryPlainException {
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();
        List<MilitaryPlane> transportMilitaryPlanes = new ArrayList<>();
        return findInCycleMilitaryPlane(militaryPlanes, transportMilitaryPlanes);
    }


    public List<MilitaryPlane> findInCycleMilitaryPlane(List<MilitaryPlane> militaryPlanes, List<MilitaryPlane> transportMilitaryPlanes) throws NonMilitaryPlainException {
        for (int i = 0; i < getMilitaryPlanes().size(); i++) {
            MilitaryPlane plane = militaryPlanes.get(i);
            if (plane.getType() == MilitaryType.TRANSPORT) {
                transportMilitaryPlanes.add(plane);
            }
        }
        return transportMilitaryPlanes;
    }

    public List<MilitaryPlane> getBomberMilitaryPlanes() throws NonMilitaryPlainException {
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();
        List<MilitaryPlane> bomberMilitaryPlanes = new ArrayList<>();
        return findInCycleBomberMilitaryPlanes(militaryPlanes, bomberMilitaryPlanes);
    }


    public List<MilitaryPlane> findInCycleBomberMilitaryPlanes(List<MilitaryPlane> militaryPlanes, List<MilitaryPlane> bomberMilitaryPlanes) throws NonMilitaryPlainException {
        {
            for (int i = 0; i < getMilitaryPlanes().size(); i++) {
                MilitaryPlane plane = militaryPlanes.get(i);
                if (plane.getType() == MilitaryType.BOMBER) {
                    bomberMilitaryPlanes.add(plane);
                }
            }
        }
        return bomberMilitaryPlanes;
    }


    public List<ExperimentalPlane> getExperimentalPlanes() {
        List<ExperimentalPlane> experimentalPlanes = new ArrayList<>();
        return findInCycleExperimentalPlanes(experimentalPlanes);
    }

    public List<ExperimentalPlane> findInCycleExperimentalPlanes(List<ExperimentalPlane> experimentalPlanes) {
        for (Plane plane : planes) {
            if (plane instanceof ExperimentalPlane) {
                experimentalPlanes.add((ExperimentalPlane) plane);
            }
        }
        return experimentalPlanes;
    }


    public List<? extends Plane> sortByMaxDistance(List<? extends Plane> planes) {
        for (Plane plane :
                planes) {
            Collections.sort(planes, new Comparator<Plane>() {
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxFlightDistance() - o2.getMaxFlightDistance();
                }
            });
        }
        return planes;
    }


    public List<? extends Plane> sortByMaxSpeed(List<? extends Plane> planes) {
        for (Plane plane :
                planes) {
            Collections.sort(planes, new Comparator<Plane>() {
                @Override
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxSpeed() - o2.getMaxSpeed();
                }
            });
        }
        return planes;
    }

    public List<? extends Plane> sortByMaxLoadCapacity(List<? extends Plane> planes) {
        for (Plane plane :
                planes) {
            Collections.sort(planes, new Comparator<Plane>() {
                public int compare(Plane o1, Plane o2) {
                    return o1.getMaxLoadCapacity() - o2.getMaxLoadCapacity();
                }
            });
        }
        return planes;
    }

    public List<? extends Plane> getPlanes() {
        return planes;
    }

    private void print(Collection<? extends Plane> collection) {
        Iterator<? extends Plane> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Plane plane = iterator.next();
            System.out.println(plane);
        }
    }

    @Override
    public String toString() {
        return "Airport{" +
                "planes=" + planes.toString() +
                '}';
    }


    public Airport(List<? extends Plane> planes) throws NonMilitaryPlainException {
        this.planes = planes;
    }
}
