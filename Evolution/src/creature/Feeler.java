package creature;

import data.Tile;
import main.Simulation;
import neuralNetwork.InputNeuron;
import neuralNetwork.WorkingNeuron;
import stuff.Const;
import stuff.Helper;
import stuff.Position;

public class Feeler {
	
	private float feelerLength = Const.STANDARD_FEELER_LENGTH;
	private float feelerAngle = (float)(Helper.randomPosNegFloat() * Math.PI);
	private Position feelerPosition;
	
	private InputNeuron inFoodValueFeeler = new InputNeuron();
	private InputNeuron inWaterOnFeeler = new InputNeuron();
	
	private WorkingNeuron outFeelerAngle = new WorkingNeuron();
	
	private Creature creature = null;
	
	public Feeler(Creature creature, int index, boolean isChild, float feelerAngle) {
		this.creature = creature;
		this.feelerAngle = feelerAngle;
		
		inFoodValueFeeler.setName("NAME_IN_FOODVALUEFEELER #" + index);
		inWaterOnFeeler.setName("NAME_IN_WATERONFEELER #" + index);
		outFeelerAngle.setName("NAME_OUT_FEELERANGLE #" + index);
		
		if (!isChild) {
			creature.getBrain().addInputNeuronAndMesh(inFoodValueFeeler);
			creature.getBrain().addInputNeuronAndMesh(inWaterOnFeeler);
			creature.getBrain().addOutputNeuronAndMesh(outFeelerAngle);
		}
		
		calcFeelerPos();
		setupVariablesFromBrain(index);
		
	}
	
	public void calcFeelerPos() {
		float angle = feelerAngle + creature.getViewAngle();
		Position localFeelerPos = new Position((float)(Math.sin(angle) * feelerLength), 
				(float)(Math.cos(angle) * feelerLength));
		feelerPosition = creature.getPos().add(localFeelerPos);
	}
	
	public void setupVariablesFromBrain(int index) {
		inFoodValueFeeler = creature.getBrain().getInputNeuronFromName(
				"NAME_IN_FOODVALUEFEELER #" + index);
		inWaterOnFeeler = creature.getBrain().getInputNeuronFromName(
				"NAME_IN_WATERONFEELER #" + index);
		outFeelerAngle = creature.getBrain().getOutputNeuronFromName(
				"NAME_OUT_FEELERANGLE #" + index);
	}
	
	public void readSensors() {
		Tile tile = Simulation.getTileAtWorldPosition(feelerPosition);
		inFoodValueFeeler.setValue(tile.getFood() / Const.MAXIMUM_FOOD_PER_TILE);
		inWaterOnFeeler.setValue(tile.isLand() ? 0 : 1);
	}
	
	public void actFeelerRotate() {
		feelerAngle = (float)(Helper.toPosNegIntervall(
				outFeelerAngle.getValue()) * Math.PI);
	}
	
	public float getFeelerLength() {
		return feelerLength;
	}

	public void setFeelerLength(float feelerLength) {
		this.feelerLength = feelerLength <= Const.MAX_FEELER_DISTANCE ? feelerLength : Const.MAX_FEELER_DISTANCE;
	}

	public Position getFeelerPosition() {
		return feelerPosition;
	}

	public void setFeelerPosition(Position feelerPosition) {
		this.feelerPosition = feelerPosition;
	}
	
	public float getFeelerAngle() {
		return feelerAngle;
	}

	public void setFeelerAngle(float feelerAngle) {
		this.feelerAngle = feelerAngle;
	}


	
	
}
