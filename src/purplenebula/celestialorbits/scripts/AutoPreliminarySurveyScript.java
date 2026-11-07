package purplenebula.celestialorbits.scripts;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.misc.SurveyDataForPlanetIntel;
import com.fs.starfarer.api.util.Misc;
import lunalib.lunaSettings.LunaSettings;

import java.util.List;

/**
 * EFS to continuously check the player's position and if a planet is within its sensor range enough to discover it.
 * (Works ONLY if the "Auto Preliminary Survey" LunaSetting is enabled)
 */
public class AutoPreliminarySurveyScript implements EveryFrameScript {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {

        if (Boolean.TRUE.equals(LunaSettings.getBoolean("PN_CelestialOrbits", "celorb_disableModBoolean"))) return;

        // If LunaSetting celorb_autoPreliminarySurveyBoolean is false, do not run this script
        if (Boolean.FALSE.equals(LunaSettings.getBoolean("PN_CelestialOrbits",
                "celorb_autoPreliminarySurveyBoolean"))) return;

        // Declarations and null-checks
        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        if (playerFleet == null) return;
        StarSystemAPI starSystem = playerFleet.getStarSystem();
        if (starSystem == null) return;
        List<PlanetAPI> planets = starSystem.getPlanets();
        if (planets == null || planets.isEmpty()) return;

        // Loop through all planets in the system
        for (PlanetAPI planet : planets) {

            // Declare market object and do a null-check
            MarketAPI market = planet.getMarket();
            if (market == null) continue;

            // If survey level of planet market object is full or preliminary, skip
            if (market.getSurveyLevel() == MarketAPI.SurveyLevel.FULL ||
                    market.getSurveyLevel() == MarketAPI.SurveyLevel.PRELIMINARY) continue;

            // Get max sensor range to be in to detect a planet
            float maxSensorRangeToDetect = playerFleet.getMaxSensorRangeToDetect(planet);

            // Get distance from player fleet to planet
            float distance = Misc.getDistance(playerFleet, planet);

            // Set preliminary surveyed status if within range
            if (distance <= maxSensorRangeToDetect) {

                // Set preliminary surveyed state
                Misc.setPreliminarySurveyed(
                        planet.getMarket(), null, false);
                float sensorProfile = planet.getSensorProfile();
                float sensorStrength = planet.getSensorStrength();
                String text = "Remote survey telemery received.";

                // Create survey data for planet intel
                new SurveyDataForPlanetIntel(planet, text, null);

            }
        }

    }
}
