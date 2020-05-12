/**
 * This package contains the one interface {@link evaluation.Scorer}.
 * The interface is to be implemented by classifiers, it provides the function
 * to calculate the following accuracy measures:
 * <li> Accuracy
 * <li> Sensitivity
 * <li> F1 Score
 * <li> Specificity
 * <p>
 * It also contains the function
 *  {@link evaluation.Scorer#printMetrics(Double, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList)}
 * which prints all the metrics in a pre determined way.
 */
package evaluation;