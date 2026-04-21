package org.example;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        Console console = new Console();
        Scanner sc = new Scanner(System.in);
        String command;

        while(console.getStatus() == Console.Status.ONGOING)
        {
            command = sc.nextLine();
            if(!command.isEmpty())
            {
                console.readCommand(command);
            }
        }
    }
}