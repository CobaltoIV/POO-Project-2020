/**
 * Provides the classes necessary to create an
 * adjacency matrix from the training data (equivalent to creating a fully connected graph).
 * <p>
 * The scoring framework involves two entities:
 * The edges object (@link scoring.Edges) and the alpha object(@link scoring.alpha).
 * The alpha object is used to store the information about one connections in the graph.
 * The edges object is used to store every alpha into an adjacency matrix
 * The edges object will vary according to the scoring criterion used
 */
package scoring;