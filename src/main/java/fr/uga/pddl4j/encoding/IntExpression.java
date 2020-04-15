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

package fr.uga.pddl4j.encoding;

import fr.uga.pddl4j.parser.PDDLConnective;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to encode expressions in compact encoding, i.e., with integer representation.
 *
 * @author D. Pellier
 * @version 1.0 - 07.04.2010
 */
public class IntExpression implements Serializable {

    /**
     * The constant used to encode the default taskID id.
     */
    public static final int DEFAULT_TASK_ID = -1;

    /**
     * The constant used to encode the default variable.
     */
    public static final int DEFAULT_VARIABLE = -1;

    /**
     * The constant used to encode the default type.
     */
    public static final int DEFAULT_TYPE = -1;

    /**
     * The constant used to encode the default variable value.
     */
    public static final int DEFAULT_VARIABLE_VALUE = -1;

    /**
     * The constant used to encode the specific predicate equal.
     */
    public static final int EQUAL_PREDICATE = -1;

    /*
     * The constant used to encode the default predicate value.
     */
    public static final int DEFAULT_PREDICATE = -2;

    /**
     * The connective of the expression.
     */
    private PDDLConnective connective;

    /**
     * The integer representation of the predicate.
     */
    private int predicate;

    /**
     * The integer representation of the task ID.
     */
    private int taskID;

    /**
     * The list of arguments of the expression. This attribute is used to store the argument of the
     * atomic expression.
     */
    private int[] arguments;

    /**
     * The children of the expression.
     */
    private List<IntExpression> children;

    /**
     * The variable is used by quantified expression.
     */
    private int variable;

    /**
     * The type of the variable used by a quantified expression.
     */
    private int type;

    /**
     * The value of the expression. This attribute is used to store value of number expression.
     */
    private double value;

    /**
     * A flag to indicate if this expression represente a primitive task.
     */
    private boolean isPrimtive;

    /**
     * Create a new expression from an other one. This constructor make a deep copy of the specified
     * expression.
     *
     * @param other the expression.
     */
    public IntExpression(final IntExpression other) {
        this.connective = other.getConnective();
        this.predicate = other.getPredicate();
        this.taskID = other.getTaskID();
        this.arguments = other.getArguments();
        if (this.arguments != null) {
            this.arguments = Arrays.copyOf(other.getArguments(), other.getArguments().length);
        }
        List<IntExpression> otherChildren = other.getChildren();
        this.children = new ArrayList<>(otherChildren.size());
        this.children.addAll(otherChildren.stream().map(IntExpression::new).collect(Collectors.toList()));
        this.variable = other.getVariable();
        this.type = other.getType();
        this.value = other.getValue();
        this.isPrimtive = other.isPrimtive();
    }

    /**
     * Create a new expression with a specified connective.
     *
     * @param connective the connective of the expression.
     */
    public IntExpression(final PDDLConnective connective) {
        this.connective = connective;
        this.predicate = IntExpression.DEFAULT_PREDICATE;
        this.taskID = IntExpression.DEFAULT_TASK_ID;
        this.arguments = null;
        this.children = new ArrayList<>();
        this.variable = IntExpression.DEFAULT_VARIABLE;
        this.type = IntExpression.DEFAULT_TYPE;
        this.value = IntExpression.DEFAULT_VARIABLE_VALUE;
        this.isPrimtive = false;
    }

    /**
     * Return the connective of the expression.
     *
     * @return the connective of the expression.
     */
    public final PDDLConnective getConnective() {
        return this.connective;
    }

    /**
     * Set a new connective to this expression.
     *
     * @param connective the new connective to set.
     */
    public final void setConnective(final PDDLConnective connective) {
        this.connective = connective;
    }

    /**
     * Add a child to this expression.
     *
     * @return <code>true</code> if the child was added <code>false</code> otherwise.
     */
    public final boolean addChild(final IntExpression child) {
        return this.children.add(child);
    }

    /**
     * Return the list of children of this expression.
     *
     * @return the list of children of this expression.
     */
    public final List<IntExpression> getChildren() {
        return this.children;
    }

    /**
     * Return the predicate of this expression.
     *
     * @return the predicate
     */
    public final int getPredicate() {
        return predicate;
    }

    /**
     * Set a new predicate to this expression.
     *
     * @param predicate the new predicate to set
     */
    public final void setPredicate(int predicate) {
        this.predicate = predicate;
    }

    /**
     * Return the tasks of this expression.
     *
     * @return the taskID.
     */
    public final int getTaskID() {
        return this.taskID;
    }

    /**
     * Set a new taskID to this expression.
     *
     * @param taskID the new predicate to set.
     */
    public final void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Returns the list of argument of this expression.
     *
     * @return the arguments the list of arguments of this expression.
     */
    public final int[] getArguments() {
        return this.arguments;
    }

    /**
     * Set the arguments of the expression.
     *
     * @param args the arguments to set.
     */
    public final void setArguments(final int[] args) {
        this.arguments = args;
    }

    /**
     * Return the variable of the expression.
     *
     * @return the variable of the expression.
     */
    public final int getVariable() {
        return this.variable;
    }

    /**
     * Set a new quantified variable to the expression.
     *
     * @param variable the new quantified variable to set
     */
    public final void setVariable(final int variable) {
        this.variable = variable;
    }

    /**
     * Returns the type of the quantified variable of the expression.
     *
     * @return the type of the quantified variable of the expression.
     */
    public final int getType() {
        return this.type;
    }

    /**
     * Set a new type to the quantified variable of the expression.
     *
     * @param type the type to the quantified variable to set.
     */
    public final void setType(final int type) {
        this.type = type;
    }

    /**
     * Returns the value of this expression.
     *
     * @return the value of this expression.
     */
    public final double getValue() {
        return this.value;
    }

    /**
     * Set a new value to the expression.
     *
     * @param value the new value to set
     */
    public final void setValue(double value) {
        this.value = value;
    }

    /**
     * Returns <code>true</code> if the expression is a primitive task.
     *
     * @return <code>true</code> if the expression is a primitive task, <code>false</code> otherwise.
     */
    public final boolean isPrimtive() {
        return this.isPrimtive;
    }

    /**
     * Set the boolean flag used to specified if the expression is a primitive task to a specified value.
     *
     * @param flag the flag.
     */
    public final void setPrimtive(final boolean flag) {
        this.isPrimtive = flag;
    }

    /**
     * Affect this expression to an other. After affectation this expression and the other are
     * equal. No copy of the content of the other expression is done.
     *
     * @param other expression.
     */
    public final void affect(final IntExpression other) {
        this.connective = other.getConnective();
        this.predicate = other.getPredicate();
        this.taskID = other.getTaskID();
        this.arguments = other.getArguments();
        this.children = other.getChildren();
        this.variable = other.getVariable();
        this.type = other.getType();
        this.value = other.getValue();
        this.isPrimtive = other.isPrimtive();
    }

    /**
     * Return if the expression is equal to an other object. The primitive flag and the task id are not used for
     * comparison.
     *
     * @param object the other object.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (object != null && object instanceof IntExpression) {
            final IntExpression other = (IntExpression) object;
            return this.connective.equals(other.connective)
                && this.predicate == other.predicate
                && Arrays.equals(this.arguments, other.arguments)
                && Double.compare(this.value, other.value) == 0
                && this.variable == other.variable
                && this.type == other.type
                && this.children.equals(other.children);
        }
        return false;
    }

    /**
     * Return the hash code value of the expression.
     *
     * @return the hash code value of the expression.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.arguments);
        result = prime * result + this.children.hashCode();
        result = prime * result + this.connective.hashCode();
        result = prime * result + this.predicate;
        result = prime * result + this.type;
        long temp;
        temp = Double.doubleToLongBits(this.value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + this.variable;
        return result;
    }

}
