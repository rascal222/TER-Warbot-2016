package edu.warbot.gui.viewer.debug;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.launcher.AbstractWarViewer;
import edu.warbot.loader.situation.XMLSituationLoader;
import edu.warbot.tools.WarXmlWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class SaveSituationAction extends AbstractAction {

    private AbstractWarViewer warViewer;

    public SaveSituationAction(AbstractWarViewer warViewer, String text, Icon icon, String description, Integer mnemonic) {
        this(warViewer, text, icon);
        putValue(SHORT_DESCRIPTION, description);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public SaveSituationAction(AbstractWarViewer warViewer, String text, Icon icon) {
        super(text, icon);
        this.warViewer = warViewer;
    }

    public SaveSituationAction(AbstractWarViewer warViewer, String text) {
        super(text);
        this.warViewer = warViewer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        WarGame.getInstance().setGamePaused();

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return "*" + XMLSituationLoader.SITUATION_FILES_EXTENSION;
            }

            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(XMLSituationLoader.SITUATION_FILES_EXTENSION);
            }
        });
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.toString();
            if (!fileName.endsWith(XMLSituationLoader.SITUATION_FILES_EXTENSION))
                file = new File(fileName + XMLSituationLoader.SITUATION_FILES_EXTENSION);
            if (saveSituation(file))
                JOptionPane.showMessageDialog(null, "Situation sauvegardée dans " + file.getAbsolutePath(), "Sauvegarde effectuée", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde dans " + file.getAbsolutePath(), "Sauvegarde échouée", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean saveSituation(File file) {
        try {
            // Création du document
            Document doc;
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("WarSituation");
            doc.appendChild(rootElement);
            
            rootElement.appendChild(WarXmlWriter.createTextElement(doc,
                    "WarGameMode", WarGame.getInstance().getSettings().getGameMode().toString()));
            
            rootElement.appendChild(WarXmlWriter.createTextElement(doc,
                    "WarMap", WarGame.getInstance().getSettings().getSelectedMap().getClass().getSimpleName()));
            
            Element teams = doc.createElement("Teams");
            rootElement.appendChild(teams);
            for (InGameTeam t : warViewer.getGame().getAllTeams()) {
                Element currentTeam = doc.createElement("InGameTeam");
                rootElement.appendChild(currentTeam);
                currentTeam.appendChild(WarXmlWriter.createTextElement(doc,
                        "Name", t.getName()));
                for (WarAgent a : t.getAllAgents()) {
                    if (!(a instanceof WarProjectile)) {
                        Element currentAgent = doc.createElement("WarAgent");

                        currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                "Type", a.getClass().getSimpleName()));
                        currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                "xPosition", String.valueOf(a.getX())));
                        currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                "yPosition", String.valueOf(a.getY())));
                        currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                "Heading", String.valueOf(a.getHeading())));

                        if (a instanceof AliveWarAgent) {
                            currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                    "Health", String.valueOf(((AliveWarAgent) a).getHealth())));
                            if (a instanceof ControllableWarAgent) {
                                currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                        "ViewDirection", String.valueOf(((ControllableWarAgent) a).getViewDirection())));
                                currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                        "NbElementsInBag", String.valueOf(((ControllableWarAgent) a).getNbElementsInBag())));
                            }
                        } else if (a instanceof WarProjectile) {
                            currentAgent.appendChild(WarXmlWriter.createTextElement(doc,
                                    "CurrentAutonomy", String.valueOf(String.valueOf(((WarProjectile) a).getCurrentAutonomy()))));
                        }
                        currentTeam.appendChild(currentAgent);
                    }
                }
                teams.appendChild(currentTeam);
            }

            // Sauvegarde du document
            WarXmlWriter.saveXmlFile(doc, file);

            return true;
        } catch (ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException | IOException e) {
            System.err.println("Erreur lors de la création du fichier XML.");
            e.printStackTrace();
        }
        return false;
    }
}
