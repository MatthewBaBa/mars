package com.wy

import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}

/**
 *
 * @author matthew_wu
 * @since 2021/7/14 3:05 下午
 */
object CsvLoader {
  case class Movie(name: String, running_time: Double, rating: Double)

  // 反思的点，第一立马找到错误的数据
  // 多看官方文档，不要google，直接通过ide自身的文档去看
  // 遇到失败怎么办(分割后数据少，数据多，转换失败)， 怎么排序， 怎么group
  def main(args: Array[String]): Unit = {
    val content = Source.fromURL(getClass.getResource("/movies.csv"))
    solution2(content)
  }

  // 使用Either
  def solution1(content: BufferedSource): Unit = {
    val movies = content
      .getLines()
      .map(convertToMovie(_))
      .filter(_.isRight)
      .map(_.right.get)
      .toList
    // 使用sortWith
    val top10 = movies.sortWith((m1, m2) => {
      m1.rating > m2.rating || (m1.rating == m2.rating && m1.running_time > m2.running_time)
    })
    // 使用sortBy
    val top10_1 = movies.sortBy(r => (r.rating, r.running_time))(Ordering.Tuple2(Ordering.Double.reverse, Ordering.Double.reverse)).take(10)
    // 使用sorted
    implicit val movieOrder: Movie => MovieOrder = movie => new MovieOrder(movie)
    val top10_2 = movies.sorted
    val res = top10.map(_.name).distinct
    println(res)
  }

  // 使用Option
  def solution2(content: BufferedSource): Unit = {
    val movies = content
      .getLines()
      .map(_.getOptionalMovie)
      .flatMap(identity(_))
      .toList
    val top10 = movies.sortWith((m1, m2) => {
          m1.rating > m2.rating || (m1.rating == m2.rating && m1.running_time > m2.running_time)
        }).take(10)
    val res = top10.map(_.name).distinct
    println(res)
  }

  class MovieOrder(movie: Movie) extends Ordered[Movie] {
    override def compare(that: Movie): Int = {
      if ((movie.rating > that.rating) || (movie.rating == that.rating && movie.running_time > that.running_time)) -1
      else 1
    }
  }

  def convertToMovie(str: String): Either[String, Movie] = {
    val arr = str.split(",")
    try {
      Right(Movie(arr(0), arr(1).toDouble, arr(1).toDouble))
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  // 怎么log字符
  implicit class RichString(val str: String) extends AnyVal {
    def getOptionalMovie(): Option[Movie] = {
      str.split(",") match {
        case Array(col1, col2, col3) => {
          val movie = Try(Movie(col1, col2.toDouble, col3.toDouble))
          movie match {
            case Success(value) => Option(value)
            case Failure(exception) => {
              println(str + "  " + exception.getMessage)
              None
            }
          }
        }
        case Array(col1, col2, col3, _@_*) => {
          println(col1 + "   " + col2 + "   " + col3 + "   " + str)
          None
        }
        case _ => {
          println(str)
          None
        }
      }
    }
  }

}

//  隐式转换 方法  类的区别
// () => B         => B    两者的区别