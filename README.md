# Celestial Orbits
![img.png](img.png)
> "I kinda wish we had an orbital trajectory line for anything we lock onto in the map"  
> \- S0medudewhodoesthings, KMB "starsector-general" chat

That message is what got me going down a rabbit hole, although in the end not as 
deep as I thought, on how to make orbital trajectory lines show up for things that 
orbit around celestial objects. And thus, "Celestial Orbits" was born.

![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/NebulaModLab/Celestial-Orbits/total?style=for-the-badge&color=%234D23A8&link=https%3A%2F%2Fgithub.com%2FNebulaModLab%2FCelestial-Orbits%2Freleases)

## What is "Celestial Orbits"?
By default, this mod allows you to learn and see the orbital trajectory of planetary 
bodies upon doing a full survey of a planet. Why a full survey of a planet? For 
gameplay ease-of-use reasons and not requiring you to stay in-system for the duration 
of a planets full rotation around its orbital focus, be it a star or another planet.
Although, *this **is** a setting you can enable*, if you want a more semi-realistic
way of surveying a planet's orbital trajectory.

### Features
- Visualising orbital trajectories of planetary bodies, stations, gates, and 
  jump-points within a system.
- "Course focus", highlights or only shows the orbit of the course destination object. (Useful 
  for intercepting. Can be switched between or disabled in settings.)
- A wide array of settings:
  - Change which celestial objects can show their orbit:
    - Only planetary bodies **(default)**
    - Stations included
    - All celestial objects
  - Change the learning/unlocking behavior:
    - "On Full Survey": Requires the player to fully survey a planet. **(default)**
    - "Always show": Shows every orbital trajectory.
    - "Preliminary": Preliminary survey a planet to know its orbit (Has an extra setting 'Auto Preliminary Survey' in 
      the 'Extras' tab)
    - "Passively": Discover orbits passively as you spend time within a system.
      - **Extra setting:** Choice between full, half, and third of the orbital 
        rotation time.
    - "Orbital": Discover orbits by hanging out in a planets orbit for some time. When done on a planet with moons, also
      discovers the orbits of those moons.
  - Change the "Course focus" behavior:
    - "Highlight": Highlights the course target's orbit. **(default)**
    - "Filter": Filters out the course target's orbit, showing only that orbit.
    - "Disabled": Disabled the functionality.
  - Change the color of trajectories:
    - Switch option to enable/disable faction coloring.
      - If a planet/station has a faction owner, change color to the faction's.
    - Switch option to enable/disable custom coloring.
      - Built-in LunaSetting color picker
    - Slider option to change the alpha level of the trajectories, controlling the transparency/opacity

### F.A.Q. 
> Q: Can this be removed mid-save?  
> A: Yes, **BUT** with a requirement, being ONE of the following:
>    1. You have left your save in hyperspace and not within a star system.
>    2. You disabled the mod through LunaSettings.

### AI use disclaimer
No AI was used in the creation of this mod's code or graphical assets

### Credits
- Fractal Softworks for the amazing game we all love to play
- Vexlia & Crablobab for helping me identify orbital junk entities
- Wymorlon for helping me think of a name for the mod
- Kaysaar for pointing me in the right direction of using custom entities & VoK's way of handling the Nidavelir
