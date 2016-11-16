package edu.warbot.scriptcore.wrapper;

public class JsWrapper<T extends Object> {

    /**
     * L'objet accessible
     */
    private T object;

    /**
     * Contructeur
     *
     * @param object l'objet à rendre accessible
     */
    public JsWrapper(T object) {
        //System.out.println(object.toString());
        this.object = object;
    }

    /**
     * Contructeur par recopie
     *
     * @param copy
     */
    public JsWrapper(JsWrapper<T> copy) {

        this.object = copy.get();
    }

    /**
     * Getter
     *
     * @return l'objet java
     */
    public T get() {
        //System.out.println(object.toString());
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
