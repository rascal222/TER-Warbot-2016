package edu.warbot.fsm.editor.settings;

import edu.warbot.tools.ClassFinder;

import java.util.List;
import java.util.Vector;

public class EditorSettings {

    public static final String FSM_CLASS_PATH = "edu.warbot.fsm.";

    public static final String FSM_CLASS_PATH_PLAN = FSM_CLASS_PATH + "plan";
    public static final String FSM_CLASS_PATH_CONDITION = FSM_CLASS_PATH + "condition";
    public static final String FSM_CLASS_PATH_ACTION = FSM_CLASS_PATH + "action.";
    public static final String FSM_CLASS_PATH_REFELEXE = FSM_CLASS_PATH + "reflexe.";

    public static final String WarPlanIdle = FSM_CLASS_PATH_PLAN + "." + "WarPlanIdle";

    private static List<Class<?>> getPlansClass() {
        return ClassFinder.findConcreate(FSM_CLASS_PATH_PLAN);
    }

    private static List<Class<?>> getConditionsClass() {
        return ClassFinder.findConcreate(FSM_CLASS_PATH_CONDITION);
    }

    private static List<Class<?>> getRefelxesClass() {
        return ClassFinder.findConcreate(FSM_CLASS_PATH_REFELEXE);
    }

    private static List<Class<?>> getActionsClass() {
        return ClassFinder.findConcreate(FSM_CLASS_PATH_ACTION);
    }

    @SuppressWarnings("rawtypes")
	public static Vector<String> getPlansClassSimpleName() {
        Vector<String> res = new Vector<>();
        for (Class c : getPlansClass()) {
            res.add(c.getSimpleName());
        }
        return res;
    }

    @SuppressWarnings("rawtypes")
	public static Vector<String> getConditionClassSimpleName() {
        Vector<String> res = new Vector<>();
        for (Class c : getConditionsClass()) {
            res.add(c.getSimpleName());
        }
        return res;
    }

    @SuppressWarnings("rawtypes")
	public static Vector<String> getRefexesClassSimpleName() {
        Vector<String> res = new Vector<>();
        for (Class c : getRefelxesClass()) {
            res.add(c.getSimpleName());
        }
        return res;
    }

    @SuppressWarnings("rawtypes")
	public static Vector<String> getActionsClassSimpleName() {
        Vector<String> res = new Vector<>();
        for (Class c : getActionsClass()) {
            res.add(c.getSimpleName());
        }
        return res;
    }

    public static String getPlanFullName(String plan) {
        return FSM_CLASS_PATH_PLAN + "." + plan;
    }

    public static String getConditionFullName(String plan) {
        return FSM_CLASS_PATH_CONDITION + "." + plan;
    }

    public static String getReflexeFullName(String plan) {
        return FSM_CLASS_PATH_REFELEXE + "." + plan;
    }

    public static String getActionFullName(String plan) {
        return FSM_CLASS_PATH_ACTION + "." + plan;
    }

    public static String getSimpleName(String element) {
        return element.substring(element.lastIndexOf(".") + 1, element.length());
    }

}
