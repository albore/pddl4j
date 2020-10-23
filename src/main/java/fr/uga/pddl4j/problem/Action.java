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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements a compact representation for action of the planning problem.
 *
 * <p>Revisions:
 * <ul>
 * <li>21.10.2020: change the duration attribute to encode temporal problem.</li>
 * </ul>
 *
 * @author D. Pellier
 * @version 1.2 - 08.04.2010
 */
public class Action extends AbstractOperator {

    /**
     * The list of effects of the action.
     */
    private List<ConditionalEffect> effects;

    /**
     * The cost of the action.
     */
    private double cost;

    /**
     * The duration of the action.
     */
    private double duration;

    /**
     *
     */
    public static final double DEFAULT_DURATION = -1.0;

    /**
     * Creates a new action from an other. This constructor is the copy constructor.
     *
     * @param other the other action.
     */
    public Action(final Action other) {
        super(other);
        this.effects = new ArrayList<>();
        this.effects.addAll(other.getConditionalEffects().stream().map(ConditionalEffect::new)
            .collect(Collectors.toList()));
        this.duration = this.getDuration();
    }

    /**
     * Creates a new action.
     *
     * @param name  the name of the action.
     * @param arity the arity of the action.
     */
    public Action(final String name, final int arity) {
        super(name, arity);
        this.effects = new ArrayList<>();
        this.duration = Action.DEFAULT_DURATION;
    }

    /**
     * Creates a new action.
     *
     * @param name          the name of the action.
     * @param arity         the arity of the action.
     * @param preconditions the precondition of the action.
     * @param effects       the effects of the action.
     */
    public Action(final String name, final int arity, final GoalDescription preconditions,
                  final GoalDescription effects) {
        this(name, arity);
        this.setPreconditions(preconditions);
        ConditionalEffect cexp = new ConditionalEffect();
        cexp.setCondition(new GoalDescription());
        cexp.setEffects(effects);
        this.addConditionalEffect(cexp);
    }

    /**
     * Returns the effects of the action.
     *
     * @return the effects of the action.
     */
    public final List<ConditionalEffect> getConditionalEffects() {
        return this.effects;
    }

    /**
     * Returns the conditional effects to the action.
     *
     * @param effects the conditional effects of the action.
     */
    public final void setConditionalEffects(List<ConditionalEffect> effects) {
        this.effects = effects;
    }

    /**
     * Adds a conditional effect to the action.
     *
     * @param effect the conditional effect to add.
     */
    public final void addConditionalEffect(ConditionalEffect effect) {
        this.effects.add(effect);
    }

    /**
     * Returns <code>true</code> if this action is applicable in a specified state.
     *
     * @param state the state.
     * @return <code>true</code> if this action is applicable in a specified state;
     * <code>false</code> otherwise.
     */
    public boolean isApplicable(final ClosedWorldState state) {
        return state.satisfy(this.getPreconditions());
    }

    /**
     * Returns the unconditional effects of the action.
     *
     * @return the unconditional effects of the action.
     */
    public final GoalDescription getUnconditionalEffects() {
        final GoalDescription ucEffect = new GoalDescription();
        this.effects.stream().filter(cEffect -> cEffect.getCondition().isEmpty()).forEach(cEffect -> {
            final GoalDescription condEff = cEffect.getEffects();
            ucEffect.getPositiveFluents().or(condEff.getPositiveFluents());
            ucEffect.getNegativeFluents().or(condEff.getNegativeFluents());
        });
        return ucEffect;
    }

    /**
     * Returns if this action is durative.
     *
     * @return <code>true</code> if this action is durative or <code>false</code> otherwise.
     */
    public final boolean isDurative() {
        return this.duration != DEFAULT_DURATION;
    }

    /**
     * Returns the duration of the action.
     *
     * @return the duration of the action.
     */
    public final double getDuration() {
        return this.duration;
    }

    /**
     * Sets the duration of the action.
     *
     * @param duration the duration to set.
     */
    public final void setDuration(final double duration) {
        this.duration = duration;
    }

    /**
     * Returns the cost of the action.
     *
     * @return the cost of the action.
     */
    public final double getCost() {
        return this.cost;
    }

    /**
     * Sets the cost of the action.
     *
     * @param cost the cost to set.
     */
    public final void setCost(double cost) {
        this.cost = cost;
    }

}
