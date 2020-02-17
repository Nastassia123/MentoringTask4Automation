import exceptions.NonMilitaryPlainException;
import models.ClassificationLevel;
import models.ExperimentalTypes;
import models.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import planes.ExperimentalPlane;
import planes.MilitaryPlane;
import planes.PassengerPlane;
import planes.Plane;

import java.util.Arrays;
import java.util.List;

public class AirportTest {
    private static List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalTypes.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalTypes.VTOL, ClassificationLevel.TOP_SECRET)
    );


    private static PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 242);

    private boolean  isPlaneUnclassified(List<ExperimentalPlane> experimentalPlanes, boolean hasUnclassifiedPlanes) {
        for (ExperimentalPlane experimentalPlane : experimentalPlanes) {
            if (experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED) {
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        return hasUnclassifiedPlanes;
    }

    private boolean getTransportMilitaryPlanes(List<MilitaryPlane> transportMilitaryPlanes, boolean militaryTypeIsTransport) {
        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if ((militaryPlane.getType() == MilitaryType.TRANSPORT)) {
                militaryTypeIsTransport = true;
                break;
            }
        }
        return militaryTypeIsTransport;
    }


    private boolean comparasionNextPlaneWithCurrent(List<? extends Plane> planesSortedByMaxLoadCapacity, boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent) throws NonMilitaryPlainException {
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            Plane currentPlane = planesSortedByMaxLoadCapacity.get(i);
            Plane nextPlane = planesSortedByMaxLoadCapacity.get(i + 1);
            if (currentPlane.getMaxLoadCapacity() > nextPlane.getMaxLoadCapacity()) {
                nextPlaneMaxLoadCapacityIsHigherThanCurrent = false;
                break;
            }
        }
        return nextPlaneMaxLoadCapacityIsHigherThanCurrent;
    }


    private boolean isBomberMilitaryPlane(List<MilitaryPlane> bomberMilitaryPlanes, boolean isbomberMilitaryPlane) {
        for (MilitaryPlane militaryPlane : bomberMilitaryPlanes) {
            if ((militaryPlane.getType() == MilitaryType.BOMBER)) {
                isbomberMilitaryPlane = true;
            }
        }
        return isbomberMilitaryPlane;
    }

    @Test
    public void testGetTransportMilitaryPlanes() {
        Airport airport = new Airport(planes);
        boolean isTransportMilitaryPlaneFound = false;
        List<MilitaryPlane> transportMilitaryPlanes = airport.getMilitaryPlanesByType(MilitaryType.TRANSPORT);
        if (!(transportMilitaryPlanes.isEmpty())) {
            isTransportMilitaryPlaneFound = true;
        }
        Assert.assertEquals(isTransportMilitaryPlaneFound, true);
    }


    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        Airport airport = new Airport(planes);
        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertTrue(expectedPlaneWithMaxPassengersCapacity.equals(planeWithMaxPassengerCapacity));
    }

    @Test
    public void testNextPlaneMaxLoadCapacityIsHigherThanCurrent() throws NonMilitaryPlainException {
        Airport airport = new Airport(planes);
        airport.sortByMaxLoadCapacity(planes);
        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.getPlanes();
        boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent = true;

        {
            if (!(comparasionNextPlaneWithCurrent(planesSortedByMaxLoadCapacity, nextPlaneMaxLoadCapacityIsHigherThanCurrent)))
                Assert.fail("Test failed!");

        }
        Assert.assertTrue(comparasionNextPlaneWithCurrent(planesSortedByMaxLoadCapacity, nextPlaneMaxLoadCapacityIsHigherThanCurrent));
    }


    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {
        Airport airport = new Airport(planes);
        List bomberMilitaryPlanes = airport.getMilitaryPlanesByType(MilitaryType.BOMBER);
        boolean isBomberMilitaryPlane = false;
        if (bomberMilitaryPlanes.isEmpty()) {
            Assert.fail("Test failed!");
        }
        else {
            isBomberMilitaryPlane = true;
        }
        Assert.assertTrue(isBomberMilitaryPlane(bomberMilitaryPlanes, isBomberMilitaryPlane));
    }




    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified()  {
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        Assert.assertFalse(isPlaneUnclassified(experimentalPlanes, hasUnclassifiedPlanes));
    }
}