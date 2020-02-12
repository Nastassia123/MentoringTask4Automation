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

    private boolean testisPlaneUnclassified(List<ExperimentalPlane> experimentalPlanes, boolean hasUnclassifiedPlanes) {
        for (ExperimentalPlane experimentalPlane : experimentalPlanes) {
            if (experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED) {
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        return hasUnclassifiedPlanes;
    }

    private boolean testGetTransportMilitaryPlanes(List<MilitaryPlane> transportMilitaryPlanes, boolean militaryTypeIsTransport) {
        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if ((militaryPlane.getType() == MilitaryType.TRANSPORT)) {
                militaryTypeIsTransport = true;
                break;
            }
        }
        return militaryTypeIsTransport;
    }


    private boolean testComparasionNextPlaneWithCurrent(List<? extends Plane> planesSortedByMaxLoadCapacity, boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent) throws NonMilitaryPlainException {
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


    private boolean testisBomberMilitaryPlane(List<MilitaryPlane> bomberMilitaryPlanes, boolean isbomberMilitaryPlane) {
        for (MilitaryPlane militaryPlane : bomberMilitaryPlanes) {
            if ((militaryPlane.getType() == MilitaryType.BOMBER)) {
                isbomberMilitaryPlane = true;
            }
        }
        return isbomberMilitaryPlane;
    }

    @Test
    public void testGetTransportMilitaryPlanes() throws NonMilitaryPlainException {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> transportMilitaryPlanes = airport.getMilitaryPlanesByType(MilitaryType.TRANSPORT);
        boolean militaryTypeIsTransport = false;
        Assert.assertEquals(testGetTransportMilitaryPlanes(transportMilitaryPlanes, militaryTypeIsTransport), true);
    }


    @Test
    public void testGetPassengerPlaneWithMaxCapacity() throws NonMilitaryPlainException {
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
            if (!(testComparasionNextPlaneWithCurrent(planesSortedByMaxLoadCapacity, nextPlaneMaxLoadCapacityIsHigherThanCurrent)))
                Assert.fail("Test failed!");

        }
        Assert.assertTrue(testComparasionNextPlaneWithCurrent(planesSortedByMaxLoadCapacity, nextPlaneMaxLoadCapacityIsHigherThanCurrent));
    }


    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() throws NonMilitaryPlainException {
        Airport airport = new Airport(planes);
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getMilitaryPlanesByType(MilitaryType.BOMBER);
        boolean isbomberMilitaryPlane = false;
        if (!(testisBomberMilitaryPlane(bomberMilitaryPlanes, isbomberMilitaryPlane))) {
            Assert.fail("Test failed!");
        }
        Assert.assertTrue(testisBomberMilitaryPlane(bomberMilitaryPlanes, isbomberMilitaryPlane));
    }


    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified() throws NonMilitaryPlainException {
        Airport airport = new Airport(planes);
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        Assert.assertFalse(testisPlaneUnclassified(experimentalPlanes, hasUnclassifiedPlanes));
    }
}