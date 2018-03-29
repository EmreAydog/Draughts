package experiments;

import java.util.List;
import nl.tue.s2id90.dl.NN.tensor.TensorPair;
import nl.tue.s2id90.dl.NN.transform.DataTransform;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 *
 * @author Frouke && Emre
 */
public class MeanSubtraction implements DataTransform {
    INDArray fit_input;
    INDArray fit_output;
    /** computes statistics for the dataset consisting of input-output pairs, 
     * these statistics are used in the transform method.  Note that the stats
     * are only based on the input.
     * 
     * @param data 
     */
    @Override public void fit (List<TensorPair> data) {
        int j=0;
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Empty dataset");
        }
        INDArray inputtotal=Nd4j.zeros(1,1);
        INDArray outputtotal=Nd4j.zeros(1,1);
        for (TensorPair pair : data) {
            INDArray input=pair.model_input.getValues();
            INDArray output=pair.model_input.getValues();
            inputtotal.addi(input);
            outputtotal.addi(input);
            j++;                       
        }
        fit_input=inputtotal.div(j);
        fit_output=outputtotal.div(j);
    }
    
    @Override public void transform (List<TensorPair> data) {
        for (TensorPair pair : data) {
            pair.model_input.getValues().subi(fit_input);
            pair.model_output.getValues().subi(fit_output);
        }
    }
    
          
}

