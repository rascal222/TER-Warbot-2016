package edu.warbot.fsm.condition;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.editor.settings.EnumMethod;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WarConditionBooleanCheck<BrainType extends WarBrain> extends WarCondition<BrainType> {

    EnumMethod methodName;
    Method method;

    public WarConditionBooleanCheck(String name, BrainType brain, GenericConditionSettings conditionSettings) {

        super(name, brain, conditionSettings);

        if (conditionSettings.Methode != null)
            this.methodName = conditionSettings.Methode;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Method> for condition <WarConditionBooleanCheck>", "Missing attribut", JOptionPane.ERROR_MESSAGE);


        try {
            this.method = this.brain.getClass().getMethod(this.methodName.name());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("no suck method name for " + this.methodName);
            e.printStackTrace();
        }

    }

    @Override
    public boolean isValide() {
        Boolean currentValue = null;
        try {
            currentValue = (Boolean) (method.invoke(this.brain));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return currentValue;

    }

}
