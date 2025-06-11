package cz.cvut.fel.pjv.stolbajakub.myperms;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;
/**
 * Manages player permissions including storing, removing, checking, and loading permissions.
 */

public final class PermsManager {
    private PermsManager(){

    }

    private static final Map<UUID, List<PermissionAttachment>> playerPermissions = new HashMap<>();

    /**
     * Stores the given permission attachment for the player.
     *
     * @param player     The player whose permission is being stored.
     * @param attachment The permission attachment to store.
     */

    public static void store(Player player, PermissionAttachment attachment) {
        List<PermissionAttachment> permissionsList = playerPermissions.get(player.getUniqueId());
        if (permissionsList == null) {
            permissionsList = new ArrayList<>();
            playerPermissions.put(player.getUniqueId(), permissionsList);
        }
        permissionsList.add(attachment);
    }
    /**
     * Removes the given permission from the player.
     *
     * @param player     The player whose permission is being removed.
     * @param permission The permission to remove.
     * @return True if the permission was successfully removed, false otherwise.
     */
    public static boolean remove(Player player, String permission) {
        List<PermissionAttachment> permissionsList = playerPermissions.get(player.getUniqueId());
        if (permissionsList != null) {
            for (PermissionAttachment attachment : new ArrayList<>(permissionsList)) { // Iterace přes kopii, abychom mohli bezpečně odebírat
                if (attachment.getPermissions().containsKey(permission)) {
                    attachment.unsetPermission(permission);

                    if (attachment.getPermissions().isEmpty()) {
                        player.removeAttachment(attachment);
                        permissionsList.remove(attachment);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player has a certain permission set.
     *
     * @param player     The player to check.
     * @param permission The permission to check.
     * @return True if the player has the permission set, false otherwise.
     */
    public static boolean hasPermissionSet(Player player, String permission) {
        for (PermissionAttachment attachment : playerPermissions.getOrDefault(player.getUniqueId(), new ArrayList<>())) {
            if (attachment.getPermissions().containsKey(permission)) {
                return attachment.getPermissions().get(permission);
            }
        }
        return false;
    }
//nepotřebná nyxní
    private static PermissionAttachment getPermission(Player player, PermissionAttachment attachment) {
        List<PermissionAttachment> permissionsList = playerPermissions.get(player.getUniqueId());
        if (permissionsList == null) {
            return null;
        }
        if (permissionsList.contains(attachment)){
            return attachment;
        } else return null;

    }
    public static List<PermissionAttachment> getPlayerPermissions(UUID playerId) {
        return playerPermissions.getOrDefault(playerId, new ArrayList<>());
    }
    public static void freeSpace(Player player){
        playerPermissions.remove(player.getUniqueId());

    }

    /**
     * Loads permissions for a player from a map.
     *
     * @param player       The player to load permissions for.
     * @param permissions  The permissions to load.
     */
    public static void loadPermissionsForPlayer(Player player, Map<String, Boolean> permissions) {
        PermissionAttachment attachment = player.addAttachment(Myperms.getInstance());
        permissions.forEach((permission, value) -> attachment.setPermission(permission, value));

        List<PermissionAttachment> attachments = playerPermissions.getOrDefault(player.getUniqueId(), new ArrayList<>());
        attachments.add(attachment);
        playerPermissions.put(player.getUniqueId(), attachments);
    }

}

