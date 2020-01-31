package Planes;

import models.ClassificationLevel;
import models.ExperimentalTypes;

public class ExperimentalPlane extends Plane {

    private ExperimentalTypes experimentalTypes;
    private ClassificationLevel classificationLevel;

    public ExperimentalPlane(String planemodel, int maxSpeed, int maxFlightDistance, int maxLoadCapacity, ExperimentalTypes experimentalTypes, ClassificationLevel classificationLevel) {
        super(planemodel, maxSpeed, maxFlightDistance, maxLoadCapacity);
        this.experimentalTypes = experimentalTypes;
        this.classificationLevel = classificationLevel;
    }

    public ExperimentalTypes getExperimentalTypes() {
        return experimentalTypes;
    }

    public void setExperimentalTypes(ExperimentalTypes experimentalTypes) {
        this.experimentalTypes = experimentalTypes;
    }

    public ClassificationLevel getClassificationLevel() {
        return classificationLevel;
    }

    public void setClassificationLevel(ClassificationLevel classificationLevel) {
        this.classificationLevel = classificationLevel;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "experimentalPlane{" +
                "model='" + getModel() + '\'' +
                '}';
    }
}
