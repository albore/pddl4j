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

package fr.uga.pddl4j.problem;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class implements the effect of the actions.
 *
 * @author D. Pellier
 * @version 1.0 - 28.10.2020
 * @since 4.0
 * @see FluentDescription
 * @see NumericAssignment
 */
public class Effect extends AbstractFluentDescription {

    /**
     * The list of numeric assignments of this effect.
     */
    private Set<NumericAssignment> assignments;

    /**
     * Creates new effect. By default the effect has no positive and no negative fluents.
     */
    public Effect() {
        this(new TimedFluentDescription(), new TimedFluentDescription());
    }

    /**
     * Creates new effect from an other one. This constructor create a deep copy of the object in parameter.
     *
     * @param other the other one.
     */
    public Effect(final Effect other) {
        super(other);
        this.assignments = new LinkedHashSet<>();
        this.assignments.addAll(other.getNumericAssignments().stream().map(NumericAssignment::new)
            .collect(Collectors.toList()));
    }

    /**
     * Creates new effect from a specified positive and negative timed fluent description and an empty set of
     * numeric assignments.
     *
     * @param positive the positive timed goal description of goal description.
     * @param negative the positive timed goal description of goal description.
     * @see TimedFluentDescription
     */
    public Effect(final TimedFluentDescription positive, final TimedFluentDescription negative) {
        super(positive, negative);
        this.assignments = new LinkedHashSet<>();
    }

    /**
     * Returns the numeric assignments of the effect.
     *
     * @return the numeric assignments of the effect.
     */
    public final Set<NumericAssignment> getNumericAssignments() {
        return this.assignments;
    }

    /**
     * Sets the numeric assignments of the effect.
     *
     * @param assignments the numeric assignments of the effect.
     */
    public final void setNumericAssignments(final Set<NumericAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Adds a numeric assignment to the effect.
     *
     * @param assignment the numeric assignment to add.
     */
    public final void addNumericAssignment(final NumericAssignment assignment) {
        this.assignments.add(assignment);
    }

    /**
     * Returns if this precondition is durative. The precondition is durative it has fluent with time specifier
     * (at start, at end, over-all) or have durative numeric constraints.
     *
     * @return <code>true</code> if the precondition is durative; <code>false</code> otherwise.
     */
    public final boolean isDurative() {
        return super.isDurative() || this.hasDurativeNumericAssignments();
    }

    /**
     * Returns if the effect has a durative numeric assignments.
     *
     * @return <code>true</code> if the effect has a durative numeric assignment; <code>false</code> otherwise.
     */
    public final boolean hasDurativeNumericAssignments() {
        boolean durative = false;
        final Iterator<NumericAssignment> i = this.getNumericAssignments().iterator();
        while (i.hasNext() && !durative) {
            final NumericAssignment assignment = i.next();
            durative = assignment.isDurative();
        }
        return durative;
    }

    /**
     * Returns the hash code value of this effect.
     *
     * @return the hash code value of this effect.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getPositiveTimedGoalDescription(), this.getNegativeTimedGoalDescription(),
            this.getNumericAssignments());
    }

    /**
     * Return if a specified object is equals to this effect. The specified object is equal to
     * the effect if and only if the object is an instance of the class <code>Effect</code>
     * and it has the same positive and negative timed fluent description and the same numeric assignments.
     *
     * @param object the specified object to compared.
     * @return <code>true</code> if the specified object is equal to the precondition; <code>false</code> otherwise.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object != null && object instanceof Effect) {
            Effect other = (Effect) object;
            return this.getPositiveTimedGoalDescription().equals(other.getPositiveTimedGoalDescription())
                && this.getNegativeTimedGoalDescription().equals(other.getNegativeTimedGoalDescription())
                && this.getNumericAssignments().equals(other.getNumericAssignments());
        }
        return false;
    }

    /**
     * Returns a string representation of this effect.
     *
     * @return a string representation of this effect.
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("** Positive fluents **\n");
        str.append(this.getPositiveTimedGoalDescription());
        str.append("** Negative fluents **\n");
        str.append(this.getNegativeTimedGoalDescription());
        str.append("** Numeric assignment **\n");
        for (NumericAssignment assignment : this.getNumericAssignments()) {
            str.append(assignment);
            str.append("\n");
        }
        return str.toString();
    }
}
