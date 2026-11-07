package purplenebula.celestialorbits;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorAPI;
import purplenebula.celestialorbits.scripts.AutoPreliminarySurveyScript;
import purplenebula.celestialorbits.trackers.InSystemTracker;
import purplenebula.celestialorbits.trackers.OrbitalRBTracker;
import purplenebula.celestialorbits.trackers.PassivelyRBTracker;

public class Celorb_ModPlugin extends BaseModPlugin {


    private Celorb_ModPlugin covModPlugin;

    public Celorb_ModPlugin() {
        covModPlugin = this;
    }

    public Celorb_ModPlugin getCovModPlugin() {
        return covModPlugin;
    }

    @Override
    public void onGameLoad(boolean newGame) {

        SectorAPI sector = Global.getSector();

        sector.addTransientScript(new InSystemTracker());
        sector.addTransientScript(new PassivelyRBTracker());
        sector.addTransientScript(new OrbitalRBTracker());
        sector.addTransientScript(new AutoPreliminarySurveyScript());

    }

}
