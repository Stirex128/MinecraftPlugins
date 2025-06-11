package cz.cvut.fel.pjv.stolbajakub;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public final class PermsManager {

    private static final Map<UUID, List<PermissionAttachment>> playerPermissions = new HashMap<>();

    public static void store(Player player, PermissionAttachment attachment) {
        List<PermissionAttachment> permissionsList = playerPermissions.get(player.getUniqueId());
        if (permissionsList == null) {
            permissionsList = new ArrayList<>();
            playerPermissions.put(player.getUniqueId(), permissionsList);
        }
        permissionsList.add(attachment);
    }

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

    public static boolean hasPermissionSet(Player player, String permission) {
        for (PermissionAttachment attachment : playerPermissions.getOrDefault(player.getUniqueId(), new ArrayList<>())) {
            if (attachment.getPermissions().containsKey(permission)) {
                return attachment.getPermissions().get(permission);
            }
        }
        return false;
    }
    public static PermissionAttachment getPermission(Player player, PermissionAttachment attachment) {
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
    public static void loadPermissionsForPlayer(Player player, Map<String, Boolean> permissions) {
        PermissionAttachment attachment = player.addAttachment(plugin); // 'plugin' musí být instance vašeho pluginu
        permissions.forEach(attachment::setPermission);

        List<PermissionAttachment> attachments = playerPermissions.getOrDefault(player.getUniqueId(), new ArrayList<>());
        attachments.add(attachment);
        playerPermissions.put(player.getUniqueId(), attachments);
    }
}

