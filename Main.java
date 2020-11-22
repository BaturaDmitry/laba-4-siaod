package com.company;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static ArrayList<Operators> operators = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //добавим в список операторы и их приоритеты
        List<String> lines = Files.readAllLines(new File("C:\\Users\\37529\\IdeaProjects\\siaod 4\\src\\com\\company\\operators.txt").toPath());
        for (String s : lines) {
            operators.add(new Operators(s.charAt(0), Integer.parseInt(String.valueOf(s.charAt(2)))));
        }

        System.out.println("Введите инфиксное выражение:");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        post(input);
        pre(input);
    }

    private static char[] process(String input) {
        ArrayList<Character> result = new ArrayList<>();
        //создаем стек для обработки операторов
        Stack stack = new Stack();
        for (int i = 0; i < input.length(); i++) {
            //если рассматриваемый символ является операндом, то мы выводим его без занесения чего-либо в стек
            if (Character.isLetter(input.charAt(i))) {
                result.add(input.charAt(i));
                continue;
            }

            char operat = input.charAt(i);
            int j=0;
            //находим номер оператора в списке
            //так будет легче получать приоритет оператора из списка
            //достаточно просто будет обратиться по номеру оператора
            //в стек также будем заносить номер оператора из списка
            for (; j < operators.size(); j++) {
                if (operators.get(j).getSymbol() == operat) break;
            }

            if (stack.isEmpty()) {
                stack.push(j);
                continue;
            }
            //появление закрывающей скобки вызывает
            //выталкивание всех знаков до ближайшей открывающей скобки включительно
            //скобки взаимно уничтожаются и в выходную строку не записываются
                if (operators.get(j).getSymbol() == ')') {
                    while (operators.get(stack.showTop()).getSymbol() != '(') result.add(stack.pop());
                    stack.delete();
                    continue;
                }

             //значение приоритета очередного символа сравнивается с приоритетом верхнего элемента стека
            //если приоритет входного знака равен нулю или больше приоритетного знака
            // ,находящегося в вершине стека, то новый знак добавляется к вершине стека

            if (operators.get(j).getPriority() == 0 || operators.get(j).getPriority() > operators.get(stack.showTop()).getPriority()) {
                stack.push(j);
            } else {
              //иначе из стека вытаткивается и переписывается в выходную строку знак,находящейся в вершине,
                //выталкиваются следующие за ним знаки с приоритетами большими или равными приоритету входного знака
                while (!stack.isEmpty() && operators.get(stack.showTop()).getPriority() >= operators.get(j).getPriority()) {
                    result.add(stack.pop());
                }
                //входной знак добавляется к вершине стека
                stack.push(j);
            }

        }
        //по завершении просмотра всех символов происходит выталкивание оставшихся
        // в стеке символов и добавление их к выходной строке
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        char res[] = new char[result.size()];
        for (int i = 0; i < result.size(); i++) res[i] = result.get(i);
        return res;
    }

    private static void post(String input) {
        System.out.print("Постфиксное выражение: ");
        char res[] = process(input);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i]);
        }
        System.out.println();
    }


    //для получения префиксного выражения
    //перепишем исходное выражение справа налево(необходимо учесть поворот круглых скобок)
    //затем преобразуем аналогично постфиксному
    //результат перепишем справа налево
    private static void pre(String input) {
        int l = input.length();
        char[] str = new char[l];
        for (int i = 0, j=l-1; i <l; i++,j--) {
            if (input.charAt(j) == '(') {
                str[i] = ')';
                continue;
            }
            if (input.charAt(j) == ')') {
                str[i] = '(';
                continue;
            }
            str[i] = input.charAt(j);
        }
        System.out.print("Префиксное выражение: ");
        char[] res = process(String.valueOf(str));
        for (int i = res.length - 1; i >= 0; i--) System.out.print(res[i]);
        System.out.println();
    }
}