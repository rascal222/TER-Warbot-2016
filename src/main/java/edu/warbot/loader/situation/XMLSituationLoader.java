package edu.warbot.loader.situation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.exceptions.UnauthorizedAgentException;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameMode;
import edu.warbot.game.WarGameSettings;
import edu.warbot.launcher.WarLauncher;
import edu.warbot.loader.SituationLoader;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.WarXmlReader;


/**
 * @version 2.0
 * @since 3.2.2
 */
public class XMLSituationLoader implements SituationLoader {

    public static final String SITUATION_FILES_EXTENSION = ".warsit";

    private Map<String, ArrayList<HashMap<String, String>>> xmlSituationFileContent;
    
    private WarGameSettings _settings;

    public XMLSituationLoader(File xmlSituationFile, WarGameSettings settings, Map<String, Team> availableTeams)
    {
    	_settings = settings;
        xmlSituationFileContent = getXmlSituationFileContent(xmlSituationFile);
        for(String nameTeam : xmlSituationFileContent.keySet())
        {
        	if(availableTeams.get(nameTeam) != null)
        		_settings.addSelectedTeam(availableTeams.get(nameTeam));
        	else if((nameTeam.endsWith(".0") || nameTeam.endsWith(".1") || nameTeam.endsWith(".2") || nameTeam.endsWith(".3")
        			|| nameTeam.endsWith(".4") || nameTeam.endsWith(".5") || nameTeam.endsWith(".6") || nameTeam.endsWith(".7")
        			|| nameTeam.endsWith(".8") || nameTeam.endsWith(".9")) && availableTeams.get(nameTeam.substring(0, nameTeam.length()-2)) != null)
        		_settings.addSelectedTeam(availableTeams.get(nameTeam.substring(0, nameTeam.length()-2)));
        }
    }


    /**
     * Retourne une hashmap contenant les équipes (identifiées par leur nom). Chaque équipe est composée d'une liste d'agents.
     * Chaque donnée de chaque agent est enregistrée dans une hashmap
     * Exemple : je veux la position en X du premier agent de l'équipe "Test" :
     * Double.valueOf(getXmlSituationFileContent(file).get("Test").get(0).get("xPosition"));
     */
    private Map<String, ArrayList<HashMap<String, String>>> getXmlSituationFileContent(File file) {
        String rootPath = "/WarSituation";
        String gameModePath = rootPath + "/WarGameMode";
        String mapPath = rootPath + "/WarMap";
        String teamsPath = rootPath + "/Teams";
        HashMap<String, ArrayList<HashMap<String, String>>> toReturn = new HashMap<>();
        try {
            // Ouverture du document
            Document doc = WarXmlReader.openXmlFile(file.getAbsolutePath());

            // Chargement
            XPath xPath = XPathFactory.newInstance().newXPath();
            
            String gameModeName = (String) xPath.compile(gameModePath).evaluate(doc, XPathConstants.STRING);
            _settings.setGameMode(WarGameMode.valueOf(gameModeName));
            
            String mapName = (String) xPath.compile(mapPath).evaluate(doc, XPathConstants.STRING);
            try
            {
            	String pathMapClass = AbstractWarMap.class.getPackage().getName() + "." + mapName;
				_settings.setSelectedMap((AbstractWarMap) Class.forName(pathMapClass).getConstructor().newInstance());
			}
            catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e)
            {
				e.printStackTrace();
			}
            
            NodeList teamsNode = (NodeList) xPath.compile(teamsPath).evaluate(doc, XPathConstants.NODESET);
            teamsNode = teamsNode.item(0).getChildNodes();
            
            Node node;
            for (int i = 0; i < teamsNode.getLength(); i++) { // Parcours des équipes
                if (teamsNode.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    NodeList teamNode = teamsNode.item(i).getChildNodes();
                    // On récupère l'équipe courante (l'objet)
                    String currentTeamName = teamNode.item(0).getFirstChild().getNodeValue();
                    ArrayList<HashMap<String, String>> agentsOfCurrentTeam = new ArrayList<>();

                    for (int j = 0; j < teamNode.getLength(); j++) { // Parcours des agents d'une équipe
                        node = teamNode.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if (node.getNodeName().equals("WarAgent")) {
                                // On récupère tous les paramètres de l'agent
                                agentsOfCurrentTeam.add(WarXmlReader.getNodesFromNodeList(doc, node.getChildNodes()));
                            }
                        }
                    } // Fin parcours des agents

                    toReturn.put(currentTeamName, agentsOfCurrentTeam);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (ParserConfigurationException | TransformerFactoryConfigurationError | IOException | SAXException | IllegalArgumentException | SecurityException e) {
            System.err.println("Erreur lors du chargement depuis le fichier XML.");
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            System.err.println("Wrong XPath : " + teamsPath);
            e.printStackTrace();
        }
        return toReturn;
    }

    public List<InGameTeam> getTeamsToLoad() {
        List<InGameTeam> teamsToLoad = new ArrayList<InGameTeam>();

//        for (String teamName : xmlSituationFileContent.keySet()) {
//            if (!teamName.equals(MotherNatureTeam.NAME)) {
//                InGameTeam currentInGameTeam = Shared.getAvailableTeams().get(InGameTeam.getRealNameFromTeamName(teamName));
//                // On vérifie si le jar de l'équipe existe
//                if (currentInGameTeam == null) {
//                    System.err.println("Le fichier JAR de l'équipe " + InGameTeam.getRealNameFromTeamName(teamName) + " est manquant.");
//                    System.exit(0);
//                } else {
//                    if (!teamsToLoad.contains(currentInGameTeam))
//                        teamsToLoad.add(currentInGameTeam);
//                    else
//                        teamsToLoad.add(currentInGameTeam.duplicate(teamName));
//                }
//            }
//        }

        return teamsToLoad;
    }

    private void launchAllAgentsFromXmlSituationFile(WarLauncher launcher, WarGame game) {
        ArrayList<InGameTeam> inGameTeams = game.getPlayerTeams();
        inGameTeams.add(game.getMotherNatureTeam());

        try {
            for (InGameTeam t : inGameTeams) {
                ArrayList<HashMap<String, String>> agentsOfCurrentTeam = xmlSituationFileContent.get(t.getName());
                for (HashMap<String, String> agentDatas : agentsOfCurrentTeam) {
                    WarAgent agent;
                    String agentTypeName = agentDatas.get("Type");
                    try {
                        if (WarAgentType.valueOf(agentTypeName).getCategory() == WarAgentCategory.Resource) {
                            agent = game.getMotherNatureTeam().instantiateNewWarResource(agentTypeName);
                        } else if (WarAgentType.valueOf(agentTypeName).isControllable()) {
                            agent = t.instantiateNewControllableWarAgent(agentTypeName);
    		                t.getTeam().associateBrain((ControllableWarAgent) agent);
                        } else {
                            agent = t.instantiateNewBuilding(agentTypeName);
                        }
                        launcher.launchAgent(agent);
                        agent.setPosition(Double.valueOf(agentDatas.get("xPosition")), Double.valueOf(agentDatas.get("yPosition")));
                        if (agent instanceof ControllableWarAgent) {
                            agent.setHeading(Double.valueOf(agentDatas.get("Heading")));
                            ((ControllableWarAgent) agent).setViewDirection(Double.valueOf(agentDatas.get("ViewDirection")));
                            ((ControllableWarAgent) agent).init(Integer.valueOf(agentDatas.get("Health")),
                                    Integer.valueOf(agentDatas.get("NbElementsInBag")));
                        } else if (agent instanceof AliveWarAgent) {
                            ((AliveWarAgent) agent).init(Integer.valueOf(agentDatas.get("Health")));
                        }
                    } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                        System.err.println("Erreur lors de l'instanciation de l'agent. Type non reconnu : " + agentTypeName);
                        e.printStackTrace();
                    } catch (UnauthorizedAgentException e) {
                    	System.err.println(e.getMessage());
					}
                }
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            System.err.println("Erreur lors de l'instanciation des classes à partir des données XML");
            e.printStackTrace();
        }
    }

    @Override
    public void launchAllAgentsFromSituation(WarLauncher warLauncher, WarGame warGame) {
        launchAllAgentsFromXmlSituationFile(warLauncher, warGame);
    }
}
