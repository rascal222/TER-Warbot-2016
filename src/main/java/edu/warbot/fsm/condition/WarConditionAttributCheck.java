package edu.warbot.fsm.condition;

import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.editor.settings.EnumMethod;
import edu.warbot.fsm.editor.settings.EnumOperand;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WarConditionAttributCheck<BrainType extends WarBrain> extends WarCondition<BrainType> {


    EnumMethod methodName;
    EnumOperand operand;
    Integer reference;

    Method method;

    public WarConditionAttributCheck(String name, BrainType brain, GenericConditionSettings conditionSettings) {
        super(name, brain, conditionSettings);

//		JOptionPane.showMessageDialog(null, "Attention la condition <Attributcheck> n'a pas été vérifié et risque de ne pas fonctionner", "Waring not terminated condition", JOptionPane.WARNING_MESSAGE);

        if (conditionSettings.Reference != null)
            this.reference = conditionSettings.Reference;
        else
            this.reference = (int) (brain.getMaxHealth() * 30 / 100);

        if (conditionSettings.Operateur != null)
            this.operand = conditionSettings.Operateur;
        else
            this.operand = EnumOperand.egal;

        if (conditionSettings.Methode != null)
            this.methodName = conditionSettings.Methode;
        else
            JOptionPane.showMessageDialog(null, "You must chose <Method> for condition <WarConditionAttributCheck>", "Missing attribut", JOptionPane.ERROR_MESSAGE);


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
        Integer currentValue = null;
        try {
            currentValue = (Integer) (method.invoke(this.brain));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("WarCondition : unknow method for name " + method);
            e.printStackTrace();
        }

        switch (this.operand) {
            case inf:
                return currentValue < this.reference;
            case sup:
                return currentValue > this.reference;
            case egal:
                return currentValue == this.reference;
            case dif:
                return currentValue != this.reference;
            default:
                System.err.println("WarCondition : unknown operateur " + this.operand);
                return false;
        }

    }

}
