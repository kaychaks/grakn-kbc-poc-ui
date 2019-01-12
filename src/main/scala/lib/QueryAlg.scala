package lib

import scala.language.higherKinds

trait QueryAlg[F[_]] {

  def skills(atLevel: Option[ID[Level]],
             havingState: Option[List[SkillState.Value]])(id: ID[Student])(
      implicit mc: ModelConsAlg[F]): F[List[Skill]]

  def state(forAgent: Student, atLevel: ID[Level])(id: ID[Skill])(
      implicit mc: ModelConsAlg[F]): F[Option[SkillState.Value]]
  def owners(atLevel: Option[ID[Level]])(id: ID[Skill])(
      implicit mc: ModelConsAlg[F]): F[List[Student]]
  def enables(atLevel: Option[ID[Level]])(id: ID[Skill])(
      implicit mc: ModelConsAlg[F]): F[List[(Activity, Level)]]
  def relatedSkills(id: ID[Skill])(implicit mc: ModelConsAlg[F]): F[List[Skill]]
  def inField(id: ID[Skill])(implicit mc: ModelConsAlg[F]): F[Knowledge_Field]

  def enabledBy(id: ID[Activity])(
      implicit mc: ModelConsAlg[F]): F[(Skill, Level)]
  def occurredIn(id: ID[Activity])(
      implicit mc: ModelConsAlg[F]): F[List[Activity_Occurrence]]

  def skills(id: ID[Knowledge_Field])(
      implicit mc: ModelConsAlg[F]): F[List[Skill]]
}
