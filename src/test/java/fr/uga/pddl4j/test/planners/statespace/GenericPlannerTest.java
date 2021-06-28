/*
 * Copyright (c) 2016 by Damien Pellier <Damien.Pellier@imag.fr>.
 *
 * This file is part of PDDL4J library.
 *
 * PDDL4J is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * PDDL4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with PDDL4J.  If not, see
 * <http://www.gnu.org/licenses/>
 */

package fr.uga.pddl4j.test.planners.statespace;

import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.parser.PDDLProblem;
import fr.uga.pddl4j.parser.ParsedProblem;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.Setting;
import fr.uga.pddl4j.planners.statespace.GenericPlanner;
import fr.uga.pddl4j.planners.statespace.search.AStar;
import fr.uga.pddl4j.planners.statespace.search.BreadthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.DepthFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.EnforcedHillClimbing;
import fr.uga.pddl4j.planners.statespace.search.GreedyBestFirstSearch;
import fr.uga.pddl4j.planners.statespace.search.HillClimbing;
import fr.uga.pddl4j.planners.statespace.search.StateSpaceStrategy;
import fr.uga.pddl4j.problem.ADLProblem;
import fr.uga.pddl4j.test.Tools;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Implements the <tt>GenericPlannerTest</tt> of the PDD4L library. The planner accepts only PDDL3.0 language.
 * See BNF Description of PDDL3.0 - Alfonso Gerevini and Derek Long for more details.
 * <p>This class will test the planner on benchmark domain and problem from International planning contest.
 * The goal here is to test the PDDL4J 3.0 plan using all the file used in the competition and
 * KCL-Planning validator: https://github.com/KCL-Planning/VAL</p>
 * <p>Note that IPC benchmark files are note delivered with the source code because of their 3GB size.
 * It suppose benchmark directory is a the root of your project.
 * If no test files are provided all test will pass the validation.</p>
 *
 * @author Emmanuel Hermellin
 * @version 0.1 - 22.11.18
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GenericPlannerTest {

    /**
     * Computation timeout.
     */
    private static final int TIMEOUT = 10;

    /**
     * Default Heuristic Type.
     */
    private static final Setting.Heuristic HEURISTIC = Setting.Heuristic.FAST_FORWARD;

    /**
     * Default Heuristic Weight.
     */
    private static final double HEURISTIC_WEIGHT = 1.0;

    /**
     * Default Trace level.
     */
    private static final int TRACE_LEVEL = 0;

    /**
     * Default statistics computation.
     */
    private static final boolean STATISTICS = false;

    /**
     * The FF planner reference.
     */
    private GenericPlanner planner = null;

    /**
     * Method that executes benchmarks using files on the Generic planner with Astar search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_Astar_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new AStar(TIMEOUT * 1000,
            HEURISTIC, HEURISTIC_WEIGHT);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "Astar");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Method that executes benchmarks using files on the Generic planner with BFS search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_BFS_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new BreadthFirstSearch(TIMEOUT * 1000);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "BFS");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Method that executes benchmarks using files on the Generic planner with DFS search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_DFS_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new DepthFirstSearch(TIMEOUT * 1000);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "DFS");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Method that executes benchmarks using files on the Generic planner with EHC search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_EHC_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new EnforcedHillClimbing(TIMEOUT * 1000,
            HEURISTIC, HEURISTIC_WEIGHT);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "EHC");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Method that executes benchmarks using files on the Generic planner with GBFS search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_GBFS_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new GreedyBestFirstSearch(TIMEOUT * 1000,
            HEURISTIC, HEURISTIC_WEIGHT);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "GBFS");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Method that executes benchmarks using files on the Generic planner with HC search to test its output plan.
     * IPC1 gripper tests
     */
    @Test
    public void testGenericPlanner_HC_gripper() throws Exception {
        final StateSpaceStrategy stateSpaceStrategy = new HillClimbing(TIMEOUT * 1000,
            HEURISTIC, HEURISTIC_WEIGHT);
        planner = new GenericPlanner(STATISTICS, TRACE_LEVEL, stateSpaceStrategy);
        Tools.changeVALPerm();
        final String localTestPath = Tools.PDDL_BENCH_DIR + "ipc1"
            + File.separator + "gripper"
            + File.separator;

        if (!Tools.isBenchmarkExist(localTestPath)) {
            System.out.println("missing Benchmark [directory: " + localTestPath + "] test skipped !");
            return;
        }

        generateValOutputPlans(localTestPath, "HC");
        Tools.validatePDDLPlans(localTestPath);
    }

    /**
     * Generate output plan KLC-planning validator formatted.
     *
     * @param currentTestPath the current sub dir to test
     */
    private void generateValOutputPlans(String currentTestPath, String strategy) {
        Tools.cleanValPlan(currentTestPath);
        String currentDomain = currentTestPath + Tools.PDDL_DOMAIN;
        boolean oneDomainPerProblem = false;
        String problemFile;
        String currentProblem;

        // Counting the number of problem files
        File[] pbFileList = new File(currentTestPath)
            .listFiles((dir, name) -> name.startsWith("p") && name.endsWith(".pddl") && !name.contains("dom"));

        int nbTest = 0;
        if (pbFileList != null) {
            nbTest = pbFileList.length;
        }

        // Check if there is on domain per problem or a shared domain for all
        if (!new File(currentDomain).exists()) {
            oneDomainPerProblem = true;
        }

        System.out.println("GenericPlanner: Test Generic planner on " + currentTestPath + " with " + strategy);
        // Loop around problems in one category
        for (int i = 1; i < nbTest + 1; i++) {
            if (i < 10) {
                problemFile = "p0" + i + Tools.PDDL_EXT;
            } else {
                problemFile = "p" + i + Tools.PDDL_EXT;
            }

            currentProblem = currentTestPath + problemFile;

            if (oneDomainPerProblem) {
                currentDomain = currentTestPath + problemFile.split(".p")[0] + "-" + Tools.PDDL_DOMAIN;
            }
            // Parses the PDDL domain and problem description
            try {
                ParsedProblem parsedProblem = this.planner.parse(currentDomain, currentProblem);
                ErrorManager errorManager = this.planner.getParserErrorManager();
                Assert.assertTrue(errorManager.isEmpty());

                ADLProblem pb = null;
                Plan plan = null;
                try {
                    // Encodes and instantiates the problem in a compact representation
                    System.out.println("* Encoding [" + currentProblem + "]" + "...");
                    pb = this.planner.instantiate(parsedProblem);
                    if (pb.isSolvable()) {
                        // Searches for a solution plan
                        System.out.println("* Trying to solve [" + currentProblem + "]"
                            + " in " + TIMEOUT + " seconds");
                        plan = planner.solve(pb);
                    } else {
                        System.err.println("* PDDLProblem [" + currentProblem + "]" + " not solvable.");
                    }
                } catch (OutOfMemoryError err) {
                    System.out.println("ERR: " + err.getMessage() + " - test aborted");
                    return;
                } catch (IllegalArgumentException iaex) {
                    if (iaex.getMessage().equalsIgnoreCase("problem to encode not ADL")) {
                        System.err.println("[" + currentProblem + "]: Not ADL problem!");
                    } else {
                        throw iaex;
                    }
                }

                if (plan == null) { // no solution in TIMEOUT computation time
                    System.out.println("* No solution found in " + TIMEOUT + " seconds for " + currentProblem);
                } else if (plan.isEmpty()) { // Empty solution
                    System.out.println("* Empty solution for " + currentProblem);
                } else { // Save output plan
                    try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(currentProblem.substring(0,
                        currentProblem.length() - Tools.PDDL_EXT.length()) + Tools.VAL_EXT))) {
                        bw.write(pb.toString(plan));
                    }
                    System.out.println("* Solution found for " + currentProblem);
                }

            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
            System.out.println();
        }
    }
}
