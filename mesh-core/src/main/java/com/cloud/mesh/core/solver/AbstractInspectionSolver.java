package com.cloud.mesh.core.solver;

/**
 * Thew type abstract inspection Solver.
 *
 * @author willlu.zheng
 * @date 2019-09-02
 */
public abstract class AbstractInspectionSolver<T> {

    /**
     * the solve
     *
     * @param t
     */
    public abstract void solve(T t);

    /**
     * the supports
     *
     * @return
     */
    public abstract String[] supports();

}