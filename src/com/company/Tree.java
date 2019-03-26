package com.company;

public class Tree {
    Node root;

    public Node find(int key) // Поиск узла с заданным ключом
    { // (предполагается, что дерево не пустое)
        Node current = root; // Начать с корневого узла
        while(current.iData != key) // Пока не найдено совпадение
        {
            if(key < current.iData) // Двигаться налево?
                current = current.leftChild;
            else // Или направо?
                current = current.rightChild;
            if(current == null) // Если потомка нет,
                return null; // поиск завершился неудачей
        }
        return current; // Элемент найден
    }

    public void insert(int id, double dd)
    {
        Node newNode = new Node(); // Создание нового узла
        newNode.iData = id; // Вставка данных
        newNode.dData = dd;
        if(root==null) // Корневой узел не существует
            root = newNode;
        else // Корневой узел занят
        {
            Node current = root; // Начать с корневого узла
            Node parent;
            while(true) // (внутренний выход из цикла)
            {
                parent = current;
                if(dd < current.dData) // Двигаться налево?
                {
                    current = current.leftChild;
                    if(current == null) // Если достигнут конец цепочки,
                    { // вставить слева
                        parent.leftChild = newNode;
                        newNode.parent = parent;
                        return;
                    }
                }
                else // Или направо?
                {
                    current = current.rightChild;
                    if(current == null) // Если достигнут конец цепочки,
                    { // вставить справа
                        parent.rightChild = newNode;
                        newNode.parent = parent;
                        return;
                    }
                }
            }
        }
    }


    public void print(Node startNode){
        if(startNode != null){//условие сработает, когда мы достигним конца дерева и потомков не останется
            print(startNode.leftChild);//рекурсивно вызываем левых потомков
            startNode.displayNode();//вызов метода принт
            print(startNode.rightChild);//вызов правых
        }
    }
}
