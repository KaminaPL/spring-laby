package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    public static void main(String[] args)
    {
        Authentication auth = new Authentication();
        User user;
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            user = auth.authenticate(sc.nextLine(), sc.nextLine());

            if(user.getLogin() != null)
            {
                break;
            }
        }

        Console console = new Console(user);
        String command;

        System.out.println("Hello " + user.getLogin() + "!\n");

        while(console.getStatus() == Console.Status.ONGOING)
        {
            command = sc.nextLine();
            console.readCommand(command.split(" "));
        }
    }
}