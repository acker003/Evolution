package neuralNetwork;

public class InputNeuron extends Neuron {

	private float value = 0;
	
	@Override
	public float getValue() {
		return value;
	}
	
	public void setValue(float x) {
		this.value = x;
	}

	@Override
	public Neuron nameCopy() {
		InputNeuron clone = new InputNeuron();
		clone.setName(getName());
		return clone;
	}

}
