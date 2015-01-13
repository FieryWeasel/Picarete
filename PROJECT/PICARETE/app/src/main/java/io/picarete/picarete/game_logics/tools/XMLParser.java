package io.picarete.picarete.game_logics.tools;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.model.container.AUnlock;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.Condition;
import io.picarete.picarete.model.container.Level;
import io.picarete.picarete.model.container.Title;
import io.picarete.picarete.model.container.UnlockColor;
import io.picarete.picarete.model.container.UnlockMode;
import io.picarete.picarete.model.data_sets.GameModeSet;

/**
 * Created by iem on 13/01/15.
 */
public class XMLParser {

    private static final String ELEMENT_CONFIG = "config";
    private static final String ELEMENT_TITLE = "title";
    private static final String ELEMENT_TITLE_VALUE = "value";
    private static final String ELEMENT_TITLE_CONDITION = "condition";

    private static List<Level> levels = new ArrayList<>();
    private static List<Title> titles = new ArrayList<>();

    public static List<Level> getLevels(InputStream in){

        if(levels.size() == 0)
            parse(in, 0);
        return levels;

    }

    public static List<Title> getTitles(InputStream in){

        if(titles.size() == 0)
        parse(in, 1);
        return titles;

    }

    private static void parse(InputStream in, int typeParse){

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        parseXML(parser, typeParse);

    }

    private static void parseXML(XmlPullParser parser, int typeParse) {

        try{
            int eventType = parser.getEventType();
            Title title = new Title("", new ArrayList<Condition>());
            while (eventType != XmlPullParser.END_DOCUMENT){

                switch(typeParse) {
                    case 0:
                        parseConfig(parser, eventType);
                        break;
                    case 1:
                        parseTitles(parser, eventType, title);
                        break;
                }

                eventType = parser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void parseTitles(XmlPullParser parser, int event, Title title) {

        switch (event){

            case XmlPullParser.START_DOCUMENT:

                break;

            case XmlPullParser.START_TAG:
                String name = parser.getName();
                if (name.equalsIgnoreCase(ELEMENT_TITLE)) {

                    title = new Title("", new ArrayList<Condition>());

                }else if(name.equalsIgnoreCase(ELEMENT_TITLE_VALUE)){

                    title.title = parser.getText();

                }else if(name.equalsIgnoreCase(ELEMENT_TITLE_CONDITION)){

                    Map<Condition.EConditionType, String> map = new HashMap<>();

                    String GAME_MODE = parser.getAttributeValue(null, "game_mode");
                    String MODE = parser.getAttributeValue(null, "mode");
                    String WIN = parser.getAttributeValue(null, "win");
                    String LOST = parser.getAttributeValue(null, "lost");
                    String PLAY = parser.getAttributeValue(null, "played");
                    String LEVEL = parser.getAttributeValue(null, "level");
                    String DIFFICULTY = parser.getAttributeValue(null, "difficulty");

                    if(GAME_MODE != null){
                        map.put(Condition.EConditionType.GAME_MODE, GAME_MODE);
                    }

                    if(MODE != null){
                        map.put(Condition.EConditionType.MODE, MODE);
                    }

                    if(WIN != null){
                        map.put(Condition.EConditionType.WIN, WIN);
                    }

                    if(LOST != null){
                        map.put(Condition.EConditionType.LOST, LOST);
                    }

                    if(PLAY != null){
                        map.put(Condition.EConditionType.PLAY, PLAY);
                    }

                    if(LEVEL != null){
                        map.put(Condition.EConditionType.LEVEL, LEVEL);
                    }

                    if(DIFFICULTY != null){
                        map.put(Condition.EConditionType.DIFFICULTY, DIFFICULTY);
                    }


                    Condition condition = new Condition(map);
                    title.conditions.add(condition);
                }
                break;
            case XmlPullParser.END_TAG:
                if(parser.getName().equalsIgnoreCase(ELEMENT_TITLE))
                    titles.add(title);
                break;
        }
    }

    private static void parseConfig(XmlPullParser parser, int event){
        String name = parser.getName();
        Level currentLevel;
        switch (event){

            case XmlPullParser.START_DOCUMENT:

                break;

            case XmlPullParser.START_TAG:

                if (name.equalsIgnoreCase(ELEMENT_CONFIG)) {
                    String lvl = parser.getAttributeValue(null, "level");
                    String color = parser.getAttributeValue(null, "color");
                    String mode = parser.getAttributeValue(null, "mode");
                    String ia = parser.getAttributeValue(null, "ia");

                    currentLevel = new Level(new LinkedList<AUnlock>());

                    currentLevel.id = Integer.parseInt(lvl);

                    if (color != null) {
                        UnlockColor unlockColor = new UnlockColor(new ColorCustom(color));
                        currentLevel.unlocks.add(unlockColor);
                    }

                    if (mode != null) {
                        UnlockMode unlockColor = new UnlockMode(GameModeSet.searchGameMode(mode));
                        currentLevel.unlocks.add(unlockColor);
                    }

                    if (ia != null) {
                        UnlockColor unlockColor = new UnlockColor(new ColorCustom(color));
                        currentLevel.unlocks.add(unlockColor);
                    }

                    levels.add(currentLevel);
                }

            case XmlPullParser.END_TAG:

                name = parser.getName();
                break;
        }

    }
}

