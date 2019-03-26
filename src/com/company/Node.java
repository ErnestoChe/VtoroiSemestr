package com.company;

class Node
{
    public int iData; // Данные, используемые в качестве ключа
    public double dData; // Другие данные
    public Node leftChild; // Левый потомок узла
    public Node rightChild; // Правый потомок узла
    public Node parent;

    public void displayNode() // Вывод узла
    {
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
        System.out.println();
        System.out.println("parent " + (parent != null ? parent.iData : "null"));
        System.out.println("left " + (leftChild != null ? leftChild.iData : "null"));
        System.out.println("right " + (rightChild != null ? rightChild.iData : "null"));
        System.out.println();

    }
}