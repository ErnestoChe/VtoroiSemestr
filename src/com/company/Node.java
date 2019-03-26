package com.company;

class Node
{
    public int iData; // Данные, используемые в качестве ключа
    public double dData; // Другие данные
    public Node leftChild; // Левый потомок узла
    public Node rightChild; // Правый потомок узла
    public Node parent;     //Родительский узел

    public void displayNode() // Вывод узла
    {
        System.out.println("data " + iData + " value " + dData);
        System.out.println("parent " + (parent != null ? parent.iData : "null"));
        System.out.println("left " + (leftChild != null ? leftChild.iData : "null"));
        System.out.println("right " + (rightChild != null ? rightChild.iData : "null"));
        System.out.println();

    }
}