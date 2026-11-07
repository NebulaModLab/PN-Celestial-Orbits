package purplenebula.celestialorbits.trackers;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.util.Misc;
import lunalib.lunaSettings.LunaSettings;
import purplenebula.celestialorbits.helpers.COMisc;
import purplenebula.celestialorbits.intel.PlanetOrbitDiscoveryIntel;

/**
 * Tracks player presence in a system and sets memory if player's presence exceeds the requirement.
 * If orbit-time has passed 1x/0.5x/0.25x of the orbital period around the planet, successfully identify orbital
 * trajectory of the orbited planet.
 */
public class PassivelyRBTracker implements EveryFrameScript {
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

        if (Global.getSector().isPaused()) return;

        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
        if(playerFleet == null) return;

        StarSystemAPI starSystem = playerFleet.getStarSystem();
        if (starSystem == null) return;

        float days = Global.getSector().getClock().convertToDays(amount);

        MemoryAPI systemMemory = starSystem.getMemoryWithoutUpdate();

        float totalDays = 0;
        if (systemMemory.contains("$celorb_daysSpentInSystem")) {
            totalDays = systemMemory.getFloat("$celorb_daysSpentInSystem");
        }
        totalDays += days;
        systemMemory.set("$celorb_daysSpentInSystem", totalDays);

        for (PlanetAPI planet : starSystem.getPlanets()) {

            if (planet.getOrbit() == null) continue;

            MemoryAPI planetMem = planet.getMemoryWithoutUpdate();
            if (planetMem.getBoolean("$celorb_PassivelyDiscovered")) continue;

            float comparingOrbitalPeriod = COMisc.getComparingOrbitalPeriod(planet);
            if (totalDays >= comparingOrbitalPeriod) {
                planetMem.set("$celorb_PassivelyDiscovered",true);

                // Populate research setting variable
                String researchSetting = LunaSettings.getString("PN_CelestialOrbits", "celorb_researchRadio");
                // Check if researchSetting is null, if so, throw an error
                if (researchSetting == null)
                    throw new RuntimeException("Unable to find LunaSetting 'celorb_researchRadio' for mod 'PN_CelestialOrbits'");
                // ONLY If "Passively" is the active research setting, show floating text and intel
                if (researchSetting.equals("Passively")) {
                    Global.getSector().getPlayerFleet().addFloatingText("Identified orbital trajectory",
                            Misc.getBasePlayerColor(),2f);
//                            new PlanetOrbitDiscoveryIntel(planet,"This is a test",null);
                    Global.getSector().getCampaignUI().addMessage(new PlanetOrbitDiscoveryIntel(planet,"",null));
                }
            }

        }
    }

}
