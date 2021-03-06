package com.itzixue.service;

public interface TestTransService {

    /**
     *事务传播 Propagation
     *  REQUIRED:（增删改）  使用当前的事务  如果没有事务则自己新建一个事务 子方法是必须运行在一个事务当中的
     *             如果存在事务就加入成为一个整体
     *             领导有饭吃，分我一点吃，领导没饭吃，我自己去买了吃
     *  SUPPORTS: （查）  如果当前有事务则使用，如果当前没有事务则不使用.
     *              领导有饭吃我就有，没饭就没有
     *  MANDATORY:  该传播属性强制必须存在一个事务，如果不存在就抛出异常
     *              领导必须管饭，不给就不干了
     *  REQUIRED_NEW: 如果当前有事务则挂起事务并且自己创建一个新的事务给自己使用
     *                  如果当前没有事务，则同REQUIRED
     *                  领导有饭吃，我偏不要，我自己去买了吃
     *  NOT_SUPPORTS: 如果当前存在事务则挂起，自己不使用数据库操作
     *      领导有饭吃，分我一点，我不吃
     *  NEVER: 如果当前存在事务就抛出异常
     *  NESTED: 如果当前有事务则开启子事务(嵌套事务)，嵌套事务是独立提交或者回滚
     *          如果当前没有事务  则同REQUIRED
     *          但是如果主事务提交，则会携带子事务提交
     *          如果主事务回滚，则子事务也会回滚 相反，如果子事务异常，父事务可以回滚或者不回滚
     */
}
