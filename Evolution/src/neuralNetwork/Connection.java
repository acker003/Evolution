package neuralNetwork;

public class Connection {

	private Neuron entrieNeuron = null;
	private float weight = 1;
	
	public Connection(Neuron n, float weight) {
		this.setEntrieNeuron(n);
		this.setWeight(weight);
	}

	public float getValue() {
		return weight * entrieNeuron.getValue();
	}
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Neuron getEntrieNeuron() {
		return entrieNeuron;
	}

	public void setEntrieNeuron(Neuron entrieNeuron) {
		this.entrieNeuron = entrieNeuron;
	}
}
