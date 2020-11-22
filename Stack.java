package com.company;

public class Stack {
    public Node head = null;

    private class Node {
        //здесь оператор - это номер оператора в списке операторов
        int operator;
        Node next;

        public Node(int operator, Node next) {
            this.operator = operator;
            this.next = next;
        }
    }

    public void push(int operator) {
        Node node = new Node(operator, head);
        head = node;
    }

    public boolean isEmpty() {
        if (head == null) {
            return true;
        } else {
            return false;
        }
    }

    public char pop() {
        char a = Main.operators.get(head.operator).getSymbol();
        head = head.next;
        return a;
    }

    public void delete() {
        head = head.next;
    }

    public int showTop() {
        return head.operator;
    }
}