package cz.cvut.fel.pjv.stolbajakub.bedwars.listeners.guis.helpers;

/**
 * Represents an item in a GUI menu, encapsulating the details necessary for display and interaction.
 * This class holds information about the menu item's name and the icon used for display.
 */
public class MainMenuItem {
    private String name;
    private String icon;

    /**
     * Constructs a new menu item with the specified name and icon.
     *
     * @param name The display name of the menu item.
     * @param icon The icon key or identifier used to represent the menu item visually.
     */
    public MainMenuItem(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
