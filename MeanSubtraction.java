package experiments;

import java.util.List;
import nl.tue.s2id90.dl.NN.tensor.TensorPair;
import nl.tue.s2id90.dl.NN.transform.DataTransform;

/**
 *
 * @author Frouke && Emre
 */
public class MeanSubtraction implements DataTransform {
    Float mean;
    
    /** computes statistics for the dataset consisting of input-output pairs, 
     * these statistics are used in the transform method.  Note that the stats
     * are only based on the input.
     * 
     * @param data 
     */
    @Override public void fit (List<TensorPair> data) {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("Empty dataset");
        }
        for (TensorPair pair : data) {
            System.out.println(pair);
        }
        System.out.println("\nExiting fit()");
    }
    
    @Override public void transform (List<TensorPair> data) {
        // TODO
    }
            
}
