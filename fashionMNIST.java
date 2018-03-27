package experiments ;
import nl.tue.s2id90.dl.experiment.Experiment ;
import java.io.IOException ;
import nl.tue.s2id90.dl.NN.Model;
import nl.tue.s2id90.dl.NN.initializer.Gaussian;
import nl.tue.s2id90.dl.NN.layer.Flatten;
import nl.tue.s2id90.dl.NN.layer.InputLayer;
import nl.tue.s2id90.dl.NN.layer.OutputSoftmax;
import nl.tue.s2id90.dl.NN.loss.CrossEntropy;
import nl.tue.s2id90.dl.NN.optimizer.Optimizer;
import nl.tue.s2id90.dl.NN.optimizer.SGD;
import nl.tue.s2id90.dl.NN.tensor.TensorShape;
import nl.tue.s2id90.dl.NN.validate.Classification;
import nl.tue.s2id90.dl.input.InputReader;
import nl.tue.s2id90.dl.input.MNISTReader;
public class fashionMNIST extends Experiment {
// ( hyper ) parameters
int batchSize=32;
int epochs=5;
float learningRate=0.01f;
Model createModel ( int inputs , int outputs ) {
    Model model = new Model (new InputLayer ( " In " , new TensorShape ( inputs ) , true ) );
    // add f l a t t e n l aye r a f t e r input l aye r
    model.addLayer (new Flatten( "Flatten" , new TensorShape ( inputs ))) ;
    // add output layer
    int m=3;
    int n=outputs;
    model.addLayer (new OutputSoftmax ( "Out " ,new TensorShape (m) , n , new CrossEntropy ( ) ) ) ;
    return model;
}

public void go ( ) throws IOException {
 // read input and pr int some informat ion on the data
InputReader reader = MNISTReader.fashion( batchSize ); 
System.out.println( " Reader info :\n" + reader.toString( ) ) ;
// print a record
 //reader.getValidationData(1).forEach(System.out::println) ; 
 int inputs = reader.getInputShape( ).getNeuronCount ( ) ;
 int outputs = reader.getOutputShape( ).getNeuronCount ( );
 Model model=createModel(inputs, outputs);
 model.initialize(new Gaussian());
 // Training : c r e a t e and conf igure SGD && t r a i n model
Optimizer sgd = SGD.builder( )
.model(model)
.learningRate( learningRate )
.validator(new Classification( ) )
.build( );
trainModel(model , reader , sgd , epochs , 0) ;
}

public static void main ( String [ ] args ) throws IOException {
new FunctionEperiment( ).go( ) ;

 }
}