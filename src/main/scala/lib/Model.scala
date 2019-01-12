package lib

import scala.language.higherKinds

case class ID[T] private (value: String)

object SkillState extends Enumeration {
  val PROBABLE, POSSIBLE, DEMONSTRATED, REFUTED = Value
}

sealed trait Base[T] {
  val id: ID[T]
}

final case class Level private (id: ID[Level], name: String) extends Base[Level]

final case class Student private (id: ID[Student],
                            firstname: String,
                            lastname: Option[String])
    extends Base[Student]

final case class Skill private (id: ID[Skill], name: String) extends Base[Skill]

final case class Activity private (id: ID[Activity], name: String)
    extends Base[Activity]

final case class Knowledge_Field private (id: ID[Knowledge_Field], name: String)
    extends Base[Knowledge_Field]

final case class Activity_Occurrence private (id: ID[Activity_Occurrence],
                                        activity_source: Activity,
                                        occurrence_date: Option[String])
    extends Base[Activity_Occurrence]

// these concrete types are required to pattern match on ID, thanks to JVM type erasure
trait LevelID extends ID[Level]
trait SkillID extends ID[Skill]
trait StudentID extends ID[Student]
trait ActivityID extends ID[Activity]

trait Repository[F[_]]{
  def get[I,T <: Base[_]](id: I): F[T]
  def put[T <: Base[_]](t: Base[T]): F[T]
  def all[T <: Base[_]]: F[List[T]]
}

/*
Smart constructors algebra
 */
trait ModelConsAlg[F[_]]{

  def level(id: ID[Level], name: String): F[Level]

  def student(id: ID[Student], fn: String, ln: Option[String]): F[Student]

  def skill(id: ID[Skill], name: String): F[Skill]

  def activity(id: ID[Activity], name: String): F[Activity]

  def knowledge_field(id: ID[Knowledge_Field], name: String): F[Knowledge_Field]

  def activity_occurrence(id: ID[Activity_Occurrence],
                          as: Activity,
                          od: Option[String]): F[Activity_Occurrence]
}
