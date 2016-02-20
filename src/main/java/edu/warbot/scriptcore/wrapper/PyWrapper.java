package edu.warbot.scriptcore.wrapper;

import org.python.core.PyObject;

/**
 * @param <T> Une classe java que l'on souhaite accessible dans du code python
 * @author jlopez02
 */
@SuppressWarnings("serial")
public class PyWrapper<T extends Object> extends PyObject {

    /**
     * L'objet accessible
     */
    private T object;

    /**
     * Contructeur
     *
     * @param object l'objet à rendre accessible
     */
    public PyWrapper(T object) {
        this.object = object;
    }

    /**
     * Contructeur par recopie
     *
     * @param copy
     */
    public PyWrapper(PyWrapper<T> copy) {
        this.object = copy.get();
    }

    /**
     * Getter
     *
     * @return l'objet java
     */
    public T get() {
        return object;
    }

    /**
     * Setter
     *
     * @param object
     */
    public void set(T object) {
        this.object = object;
    }


}
