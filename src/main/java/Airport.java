import exceptions.NonMilitaryPlainException;
import models.MilitaryType;
import planes.ExperimentalPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


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


    protected boolean isMilitaryPlane(Plane plane) {

        return (plane instanceof MilitaryPlane) ? true : false;
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        List<PassengerPlane> passengerPlanes = getPassengerPlanes();
        AtomicReference<PassengerPlane> planeWithMaxCapacity = new AtomicReference<>(passengerPlanes.get(0));
        passengerPlanes.forEach(plane -> {
            planeWithMaxCapacity.set(comparePlanesByCapacity(planeWithMaxCapacity.get(), plane));
        });

        return planeWithMaxCapacity.get();
    }

    public PassengerPlane comparePlanesByCapacity(PassengerPlane plane1, PassengerPlane plane2) {
        return plane1.getPassengersCapacity() > plane2.getPassengersCapacity() ? plane1 : plane2;
    }

    public List<MilitaryPlane> getMilitaryPlanes()  {
        List<MilitaryPlane> militaryPlanes = new ArrayList<MilitaryPlane>();
        for (Plane plane : planes) {
            if (plane instanceof MilitaryPlane) {
                militaryPlanes.add((MilitaryPlane) plane);
            }
        }
        return militaryPlanes;
    }

    public List getMilitaryPlanesByType(MilitaryType type)  {
        List militaryPlanesByType = new ArrayList<>();
        Airport airport = new Airport(planes);
        airport.getMilitaryPlanes().forEach(plane -> {
            if(isExpectedMilitaryPlaneType(plane, type)) {
                militaryPlanesByType.add(plane);
            }
        });
        return militaryPlanesByType;
    }




    public boolean isExpectedMilitaryPlaneType(MilitaryPlane plane, MilitaryType militaryType) {
        return plane.getType() == militaryType ? true : false;
    }

       public List<ExperimentalPlane> getExperimentalPlanes() {
           return   planes.stream().filter(plane -> plane instanceof ExperimentalPlane).map((ExperimentalPlane)plane).collect(Collectors.toList());
    }


    public List<ExperimentalPlane> findInCycleExperimentalPlanes(List<ExperimentalPlane> experimentalPlanes) {
        planes.forEach(plane -> {
            if (plane instanceof ExperimentalPlane) {
                experimentalPlanes.add((ExperimentalPlane) plane);
            }
        });
        return experimentalPlanes;
    }


    public List<MilitaryPlane> findInCycleMilitaryPlane
            (List<MilitaryPlane> militaryPlanes, MilitaryType type) {
        List<MilitaryPlane> militaryPlane = new ArrayList<>();
        for (int i = 0; i < getMilitaryPlanes().size(); i++) {
            MilitaryPlane plane = militaryPlanes.get(i);
            if (plane.getType() == type) {
                militaryPlane.add(plane);
            }
        }
        return militaryPlanes;
    }



    public List<? extends Plane> sortByMaxDistance(List<? extends Plane> planes) {
        planes.forEach(plane -> {
            Collections.sort(planes, new Comparator<Plane>() {
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


    public Airport(List<? extends Plane> planes) {
        this.planes = planes;
    }
}
