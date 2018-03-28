package experiments;

import java.io.IOException;
import nl.tue.s2id90.dl.NN.Model;
import nl.tue.s2id90.dl.NN.initializer.Gaussian;
import nl.tue.s2id90.dl.NN.layer.Flatten;
import nl.tue.s2id90.dl.NN.layer.InputLayer;
import nl.tue.s2id90.dl.NN.layer.OutputSoftmax;
import nl.tue.s2id90.dl.NN.loss.CrossEntropy;
import nl.tue.s2id90.dl.NN.optimizer.Optimizer;
import nl.tue.s2id90.dl.NN.optimizer.SGD;
import nl.tue.s2id90.dl.NN.tensor.Tensor;
import nl.tue.s2id90.dl.NN.tensor.TensorShape;
import nl.tue.s2id90.dl.NN.validate.Classification;
import nl.tue.s2id90.dl.experiment.Experiment;
import nl.tue.s2id90.dl.input.InputReader;
import nl.tue.s2id90.dl.input.MNISTReader;
import nl.tue.s2id90.dl.javafx.FXGUI;
import nl.tue.s2id90.dl.javafx.ShowCase;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 *
 * @author Frouke && Emre
 */
public class ZalandoExperiment extends Experiment {
    //hyper parameters
    int batchSize = 32; 
    int epochs = 5;
    float learningRate = 0.01f;
    
    String[] labels = {
        "T-shirt/Top", "Trouser", "Pullover", "Dress", "Coat", "Sandal",
        "Shirt", "Sneaker", "Bag", "Ankle boot"
    };
    
    public ZalandoExperiment() {
        super(true);
    }
    
    Model createModel(int inputs, int outputs) {
        // initialize model and inputlayer
        Model model = new Model(new InputLayer("In: ", new TensorShape(28, 28, 1), true)); 
        
        // add intermediate Flatten layer
        model.addLayer(new Flatten("Flatten", new TensorShape(28, 28, 1)));
        
        // use SoftMax activation layer for the output layer
        model.addLayer(new OutputSoftmax("Out", new TensorShape(inputs), outputs,
            new CrossEntropy()));
        
        return model;
    }
    public void go() throws IOException {
        InputReader reader = MNISTReader.fashion(batchSize);
        
        // GUI stuff
        ShowCase showCase = new ShowCase(i -> labels[i]);
        FXGUI.getSingleton().addTab("show case", showCase.getNode());
        showCase.setItems(reader.getValidationData(100));
        
        int inputs = reader.getInputShape().getNeuronCount();
        int outputs = reader.getOutputShape().getNeuronCount();
        
        // debug
        //System.out.println("Retrieved inputs: " + inputs);
        //System.out.println("Retrieved outputs: " + outputs);
        
        Model model = createModel(inputs, outputs);
        model.initialize(new Gaussian());

        Optimizer sgd = SGD.builder()
                .model(model)
                .learningRate(learningRate)
                .validator(new Classification())
                .build();
        

        trainModel(model, reader, sgd, epochs, 0);  
        
        MeanSubtraction dt = new MeanSubtraction();
        
        // not yet functional
        /*
        dt.fit(myTrainingData);
        dt.transform(myTrainingData);
        dt.transform(myValidationData);
        */
    }
    

    
    public static void main(String[] args) throws IOException {
        new ZalandoExperiment().go();
    }
}
   

