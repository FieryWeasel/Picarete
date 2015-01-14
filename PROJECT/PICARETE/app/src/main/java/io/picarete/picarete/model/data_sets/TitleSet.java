package io.picarete.picarete.model.data_sets;

import android.content.Context;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.tools.XMLParser;
import io.picarete.picarete.model.container.Title;

/**
 * Created by iem on 13/01/15.
 */
public class TitleSet {

    private static List<Title> titles = null;

    public static List<Title> getTitles(Context context){
        if (titles == null){
            titles = XMLParser.getTitles(context.getResources().openRawResource(R.raw.titles));
        }

        return titles;
    }
}
