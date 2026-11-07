package purplenebula.celestialorbits.trackers;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.OrbitAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.CampaignPlanet;
import com.fs.starfarer.campaign.CampaignTerrain;
import lunalib.lunaSettings.LunaSettings;
import org.lazywizard.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tracks how long the player fleet has orbited planets.
 * If orbit-time has passed 1/0.5/0.25 of the orbital period around the planet, successfully identify orbital
 * trajectories of the orbited planet and planets/moons around it.
 */
public class OrbitTrackerBackup implements EveryFrameScript {
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

        OrbitAPI playerFleetOrbit = playerFleet.getOrbit();
        if(playerFleetOrbit == null) return;

        SectorEntityToken playerFleetOrbitFocus = playerFleet.getOrbitFocus();
        if(playerFleetOrbitFocus == null) return;

        float pfCircularOrbitPeriod = playerFleet.getCircularOrbitPeriod()/4; // CONTINUE | ADD SETTING FOR PARTIALS
        float days = Global.getSector().getClock().convertToDays(amount);

        // If player fleet orbit focus is a planet itself:
        if (playerFleetOrbitFocus instanceof CampaignPlanet planet) {

            if (planet.getMemoryWithoutUpdate().getBoolean("$celorb_isMeasured")) return;

            MemoryAPI planetMemory = planet.getMemoryWithoutUpdate();

            float totalDays = 0;
            if (planetMemory.contains("$celorb_daysSpentInOrbit")) {
                totalDays = planetMemory.getFloat("$celorb_daysSpentInOrbit");
            }
            totalDays += days;
            planetMemory.set("$celorb_daysSpentInOrbit", totalDays);

            if (totalDays >= pfCircularOrbitPeriod) {
                planetMemory.set("$celorb_isMeasured",true);
                setChildPlanetsIsMeasuredMemKey(planet);
                playerFleet.addFloatingText("Identified orbital trajectories", Misc.getBasePlayerColor(),2f);
            }

        }
        // In case player fleet orbit focus is campaign terrain covering a planet's orbit:
        else if (playerFleetOrbitFocus instanceof CampaignTerrain terrain) {
            // Null-check to see if terrain is even connected to a planet
            if (terrain.getOrbitFocus() == null) return;
            // Check if terrain plugin is a magnetic field
            if (terrain.getPlugin() instanceof MagneticFieldTerrainPlugin) {
                if (terrain.getOrbitFocus() instanceof CampaignPlanet planet) {

                    if (planet.getMemoryWithoutUpdate().getBoolean("$celorb_isMeasured")) return;

                    MemoryAPI planetMemory = planet.getMemoryWithoutUpdate();

                    float totalDays = 0;
                    if (planetMemory.contains("$celorb_daysSpentInOrbit")) {
                        totalDays = planetMemory.getFloat("$celorb_daysSpentInOrbit");
                    }
                    totalDays += days;
                    planetMemory.set("$celorb_daysSpentInOrbit", totalDays);

                    if (totalDays >= pfCircularOrbitPeriod) {
                        planetMemory.set("$celorb_isMeasured",true);
                        setChildPlanetsIsMeasuredMemKey(planet);
                        playerFleet.addFloatingText("Identified orbital trajectories", Misc.getBasePlayerColor(),2f);
                    }

                }

            }


        }


//        import com.fs.starfarer.campaign.CampaignPlanet;
//        import com.fs.starfarer.campaign.CampaignTerrain;
//        import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin;
//        CampaignFleetAPI playerFleet = Global.getSector().getPlayerFleet();
//        OrbitAPI playerFleetOrbit = playerFleet.getOrbit();
//        SectorEntityToken playerFleetOrbitFocus = playerFleet.getOrbitFocus();
//        float pfCircularOrbitPeriod = playerFleet.getCircularOrbitPeriod();
//        if (playerFleetOrbit != null && playerFleetOrbitFocus != null && (playerFleetOrbitFocus instanceof CampaignPlanet)) {
//
//            List<PlanetAPI> orbitalChildren = new ArrayList<PlanetAPI>();
//            List<String> orbitalChildrenNames = new ArrayList<String>();
//            for (PlanetAPI planet : playerFleetOrbitFocus.getStarSystem().getPlanets()) {
//                if (planet.getOrbitFocus() != playerFleetOrbitFocus) continue;
//                orbitalChildren.add(planet);
//                orbitalChildrenNames.add(planet.getFullName());
//            }
//
//            Console.showMessage("======================================================================");
//            Console.showMessage("Orbit:        " + playerFleetOrbit);
//            Console.showMessage("======================================================================");
//            Console.showMessage("Orbit focus:  " + playerFleetOrbitFocus);
//            Console.showMessage("   ID:   " + playerFleetOrbitFocus.getId());
//            Console.showMessage("   Name: " + playerFleetOrbitFocus.getName());
//            Console.showMessage("   CES:  " + playerFleetOrbitFocus.getCustomEntitySpec());
//            Console.showMessage("   MEM:  " + playerFleetOrbitFocus.getMemoryWithoutUpdate().getKeys());
//            Console.showMessage("   SUBS: " + orbitalChildrenNames);
//            Console.showMessage("======================================================================");
//            Console.showMessage("Orbit period: " + pfCircularOrbitPeriod);
//        }
//        else if (playerFleetOrbitFocus instanceof CampaignTerrain) {
//            CampaignTerrain terrain = (CampaignTerrain) playerFleetOrbitFocus;
//            if (terrain.getPlugin() instanceof MagneticFieldTerrainPlugin) {
//
//                List<PlanetAPI> orbitalChildren = new ArrayList<PlanetAPI>();
//                List<String> orbitalChildrenNames = new ArrayList<String>();
//                for (PlanetAPI planet : terrain.getOrbitFocus().getStarSystem().getPlanets()) {
//                    if (planet.getOrbitFocus() != terrain.getOrbitFocus()) continue;
//                    orbitalChildren.add(planet);
//                    orbitalChildrenNames.add(planet.getFullName());
//                }
//
//                Console.showMessage("======================================================================");
//                Console.showMessage("Terrain orbit: " + terrain.getOrbit());
//                Console.showMessage("======================================================================");
//                Console.showMessage("Terrain orbit focus: " + terrain.getOrbitFocus());
//                Console.showMessage("   ID:   " + terrain.getOrbitFocus().getId());
//                Console.showMessage("   Name: " + terrain.getOrbitFocus().getName());
//                Console.showMessage("   CES:  " + terrain.getOrbitFocus().getCustomEntitySpec());
//                Console.showMessage("   MEM:  " + terrain.getOrbitFocus().getMemoryWithoutUpdate().getKeys());
//                Console.showMessage("   SUBS: " + orbitalChildrenNames);
//                Console.showMessage("======================================================================");
//            }
//        }
    }

    /**
     * Sets the MemKey "$celorb_isMeasured" to "true" of a planet's child orbital planets
     * if the measured planet has any.
     * @param planet The parent planet that has been measured
     */
    private void setChildPlanetsIsMeasuredMemKey(CampaignPlanet planet) {
        for (PlanetAPI childPlanet : findOrbitalChildren(planet)) {
            childPlanet.getMemoryWithoutUpdate().set("$celorb_isMeasured",true);
        }
    }

    /**
     * Finds all planets in a star-system that are sub-entities of a sector entity
     * @param entity The entity to find the sub-entities of
     * @return A list of found sub-entities
     */
    private List<PlanetAPI> findOrbitalChildren(SectorEntityToken entity) {
//        List<PlanetAPI> orbitalChildren = new ArrayList<>();
//        for (PlanetAPI planet : entity.getStarSystem().getPlanets()) {
//            if (planet.getOrbitFocus() != entity) continue;
//            orbitalChildren.add(planet)
//        }
        return entity.getStarSystem().getPlanets().stream()
                .filter(p -> p.getOrbitFocus() == entity)
                .collect(Collectors.toList());
    }
}
