/*3. Есть 2 потока - тренер и спортсмен. тренер ставит задачу перед спортсменом. Когда  спортсмен выполняет задание, тренер ставит следующую задачу и т. д. Число заданий задается параметром
        На дисплей выводится номер задания и имя тренера или спортсмена.
        Не используйте любые задержки для потоков после начала их работы в виде методов sleep, yield или wait c параметром. Потоки должны выполняться строго по очереди. Неработающий поток находится в состоянии ожидания сигнала от другого потока и продолжает работу только когда такой сигнал получен.*/

import java.lang.Math;

class Task {
    int num=1;
    int numtasks;
    boolean sw = true;

    Task(int numtasks) {this.numtasks = numtasks;}

    public synchronized void tasksent() {
        if (!sw) {
            try {wait();} catch (InterruptedException e){}
        }
        System.out.println("Тренер выдал задание №" + num);
        sw=false;
        notify();
    }

    public synchronized void taskdone() {
        if (sw) {
            try {wait();} catch (InterruptedException e){}
        }
        System.out.println("Спортсмен выполнил задание № " + num);
        num++;
        sw = true;
        notify();
    }
}

class Trener extends Thread {
    Task task;
    Trener(Task task) {this.task = task;}

    public void run() {
        for (int i=0;i<task.numtasks;i++)
            task.tasksent();
    }
}

class Sportsmen extends Thread {
    Task task;
    Sportsmen(Task task) {this.task = task;}

    public void run() {
        for (int i=0;i<task.numtasks;i++)
            task.taskdone();
    }
}

public class lab_3 {
    public static void main(String[] args) throws Exception {
        Task P1 = new Task(5);
        Trener S1 = new Trener(P1);
        Sportsmen R1 = new Sportsmen(P1);
        S1.start();
        R1.start();
    }
}
