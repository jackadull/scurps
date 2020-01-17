package scurps.meta.math

import scurps.meta.data.CP
import scurps.meta.data.Score.IntScore

trait Multiply[-T1,-T2,+R] {def multiply(lhs:T1, rhs:T2):R}
object Multiply {
  implicit val multiplyCPWithIntScore:Multiply[CP,IntScore,CP] = {(lhs, rhs) => CP(lhs.intValue * rhs.intValue)}
  implicit val multiplyIntScoreWithCP:Multiply[IntScore,CP,CP] = {(lhs, rhs) => CP(lhs.intValue * rhs.intValue)}
}
