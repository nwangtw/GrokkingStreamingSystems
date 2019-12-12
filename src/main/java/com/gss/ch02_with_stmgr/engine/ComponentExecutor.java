package com.gss.ch02_with_stmgr.engine;


/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public abstract class ComponentExecutor extends Process {
}
