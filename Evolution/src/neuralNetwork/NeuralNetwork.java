package neuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stuff.Helper;

public class NeuralNetwork {

	private List<InputNeuron> inputLayer = new ArrayList<InputNeuron>();
	private List<WorkingNeuron> hiddenLayer = new ArrayList<WorkingNeuron>();
	private List<WorkingNeuron> outputLayer = new ArrayList<WorkingNeuron>();
	
	public void addInputNeuron(InputNeuron neuron) {
		inputLayer.add(neuron);
	}
	
	public void addHiddenNeuron(WorkingNeuron neuron) {
		hiddenLayer.add(neuron);
	}
	
	public void addOutputNeuron(WorkingNeuron neuron) {
		outputLayer.add(neuron);
	}
	
	public void addInputNeuronAndMesh(InputNeuron neuron) {
		addInputNeuron(neuron);
		for (WorkingNeuron wn : hiddenLayer) {
			wn.addNeuronConnection(neuron, 0);
		}
	}
	
	public void addHiddenNeuronAndMesh(WorkingNeuron neuron) {
		addHiddenNeuron(neuron);
		for (InputNeuron in : inputLayer) {
			neuron.addNeuronConnection(in, 0);
		}
	}
	
	public void addOutputNeuronAndMesh(WorkingNeuron neuron) {
		addOutputNeuron(neuron);
		for (WorkingNeuron wn : hiddenLayer) {
			neuron.addNeuronConnection(wn, 0);
		}
	}
	
	public InputNeuron getInputNeuronFromName(String name) {
		// TODO: Maybe solve this via Map
		for (InputNeuron neuron : inputLayer) {
			if (name.equals(neuron.getName())) {
				return neuron;
			}
		}
		return null;
	}
	
	public WorkingNeuron getHiddenNeuronFromName(String name) {
		// TODO: Maybe solve this via Map
		for (WorkingNeuron neuron : hiddenLayer) {
			if (name.equals(neuron.getName())) {
				return neuron;
			}
		}
		return null;
	}
	
	public WorkingNeuron getOutputNeuronFromName(String name) {
		// TODO: Maybe solve this via Map
		for (WorkingNeuron neuron : outputLayer) {
			if (name.equals(neuron.getName())) {
				return neuron;
			}
		}
		return null;
	}
	
	public void generateHiddenNeurons(int amount) {
		for (int i = 0; i < amount; i++) {
			addHiddenNeuronAndMesh(new WorkingNeuron());
		}
	}
	
	public void generateFullMesh() {
		for (InputNeuron in : inputLayer) {
			for (WorkingNeuron wn : hiddenLayer) {
				wn.addNeuronConnection(in, 0);
			}
		}
		for (WorkingNeuron wn : hiddenLayer) {
			for (WorkingNeuron opn : outputLayer) {
				opn.addNeuronConnection(wn, 0);
			}
		}
	}
	
	public void invalidate() {
		for (WorkingNeuron wn : hiddenLayer) {
			wn.invalidate();
		}
		for (WorkingNeuron wn : outputLayer) {
			wn.invalidate();
		}
	}
	
	public void randomizeAllWeights() {
		for (WorkingNeuron wn : hiddenLayer) {
			wn.randomizeWeights(1);
		}
		for (WorkingNeuron wn : outputLayer) {
			wn.randomizeWeights(1);
		}
	}
	
	public void randomMutation(float mutationRate) {
		int layer = Helper.randomInt(2);
		switch (layer) {
		case 1:
			int index = Helper.randomInt0(hiddenLayer.size());
			hiddenLayer.get(index).randomMutation(mutationRate);
			break;
		case 2:
			index = Helper.randomInt0(outputLayer.size());
			outputLayer.get(index).randomMutation(mutationRate);
			break;
		default:
			break;
		}
	}
	
	public Connection getConnection(int layer, int neuron, int connection) {
		switch (layer) {
		case 1:
			return hiddenLayer.get(neuron).getConnections().get(connection);
		default:
			return outputLayer.get(neuron).getConnections().get(connection);
		}
	}
	
	public NeuralNetwork cloneFullMesh() {
		
		NeuralNetwork clone = new NeuralNetwork();
		Map<Neuron, Neuron> oldToNewMap = new HashMap<Neuron, Neuron>();
		
		for (InputNeuron in : inputLayer) {
			Neuron newNeuron = in.nameCopy();
			oldToNewMap.put(in, newNeuron);
			clone.addInputNeuron((InputNeuron)newNeuron);
		}
		
		List<WorkingNeuron> newHiddenNeurons = new ArrayList<WorkingNeuron>();
		for (WorkingNeuron wn : hiddenLayer) {
			WorkingNeuron newNeuron = (WorkingNeuron)wn.nameCopy();
			oldToNewMap.put(wn, newNeuron);
			newHiddenNeurons.add(newNeuron);
			for (Connection con : wn.getConnections()) {
				newNeuron.addNeuronConnection(oldToNewMap.get(
						con.getEntrieNeuron()), con.getWeight());
			}
		}
		clone.setHiddenLayer(newHiddenNeurons);
		
		List<WorkingNeuron> newOutputNeurons = new ArrayList<WorkingNeuron>();
		for (WorkingNeuron wn : outputLayer) {
			WorkingNeuron newNeuron = (WorkingNeuron)wn.nameCopy();
			newOutputNeurons.add(newNeuron);
			for (Connection con : wn.getConnections()) {
				newNeuron.addNeuronConnection(oldToNewMap.get(
						con.getEntrieNeuron()), con.getWeight());
			}
		}
		clone.setOutputLayer(newOutputNeurons);
		
		return clone;
	}
	
	public List<InputNeuron> getInputLayer() {
		return inputLayer;
	}
	
	public void setInputLayer(List<InputNeuron> inputLayer) {
		this.inputLayer = inputLayer;
	}
	
	public List<WorkingNeuron> getHiddenLayer() {
		return hiddenLayer;
	}
	
	public void setHiddenLayer(List<WorkingNeuron> hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}
	
	public List<WorkingNeuron> getOutputLayer() {
		return outputLayer;
	}
	
	public void setOutputLayer(List<WorkingNeuron> outputLayer) {
		this.outputLayer = outputLayer;
	}
	
	
}
