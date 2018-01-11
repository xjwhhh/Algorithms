package cn.edu.nju.software151250171.context.event_driven_simulation;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

public class CollisionSystem {
    private final static double HZ = 0.5;    // number of redraw events per clock tick

    private MinPQ<Event> pq;          // the priority queue 优先队列
    private double t  = 0.0;          // simulation clock time 模拟时钟,相当于当前时间
    private Particle[] particles;     // the array of particles 粒子数组

    /**
     * Initializes a system with the specified collection of particles.
     * The individual particles will be mutated during the simulation.
     *
     * @param  particles the array of particles
     */
    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();   // defensive copy
    }

    // updates priority queue with all new events for particle a
    // 计算与粒子a相关的所有潜在碰撞，可能是与另一个粒子，也可能是一面墙，并将相应的事件加入优先队列中
    private void predict(Particle a, double limit) {
        if (a == null) return;

        // particle-particle collisions 将与particles[i]发生碰撞的事件插入pq中
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    // redraw all particles
    private void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / HZ, null, null));
        }
    }


    /**
     * Simulates the system of particles for the specified amount of time.
     *
     * @param  limit the amount of time
     */
    public void simulate(double limit) {

        // initialize PQ with collision events and redraw event
        // 调用predict()方法来初始化每个粒子，将所有粒子和墙体以及粒子和粒子之间的潜在碰撞加入优先队列中
        pq = new MinPQ<>();
        for (int i = 0; i < particles.length; i++) {
            predict(particles[i], limit);
        }
        pq.insert(new Event(0, null, null));        // redraw event


        // the main event-driven simulation loop
        while (!pq.isEmpty()) {

            // get impending event, discard if invalidated 取出即将发生的事件(时间为t的优先级最小的事件)
            Event e = pq.delMin();
            if (!e.isValid()) continue;//如果事件无效，将它忽略
            Particle a = e.a;
            Particle b = e.b;

            // physical collision, so update positions, and then simulation clock
            // 按照直线运动轨迹使所有例子运动到时间t
            for (int i = 0; i < particles.length; i++)
                particles[i].move(e.time - t);
            t = e.time;

            // process event 更新所有参加碰撞的粒子速度
            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) redraw(limit);               // redraw event

            // update the priority queue with new collisions involving a or b
            // 预测参与碰撞的粒子在未来可能发生的碰撞，并向优先队列插入相应的事件
            predict(a, limit);
            predict(b, limit);
        }
    }


    /***************************************************************************
     *  An event during a particle collision simulation. Each event contains
     *  the time at which it will occur (assuming no supervening actions)
     *  and the particles a and b involved.
     *
     *    -  a and b both null:      redraw event 粒子与粒子相撞
     *    -  a null, b not null:     collision with vertical wall 粒子a与垂直墙体的碰撞
     *    -  a not null, b null:     collision with horizontal wall 粒子b与水平墙体的碰撞
     *    -  a and b both not null:  binary collision between a and b 重绘事件，画出所有粒子
     *
     ***************************************************************************/
    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur 事件的预计发生时间
        private final Particle a, b;       // particles involved in event, possibly null 和该事件相关的粒子
        private final int countA, countB;  // collision counts at event creation
        // 事件创建时每个粒子所参与的碰撞事件的数量。如果在将事件从优先队列中取出时该值没有发生变化，那么就可以继续模拟这个事件的发生
        // 但如果在这个事件进入优先队列和离开优先队列的这段时间内任何计数器发生了变化，这个时间就失效了，可以忽略它

        // create a new event to occur at time t involving a and b
        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) countA = a.count();
            else countA = -1;
            if (b != null) countB = b.count();
            else countB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        // has any collision occurred between when event was created and now?
        // 查看事件是否有效
        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }
}
