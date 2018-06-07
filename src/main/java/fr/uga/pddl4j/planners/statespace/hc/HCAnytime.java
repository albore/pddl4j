/*
 * Copyright (c) 2010 by Damien Pellier <Damien.Pellier@imag.fr>.
 *
 * This file is part of PDDL4J library.
 *
 * PDDL4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PDDL4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PDDL4J.  If not, see <http://www.gnu.org/licenses/>
 */

package fr.uga.pddl4j.planners.statespace.hc;

import fr.uga.pddl4j.encoding.CodedProblem;
import fr.uga.pddl4j.heuristics.relaxation.Heuristic;
import fr.uga.pddl4j.heuristics.relaxation.HeuristicToolKit;
import fr.uga.pddl4j.planners.statespace.AbstractStateSpacePlannerAnytime;
import fr.uga.pddl4j.planners.statespace.StateSpacePlannerFactory;
import fr.uga.pddl4j.planners.statespace.search.strategy.Node;
import fr.uga.pddl4j.util.BitOp;
import fr.uga.pddl4j.util.BitState;
import fr.uga.pddl4j.util.MemoryAgent;
import fr.uga.pddl4j.util.SequentialPlan;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class HCAnytime extends AbstractStateSpacePlannerAnytime {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The list containing the nodes on which it is interesting to restart hill climbing.
     */
    private LinkedList<Node> restartList;

    /**
     * The list containing the nodes of hill climbing search.
     */
    private final LinkedList<Node> openList;

    /**
     * The time spent to search a solution.
     */
    private long searchingTime;

    /**
     * Creates a new planner.
     */
    public HCAnytime() {
        super();
        restartList = new LinkedList<>();
        openList = new LinkedList<>();
        this.searchingTime = 0;
    }

    /**
     * Search a solution plan to a specified domain and problem.
     *
     * @param problem the problem to solve.
     */
    @Override
    public SequentialPlan search(final CodedProblem problem) {
        final Logger logger = this.getLogger();
        Objects.requireNonNull(problem);
        this.searchingTime = 0;

        final Heuristic heuristic = HeuristicToolKit.createHeuristic(this.getHeuristicType(), problem);

        BitState init = new BitState(problem.getInit());
        Node root = new Node(init, null, 0, 0, heuristic.estimate(init, problem.getGoal()));
        root.setHeuristic(heuristic.estimate(root, problem.getGoal()));
        restartList.add(root);
        Node solution = null;

        double bound = Double.MAX_VALUE;
        final int timeout = this.getTimeout();

        logger.trace("* starting hill climbing anytime search\n");
        while (!restartList.isEmpty() && searchingTime < timeout) {
            final Node node = restartList.getFirst();
            restartList.remove(node);

            final long begin = System.currentTimeMillis();
            final Node returnedSolution = hillClimbing(problem, node, heuristic);
            final long end = System.currentTimeMillis();

            searchingTime += (end - begin);

            if (returnedSolution != null && returnedSolution.getCost() < bound) {
                this.getSolutionNodes().add(new Node(returnedSolution, returnedSolution.getParent(), 0,
                    returnedSolution.getCost(), returnedSolution.getDepth(), returnedSolution.getHeuristic()));
                bound = returnedSolution.getCost();
                solution = returnedSolution;
                logger.trace("* " + this.getSolutionNodes().size() + " solutions found. Best cost: " + bound + "\n");
            }
        }

        if (isSaveState()) {
            this.getStatistics().setTimeToSearch(searchingTime);
            if (StateSpacePlannerFactory.isMemoryAgent()) {
                // Compute the memory used by the search
                try {
                    this.getStatistics().setMemoryUsedToSearch(MemoryAgent.deepSizeOf(restartList)
                        + MemoryAgent.deepSizeOf(openList) + MemoryAgent.deepSizeOf(heuristic));
                } catch (IllegalStateException ilException) {
                    this.getLogger().error(ilException);
                }
            }
        }

        this.getSolutionNodes().clear();

        if (solution == null) {
            logger.trace("* hill climbing anytime search failed\n");
            return null;
        } else {
            logger.trace("* hill climbing anytime search succeeded\n");
            return extract(solution, problem);
        }
    }

    /**
     * The hill climbing algorithm. Solves the planning problem and returns the solution's node.
     *
     * @param problem   the coded problem to solve.
     * @param node      the root node for the hill climbing.
     * @param heuristic the heuristic used in hill climbing.
     * @return the solution node or null.
     */
    private Node hillClimbing(CodedProblem problem, Node node, Heuristic heuristic) {
        openList.clear();

        Node root = node;
        if (node == null) {
            BitState init = new BitState(problem.getInit());
            root = new Node(init, null, 0, 0, heuristic.estimate(init, problem.getGoal()));
            root.setHeuristic(heuristic.estimate(root, problem.getGoal()));
            openList.add(root);
        } else {
            openList.add(root);
        }

        Node solution = null;
        boolean deadEndFree = true;

        final int timeout = this.getTimeout();
        final long begin = System.currentTimeMillis();
        long hillClimbingSearchingTime = 0;
        while (!openList.isEmpty() && solution == null
            && deadEndFree && (hillClimbingSearchingTime + searchingTime) < timeout) {

            final Node currentState = openList.pop();
            final LinkedList<Node> successors = this.getSuccessors(currentState, problem, heuristic);
            deadEndFree = !successors.isEmpty();

            if (deadEndFree) {
                final Node successor = popBestNode(successors);
                if (successor.satisfy(problem.getGoal())) {
                    solution = successor;
                } else {
                    successors.clear();
                    openList.clear();
                    openList.addLast(successor);
                }
            }

            long end = System.currentTimeMillis();
            hillClimbingSearchingTime = end - begin;
        }
        return solution;
    }

    /**
     * Get the successors from a node.
     *
     * @param parent    the parent node.
     * @param problem   the coded problem to solve.
     * @param heuristic the heuristic used.
     * @return the list of successors from the parent node.
     */
    private LinkedList<Node> getSuccessors(Node parent, CodedProblem problem, Heuristic heuristic) {
        final LinkedList<Node> successors = new LinkedList<>();

        int index = 0;
        for (BitOp op : problem.getOperators()) {
            if (op.isApplicable(parent)) {
                final BitState nextState = new BitState(parent);
                nextState.or(op.getCondEffects().get(0).getEffects().getPositive());
                nextState.andNot(op.getCondEffects().get(0).getEffects().getNegative());

                // Apply the effect of the applicable operator
                final Node successor = new Node(nextState);
                successor.setCost(parent.getCost() + op.getCost());
                successor.setHeuristic(heuristic.estimate(successor, problem.getGoal()));
                successor.setParent(parent);
                successor.setOperator(index);
                successor.setDepth(parent.getDepth() + 1);
                successors.add(successor);
            }
            index++;
        }

        return successors;
    }

    /**
     * Return the best node from a list according to the heuristic value.
     *
     * @param nodes the list containing nodes.
     * @return the best node from the nodes' list.
     */
    private Node popBestNode(Collection<Node> nodes) {
        Node node = null;
        if (!nodes.isEmpty()) {
            final Iterator<Node> i = nodes.iterator();
            node = i.next();
            while (i.hasNext()) {
                final Node next = i.next();
                if (next.getHeuristic() < node.getHeuristic()) {
                    node = next;
                } else if (Math.abs(next.getHeuristic() - node.getHeuristic()) < .001 && !restartList.contains(next)) {
                    restartList.addLast(next);
                }
            }
            nodes.remove(node);
        }
        return node;
    }
}
