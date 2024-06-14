package ru.komap.bankplugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class BankCommandExecutor implements CommandExecutor {

    static private HashMap<String, Integer> playerBalances = new HashMap<>();
    private final File balancesFile = new File("balances.json");
    private final BankPlugin plugin;

    public BankCommandExecutor(BankPlugin plugin) {
        loadBalances();
        this.plugin = plugin;
    }
    public int f;
    public int h;
    public int m;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int a;
        int b;
        int c;
        int d;
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();


        if (cmd.getName().equalsIgnoreCase("deposit")) {
            if (args.length != 1) {
                player.sendMessage("Используйте: /deposit <количество>");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("Пожалуйста, введите число для депозита.");
                return true;
            }

            if (amount <= 0) {
                player.sendMessage("Количество должно быть больше нуля.");
                return true;
            }

            ItemStack diamonds = new ItemStack(Material.DIAMOND, amount/2);

            if (player.getInventory().containsAtLeast(diamonds, amount)) {
                if (playerBalances.containsKey(playerName)){
                    f = 1;
                    player.getInventory().removeItem(diamonds);
                    a = getVariable(playerName);
                    playerBalances.remove(playerName);
                    b = a + amount;
                } else {
                    b = amount;
                    player.sendMessage(String.valueOf(b));
                    player.getInventory().removeItem(diamonds);
                }
                playerBalances.put(playerName, b);
                player.sendMessage("Вы положили " + amount + " алмазов на свой счет.");
                saveBalances();
            } else {
                player.sendMessage("У вас нет достаточного количества алмазов в инвентаре.");
            }
        } else if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (args.length != 1) {
                player.sendMessage("Используйте: /withdraw <количество>");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("Пожалуйста, введите число для снятия.");
                return true;
            }

            if (amount <= 0) {
                player.sendMessage("Количество должно быть больше нуля.");
                return true;
            }


            if (getPlayerBalance(playerName) >= amount) {
                h = 1;
                ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
                player.getInventory().addItem(diamonds);
                a = getVariable(playerName);
                b = a - amount;
                playerBalances.remove(playerName);
                playerBalances.put(playerName, b);
                player.sendMessage("Вы сняли " + amount + " алмазов со своего счета.");
                saveBalances();
            } else {
                player.sendMessage("У вас недостаточно средств на счету.");
            }
        } else if (cmd.getName().equalsIgnoreCase("transfer")) {
            if (args.length != 2) {
                player.sendMessage("Используйте: /transfer <игрок> <количество>");
                return true;
            }

            Player targetPlayer = player.getServer().getPlayer(args[0]);
            if (targetPlayer == null) {
                player.sendMessage("Игрок не найден.");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("Пожалуйста, введите число для перевода.");
                return true;
            }

            if (amount <= 0) {
                player.sendMessage("Количество должно быть больше нуля.");
                return true;
            }


            if (playerBalances.containsKey(playerName)) {
                m = 1;
                if (playerBalances.containsKey(targetPlayer.getName())) {
                    a = getVariable(playerName);
                    c = getVariable(targetPlayer.getName());
                    playerBalances.remove(playerName);
                    playerBalances.remove(targetPlayer.getName());
                    b = a - amount;
                    d = c + amount;
                }else{
                    a = getVariable(playerName);
                    playerBalances.remove(playerName);
                    b = a - amount;
                    d = amount;
                }
                playerBalances.put(playerName, b);
                playerBalances.put(targetPlayer.getName(), d);
                player.sendMessage("Вы отправили " + amount + " алмазов игроку " + targetPlayer.getName());
                targetPlayer.sendMessage("Игрок " + player.getName() + " отправил вам " + amount + " алмазов.");
                saveBalances();
            } else {
                player.sendMessage("У вас недостаточно средств на счету.");
            }
        } else if (cmd.getName().equalsIgnoreCase("balance")) {
            if (args.length != 0) {
                player.sendMessage("Используйте: /balance");
                return true;
            }


            if (playerBalances.containsKey(playerName)) {
                player.sendMessage("У вас на счету: " + getPlayerBalance(playerName));
                saveBalances();
            } else {
                player.sendMessage("Вашего баланса нет. Создайте баланс положив на него алмазы.");
            }
        }

        return true;
    }


    private void loadBalances() {
        if (balancesFile.exists()) {
            try {
                FileReader reader = new FileReader(balancesFile);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

                for (Object key : jsonObject.keySet()) {
                    String playerName = (String) key;
                    int balance = ((Long) jsonObject.get(key)).intValue();
                    playerBalances.put(playerName, balance);
                }

                reader.close();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveBalances() {
        JSONObject jsonObject = new JSONObject();
        for (String playerName : playerBalances.keySet()) {
            jsonObject.put(playerName, playerBalances.get(playerName));
        }

        try {
            FileWriter writer = new FileWriter(balancesFile);
            writer.write(jsonObject.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int getPlayerBalance(String playerName) {
        try {
            FileReader reader = new FileReader(balancesFile);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            if (jsonObject.containsKey(playerName.toString())) {
                int balance = ((Long) jsonObject.get(playerName.toString())).intValue();
                reader.close();
                return balance;
            } else {
                reader.close();
                return 0; // Если игрок не найден в файле, вернуть 0 как баланс по умолчанию или выбрать другое значение.
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return 0; // Обработать ошибку и вернуть 0 в случае проблем с чтением файла.
        }
    }
    private int getVariable(String Name) {
        int e = 0;
        if(f == 1){
            e = playerBalances.get(Name);
        }
        if(h == 1){
            e = playerBalances.get(Name);
        }
        if(m == 1){
            e = playerBalances.get(Name);
        }return e;
    }
}