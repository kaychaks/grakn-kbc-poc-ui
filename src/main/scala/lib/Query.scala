package lib

import cats.Monad

import scala.language.higherKinds

trait Query[+A] {
  def run[F[+ _]: Monad: ModelConsAlg](F: QueryAlg[F])(R: Repository[F]): F[A]
}

object Query {
  def skills(id: Either[ID[Student], ID[Knowledge_Field]])(
      atLevel: Option[ID[Level]],
      havingState: Option[List[SkillState.Value]]): Query[List[Skill]] =
    new Query[List[Skill]] {
      override def run[F[+ _]: Monad: ModelConsAlg](F: QueryAlg[F])(
          R: Repository[F]): F[List[Skill]] =
        id match {
          case Left(iSt)  => F.skills(atLevel, havingState)(iSt)
          case Right(iKf) => F.skills(iKf)
        }
    }

  def state(id: ID[Skill])(
      forAgent: Student,
      atLevel: ID[Level]): Query[Option[SkillState.Value]] =
    new Query[Option[SkillState.Value]] {
      override def run[F[+ _]: Monad: ModelConsAlg](F: QueryAlg[F])(
          R: Repository[F]): F[Option[SkillState.Value]] =
        F.state(forAgent, atLevel)(id)
    }

  def owners(id: ID[Skill])(atLevel: Option[ID[Level]]): Query[List[Student]] =
    ???

  def enables(id: ID[Skill])(
      atLevel: Option[ID[Level]]): Query[List[(Activity, Level)]] = ???

  def enabledBy(id: ID[Activity]): Query[(Skill, Level)] = ???

  def related[T <: Base[_]](id: ID[T]): Query[List[T]] = new Query[List[T]] {
    override def run[F[+ _]: Monad: ModelConsAlg](F: QueryAlg[F])(
        R: Repository[F]): F[List[T]] =
      id match {
        case id: SkillID => F.relatedSkills(id)
        case _           => implicitly[Monad[F]].pure(List())
      }
  }

  def levels: Query[List[Level]] = new Query[List[Level]] {
    override def run[F[+ _]: Monad: ModelConsAlg](F: QueryAlg[F])(
        R: Repository[F]): F[List[Level]] = {
      R.all[Level]
    }
  }

}
