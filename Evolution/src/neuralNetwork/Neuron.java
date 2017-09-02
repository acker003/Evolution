package neuralNetwork;

public abstract class Neuron {

	private String name = "";
	
	public abstract float getValue();

	public abstract Neuron nameCopy();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
