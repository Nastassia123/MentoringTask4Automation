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


    public List<PassengerPlane> getPassengerPlane() {
        List<PassengerPlane> passengersPlaneList = new ArrayList<>();
        planes.forEach(plane -> {
            if (isPassengerPlane(plane)) {
                passengersPlaneList.add((PassengerPlane) plane);
            }
        });
        return passengersPlaneList;
    }


    public boolean isPassengerPlane(Plane plane) {
        boolean isPasenger = false;
        if (plane instanceof PassengerPlane) {
            isPasenger = true;
        } else {
            LOGGER.info("Plane's model is not passenger - will be skipped");
        }
        return isPasenger;
    }


    public List<MilitaryPlane> getMilitaryPlanes() {
        List<MilitaryPlane> militaryPlanes = new ArrayList<MilitaryPlane>();
        planes.forEach(plane ->
        {
            if (isMilitaryPlane(plane)) {
                militaryPlanes.add((MilitaryPlane) plane);
            } else {
                try {
                    throw new NonMilitaryPlainException("Plane is not military");
                } catch (NonMilitaryPlainException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        });
        return militaryPlanes;
    }


    protected boolean isMilitaryPlane(Plane plane) {
        boolean isMilitaryPlane = false;
        if (plane instanceof MilitaryPlane) {
            isMilitaryPlane = true;
        } else {
            LOGGER.info("Plane is not military");
        }
        return isMilitaryPlane;
    }

    public PassengerPlane getPassengerPlaneWithMaxPassengersCapacity() {
        List<PassengerPlane> passengerPlanes = getPassengerPlane();
        PassengerPlane planeWithMaxCapacity = passengerPlanes.get(0);
        planeWithMaxCapacity = Collections.max(passengerPlanes, Comparator.comparing(PassengerPlane::getPassengersCapacity));
        return planeWithMaxCapacity;
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

    public List<MilitaryPlane> findInCycleMilitaryPlane
            (List<MilitaryPlane> militaryPlanes, List<MilitaryPlane> transportMilitaryPlanes) {
        for (int i = 0; i < getMilitaryPlanes().size(); i++) {
            MilitaryPlane plane = militaryPlanes.get(i);
            if (plane.getType() == MilitaryType.TRANSPORT) {
                transportMilitaryPlanes.add(plane);
            }
        }
        return transportMilitaryPlanes;
    }


    public List<MilitaryPlane> findInCycleBomberMilitaryPlanes
            (List<MilitaryPlane> militaryPlanes, List<MilitaryPlane> bomberMilitaryPlanes) {
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
