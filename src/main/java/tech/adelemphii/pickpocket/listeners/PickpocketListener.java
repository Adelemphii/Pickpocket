package tech.adelemphii.pickpocket.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PickpocketListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if(event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if(!event.getPlayer().isSneaking()) {
            return;
        }

        if(!(event.getRightClicked() instanceof Player)) {
            return;
        }
        Player clicker = event.getPlayer();
        Player clicked = (Player) event.getRightClicked();

        if(clicker.getInventory().getItemInMainHand().getType() != Material.AIR) {
            clicker.sendMessage(Component.text("Your main hand must be empty to pickpocket!").color(NamedTextColor.RED));
            return;
        }

        ItemStack item = pickpocket(clicked);
        if(item == null) {
            clicker.sendMessage(Component.text("You failed to pickpocket ").color(NamedTextColor.GREEN)
                .append(Component.text(clicked.getName()).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD))
                .append(Component.text("!").color(NamedTextColor.GREEN)));
            clicked.sendMessage(Component.text(clicker.getName() + " tried to pickpocket you, but your inventory was empty!").color(NamedTextColor.RED));
            return;
        }

        clicker.getInventory().setItemInMainHand(item);
        clicker.sendMessage(Component.text("You pickpocketed ").color(NamedTextColor.GREEN)
                .append(Component.text(clicked.getName()).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD))
                .append(Component.text("!").color(NamedTextColor.GREEN)));
    }

    private ItemStack pickpocket(Player clicked) {
        ItemStack item;

        List<ItemStack> contents = new ArrayList<>();
        for(ItemStack i : clicked.getInventory().getContents()) {
            if(i != null && i.getType() != Material.AIR) {
                contents.add(i.clone());
            }
        }
        if(contents.size() == 0) {
            return null;
        }
        int random = ThreadLocalRandom.current().nextInt(0, contents.size());

        item = contents.get(random);
        if(item == null || item.getType() == Material.AIR) {
            System.out.println("Item is null!");
            return null;
        }

        // find the item in the inventory and remove it
        for(ItemStack i : clicked.getInventory().getContents()) {
            if(i != null && i.equals(item)) {
                System.out.println("Found item in inventory! " + i);
                i.setAmount(0);
                break;
            }
        }

        System.out.println("Returning: " + item);
        return item;
    }
}
