/**
 * Provides the classes and interfaces necessary to create an
 * adjacency matrix from the training data (equivalent to creating a fully connected graph).
 * <p>
 * The scoring framework involves two entities:
 * The edges object ({@link scoring.Edges}) and the alpha object({@link scoring.alpha}).
 * <p>
 * The alpha object is used to store the information about one connections in the graph.
 * <p>
 * The edges object is used to store every alpha into an adjacency matrix and
 * will vary according to the scoring criterion used.
 */
package scoring;