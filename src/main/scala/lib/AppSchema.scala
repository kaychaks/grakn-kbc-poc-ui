package lib

import sangria.marshalling.{
  CoercedScalaResultMarshaller,
  FromInput,
  ResultMarshaller
}
import sangria.schema.{
  Action,
  Argument,
  EnumType,
  EnumValue,
  Field,
  InputField,
  InputObjectType,
  InputType,
  ListType,
  ObjectType,
  OptionInputType,
  OptionType,
  Schema,
  StringType,
  fields
}

trait AppSchema[C] {

  lazy val gId: ObjectType[C, ID[_]] = ObjectType(
    "id",
    fields = fields[C, ID[_]](
      Field(
        "value",
        fieldType = StringType,
        resolve = _.value.value
      )
    )
  )

  lazy val gInputId: InputObjectType[ID[_]] = InputObjectType[ID[_]](
    "inputId",
    fields = List(
      InputField("value", StringType)
    )
  )

  implicit def idFromInput: FromInput[ID[_]] =
    new FromInput[ID[_]] {
      override def fromResult(node: marshaller.Node): ID[_] =
        ID(node.toString)

      override val marshaller: ResultMarshaller =
        CoercedScalaResultMarshaller.default
    }

  def idArg(n: String) =
    Argument(n, gInputId)

  def idOptArg(n: String): Argument[Option[ID[_]]] =
    Argument(n, OptionInputType(gInputId))

  lazy val gState = EnumType(
    "SkillState",
    values = List(
      EnumValue("PROBABLE", value = SkillState.PROBABLE),
      EnumValue("POSSIBLE", value = SkillState.POSSIBLE),
      EnumValue("DEMONSTRATED", value = SkillState.DEMONSTRATED),
      EnumValue("REFUTED", value = SkillState.REFUTED)
    )
  )

  def idField[T <: Base[T]]: Field[C, T] =
    Field("id", fieldType = gId, resolve = _.value.id)

  def stringField[T](l: String, f: T => Action[C, String]): Field[C, T] =
    Field(l, fieldType = StringType, resolve = ctx => f(ctx.value))

  def stringOptField[T](l: String,
                        f: T => Action[C, Option[String]]): Field[C, T] =
    Field(l, fieldType = OptionType(StringType), resolve = ctx => f(ctx.value))

  lazy val gLevel: ObjectType[C, Level] = ObjectType(
    "level",
    fields = fields[C, Level](
      idField[Level],
      Field(
        "name",
        fieldType = StringType,
        resolve = _.value.name
      )
    )
  )

  lazy val gSkill: ObjectType[C, Skill] = ObjectType(
    "skill",
    fields = fields[C, Skill](
      idField[Skill],
      stringField("name", _.name),
    )
  )

  lazy val stateOptArg = Argument("havingState", OptionInputType(gState))

  lazy val gStudent: ObjectType[C, Student] = ObjectType(
    "student",
    fields = fields[C, Student](
      idField[Student],
      stringField("firstname", _.firstname),
      stringOptField("lastname", _.lastname),
//      Field(
//        "skills",
//        fieldType = ListType(gSkill),
//        arguments = idOptArg("atLevel") :: stateOptArg :: Nil,
//        resolve = ctx => ctx.value.skills(ctx.arg("id"), ctx.arg("havingState"))
//      )
    )
  )

  lazy val gQuery = ObjectType(
    "query",
    fields[C, Unit](
//      Field(
//        "levels",
//        ListType(gLevel),
//        resolve = ctx => ctx.ctx.queryAPI.getLevels
//      ),
//      Field(
//        "students",
//        ListType(gStudent),
//        resolve = ctx => ctx.ctx.queryAPI.getStudents
//      )
    )
  )

  val schema = Schema(gQuery)
}
