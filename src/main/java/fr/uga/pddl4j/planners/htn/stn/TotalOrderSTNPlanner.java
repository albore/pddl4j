/*
 * Copyright (c) 2020 by Damien Pellier <Damien.Pellier@imag.fr>.
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

package fr.uga.pddl4j.planners.htn.stn;

import fr.uga.pddl4j.parser.ErrorManager;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.ProblemFactory;
import fr.uga.pddl4j.problem.Problem;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * This class implements a node for the TotalOrderSTNPlanner planner of the PDDL4J library.
 *
 * @author D. Pellier
 * @version 1.0 - 15.04.2020
 * @since 4.0
 */
final public class TotalOrderSTNPlanner extends AbstractPlanner {

    /*
     * The arguments of the planner.
     */
    private Properties arguments;

    /**
     * Creates a new total order STN planner with the default parameters.
     *
     * @param arguments the arguments of the planner.
     */
    public TotalOrderSTNPlanner(final Properties arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Solves the planning problem and returns the first solution search found.
     *
     * @param problem the problem to be solved.
     * @return a solution search or null if it does not exist.
     */
    @Override
    public Plan search(Problem problem) {
        // The method to be complete with the TotalOrderSTNPlanner algorithm describes in the book of
        // automated planning of Ghallab page 239


        return new SequentialPlan();

    }

    /**
     * The main method of the <code>TotalOrderSTNPlanner</code> example. The command line syntax is as
     * follow:
     * <p>
     * <pre>
     * usage of TotalOrderSTNPlanner:
     *
     * OPTIONS   DESCRIPTIONS
     *
     * -d <i>str</i>   operator file name
     * -p <i>str</i>   fact file name
     * -t <i>num</i>   specifies the maximum CPU-time in seconds (preset: 300)
     * -h              print this message
     *
     * </pre>
     * </p>
     *
     * Commande line example:
     * <code>java -cp build/libs/pddl4j-3.8.3.jar fr.uga.pddl4j.planners.htn.stn.TotalOrderSTNPlanner</code><br>
     * <code><span>-d src/test/resources/parser/hddl/HDDL-Total-Ordered/rover/domain.hddl</code><br>
     * <code><span>-p src/test/resources/parser/hddl/HDDL-Total-Ordered/rover/pb01.hddl</code><br>
     *
     * @param args the arguments of the command line.
     */
    public static void main(final String[] args) {

        // Parse the commande line and initialize the arguments of the planner.
        final Properties arguments = TotalOrderSTNPlanner.parseCommandLine(args);
        if (arguments == null) {
            TotalOrderSTNPlanner.printUsage();
            System.exit(0);
        }

        // Create an instance of the TotalOrderSTNPlanner Planner
        final TotalOrderSTNPlanner planner = new TotalOrderSTNPlanner(arguments);

        // Create an instance of the problem factory to parse and encode the domain and problem file
        final ProblemFactory factory = ProblemFactory.getInstance();

        // Get the domain file and problem file and parse the hddl files.
        File domain = (File) arguments.get(Planner.DOMAIN);
        File problem = (File) arguments.get(Planner.PROBLEM);
        ErrorManager errorManager = null;
        try {
            errorManager = factory.parse(domain, problem);
        } catch (IOException e) {
            System.out.println("\nunexpected error when parsing the PDDL planning problem description.");
            System.exit(0);
        }

        // Print the syntax errors if detected
        if (!errorManager.isEmpty()) {
            errorManager.printAll();
            System.exit(0);
        }

        // Encode the problem into compact representation
        final Problem pb = factory.encode();
        System.out.println("\nencoding problem done successfully ("
                + pb.getActions().size() + " actions, "
                + pb.getMethods().size() + " methods, "
                + pb.getRelevantFacts().size() + " fluents, "
                + pb.getRelevantTasks().size() + " tasks)\n");

        final Plan plan = planner.search(pb);
        if (plan != null) {
            // Print plan information
            System.out.println("%nfound plan as follows:%n%n" + pb.toString(plan));
            System.out.println(String.format("%nplan total cost: %4.2f%n%n", plan.cost()));
        } else {
            System.out.println(String.format(String.format("%nno plan found%n%n")));
        }
    }

    /**
     * Print the usage of the TotalOrderSTNPlanner planner.
     */
    private static void printUsage() {
        final StringBuilder strb = new StringBuilder();
        strb.append("\nusage of TotalOrderSTNPlanner:\n")
                .append("OPTIONS   DESCRIPTIONS\n")
                .append("-d <str>    hddl domain file name\n")
                .append("-p <str>    hddl problem file name\n")
                .append("-t <num>    specifies the maximum CPU-time in seconds (preset: 300)\n")
                .append("-h          print this message\n\n");
        Planner.getLogger().trace(strb.toString());
    }

    /**
     * Parse the command line and return the planner's arguments.
     *
     * @param args the command line.
     * @return the planner arguments or null if an invalid argument is encountered.
     */
    private static Properties parseCommandLine(String[] args) {

        // Get the default arguments from the super class
        final Properties arguments = Planner.getDefaultArguments();

        // Parse the command line and update the default argument value
        for (int i = 0; i < args.length; i += 2) {
            if ("-d".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
                if (!new File(args[i + 1]).exists()) return null;
                arguments.put(Planner.DOMAIN, new File(args[i + 1]));
            } else if ("-p".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
                if (!new File(args[i + 1]).exists()) return null;
                arguments.put(Planner.PROBLEM, new File(args[i + 1]));
            } else if ("-t".equalsIgnoreCase(args[i]) && ((i + 1) < args.length)) {
                final int timeout = Integer.parseInt(args[i + 1]) * 1000;
                if (timeout < 0) return null;
                arguments.put(Planner.TIMEOUT, timeout);
            } else {
                return null;
            }
        }
        // Return null if the domain or the problem was not specified
        return (arguments.get(Planner.DOMAIN) == null
                || arguments.get(Planner.PROBLEM) == null) ? null : arguments;
    }
}
