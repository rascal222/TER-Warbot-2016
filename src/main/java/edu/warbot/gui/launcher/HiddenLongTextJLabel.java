package edu.warbot.gui.launcher;

import javax.swing.*;

@SuppressWarnings("serial")
public class HiddenLongTextJLabel extends JLabel {

    private String _normalText;
    private String _compressedText;

    public HiddenLongTextJLabel(String txt, int maxCharsInARow, int nbRowsMax) {
        super();
        _compressedText = "<html>";
        String txtLeft = txt;
        int nbRowsCurrentText = 1;
        int indexLastSpaceBeforeNewLine;
        while (txtLeft.length() > maxCharsInARow && nbRowsCurrentText <= nbRowsMax) {
            indexLastSpaceBeforeNewLine = txtLeft.substring(0, maxCharsInARow).lastIndexOf(" ");
            _compressedText += txtLeft.substring(0, indexLastSpaceBeforeNewLine);
            if (nbRowsCurrentText < nbRowsMax) {
                _compressedText += "<br>";
                txtLeft = txtLeft.substring(indexLastSpaceBeforeNewLine);
            }
            nbRowsCurrentText++;
        }
        if (txtLeft.length() < maxCharsInARow) {
            _compressedText += txtLeft;
        } else
            _compressedText += "...";
        _compressedText += "</html>";
        setText(_compressedText);

        txtLeft = txt;
        _normalText = "<html>";
        while (txtLeft.length() > maxCharsInARow) {
            indexLastSpaceBeforeNewLine = txtLeft.substring(0, maxCharsInARow).lastIndexOf(" ");
            _normalText += txtLeft.substring(0, indexLastSpaceBeforeNewLine);
            txtLeft = txtLeft.substring(indexLastSpaceBeforeNewLine);
            if (txtLeft.length() > maxCharsInARow)
                _normalText += "<br>";
        }
        _normalText += txtLeft + "</html>";
        setToolTipText(_normalText);
    }
}
