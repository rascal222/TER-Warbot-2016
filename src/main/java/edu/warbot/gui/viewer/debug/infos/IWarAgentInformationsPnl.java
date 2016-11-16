package edu.warbot.gui.viewer.debug.infos;

import java.text.DecimalFormat;

public interface IWarAgentInformationsPnl {

    public static DecimalFormat doubleFormatter = new DecimalFormat("0.00");

    public void update();

    public void resetInfos();
}
