package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.db.HibernateConfig;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{

    public enum StorageType {
        JSON,
        JDBC,
        HIBERNATE
    }

    public static void main(String[] args) {
        Console console = null;
        Scanner sc = new Scanner(System.in);
        String command;
        System.out.print("Which repository do you want to use: ");
        command = sc.nextLine();
        System.out.print("\n\n");

        switch(command) {
            case "json" ->  console = new Console(StorageType.JSON);
            case "jdbc" -> console = new Console(StorageType.JDBC);
            case "hibernate" -> console = new Console(StorageType.HIBERNATE);
            default -> System.out.println("Invalid argument.");
        }

        if(console != null) {
            while(console.getStatus() == Console.Status.ONGOING) {
                command = sc.nextLine();
                if(!command.isEmpty()) {
                    console.readCommand(command);
                }
            }
        }

    }
}