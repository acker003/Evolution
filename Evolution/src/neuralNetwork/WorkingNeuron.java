package neuralNetwork;

import java.util.ArrayList;
import java.util.List;

import stuff.Helper;

public class WorkingNeuron extends Neuron {

	private boolean valid = false;
	private float value = 0;
	private List<Connection> connections = new ArrayList<Connection>();
	
	public void randomMutation(float mutationRate) {
		Connection c = connections.get(Helper.randomInt0(connections.size()));
		c.setWeight(c.getWeight() + Helper.randomFloat() * 2 * mutationRate - mutationRate);
	}
	
	public void addNeuronConnection(Neuron n, float weight) {
		addNeuronConnection(new Connection(n, weight));
	}
	
	public void addNeuronConnection(Connection connection) {
		connections.add(connection);
	}
	
	public void invalidate() {
		this.valid = false;
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
	
	public void randomizeWeights(float factor) {
		for (Connection c : connections) {
			c.setWeight(Helper.randomFloat() * 2 * factor - factor);
		}
	}
	
	private void calculate() {
		float value = 0;
		for (Connection c : connections) {
			value += c.getValue();
		}
		value = Helper.sigmoid(value);
		this.value = value;
		this.valid = true;
	}
	
	@Override
	public float getValue() {
		if (!valid) {
			calculate();
		}
		return value;
	}
	
	@Override
	public Neuron nameCopy() {
		WorkingNeuron clone = new WorkingNeuron();
		clone.setName(getName());
		return clone;
	}

}
