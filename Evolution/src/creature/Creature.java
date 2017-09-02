package creature;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.Terrain;
import data.Tile;
import main.Simulation;
import neuralNetwork.InputNeuron;
import neuralNetwork.NeuralNetwork;
import neuralNetwork.WorkingNeuron;
import stuff.Const;
import stuff.Helper;
import stuff.Position;
import update.CreatureManager;

public class Creature {

	// Names of input neurons
	public static final String NAME_IN_BIAS = "bias";
	public static final String NAME_IN_FOODVALUEPOSITION = "Food Value Position";
	public static final String NAME_IN_ENERGY = "Energy";
	public static final String NAME_IN_AGE = "Age";
	public static final String NAME_IN_WATERONCREATURE = "Water On Creature";
	
	// Names of output neurons
	public static final String NAME_OUT_BIRTH = "Birth";
	public static final String NAME_OUT_ROTATE = "Rotate";
	public static final String NAME_OUT_FORWARD = "Forward";
	public static final String NAME_OUT_EAT = "Eat";
	
	// ID counter
	private static long currentID = 1;
	
	// Input Neurons
	private InputNeuron inBias;
	private InputNeuron inFoodValuePosition;
	private InputNeuron inEnergy;
	private InputNeuron inAge;
	private InputNeuron inWaterOnCreature;
	// Output Neurons
	private WorkingNeuron outBirth;
	private WorkingNeuron outRotate;
	private WorkingNeuron outForward;
	private WorkingNeuron outEat;
	
	// Stuff
	private CreatureManager manager;
	private NeuralNetwork brain;
	
	// Feeler
	private List<Feeler> feelers = new ArrayList<Feeler>();
	
	public NeuralNetwork getBrain() {
		return brain;
	}

	public void setBrain(NeuralNetwork brain) {
		this.brain = brain;
	}

	// Attributes
	private long id;
	private Position pos = Helper.randomPosition();
	private float viewAngle = (float)(Helper.randomFloat() * Math.PI * 2);
	private float energy = 150;
	//private Vector forward = Helper.randomMovementVector();
	private float speed = Helper.randomFloat() * 2 - 1;
	private float age = 0;
	private Color color = Helper.randomColor();
	private int generation = 1;
	private boolean isDead = false;
	
	
	public Creature(CreatureManager manager) {
		this.setManager(manager);
		this.setId(currentID++);
		initBrain();
		//addFeeler(false, (float)(Helper.randomPosNegFloat() * Math.PI));
	}
	
	public Creature(Creature mother) {
		this.setManager(mother.getManager());
		this.setId(currentID++);
		// Set up brain
		this.brain = mother.getBrain().cloneFullMesh();
		inBias = brain.getInputNeuronFromName(NAME_IN_BIAS);
		inFoodValuePosition 
				= brain.getInputNeuronFromName(NAME_IN_FOODVALUEPOSITION);
		inEnergy = brain.getInputNeuronFromName(NAME_IN_ENERGY);
		inAge = brain.getInputNeuronFromName(NAME_IN_AGE);
		inWaterOnCreature 
				= brain.getInputNeuronFromName(NAME_IN_WATERONCREATURE);
		outBirth = brain.getOutputNeuronFromName(NAME_OUT_BIRTH);
		outRotate = brain.getOutputNeuronFromName(NAME_OUT_ROTATE);
		outForward = brain.getOutputNeuronFromName(NAME_OUT_FORWARD);
		outEat = brain.getOutputNeuronFromName(NAME_OUT_EAT);
		
		this.pos = new Position(mother.getPos().getX(), mother.getPos().getY());
		this.viewAngle = (float)(mother.getViewAngle() + Math.PI);
		for (Feeler f : mother.getFeelers()) {
			addFeeler(true, f.getFeelerAngle());
		}
		doMutations(mother);
	}
	
	private void initBrain() {
		// Set up brain
		brain = new NeuralNetwork();
		
		inBias = new InputNeuron();
		inFoodValuePosition = new InputNeuron();
		inEnergy = new InputNeuron();
		inAge = new InputNeuron();
		inWaterOnCreature = new InputNeuron();
		outBirth = new WorkingNeuron();
		outRotate = new WorkingNeuron();
		outForward = new WorkingNeuron();
		outEat = new WorkingNeuron();
		
		List<InputNeuron> inputLayer = Arrays.asList(inBias, 
				inFoodValuePosition, inEnergy, inAge, inWaterOnCreature);
		List<WorkingNeuron> outputLayer = Arrays.asList(outBirth, outRotate, 
				outForward, outEat);
		brain.setInputLayer(inputLayer);
		brain.generateHiddenNeurons(Const.NUMBER_OF_START_HIDDEN_NEURONS);
		brain.setOutputLayer(outputLayer);
		brain.generateFullMesh();
		brain.randomizeAllWeights();
		
		// Set up neurons
		inBias.setName(NAME_IN_BIAS);
		inFoodValuePosition.setName(NAME_IN_FOODVALUEPOSITION);
		inEnergy.setName(NAME_IN_ENERGY);
		inAge.setName(NAME_IN_AGE);
		inWaterOnCreature.setName(NAME_IN_WATERONCREATURE);
		outBirth.setName(NAME_OUT_BIRTH);
		outRotate.setName(NAME_OUT_ROTATE);
		outForward.setName(NAME_OUT_FORWARD);
		outEat.setName(NAME_OUT_EAT);
	}

	public void addFeeler(boolean isChild, float feelerAngle) {
		feelers.add(new Feeler(this, feelers.size(), isChild, feelerAngle));
	}
	
	private void doMutations(Creature mother) {
		mutateConnections();
		mutateColor(mother);
		mutateFeeler();
	}
	
	private void mutateConnections() {
		for (int i = 0; i < Const.MUTATED_CONNECTIONS_AMOUNT; i++) {
			brain.randomMutation(Const.MUTATION_RATE);
		}
	}
	
	private void mutateColor(Creature mother) {
		int r = mother.getColor().getRed();
		int g = mother.getColor().getGreen();
		int b = mother.getColor().getBlue();
		r = Helper.toIntervall(r + Helper.randomInt(-5, 5), 0, 255);
		g = Helper.toIntervall(g + Helper.randomInt(-5, 5), 0, 255);
		b = Helper.toIntervall(b + Helper.randomInt(-5, 5), 0, 255);
		this.color = new Color(r, g, b);
	}
	
	private void mutateFeeler() {
		// New feeler
		if (Math.random() < Const.CHANCE_FOR_NEW_FEELER && feelers.size() < Const.MAX_FEELER_AMOUNT) {
			addFeeler(false, (float)(Helper.randomPosNegFloat() * Math.PI));
		}
		
		// New feeler length
		for (Feeler f : feelers) {
			if (Math.random() < Const.CHANCE_FOR_FEELER_DISTANCE_MUTATION) {
				f.setFeelerLength((float)(f.getFeelerLength() + 0.1 * Const.MAX_FEELER_DISTANCE * Helper.randomPosNegFloat()));
			}
		}
	}
	
	public void readSensors() {
		brain.invalidate();
		Tile creatureTile = Simulation.getTileAtWorldPosition(pos);
		
		// Set input data
		inBias.setValue(1);
		inFoodValuePosition.setValue(creatureTile.getFood() 
				/ Const.MAXIMUM_FOOD_PER_TILE);
		inEnergy.setValue((energy - Const.MINIMUM_SURVIVAL_ENERGY) 
				/ (Const.START_ENERGY - Const.MINIMUM_SURVIVAL_ENERGY));
		inAge.setValue(age);
		inWaterOnCreature.setValue(creatureTile.isLand() ? 0 : 1);
	}
	
	public void act(float deltaTime) {
		readSensors();
		Tile tile = Simulation.getTileAtWorldPosition(pos);
		float costMult = calculateCostMultiplier(tile);
		actRotate(costMult, deltaTime);
		actMove(costMult, deltaTime);
		actBirth(costMult);
		actEat(costMult, tile, deltaTime);
		for (Feeler f : feelers) {
			f.actFeelerRotate();
			f.readSensors();
			f.calcFeelerPos();
		}
		energy -= Const.COST_PER_FRAME * costMult * deltaTime;
		age += deltaTime * Const.AGE_PER_FRAME;
		
		if (Simulation.oldestCreatureEver == null 
				|| age > Simulation.oldestCreatureEver.getAge()) {
			Simulation.oldestCreatureEver = this;
		}
		if (Simulation.oldestCreatureNow == null 
				|| age > Simulation.oldestCreatureNow.getAge()) {
			Simulation.oldestCreatureNow = this;
		}
		
		if (energy < 100 || tile.getTerrain() == Terrain.NOTHING) {
			manager.removeCreature(this);
			setDead(true);
		}
	}
	
	private float calculateCostMultiplier(Tile tile) {
		float costMult = 1;
		switch (tile.getTerrain()) {
		case WATER:
			costMult = 3;
			break;
		default:
			break;
		}
		return costMult;
	}
	
	private void actRotate(float costMult, float deltaTime) {
		float rotateForce = Helper.toPosNegIntervall(outRotate.getValue());
		this.viewAngle += rotateForce * deltaTime * Const.ROTATE_FACTOR;
		this.energy -= Math.abs(rotateForce * deltaTime * costMult * Const.COST_ROTATE);
	}
	
	private void actMove(float costMult, float deltaTime) {
		speed = Helper.toPosNegIntervall(outForward.getValue());
		float x = (float)(Math.sin(viewAngle) * deltaTime * speed 
				* Const.MOVESPEED);
		float y = (float)(Math.cos(viewAngle) * deltaTime * speed 
				* Const.MOVESPEED);
		pos.move(x, y);
		energy -= Math.abs(speed) * Const.COST_WALK * deltaTime * costMult;
	}
	
	private void actEat(float costMult, Tile tile, float deltaTime) {
		float eatWish = Helper.toPosNegIntervall(outEat.getValue());
		if (eatWish > 0) {
			if (tile.reduceFood()) {
				energy += Const.GAIN_EAT * eatWish * deltaTime;
			}
			energy -= Const.COST_EAT * Math.abs(eatWish) * deltaTime;
		}
	}
	
	private void actBirth(float costMult) {
		// birth wish
		if (outBirth.getValue() > 0) {
			// enough energy
			if (energy > Const.START_ENERGY + Const.MINIMUM_SURVIVAL_ENERGY 
				* 1.1 && age >= Const.MIN_AGE_TO_GIVE_BIRTH) {
				Creature child = new Creature(this);
				child.setGeneration(this.generation + 1);
				manager.addCreature(child);
				energy -= Const.START_ENERGY * costMult;
			}
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public float getViewAngle() {
		return viewAngle;
	}

	public void setViewAngle(float viewAngle) {
		this.viewAngle = viewAngle;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

//	public Vector getForward() {
//		return forward;
//	}
//
//	public void setForward(Vector forward) {
//		this.forward = forward;
//	}

	public CreatureManager getManager() {
		return manager;
	}

	public void setManager(CreatureManager manager) {
		this.manager = manager;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public List<Feeler> getFeelers() {
		return feelers;
	}

	public void setFeelers(List<Feeler> feelers) {
		this.feelers = feelers;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}
