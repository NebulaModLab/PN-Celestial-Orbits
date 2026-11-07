package purplenebula.celestialorbits.helpers;

import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import lunalib.lunaSettings.LunaSettings;

/**
 * Miscellaneous helper class for Celestial Orbits containing often used/helpful methods.
 */
public class COMisc {

    /**
     * Gets the orbital period to compare time spent with.
     * @param planet Planet to retrieve the orbital period from.
     * @return The planet's orbital period calculated to whole, half, or quarter the amount.
     */
    public static float getComparingOrbitalPeriod(PlanetAPI planet) {
        // Populate researchTime setting variable
        String researchTimeSetting = LunaSettings.getString(
                "PN_CelestialOrbits", "celorb_researchTimeRadio"
        );
        // Check if researchTimeSetting is null, if so, throw an error
        if (researchTimeSetting == null) throw new RuntimeException(
                "Unable to find LunaSetting 'celorb_researchTimeRadio' for mod 'PN_CelestialOrbits'"
        );
        if (researchTimeSetting.equals("Quarter Rotation")) {
            return planet.getOrbit().getOrbitalPeriod()/4;
        }
        else if (researchTimeSetting.equals("Half Rotation")) {
            return planet.getOrbit().getOrbitalPeriod()/2;
        }
        else {
            return planet.getOrbit().getOrbitalPeriod();
        }
    }

    /**
     * Gets the orbital period to compare time spent with.
     * @param fleet Fleet to retrieve the orbital period from.
     * @return The fleet's orbital period calculated to whole, half, or quarter the amount.
     */
    public static float getComparingOrbitalPeriod(CampaignFleetAPI fleet) {
        // Populate researchTime setting variable
        String researchTimeSetting = LunaSettings.getString(
                "PN_CelestialOrbits", "celorb_researchTimeRadio"
        );
        // Check if researchTimeSetting is null, if so, throw an error
        if (researchTimeSetting == null) throw new RuntimeException(
                "Unable to find LunaSetting 'celorb_researchTimeRadio' for mod 'PN_CelestialOrbits'"
        );
        if (researchTimeSetting.equals("Quarter Rotation")) {
            return fleet.getOrbit().getOrbitalPeriod()/4;
        }
        else if (researchTimeSetting.equals("Half Rotation")) {
            return fleet.getOrbit().getOrbitalPeriod()/2;
        }
        else {
            return fleet.getOrbit().getOrbitalPeriod();
        }
    }

}
