package cn.touna

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * Created with IntelliJ IDEA. 
  * User: lijie
  * Email:lijiewj51137@touna.cn 
  * Date: 2017/7/19 
  * Time: 15:02  
  */
abstract class Subject {
  /**
    * 注册观察者
    *
    * @param obs
    */
  def registObserver(obs: Observer): Unit

  /**
    * 注销观察者
    *
    * @param obs
    */
  def removeObserver(obs: Observer): Unit

  /**
    * 通知观察者
    */
  def notifyObserver(name: String, age: Int): Unit
}

abstract class Observer {

  /**
    * 更新方法
    *
    * @param name
    * @param age
    */
  def update(name: String, age: Int): Unit
}

class SubjectImpl extends Subject {

  //注册中心
  var list = new ListBuffer[Observer]

  /**
    * 注册观察者
    *
    * @param obs
    */
  override def registObserver(obs: Observer): Unit = {
    list += obs
  }

  /**
    * 注销观察者
    *
    * @param obs
    */
  override def removeObserver(obs: Observer): Unit = {
    list -= obs
  }

  /**
    * 通知观察者
    */
  override def notifyObserver(name: String, age: Int): Unit = {
    list.foreach(x => {
      x.update(name, age)
    })
  }
}

class ObserverImpl(val sub: Subject, var name: String = null, var age: Int = 0) extends Observer {

  //构造函数调用
  online

  /**
    * 观察者上线
    */
  def online(): Unit = {
    sub.registObserver(this)
  }

  /**
    * 观察者下线
    *
    * @return
    */
  def offline(): Unit = {
    sub.removeObserver(this)
  }

  /**
    * 更新方法
    *
    * @param name
    * @param age
    */
  override def update(name: String, age: Int): Unit = {
    this.name = name
    this.age = age
    showInfo
  }

  def showInfo(): Unit = {
    println(s"current  name: $name , age: $age")
  }

}


/**
  * 主函数入口
  */
object Obs {
  def main(args: Array[String]): Unit = {
    //创建主题
    val subject = new SubjectImpl
    //创建观察者
    val observer01 = new ObserverImpl(subject)
    val observer02 = new ObserverImpl(subject)

    subject.notifyObserver("zhangsan", 24)
    println("-------------------------")
    subject.notifyObserver("wangwu", 24)

  }
}
