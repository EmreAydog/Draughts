package experiments ;
import nl.tue.s2id90.dl.experiment.Experiment ;
import java.io.IOException ;
import nl.tue.s2id90.dl.NN.Model;
import nl.tue.s2id90.dl.NN.activation.RELU;
import nl.tue.s2id90.dl.NN.initializer.Gaussian;
import nl.tue.s2id90.dl.NN.layer.FullyConnected;
import nl.tue.s2id90.dl.NN.layer.InputLayer;
import nl.tue.s2id90.dl.NN.layer.SimpleOutput;
import nl.tue.s2id90.dl.NN.loss.MSE;
import nl.tue.s2id90.dl.NN.optimizer.Optimizer;
import nl.tue.s2id90.dl.NN.optimizer.SGD;
import nl.tue.s2id90.dl.NN.tensor.TensorShape;
import nl.tue.s2id90.dl.NN.validate.Regression;
import nl.tue.s2id90.dl.input.GenerateFunctionData;
import nl.tue.s2id90.dl.input.InputReader;
public class FunctionEperiment extends Experiment {
// ( hyper ) parameters
int batchSize=32;
int epochs=8;
float learningRate=0.005f;
int n=9;
int layers=8;

Model createModel ( int inputs , int outputs ) {
    Model model = new Model (new InputLayer ( " In " , new TensorShape ( inputs ) , true ) );
    model.addLayer (new FullyConnected ( " f c 1 " , new TensorShape (inputs) , n , new RELU( ) ) ) ;
    layers=layers-1;
    for(int i=0;i<layers;i++){
    model.addLayer (new FullyConnected ( " f c 1 " , new TensorShape (n) , n , new RELU( ) ) ) ;
    }
    model.addLayer (new SimpleOutput ( "Out " , new TensorShape ( n ) , outputs , new MSE( ) , true ) ) ;
    return model;
}

public void go ( ) throws IOException {
 // read input and pr int some informat ion on the data
 InputReader reader = GenerateFunctionData.THREE_VALUED_FUNCTION( batchSize ) ;
 System.out.println( " Reader info :\n" + reader.toString( ) ) ;
 int inputs = reader.getInputShape( ).getNeuronCount ( ) ;
 int outputs = reader.getOutputShape( ).getNeuronCount ( ) ;
 Model model=createModel(inputs, outputs);
 model.initialize(new Gaussian());
 // Training : c r e a t e and conf igure SGD && t r a i n model
Optimizer sgd = SGD.builder( )
.model(model)
.validator(new Regression( ) )
.learningRate( learningRate )
.build( );
trainModel(model , reader , sgd , epochs , 0) ;
}

public static void main ( String [ ] args ) throws IOException {
new FunctionEperiment( ).go( ) ;
 }
}